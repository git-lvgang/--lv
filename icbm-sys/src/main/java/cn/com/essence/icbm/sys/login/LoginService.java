package cn.com.essence.icbm.sys.login;

import cn.com.essence.icbm.sys.bean.vo.CasValidateReqVo;
import cn.com.essence.icbm.sys.bean.vo.UserValidateReqVo;
import cn.com.essence.icbm.sys.util.BasicExceptionUtil;
import cn.com.essence.wefa.component.bean.Menu;
import cn.com.essence.wefa.component.service.MenuService;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.codec.RsaUtils;
import cn.com.essence.wefa.core.exception.ExceptionBuilder;
import cn.com.essence.wefa.iam.service.SingleSignOnService;
import cn.com.essence.wefa.rbac.UserLoginRedisConstant;
import cn.com.essence.wefa.rbac.bean.User;
import cn.com.essence.wefa.rbac.bean.UserInfo;
import cn.com.essence.wefa.rbac.common.DefultValidatorService;
import cn.com.essence.wefa.rbac.service.RoleService;
import cn.com.essence.wefa.rbac.service.UserService;
import cn.com.essence.wefa.shiro.codec.AesUtils;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.interfaces.RSAPrivateKey;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    private Logger log = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private RedisTemplate<String, byte[]> template;
    @Autowired
    private StringRedisTemplate stringTemplate;

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @Autowired
    private DefultValidatorService validator;

    @Autowired
    private SingleSignOnService singleSignOnService;

    @Autowired
    private RoleService roleService;

    private String errorHashKey = "error";


    private void addUserIfFirstLogin(Map<String, Object> attributes) {
        String employeenum = ObjectUtils.toString(attributes.get("employeenum"));// employeenum	账号	有可能会发生改变
        User user = new User();
        //添加用户
        String idNumber = ObjectUtils.toString(attributes.get("id_number"));// id_number	身份证
        String userId = ObjectUtils.toString(attributes.get("userId"));// userId	用户ID	唯一；
        String realname = ObjectUtils.toString(attributes.get("realname"));// realname	真实姓名
        String phone = ObjectUtils.toString(attributes.get("phone"));// phone	手机号
        String email = ObjectUtils.toString(attributes.get("email"));// email	邮箱
        user.setUsername(employeenum);
        user.setUserType(1);
        if (StringUtils.isNotEmpty(idNumber) && idNumber.length() > 4) {
            user.setPassword("Pwd@" + idNumber.substring(idNumber.length() - 4) + "#ax");//取身份证后四位
        } else {
            user.setPassword("Pwd@1234#ax");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setName(realname);
        userInfo.setIdCard(idNumber);
        userInfo.setOaId(userId);
        userInfo.setMobileNo(phone);
        userInfo.setEmail(email);
        user.setUserInfo(userInfo);
        CommonRspVo rsp = userService.add(user);
        if (ResultCode.C_SUCCESS.getCode() != rsp.getRtnCode()) {
            throw BasicExceptionUtil.buildRequesetFail("单点登陆成功,但初始化用户失败");
        }
        //设置默认角色
        rsp = roleService.grant(2L, user.getId());
        if (ResultCode.C_SUCCESS.getCode() != rsp.getRtnCode()) {
            throw BasicExceptionUtil.buildRequesetFail("单点登陆成功,且已初始化用户,但授予[sys_default]角色失败");
        }
    }

    /**
     * 单点登陆校验
     *
     * @param casReqVo
     * @param response
     * @return
     * @throws Exception
     */
    CommonRspVo validate(CasValidateReqVo casReqVo, HttpServletResponse response) throws Exception {
        decrypt(casReqVo);
        log.info("CAS Validate : ticket: {}, service: {}", casReqVo.getTicket(), casReqVo.getService());
        Map<String, Object> attributes = singleSignOnService.validate(casReqVo.getTicket(), casReqVo.getService());
        log.info("CAS Validate : attributes: {}", attributes);
        //检查登陆失败次数
        String userName = ObjectUtils.toString(attributes.get("employeenum"));// employeenum	账号	有可能会发生改变
        String key = UserLoginRedisConstant.LOGIN + userName;
        Optional<String> loginError = Optional.ofNullable((String) stringTemplate.opsForHash().get(key, errorHashKey));
        int loginErrorCount = Integer.parseInt(loginError.orElse("0"));
        if (loginErrorCount >= 5) {
            throw BasicExceptionUtil.buildRequesetFail("账号已经被锁定，请30分钟后重试");
        }
        User user = userService.load(userName);
        if (user != null) {
            return this.loginSuccess(user, key, response);
        }
        //首次登陆系统，需初始化用户
        addUserIfFirstLogin(attributes);
        user = userService.load(userName);
        if (user == null) {
            log.warn("user [{}] login failed", userName);
            return loginFail(loginErrorCount, key);
        }
        return this.loginSuccess(user, key, response);
    }

    /**
     * local密码登陆校验
     *
     * @param userReqVo
     * @param response
     * @return
     */
    CommonRspVo validate(UserValidateReqVo userReqVo, HttpServletResponse response) {
        decrypt(userReqVo);
        log.info("ip: {}, username: {}, codeid: {}, waitId: {}", userReqVo.getClientIp(), userReqVo.getUsername(), userReqVo.getCodeId(),
                userReqVo.getWaitId());
        String key = UserLoginRedisConstant.LOGIN + userReqVo.getUsername();
        Optional<String> loginError = Optional.ofNullable((String) stringTemplate.opsForHash().get(key, errorHashKey));
        int loginErrorCount = Integer.parseInt(loginError.orElse("0"));
        check(userReqVo, loginErrorCount);
        User user = userService.validAndReturn(userReqVo.getUsername(), userReqVo.getPassword());
        if (user == null) {
            log.warn("user [{}] login failed", userReqVo.getUsername());
            return loginFail(loginErrorCount, key);
        }
        log.info("user:{} login success", user.getUsername());
        return loginSuccess(user, key, response);
    }

    /**
     * 清理用户登陆缓存
     *
     * @return
     */
    public void cleanUserCache() {
        String userId = userService.getCurrentUserId();
        validator.removeToken(userId);
        validator.removeRoleAndPermission(userId);
        SecurityUtils.getSubject().logout();
    }

    CommonRspVo loginSuccess(User user, String key, HttpServletResponse response) {
        CommonRspVo rsp = new CommonRspVo(ResultCode.C_SUCCESS, "登录成功");
        List<Menu> menus = menuService.loadUserMenus(user.getId());
        if (menus.isEmpty()) {
            rsp.setRtnCode(ResultCode.C_NOT_PRIVILEGES, "您暂无登录权限");
            return rsp;
        }
        String uid = String.valueOf(user.getId());
        List<String> roles = roleService.getStringRoles(user.getId());
        JSONObject result = new JSONObject();
        String token = validator.getToken(uid);
        if (token == null) {
            token = validator.createToken(user.getUsername());
        }
        validator.removeRoleAndPermission(uid);
        validator.setToken(token, uid);
        result.put("menus", menus);
        result.put("user", user);
        result.put("token", token);
        result.put("permissions", validator.getStringPermissions(uid));
        result.put("roles", roles);
        stringTemplate.opsForHash().delete(key, errorHashKey);
        rsp.setData(result);
        // 静态资源使用Cookie免认证
        try {
            String val =
                    Hashing.goodFastHash(32).newHasher().putString(user.getUsername(), Charsets.UTF_8).putString(token, Charsets.UTF_8).hash().toString();
            Cookie rc = new Cookie("rcauth", Base64.getEncoder().encodeToString((uid + ":" + val).getBytes()));
            rc.setHttpOnly(true);
            rc.setPath("/");
            // rc.setMaxAge(Integer.MAX_VALUE);
            //Cookie过期时间
            rc.setMaxAge(-1);
            response.addCookie(rc);
        } catch (Exception e) {
            // ignore
        }
        return rsp;
    }

    public List<String> getUserPermissions() {
        String uid = userService.getCurrentUserId();
        return validator.getStringPermissions(uid);
    }

    public List<Menu> getUserMenus() {
        String uid = userService.getCurrentUserId();
        return menuService.loadUserMenus(Long.valueOf(uid));
    }

    private CommonRspVo loginFail(int loginErrorCount, String key) {
        loginErrorCount++;
        stringTemplate.opsForHash().put(key, errorHashKey, String.valueOf(loginErrorCount));
        if (loginErrorCount == 5) {
            stringTemplate.expire(key, 30, TimeUnit.MINUTES);
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ, "您的账号已经被锁定，请30分钟后重试");
        }
        return new CommonRspVo(ResultCode.C_PASSWD_ERROR, StrUtil.format("用户名或密码已错误<{}>次，超过5次将被锁定",loginErrorCount));
    }

    private void check(UserValidateReqVo user, int loginErrorCount) {
        if (loginErrorCount >= 5) { // 超过5次直接返回锁定
            throw ExceptionBuilder.builder()
                    .msg("您的账号已被锁定")
                    .errorCode(2, ResultCode.C_NOT_AUTHZ.getCode())
                    .crateAppEx();

        } else if (loginErrorCount > 3) { // 超过三次要求输入验证码
            String checkCode = user.getCheckcode();
            String serverCheckCode = stringTemplate.opsForValue().get(UserLoginRedisConstant.LOGIN + ":" + user.getCodeId());
            if (Strings.isNullOrEmpty(checkCode)) {
                throw BasicExceptionUtil.buildParamsError("请输入验证码");
            } else if (Strings.isNullOrEmpty(serverCheckCode)) {
                throw BasicExceptionUtil.buildParamsError("验证码已失效，请重新输入");
            } else if (!serverCheckCode.equalsIgnoreCase(checkCode)) {
                template.delete(UserLoginRedisConstant.LOGIN + ":" + user.getCodeId());
                throw BasicExceptionUtil.buildParamsError("验证码错误");
            }
        }
    }


    private void decrypt(CasValidateReqVo vo) {
        String waitId = vo.getWaitId();
        String key = UserLoginRedisConstant.PRIVATE_KEY + waitId;
        byte[] pk = template.opsForValue().get(key);
        if (pk == null) {
//      throw new ResultCodeException(ResultCode.C_PRI_KEY_EXPIRE, "private key expired");
            throw ExceptionBuilder.builder()
                    .msg("private key expire")
                    .errorCode(3, 1061)
                    .crateAppEx();
        }
        // RSA解密
        RSAPrivateKey privateKey = (RSAPrivateKey) SerializationUtils.deserialize(pk);
        String aesKey = RsaUtils.decryptString(privateKey, vo.getKey());
        // AES解密
        String text = AesUtils.decrypt(vo.getData(), aesKey);
        JSONObject user = JSONObject.parseObject(text);
        vo.setTicket(user.getString("ticket"));
        vo.setService(user.getString("service"));
        vo.setKey(aesKey);
    }

    private void decrypt(UserValidateReqVo vo) {
        String waitId = vo.getWaitId();
        String key = UserLoginRedisConstant.PRIVATE_KEY + waitId;
        byte[] pk = template.opsForValue().get(key);
        if (pk == null) {
//      throw new ResultCodeException(ResultCode.C_PRI_KEY_EXPIRE, "private key expired");
            throw ExceptionBuilder.builder()
                    .msg("private key expire")
                    .errorCode(3, 1061)
                    .crateAppEx();
        }
        // RSA解密
        RSAPrivateKey privateKey = (RSAPrivateKey) SerializationUtils.deserialize(pk);
        String aesKey = RsaUtils.decryptString(privateKey, vo.getKey());
        // AES解密
        String text = AesUtils.decrypt(vo.getData(), aesKey);
        JSONObject user = JSONObject.parseObject(text);
        vo.setUsername(user.getString("username"));
        vo.setPassword(user.getString("password"));
        vo.setCodeId(user.getString("codeId"));
        vo.setCheckcode(user.getString("checkcode"));
        vo.setKey(aesKey);
    }

