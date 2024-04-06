package cn.com.essence.icbm.sys.enums;

public enum SecuPoolEnum {
    PROHIBIT_SECU_POOL_IN("IN", "发起禁止池入池申请"),

    PROHIBIT_SECU_POOL_OUT("OUT", "发起禁止池出池申请"),

    SECU_POOL_SOURCE_HANDLER("HANDLER","手动添加"),
    SECU_POOL_SOURCE_WINDCHWALL("WINDCHWALL","万德数据同步"),
    SECU_POOL_SOURCE_QUARANTINECHWALL("QUARANTINECHWALL","隔离墙数据同步")
    ;

    /**
     * 禁止池请求代码
     */
    private final String code;
    /**
     * 禁止池请求名称
     */
    private final String name;


    SecuPoolEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
