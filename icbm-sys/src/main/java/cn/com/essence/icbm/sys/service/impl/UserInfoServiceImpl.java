package cn.com.essence.icbm.sys.service.impl;

import cn.com.essence.icbm.sys.service.UserInfoService;
import cn.com.essence.wefa.shiro.util.ShiroUtils;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Override
    public Integer getCurrentUser() {
        Long userId = ShiroUtils.getUserId();
        if(userId == null) {
            return null;
        }
        return Math.toIntExact(userId);
    }
}
