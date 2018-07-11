package com.carles.common.utils;

import com.carles.common.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ErrorMessageFactoryTest {

    private ErrorMessageFactory errorMessageFactory = new ErrorMessageFactory();

    @Test
    public void getErrorMessageId_shouldReturnMessage() {
        assertEquals(R.string.error_server_response, errorMessageFactory.getErrorMessageId(new Throwable()));
    }

}
