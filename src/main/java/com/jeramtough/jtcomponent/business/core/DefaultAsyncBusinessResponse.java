package com.jeramtough.jtcomponent.business.core;

import com.jeramtough.jtcomponent.business.bean.BusinessResult;

/**
 * Created on 2019-01-09 10:42
 * by @author JeramTough
 */
public class DefaultAsyncBusinessResponse implements AsyncBusinessResponse {

    public BusinessResult waitingBusinessResult() {
        return null;
    }

    public void callCompleted(BusinessResult businessResult) {

    }
}
