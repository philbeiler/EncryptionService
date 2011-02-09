package com.beilers.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SimpleSpringEnabledServlet extends HttpServlet {

    private static final long  serialVersionUID = 1L;

    private HttpRequestHandler target;

    @Override
    public void init() throws ServletException {

        final WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

        final String beanName = getServletConfig().getInitParameter("springBeanName");

        this.target = (HttpRequestHandler) wac.getBean(beanName);

    }

    @Override
    protected void service(final HttpServletRequest request, //
                           final HttpServletResponse response) throws ServletException, IOException {
        this.target.handleRequest(request, response);
    }

}
