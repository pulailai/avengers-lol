package com.yuapt.service.core.filters;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.yuapt.service.core.Constants;
import com.yuapt.service.core.hystrix.YuaptHystrixRequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jimmy on 17/1/5.
 */
public class HttpRequestHeaderFilter implements Filter {


    private final static Logger LOGGER = LoggerFactory.getLogger(HttpRequestHeaderFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    /**
     * 创建HystrixRequestContext HystrixRequestContext必须在每个请求开始时初始化，
     * 然后才能使用RequestVariable功能。这使HystrixRequestContext能够包含状态并管理
     * HystrixRequestVariableDefault对象的生命周期，这些对象提供请求作用域
     * （而不仅仅是线程作用域）变量，以便单个请求中的多个线程可以共享状态。
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("HystrixRequestContextServletFilter just supports HTTP requests");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        //获取service header
        String serverHeader = httpRequest.getHeader(Constants.SERVICE_HEADER);
        HystrixRequestContext context = null;
        try {
            if (null != serverHeader && StringUtils.isNotEmpty(serverHeader)) {
                //创建HystrixRequestContext
                context = HystrixRequestContext.initializeContext();
                HystrixRequestVariableDefault<String> hrvd = YuaptHystrixRequestContext.getInstance();
                hrvd.set(serverHeader);
            }
            else {
                LOGGER.warn("No incoming service header。");
            }

            filterChain.doFilter(httpRequest, response);
        }
        finally {
            if (null != context) context.shutdown();
        }
    }

    @Override
    public void destroy() {
    }
}
