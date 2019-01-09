package com.jeramtough.jtcomponent.business.core;

import com.jeramtough.jtcomponent.business.bean.BusinessResult;

/**
 * Created on 2018-12-28 11:22
 * by @author JeramTough
 */
public interface AsyncBusinessResponse extends BaseBusinessResponse{

    /**
     * Current thread will be blocked until the business completes
     *
     */
    BusinessResult waitingBusinessResult();

    /**
     * call when the business completed.
     */
    void callCompleted(BusinessResult businessResult);

}
