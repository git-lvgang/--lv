package cn.com.essence.icbm.sys.enums;

import lombok.Getter;

public enum MarketEnum {
    MARKET_TYPE_SZSE(1, "SZSE", "深交所", ".SZ"),
    MARKET_TYPE_SSE(2, "SSE", "上交所", ".SH"),
    MARKET_TYPE_HKEX(3, "HKEX", "港交所", ".HK"),
    MARKET_TYPE_STAS(4, "STAS", "新三板", ".NQ");
    /**
     * ID
     */
    @Getter
    private final int dictOrd;
    /**
     * 代码
     */
    @Getter
    private final String itemCode;
    /**
     * 名称
     */
    @Getter
    private final String itemName;

    /**
     * 后缀
     */
    @Getter
    private final String itemSuffix;


    MarketEnum(int dictOrd, String itemCode, String itemName, String itemSuffix) {
        this.dictOrd = dictOrd;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemSuffix = itemSuffix;
    }

    public static String getSuffix(String market, String secuCode) {
        for (MarketEnum marketEnum : MarketEnum.values() ) {
            if (marketEnum.getItemCode().equals(market)
                    || marketEnum.getItemName().equals(market)) {
                return secuCode + marketEnum.getItemSuffix();
            }
        }
        return null;
    }
}