//    void decrypt(SsoValidateVo vo) {
//        String waitId = vo.getWaitId();
//        String key = UserLoginRedisConstant.PRIVATE_KEY + waitId;
//        byte[] pk = template.opsForValue().get(key);
//        if (pk == null) {
////      throw new ResultCodeException(ResultCode.C_PRI_KEY_EXPIRE, "private key expired");
//            throw ExceptionBuilder.builder()
//                    .msg("private key expired")
//                    .errorCode(3, ResultCode.C_PRI_KEY_EXPIRE.getCode())
//                    .crateAppEx();
//        }
//        // RSA解密
//        RSAPrivateKey privateKey = (RSAPrivateKey) SerializationUtils.deserialize(pk);
//        String aesKey = RsaUtils.decryptString(privateKey, vo.getKey());
//        // AES解密
//        if (vo.getData() != null) {
//            String text = AesUtils.decrypt(vo.getData(), aesKey);
//            JSONObject sso = JSONObject.parseObject(text);
//            String datas = sso.getString("datas");
//            vo.setLogincert(sso.getString("logincert"));
//            vo.setSsocert(sso.getString("ssocert"));
//            if (!Strings.isNullOrEmpty(datas)) {
//                String url = new String(Base64.getDecoder().decode(datas));
//                MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUriString(url).build().getQueryParams();
//                List<String> list = parameters.getOrDefault("action", Collections.emptyList());
//                if (!list.isEmpty()) {
//                    vo.setAction(list.get(0));
//                    vo.setProcInstId(parameters.get("id").get(0));
//                    vo.setType(parameters.get("type").get(0));
//                }
//            }
//
//        }
//        vo.setKey(aesKey);
//
//    }

//    @Bean
//    public TicketValidator ticketValidator(@Value("${essence.wefa.cas.cas-server-url-prefix}") String casServerUrlPrefix) {
//        Cas30ServiceTicketValidator validator = new Cas30ServiceTicketValidator(casServerUrlPrefix);
//        validator.setEncoding("UTF-8");
//        return validator;
//    }
}
