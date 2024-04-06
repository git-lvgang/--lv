package cn.com.essence.icbm.sys.dao;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-1-16
 * Description:
 * @MybatisTest会默认SpringBootApplication注解，不加这个类会导致去扫主类的注解
 * 配合dao层测试使用
 */
@SpringBootApplication
public class DaoTestApplication {
}
