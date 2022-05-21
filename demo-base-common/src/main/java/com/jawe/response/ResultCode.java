package com.jawe.response;

public enum ResultCode implements CustomizeResultCode {

    /* 成功 */
    SUCCESS(200, "成功"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    /* 商品错误 */
//    USER_ACCOUNT_EXPIRED(2012, "账号已过期"),
//    USER_CREDENTIALS_ERROR(2013, "密码错误"),
//    USER_CREDENTIALS_EXPIRED(2014, "密码过期"),
//    USER_ACCOUNT_DISABLE(2015, "账号不可用"),
//    USER_ACCOUNT_LOCKED(2016, "账号被锁定"),
    GOODS_NOT_EXIST(2017, "商品不存在"),
    GOODS_ALREADY_EXIST(2018, "商品已存在"),

    /* 秒杀商品错误 */
    FLASH_NOT_EXIST(2019, "秒杀商品不存在"),
    FLASH_ALREADY_EXIST(2020, "秒杀商品已存在"),
    FLASH_ALREADY_OVER(2021, "秒杀商品已结束不能删除"),

    /* 订单错误 */
    GOODS_NOT_STOCK(2022,"商品库存不足"),



    /* 部门错误 */
    DEPARTMENT_NOT_EXIST(3007, "部门不存在"),
    DEPARTMENT_ALREADY_EXIST(3008, "部门已存在"),


    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),

    /*运行时异常*/
    ARITHMETIC_EXCEPTION(9001, "算数异常");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }




}
