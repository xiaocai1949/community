package cn.wasu.community.community.exception;

public enum CustomizaErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不存在，请确认信息!"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题进行评论，请确认信息!"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试!"),
    SYS_ERROR(2004,"服务器错误，请稍后重试!"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在，请确认信息")
    ;
    private String message;
    private Integer code;

    CustomizaErrorCode(Integer code,String message) {
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
