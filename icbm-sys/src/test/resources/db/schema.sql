--
--    Copyright 2015-2019 the original author or authors.
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--       http://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

CREATE ALIAS if not exists find_in_set FOR "H2ExtendedFunction.findInSet";
CREATE ALIAS if not exists UNIX_TIMESTAMP FOR "H2ExtendedFunction.unixTimestamp";
CREATE ALIAS if not exists FROM_UNIXTIME FOR "H2ExtendedFunction.fromUnixtime";

drop table if exists sys_user;
create table sys_user
(
	ID bigint auto_increment comment '用户ID'
		primary key,
	USERNAME varchar(64) not null comment '用户账号',
	USER_TYPE char not null comment '用户来源:0-本地用户;1-内部正式员工;2-营销人员（合同制;3-经纪人，;4-外聘人员，;5-外部人员;20-外包供应商;21-外包人员',
	PASSWD varchar(128) null comment '密码',
	SALT varchar(128) null comment '盐值',
	AVAILABLE char not null comment '用户状态  :  0禁用 1启用 2 未启用 3需要重置密码',
	HASH_PASSWD varchar(32) null comment 'hash密码',
	constraint UI_SYS_USER
		unique (USERNAME)
);

drop table if exists SYS_PARAM_ITEMS;
CREATE TABLE SYS_PARAM_ITEMS
(
  PARAM_ITEM_ID   INT AUTO_INCREMENT
  COMMENT '参数id'
    PRIMARY KEY,
  PARAM_CODE      VARCHAR(32)  NULL
  COMMENT '参数代码',
  PARAM_ITEM      VARCHAR(32)  NOT NULL
  COMMENT '参数值',
  PARAM_ITEM_NAME VARCHAR(128) NULL
  COMMENT '参数名称',
  CONSTRAINT sys_param_items_PARAM_ITEM_ID_uindex
  UNIQUE (PARAM_ITEM_ID),
  CONSTRAINT sys_param_items_PARAM_CODE_uindex
  UNIQUE (PARAM_CODE)
  );

  CREATE TABLE sys_param
(
  PARAM_CODE    VARCHAR(32)   NOT NULL
  COMMENT '参数代码'
    PRIMARY KEY,
  PARAM_NAME    VARCHAR(64)   NOT NULL
  COMMENT '参数名称',
  MAINTAIN_FLAG CHAR          NOT NULL
  COMMENT '维护标识(0-可维护,1-不可删除,2-不可修改,3-不可维护)',
  REMARK        VARCHAR(1024) NULL
  COMMENT '备注',
  CONSTRAINT sys_param_PARAM_CODE_uindex
  UNIQUE (PARAM_CODE)
);


drop table if exists SYS_MESSAGE;
-- drop table if exists hotel;

create table SYS_MESSAGE
(
	MESSAGE_ID INT auto_increment comment '消息ID',
	USER_ID INT not null comment '用户ID',
	IMPORTANCE CHAR not null comment '重要程度（1:紧急；2:重要；3:普通）',
	MESSAGE_SUBJECT VARCHAR(256) not null comment '消息主题',
	MESSAGE_CONTENT VARCHAR(2000) not null comment '消息详细内容',
	CREATE_TIME DATETIME not null comment '创建时间',
	IS_DELETED CHAR default 0 not null comment '是否已删除 （0：未删除，1：已删除）
',
	IS_READ CHAR default 0 null comment '是否已读（0:未读；1:已读）
',
	IS_TOP CHAR default 0 not null comment '是否置顶（0:未置顶；1:已置顶）
',
	UPDATE_TIME DATETIME not null comment '最后更新时间',
	constraint SYS_MESSAGE_pk
		primary key (MESSAGE_ID)
);


