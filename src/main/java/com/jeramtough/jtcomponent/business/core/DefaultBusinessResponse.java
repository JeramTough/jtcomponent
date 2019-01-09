package com.jeramtough.jtcomponent.business.core;

import com.jeramtough.jtcomponent.business.bean.BusinessResult;

import java.util.Objects;

/**
 * Created on 2019-01-09 10:37
 * by @author JeramTough
 */
public class DefaultBusinessResponse implements BusinessResponse {

    private BusinessResult businessResult;

    public DefaultBusinessResponse(
            BusinessResult businessResult) {
        this.businessResult = businessResult;
    }

    public void setBusinessResult(
            BusinessResult businessResult) {
        this.businessResult = businessResult;
    }

    public BusinessResult getBusinessResult() {
        return Objects.requireNonNull(businessResult);
    }

}
