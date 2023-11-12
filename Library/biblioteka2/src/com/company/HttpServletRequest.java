package com.company;

public interface HttpServletRequest {
    String getMethod();
    String getRequestURI();
    String getParameter(String name);
}
