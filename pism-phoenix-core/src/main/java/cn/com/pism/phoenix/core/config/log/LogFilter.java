package cn.com.pism.phoenix.core.config.log;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(-101)
@RequiredArgsConstructor
public class LogFilter implements Filter {

    private static final String TRACE_ID = "traceId";
    private static final String USER_ID = "userId";
    private static final String TRACE_HEADER = "X-Trace-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        try {
            // 1. 获取或生成 traceId (优先从请求头获取，实现跨服务链路追踪)
            String traceId = req.getHeader(TRACE_HEADER);
            if (traceId == null || traceId.isEmpty()) {
                traceId = IdUtil.getSnowflakeNextIdStr();
            }

            // 2. 获取 userId (根据你的业务逻辑修改，如从 JWT、Session 或特定 Header 获取)
            String userId = getUserIdFromRequest(req);

            // 3. 注入 MDC
            MDC.put(TRACE_ID, traceId);
            MDC.put(USER_ID, userId);

            // 4. 将 traceId 写入响应头，方便前端反馈问题
            resp.setHeader(TRACE_HEADER, traceId);

            chain.doFilter(request, response);
        } finally {
            // 5. 必须清除 MDC，防止线程池污染
            MDC.clear();
        }
    }

    private String getUserIdFromRequest(HttpServletRequest request) {
        try {
            return StpUtil.getLoginIdAsString();
        } catch (Exception e) {
            return "";
        }
    }
}