drop table if exists SYS_SEARCH_IDX;
create table sys_search_idx
(
    ID            int auto_increment comment '自增ID-主键'
        primary key,
    INDEX_NAME    varchar(256)     not null comment '索引全称',
    INDEX_PINGYIN varchar(256)     not null comment '索引拼音首字母',
    INDEX_KEY     varchar(256)     not null comment '索引对应键值',
    INDEX_TYPE    char(3)          not null comment '索引类型(001-菜单，002-产品全称，003-产品销售代码，004-机构)',
    CREATE_TIME   datetime         not null comment '创建时间',
    UPDATE_TIME   datetime         not null comment '更新',
    IS_DELETED    char default '0' not null comment '是否已删除 （0：未删除，1：已删除）',
    INDEX_DETAIL  varchar(512)     not null,
    constraint sys_search_idx_INDEX_TYPE_INDEX_KEY_uindex
        unique (INDEX_TYPE, INDEX_KEY)
);

create index SYS_SEARCH_IDX_INDEX_TYPE_INDEX
    on sys_search_idx (INDEX_TYPE);


drop table if exists PLC_FUND_BASE;
create table PLC_FUND_BASE
(
    FUND_ID              int auto_increment comment '产品ID'
        primary key,
    ASSM_BUSI_TYPE       varchar(2)    null comment '资管业务类型
1公募大集合；
2大集合；
3集合资产管理计划；
4单一资产管理计划；
5资产支持证券',
    SALE_CODE            varchar(32)   null comment '产品销售代码',
    FUND_CODE            varchar(16)   null comment '产品备案编码',
    FUND_NUM             varchar(32)   null comment '内部代码',
    FUND_FULLNAME        varchar(100)  null comment '产品全称',
    FUND_NAME            varchar(32)   null comment '产品简称',
    FUND_TYPE            varchar(2)    null comment '产品类型
1权益类；
2固定收益类；
3商品及金融衍生品类；
4混合类；
99不适用',
    STRA_TYPE            varchar(32)   null comment '策略类型：多选，逗号分隔
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
99-其他型',
    MANG_TYPE            varchar(2)    null comment '管理类型
1-主动管理；
2-被动管理',
    FUND_OPERATION       varchar(2)    null comment '运作方式
1-定期开放式运作；
2-封闭式运作；
99-其他',
    FUND_OPERATION_OTHER varchar(32)   null comment '其他运作方式',
    FUND_RISK_GRADE      varchar(2)    null comment '产品风险等级
字典项有：R1（低风险）；R2（中低风险）；R3（中等风险）；R4（中高风险）；R5（高风险）',
    AGMT_TYPE            varchar(2)    null comment '合同类型
字典项有：
1-电子合同；
2-纸质合同',
    REGISTER_DATE        date          null comment '产品成立日',
    RECORD_SUCC_DATE     date          null comment '备案完成日',
    MATU_DATE            date          null comment '产品到期日',
    END_DATE             date          null comment '产品终止日',
    DURATION             int           null comment '存续期限（月）',
    REGISTER_ORG         varchar(2)    null comment '登记机构，字典项：
1中登；
2自建',
    CLEA_MODE            varchar(2)    null comment '结算模式
字典项有：
1-券商结算模式；
2-托管人结算模式',
    SALE_MODE            varchar(2)    null comment '销售方式
字典项有：
1-直销；
2-安信代销；
3-第三方机构代销',
    IS_FOFMOM_FUND       varchar(2)    null comment 'FOF（MOM）
字典项有：
0-否；
1-FOF；
2-MOM',
    FUND_STRUCTURE       varchar(2)    null comment '产品结构
字典项有：
1-分级；
2-非分级',
    LEV_MODE             varchar(2)    null comment '分级模式
字典项有：
1结构化分级；
2财务分级；',
    LEV_STD_DESC         varchar(32)   null comment '分级标准描述',
    FUND_LEVER           decimal(9, 6) null comment '产品初始杠杆比例',
    REMARK               varchar(32)   null comment '特殊信息备注说明',
    MANAGER_ID           varchar(32)   null comment '投资经理',
    MANAGER_DESC         varchar(256)  null comment '投资经理简介',
    OPEATOR              varchar(32)   null comment '操作人',
    OPDATE_TIME          datetime      null comment '操作时间',
    CREATOR              varchar(32)   null comment '创建人',
    CREATE_TIME          datetime      null comment '创建时间'
);

