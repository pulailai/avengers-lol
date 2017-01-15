package com.yuapt.common;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by jimmy on 17/1/5.
 */
public class RequestFilter121 implements Filter {


    private static Logger log = LoggerFactory.getLogger(RequestFilter121.class);


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

        System.out.println("asdasljdlkajldkjaskl;jdkl;asjdl;jals;dk");
        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {
    }
}
