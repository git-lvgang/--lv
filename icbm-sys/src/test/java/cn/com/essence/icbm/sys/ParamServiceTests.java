package cn.com.essence.icbm.sys;

import cn.com.essence.icbm.sys.bean.po.SysParam;
import cn.com.essence.icbm.sys.dao.SysParamDao;
import cn.com.essence.icbm.sys.service.impl.ParamServiceImpl;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class ParamServiceTests {

    /**
     * mock对象，用于替换service里面使用的dao对象
     */
    @Mock
    private SysParamDao sysParamDao;

    /**
     * 真实的service对象
     */
    @InjectMocks
    private ParamServiceImpl service;

    @Before
    public void setup() {
        // 设置好mock行为
        Mockito.when(sysParamDao.findSysParamPage(Mockito.any())).thenReturn(new ArrayList<>());
    }

    @Test
    public void testGetSysParam() {
        SysParam req = new SysParam();
        CommonListRspVo rspVo = service.getSysParam(req);
        Assert.assertEquals(0, rspVo.getTotal());
    }
}
