package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * plc_fund_base
 * @author 
 */
@Data
public class PlcFundBase implements Serializable {
    /**
     * 产品ID
     */
    private Integer fundId;

    /**
     * 资管业务类型
1公募大集合；
2大集合；
3集合资产管理计划；
4单一资产管理计划；
5资产支持证券
     */
    private String assmBusiType;

    /**
     * 产品销售代码
     */
    private String saleCode;

    /**
     * 产品备案编码
     */
    private String fundCode;

    /**
     * 内部代码
     */
    private String fundNum;

    /**
     * 产品全称
     */
    private String fundFullname;

    /**
     * 产品简称
     */
    private String fundName;

    /**
     * 产品类型
1权益类；
2固定收益类；
3商品及金融衍生品类；
4混合类；
99不适用
     */
    private String fundType;

    /**
     * 策略类型：多选，逗号分隔
1-股票型；
2-债券型；
3-混合型；
4-货币型；
5-限定类；
6-指数型；
7-量化型；
8-QDII型；
9-FOF型；
10-保本型；
11-理财型；
12-商品及金融衍生品类；
99-其他型
     */
    private String straType;

    /**
     * 管理类型
1-主动管理；
2-被动管理
     */
    private String mangType;

    /**
     * 运作方式
1-定期开放式运作；
2-封闭式运作；
99-其他
     */
    private String fundOperation;

    /**
     * 其他运作方式
     */
    private String fundOperationOther;

    /**
     * 产品风险等级
字典项有：R1（低风险）；R2（中低风险）；R3（中等风险）；R4（中高风险）；R5（高风险）
     */
    private String fundRiskGrade;

    /**
     * 合同类型
字典项有：
1-电子合同；
2-纸质合同
     */
    private String agmtType;

    /**
     * 产品成立日
     */
    private Date registerDate;

    /**
     * 备案完成日
     */
    private Date recordSuccDate;

    /**
     * 产品到期日
     */
    private Date matuDate;

    /**
     * 产品终止日
     */
    private Date endDate;

    /**
     * 存续期限（月）
     */
    private Integer duration;

    /**
     * 登记机构，字典项：
1中登；
2自建
     */
    private String registerOrg;

    /**
     * 结算模式
字典项有：
1-券商结算模式；
2-托管人结算模式
     */
    private String cleaMode;

    /**
     * 销售方式
字典项有：
1-直销；
2-安信代销；
3-第三方机构代销
     */
    private String saleMode;

    /**
     * FOF（MOM）
字典项有：
0-否；
1-FOF；
2-MOM
     */
    private String isFofmomFund;

    /**
     * 产品结构
字典项有：
1-分级；
2-非分级
     */
    private String fundStructure;

    /**
     * 分级模式
字典项有：
1结构化分级；
2财务分级；
     */
    private String levMode;

    /**
     * 分级标准描述
     */
    private String levStdDesc;

    /**
     * 产品初始杠杆比例
     */
    private BigDecimal fundLever;

    /**
     * 特殊信息备注说明
     */
    private String remark;

    /**
     * 投资经理
     */
    private String managerId;

    /**
     * 投资经理简介
     */
    private String managerDesc;

    /**
     * 操作人
     */
    private String opeator;

    /**
     * 操作时间
     */
    private Date opdateTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}