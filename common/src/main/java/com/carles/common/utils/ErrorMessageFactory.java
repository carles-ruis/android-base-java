package com.carles.common.utils;

import com.carles.common.R;

public class ErrorMessageFactory {

    public int getErrorMessageId(Throwable e) {
        return R.string.error_server_response;
    }
}
