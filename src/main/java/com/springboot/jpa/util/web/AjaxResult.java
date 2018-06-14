package com.springboot.jpa.util.web;

/**
 * Author: WangRui
 * Date: 2018/5/20
 * Describe: 返回数据，状态码，消息
 */
public class AjaxResult<W> {
    private String msg;
    private W data;
    private Integer code;

    public AjaxResult() {
        this.msg = ResultCode.SUCCESS.getMsg();
        this.code = ResultCode.SUCCESS.getCode();
        this.data = null;
    }

    public AjaxResult(W data) {
        this.msg = ResultCode.SUCCESS.getMsg();
        this.code = ResultCode.SUCCESS.getCode();
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param data
     * @param <W>
     * @return
     */
    public static <W> AjaxResult<W> success(W data) {
        return new AjaxResult<>(data);
    }

    public static <W> AjaxResult<W> success() {
        return new AjaxResult<>();
    }


    /**
     * 业务错误
     *
     * @param msg
     * @param <W>
     * @return
     */
    public static <W> AjaxResult<W> serviceError(String msg) {
        AjaxResult<W> result = new AjaxResult<>();
        result.setCode(ResultCode.SERVICE_ERROR.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <W> AjaxResult<W> parameterError(String msg) {
        AjaxResult<W> result = new AjaxResult<>();
        result.setCode(ResultCode.PARAMETER_ERROR.getCode());
        if (msg == null) {
            result.setMsg(ResultCode.PARAMETER_ERROR.getMsg());
        } else {
            result.setMsg(msg);
        }
        result.setData(null);
        return result;
    }

    /**
     * 返回错误
     *
     * @param code
     * @param <W>
     * @return
     */
    public static <W> AjaxResult<W> error(ResultCode code) {
        AjaxResult<W> result = new AjaxResult<>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        result.setData(null);
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public W getData() {
        return data;
    }

    public AjaxResult<W> setData(W data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public AjaxResult setCode(Integer code) {
        this.code = code;
        return this;
    }
}
