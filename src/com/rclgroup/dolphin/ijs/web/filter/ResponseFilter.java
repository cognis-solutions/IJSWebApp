package com.rclgroup.dolphin.ijs.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResponseFilter implements Filter {
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN="Access-Control-Allow-Origin";
	private static final String ACCESS_CONTROL_ALLOW_HEADERS="Access-Control-Allow-Headers";
	private static final String ACCESS_CONTROL_EXPOSE_HEADERS="Access-Control-Expose-Headers";
	private static final String ASTRIC="*";
	private static final String ORIGIN="Origin, Content-Type,Accept";
	private static final String UTF="UTF-8";
    public ResponseFilter() {}

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("inside ResponseFilter");
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req=(HttpServletRequest) request;
        HttpSession session=req.getSession();
        //resp.setContentType("application/json");
        //System.out.println("inside Response: session="+session.getId());
        resp.setCharacterEncoding(UTF);
	    resp.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ASTRIC);
	    resp.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS,ACCESS_CONTROL_ALLOW_ORIGIN);
	    resp.addHeader(ACCESS_CONTROL_ALLOW_HEADERS,ORIGIN);
//TODO below code is for development testing
       // Cookie ck=new Cookie("RCL_AUTH_KEY","DEV_TEAM~DOLPHIN1~R~R~*~***~1");//creating cookie object  
        //resp.addCookie(ck);
        chain.doFilter(request, resp);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
