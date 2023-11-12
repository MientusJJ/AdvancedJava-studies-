package com.company;

import java.io.PrintWriter;

public interface HttpServletResponse {
    PrintWriter getWriter();
    void setContentType(String type);
    void setStatus(int sc);
}
