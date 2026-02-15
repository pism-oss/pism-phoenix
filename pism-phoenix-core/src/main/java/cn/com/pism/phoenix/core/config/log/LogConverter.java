package cn.com.pism.phoenix.core.config.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.MDC;

public class LogConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        String traceId = MDC.get("traceId");
        String userId = MDC.get("userId");

        if (traceId == null && userId == null) {
            return "[-,-]";
        }
        return String.format("[%12s,%8s]",
                traceId == null ? "-" : traceId,
                userId == null ? "-" : userId);
    }
}