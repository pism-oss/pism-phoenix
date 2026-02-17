package cn.com.pism.phoenix.core.util;

import cn.com.pism.exception.PismException;
import cn.com.pism.phoenix.utils.Jackson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author perccyking
 * @since 24-08-26 15:20
 */
@Slf4j
public class SpringMvcUtil {
    private SpringMvcUtil() {
    }

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new PismException("failed to get request attributes");
        }
        return requestAttributes;
    }

    public static void writeToResponse(HttpServletResponse response, Object responseData) {
        if (response == null) {
            response = getResponse();
        }
        if (response == null) {
            log.error("non http requests");
            return;
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = response.getWriter()) {
            writer.write(Jackson.toJsonString(responseData));
            writer.flush();
        } catch (IOException e) {
            log.error("write to response error", e);
        }
    }

    public static void writeToResponse(Object responseData) {
        writeToResponse(null, responseData);
    }

}
