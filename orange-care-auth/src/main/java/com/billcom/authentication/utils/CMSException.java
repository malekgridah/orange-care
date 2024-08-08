package com.billcom.authentication.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CMSException extends Exception {
    private static final long serialVersionUID = 1L;
    private String name;
    private String errorCode;

    public CMSException(String name, String message) {
        super(message);
        this.name = name;
        this.errorCode = null;
    }

    public CMSException(String name, String message, Throwable t) {
        super(message, t);
        this.name = name;
        this.errorCode = null;
    }

    public CMSException(String name, String message, String code, Throwable t) {
        super(message, t);
        this.name = name;
        this.errorCode = code;
    }

    public String getName() {
        return this.name;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String prettyPrint() {
        return this.name + ": " + this.getMessage();
    }

    public String toLogString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("\n");
        sb.append(" code       : " + this.errorCode + "\n");
        sb.append(" name       : " + this.name + "\n");
        sb.append(" message    : " + this.getMessage() + "\n");
        sb.append(" stacktrace : ");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        this.printStackTrace(pw);
        pw.flush();
        sw.flush();
        sb.append(sw.toString());
        return sb.toString();
    }
}
