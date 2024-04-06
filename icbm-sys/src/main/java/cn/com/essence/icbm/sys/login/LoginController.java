package cn.com.essence.icbm.sys.login;

import cn.com.essence.icbm.sys.bean.vo.CasValidateReqVo;
import cn.com.essence.icbm.sys.bean.vo.UserValidateReqVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.exception.WefaBaseException;
import cn.com.essence.wefa.core.util.WebUtils;
import cn.com.essence.wefa.rbac.UserLoginRedisConstant;
import cn.com.essence.wefa.rbac.common.RSAPublicKeyService;
import cn.com.essence.wefa.shiro.codec.AesUtils;
import cn.com.essence.wefa.util.DrawIdentifyImgUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RSAPublicKeyService rsaPublicKeyService;

    @Autowired
    private StringRedisTemplate template;


    @GetMapping("/rsa")
    public CommonRspVo getRSAPublicKey() {
        return rsaPublicKeyService.generateRSAKeyPair();
    }

    @GetMapping("/timestamp")
    public Long getTimestamp() {
        return System.currentTimeMillis();
    }

    @PostMapping("/login")
    public CommonRspVo login(@RequestBody UserValidateReqVo user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ip = WebUtils.getIpAddr(request);
        user.setClientIp(ip);
        CommonRspVo rsp = null;
        try {
            rsp = loginService.validate(user, response);
        } catch (WefaBaseException e) {
            rsp = new CommonRspVo(e.getCode(), e.getMsg());
        }
        // 私钥过期的情况不加密
        // 这种情况没办法拿到客户端上传过来的AES秘钥
        if (rsp.getRtnCode() != ResultCode.C_PRI_KEY_EXPIRE.getCode()) {
            String json = JSON.toJSONString(rsp);
            String chipter = AesUtils.encrypt(json, user.getKey());
            rsp = new CommonRspVo(ResultCode.C_SUCCESS);
            rsp.setData(chipter);
        }
        return rsp;
    }

    @PostMapping("/login/cas")
    public CommonRspVo login(@RequestBody() CasValidateReqVo casReqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ip = WebUtils.getIpAddr(request);
        casReqVo.setClientIp(ip);

        CommonRspVo rsp = null;
        try {
            rsp = loginService.validate(casReqVo, response);
        } catch (WefaBaseException e) {
            rsp = new CommonRspVo(e.getCode(), e.getMsg());
        }
        // 私钥过期的情况不加密
        // 这种情况没办法拿到客户端上传过来的AES秘钥
        if (rsp.getRtnCode() != ResultCode.C_PRI_KEY_EXPIRE.getCode()) {
            String json = JSON.toJSONString(rsp);
            String chipter = AesUtils.encrypt(json, casReqVo.getKey());
            rsp = new CommonRspVo(ResultCode.C_SUCCESS);
            rsp.setData(chipter);
        }
        return rsp;
    }

    @GetMapping("/logout")
    @RequiresUser
    public CommonRspVo logout() throws IOException {
        loginService.cleanUserCache();
        return new CommonRspVo(ResultCode.C_SUCCESS, "已登出");
    }

    @GetMapping("/menus")
    @RequiresUser
    public CommonRspVo menus() throws IOException {
        return new CommonRspVo(ResultCode.C_SUCCESS, loginService.getUserMenus());
    }

    @GetMapping("/permissions")
    @RequiresUser
    public CommonRspVo permissions() throws IOException {
        return new CommonRspVo(ResultCode.C_SUCCESS, loginService.getUserPermissions());
    }

    @GetMapping("/checkcode")
    public void checkcode(@RequestParam String codeId, HttpServletResponse respone) throws IOException {
        DrawIdentifyImgUtil idCode = new DrawIdentifyImgUtil();
        String codeStr = DrawIdentifyImgUtil.genCodeStr(4);
        BufferedImage image = idCode.drawImg(codeStr);
        try (OutputStream os = respone.getOutputStream()) {
            ImageIO.write(image, "png", os);
            template.opsForValue().set(UserLoginRedisConstant.LOGIN + ":" + codeId, codeStr, 5, TimeUnit.MINUTES);
        }
        image = null;
    }

}
