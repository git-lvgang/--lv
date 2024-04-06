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

-- insert into city (name, state, country) values ('San Francisco', 'CA', 'US');
-- insert into hotel(city, name, address, zip) values (1, 'Conrad Treasury Place', 'William & George Streets', '4001')
INSERT INTO SYS_PARAM (PARAM_CODE, PARAM_NAME, MAINTAIN_FLAG,REMARK)VALUES ('TEST_CODE','测试数据','1','123456789');
INSERT INTO SYS_PARAM (PARAM_CODE, PARAM_NAME, MAINTAIN_FLAG,REMARK)VALUES ('TEST_CODE1','lxy','1','123456789');
INSERT INTO SYS_PARAM_ITEMS (PARAM_ITEM_ID,PARAM_CODE, PARAM_ITEM, PARAM_ITEM_NAME) VALUES ('1','TEST_CODE','0','测试子项数据');
insert into SYS_MESSAGE(USER_ID, IMPORTANCE, MESSAGE_SUBJECT,MESSAGE_CONTENT, CREATE_TIME, UPDATE_TIME) values (1, '1', 'topic', 'content', '2021-01-18 11:13:00', NOW());
INSERT INTO sys_frequently_menu_set (CUST_CODE, MENU_ID,MENU_RANK_NO,ADDRESS) VALUES ('1824002','1002','1','中国凤凰大厦');

INSERT INTO SYS_CALENDAR_EVENT (FUND_ID, EVENT_DATE, EVENT_TITLE, EVENT_DETAIL, EVENT_TYPE, EVENT_NAME, CREATE_TIME) VALUES
(1, '20210118', '安鑫1号X1320私募基金', '安鑫1号X1320私募基金', '1', '0001', '2021-01-18 11:13:00');

INSERT INTO SYS_SEARCH_IDX (ID, INDEX_NAME, INDEX_PINGYIN, INDEX_KEY, INDEX_TYPE, CREATE_TIME, UPDATE_TIME, IS_DELETED, INDEX_DETAIL) VALUES (2, '用户管理', 'yhgl', '10100100', '001', '2021-01-26 19:00:00', '2021-02-08 13:37:34', '0', '{"path":"/sys/users","menuName":"用户管理"}');

INSERT INTO SYS_SEARCH_IDX (ID, INDEX_NAME, INDEX_PINGYIN, INDEX_KEY, INDEX_TYPE, CREATE_TIME, UPDATE_TIME, IS_DELETED, INDEX_DETAIL) VALUES (5, '444测试333', '444cs333', '1', '002', '2021-01-26 19:00:00', '2021-02-08 17:39:03', '0', '{"fundId":1,"fundName":"444测试333"}');

INSERT INTO SYS_SEARCH_IDX (ID, INDEX_NAME, INDEX_PINGYIN, INDEX_KEY, INDEX_TYPE, CREATE_TIME, UPDATE_TIME, IS_DELETED, INDEX_DETAIL) VALUES (40, 'ax0001', 'ax0001', '9', '003', '2021-02-08 17:38:52', '2021-02-08 17:38:52', '0', '{"fundId":9,"fundCode":"AX0001"}');