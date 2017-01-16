package com.yuapt.service.core.filters;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.yanglinkui.ab.dsl.service.ServiceLexer;
import com.yanglinkui.ab.dsl.service.ServiceParser;
import com.yanglinkui.ab.dsl.service.Statements;
import com.yuapt.service.core.Constants;
import com.yuapt.service.core.hystrix.AvengersHystrixRequestContext;
import com.yuapt.service.core.utils.AvengersHttpHeader;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
        HystrixRequestContext context = null;

        try {
            //创建HystrixRequestContext
            context = HystrixRequestContext.initializeContext();
            HystrixRequestVariableDefault<AvengersHttpHeader> hrvd = AvengersHystrixRequestContext.getInstance();
            hrvd.set(this.getInstance(httpRequest));

            filterChain.doFilter(httpRequest, response);
        }
        finally {

            if (null != context) context.shutdown();
        }

    }

    @Override
    public void destroy() {
    }


    private  AvengersHttpHeader getInstance(HttpServletRequest request) {

        //获取headers
        Map<String, String> headers = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }

        //解析 service header
        String serviceHeader = request.getHeader(Constants.SERVICE_HEADER);
        Statements s = null;
        if (null != serviceHeader && StringUtils.isNotEmpty(serviceHeader)) {
            ServiceParser p = new ServiceParser(new ServiceLexer(serviceHeader));
            s = p.statements();
        }

        return new AvengersHttpHeader(headers, s);

    }


}