drop table if exists sys_frequently_menu_set;
CREATE TABLE sys_frequently_menu_set
(
  id           INT AUTO_INCREMENT
    PRIMARY KEY,
  CUST_CODE    VARCHAR(16)  NOT NULL
  COMMENT '客户代码',
  MENU_ID      VARCHAR(16)  NOT NULL
  COMMENT '菜单id',
  MENU_NAME    VARCHAR(32)  NULL
  COMMENT '菜单名称',
  MENU_RANK_NO VARCHAR(16)  NOT NULL
  COMMENT '菜单序号',
  ADDRESS      VARCHAR(256) NULL
  COMMENT '地址'
);

DROP TABLE IF EXISTS sys_calendar_event;
create table sys_calendar_event
(
    EVENT_ID     int auto_increment comment '事件ID
'
        primary key,
    FUND_ID      int           not null comment '产品ID',
    EVENT_DATE   char(8)       not null comment '事件日期YYYYMMDD',
    EVENT_TITLE  varchar(256)  not null comment '事件标题',
    EVENT_DETAIL varchar(2000) not null comment '事件详情',
    EVENT_TYPE   char          not null comment '事件类别(1:产品事件，2:运营事件)',
    EVENT_NAME   char(4)       not null comment '事件名称编码(对应关系查看字典表)',
    CREATE_TIME  datetime      not null comment '创建时间
'
);

DROP TABLE IF EXISTS sys_notice;
create table sys_notice
(
    NOTICE_ID        int auto_increment comment '通知ID'
        primary key,
    NOTICE_TYPE      char             not null comment '通告类型（1:运营服务类；2:监管法规类；
3:公司发文；4:内部通知；5:其它）',
    IMPORTANCE       char             not null comment '重要程度（1:紧急；2:重要；3:普通）',
    NOTICE_TITLE     varchar(256)     not null comment '通告标题',
    NOTICE_CONTENT   varchar(2000)    null comment '通告详细内容',
    NOTICE_SUMMARY   varchar(256)     null comment '通告内容摘要',
    NOTICE_APPENDIX  varchar(256)     null comment '通告附件',
    IS_DELETED       char default '0' null comment '是否已删除 （0：未删除，1：已删除）
',
    NOTICE_SENDER_ID int              null comment '通告发送人ID',
    PUBLISHED_TIME   datetime         not null comment '发布时间',
    NOTICE_GROUP     char default '1' not null comment '通告群体（1：所有人；2：指定角色；3：指定用户）',
    NOTICE_STATUS    char default '1' not null comment '通告状态（1：未发布；2：已发布；3.已撤销）',
    EXPIRY_DATE      datetime         null comment '有效截止日期',
    ROLE_LIST        varchar(256)     null comment '角色ID列表',
    USER_LIST        varchar(1024)    null comment '用户ID列表'
);

DROP TABLE IF EXISTS sys_user_notice_status;
create table sys_user_notice_status
(
    NOTICE_ID   int              not null comment '通告ID',
    USER_ID     int              not null comment '用户ID',
    IS_READ     char default '0' not null comment '是否已读（1:已读；0:未读）',
    IS_TOP      char default '0' not null comment '是否置顶（1:已置顶；0:未置顶）
',
    UPDATE_TIME datetime         not null comment '最后更新时间',
    primary key (NOTICE_ID, USER_ID)
);
-- create table city (id int primary key auto_increment, name varchar, state varchar, country varchar);
-- create table hotel (city int, name varchar, address varchar, zip varchar);


