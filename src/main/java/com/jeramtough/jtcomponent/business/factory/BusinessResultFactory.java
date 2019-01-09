package com.jeramtough.jtcomponent.business.factory;

import com.jeramtough.jtcomponent.business.bean.BusinessResult;

/**
 * Created on 2019-01-09 10:25
 * by @author JeramTough
 */
public class BusinessResultFactory {

    public static BusinessResult getBuinessResult(boolean isSuccessful, String message) {
        BusinessResult businessResult = new BusinessResult(isSuccessful, message);
        return businessResult;
    }

    public static BusinessResult getBuinessResult(boolean isSuccessful) {
        return getBuinessResult(isSuccessful);
    }

    public static BusinessResult getSuccessBuinessResult(String message) {
        return getBuinessResult(true, message);
    }

    public static BusinessResult getFailureBuinessResult(String message) {
        return getBuinessResult(false, message);
    }

}
