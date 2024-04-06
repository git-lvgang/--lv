package cn.com.essence.icbm.sys.enums;

public enum OperationTypeEnum {
    //发起请求
    OPERATION_TYPE_SUBMIT("drafter_submit"),
    //请求通过
    OPERATION_TYPE_PASS("handler_pass"),
    //请求驳回
    OPERATION_TYPE_REJECT("handler_reject"),
    //请求废弃
    OPERATION_TYPE_ABANDON("handler_abandon"),
    //自己将流程请求废弃
    OPERATION_TYPE_DEFAULT_ABANDON("drafter_abandon");

    private String name;

    public String getName() {
        return name;
    }

    OperationTypeEnum(String name) {
        this.name = name;
    }
}
