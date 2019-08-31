package cn.wasu.community.community.exception;

public enum CustomizaErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你找的问题不存在，请确认信息!");
    private String message;


    CustomizaErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
