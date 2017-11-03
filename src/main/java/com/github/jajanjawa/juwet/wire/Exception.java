package com.github.jajanjawa.juwet.wire;

public class Exception {
    private String cause;
    private String name;
    private String message;

    public Exception() {
    }

    public Exception(java.lang.Exception e) {
        name = e.getClass().getName();
        message = e.getMessage();

        Throwable cause = e.getCause();
        if (cause != null) {
            this.cause = cause.getMessage();
        }
    }

    public Exception(String name) {
        this.name = name;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
