package com.github.jajanjawa.juwet.util;

import com.github.jajanjawa.juwet.wire.Exception;

public interface ExceptionHandler {
    void handle(Exception e, String method, String module);
}
