package com.springboot.jpa.util.exception;


import com.springboot.jpa.util.web.ResultCode;

/**
 * Author : WangRui
 * Date : 2018/5/20
 * Describe:
 */
public class DataBaseRollBackException extends BaseServiceException {
    public DataBaseRollBackException() {
        super(ResultCode.DATABASE_ERROR);
    }
}
