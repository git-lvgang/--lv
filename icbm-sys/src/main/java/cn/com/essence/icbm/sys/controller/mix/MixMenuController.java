package cn.com.essence.icbm.sys.controller.mix;

import cn.com.essence.icbm.sys.service.mix.SysMenuService;
import cn.com.essence.wefa.component.bean.Menu;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.enums.VersionFlag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "MIX版菜单管理")
@RestController
@RequestMapping("/v1")
public class MixMenuController {

    @Autowired
    private SysMenuService menuService;

    /**
     * 添加子菜单
     *
     * @param menu
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/menus/menu/{parentId}/children", method = RequestMethod.POST)
    @ApiOperation("MIX新增菜单")
    @ApiImplicitParam(paramType = "path", name = "parentId", dataTypeClass = String.class, required = true, value = "父菜单Id")
    @RequiresPermissions(value = "Wefa:Menu:Add")
    @SysLog(moduleName = "Wefa Component [MIX菜单管理]",operation = "MIX新增菜单")
    public CommonRspVo addChildrenMenu(@RequestBody Menu menu, @PathVariable String parentId) {
        menu.setParentId(parentId);
        CommonRspVo rsp = new CommonRspVo();
        boolean success = menuService.addChildren(menu);
        rsp.setRtnCode(success ? ResultCode.C_SUCCESS : ResultCode.C_FAIL);
        rsp.setData(menu);
        return rsp;
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @RequestMapping(value = "/menus/menu/{menuId}", method = RequestMethod.PUT)
    @ApiOperation("MIX修改菜单")
    @ApiImplicitParam(paramType = "path", name = "menuId", dataTypeClass = String.class, required = true, value = "菜单Id")
    @RequiresPermissions(value = "Wefa:Menu:Edit")
    @SysLog(moduleName = "Wefa Component [MIX菜单管理]",operation = "MIX更新菜单")
    public CommonRspVo edit(@RequestBody Menu menu) {
        Validate.isTrue("1".equals(menu.getVersion()), "非mix修改菜单");
        CommonRspVo rsp = new CommonRspVo();
        boolean success = menuService.update(menu);
        rsp.setRtnCode(success ? ResultCode.C_SUCCESS : ResultCode.C_FAIL);
        return rsp;
    }

    /**菜单模块相关**/
    @RequestMapping(value = "/menus/module", method = RequestMethod.POST)
    @ApiOperation("MIX新增模块菜单")
    @RequiresPermissions(value = "Wefa:Menu:Module:Add")
    @SysLog(moduleName = "Wefa Component [MIX菜单管理]",operation = "MIX新增菜单模块")
    public CommonRspVo addModuleMenu(@RequestBody Menu menu) {
        Validate.isTrue("1".equals(menu.getVersion()), "非mix新增模块");
        CommonRspVo rsp = new CommonRspVo();
        menu.setParentId("root");
        menu.setVersion(VersionFlag.COMM.getVersion());
        boolean res =  menuService.addModule(menu);
        rsp.setRtnCode(res ?ResultCode.C_SUCCESS:ResultCode.C_FAIL);
        return rsp;
    }

    @RequestMapping(value = "/menus/module/{moduleMenuId}", method = RequestMethod.PUT)
    @ApiOperation("MIX修改模块菜单")
    @RequiresPermissions(value = "Wefa:Menu:Module:Edit")
    @SysLog(moduleName = "Wefa Component [MIX菜单管理]",operation = "MIX更新菜单模块")
    @ApiImplicitParam(paramType = "path", name = "moduleMenuId", dataTypeClass = String.class, required = true, value = "菜单Id")
    public CommonRspVo moModuleMenu(@RequestBody Menu menu, @PathVariable String moduleMenuId) {
        Validate.isTrue("1".equals(menu.getVersion()), "非mix修改模块");
        menu.setMenuId(moduleMenuId);
        menu.setParentId("root");
        CommonRspVo rsp = new CommonRspVo();
        boolean success = menuService.updateModule(menu);
        rsp.setRtnCode(success ? ResultCode.C_SUCCESS : ResultCode.C_FAIL);
        return rsp;
    }

    /**
     * MIX删除菜单
     *
     * @param menuId
     * @param deleteLinkage
     * @return
     */
    @RequestMapping(value = "/menus/{menuId}", method = RequestMethod.DELETE)
    @ApiOperation("MIX删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "menuId", dataTypeClass = String.class, required = true, value = "菜单Id"),
            @ApiImplicitParam(paramType = "query", name = "deleteLinkage", dataTypeClass = String.class, required = true, value = "是否联动删除子菜单。0-否;1-是。默认为：0-否")})
    @RequiresPermissions(value = "Wefa:Menu:Delete")
    @SysLog(moduleName = "Wefa Component [MIX菜单管理]",operation = "MIX删除菜单")
    public CommonRspVo delChildrenMenu(@PathVariable String menuId, @RequestParam String deleteLinkage) {
        CommonRspVo rsp = new CommonRspVo();
        boolean success = "1".equals(deleteLinkage) ? menuService.deleteLinkage(menuId) : menuService.delete(menuId);
        rsp.setRtnCode(success ? ResultCode.C_SUCCESS : ResultCode.C_DATA_NOT_FOUND);
        return rsp;
    }

    /**
     * MIX查询登陆用户所有权限
     *
     * @return
     */
    @GetMapping("/auth/menus")
    @RequiresUser
    @ApiOperation("MIX查询登陆用户所有菜单")
    public CommonRspVo getUserMenus() {
        return new CommonRspVo(ResultCode.C_SUCCESS, menuService.getUserMenus());
    }

    @RequestMapping(value = "/menus/module", method = RequestMethod.GET)
    @ApiOperation("MIX获取所有模块")
    @RequiresPermissions(value = "Wefa:Menu:Module:Query")
    public CommonRspVo getModules() {
        CommonRspVo rsp = new CommonRspVo();
        List<Menu> menus = menuService.findAllModule();
        rsp.setData(menus);
        rsp.setRtnCode(ResultCode.C_SUCCESS);
        return rsp;
    }

    @RequestMapping(value = "/menus/module/{moduleMenuId}/children", method = RequestMethod.GET)
    @ApiOperation("MIX查询模块下的所有菜单")
    @RequiresPermissions(value = "Wefa:Menu:Module:QueryMenu")
    public CommonRspVo getModuleMenus(@PathVariable String moduleMenuId) {
        CommonRspVo rsp = new CommonRspVo();
        List<Menu> menus = menuService.loadModuleMenuTree(moduleMenuId);
        rsp.setData(menus);
        rsp.setRtnCode(ResultCode.C_SUCCESS);
        return rsp;
    }

    @RequestMapping(value = "/menus/users/{userId}", method = RequestMethod.GET)
    @ApiOperation("MIX获取用户菜单")
    @ApiImplicitParam(paramType = "path", name = "userId", dataTypeClass = Long.class, required = true, value = "用户Id")
    @RequiresUser
    public CommonRspVo getUserMenus(@PathVariable Long userId) {
        CommonRspVo rsp = new CommonRspVo();
        List<Menu> menus = menuService.loadUserMenus(userId);
        rsp.setData(menus);
        rsp.setRtnCode(ResultCode.C_SUCCESS);
        return rsp;
    }

    /**
     * 启用菜单
     *
     * @param menuId
     * @return CommonRspVo
     */
    @RequestMapping(value = "/menus/enable/{menuId}", method = RequestMethod.PATCH)
    @ApiOperation("MIX启用菜单/模块")
    @ApiImplicitParam(paramType = "path", name = "menuId", dataTypeClass = String.class, required = true, value = "菜单Id")
    @RequiresPermissions(value = "Wefa:Menu:Enable")
    @SysLog(moduleName = "Wefa Component [MIX菜单管理]",operation = "MIX启用菜单/模块")
    public CommonRspVo enableMenu(@PathVariable String menuId) {
        CommonRspVo rsp = new CommonRspVo();
        boolean success = menuService.setShow(menuId, "1");
        rsp.setRtnCode(success ? ResultCode.C_SUCCESS : ResultCode.C_FAIL);
        return rsp;
    }

    /**
     * 禁用菜单
     *
     * @param menuId
     * @return CommonRspVo
     */
    @RequestMapping(value = "/menus/disable/{menuId}", method = RequestMethod.PATCH)
    @ApiOperation("MIX禁用菜单/模块")
    @ApiImplicitParam(paramType = "path", name = "menuId", dataTypeClass = String.class, required = true, value = "菜单Id")
    @RequiresPermissions(value = "Wefa:Menu:Disable")
    @SysLog(moduleName = "Wefa Component [MIX菜单管理]",operation = "MIX禁用菜单/模块")
    public CommonRspVo disableMenu(@PathVariable String menuId) {
        CommonRspVo rsp = new CommonRspVo();
        boolean success = menuService.setShow(menuId, "0");
        rsp.setRtnCode(success ? ResultCode.C_SUCCESS : ResultCode.C_FAIL);
        return rsp;
    }

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    @ApiOperation("MIX获取系统菜单")
    @RequiresPermissions(value = "Wefa:Menu:Select")
    public CommonRspVo getMenus() {
        CommonRspVo rsp = new CommonRspVo();
        List<Menu> menus = menuService.loadAllMenu(true);
        rsp.setData(menus);
        rsp.setRtnCode(ResultCode.C_SUCCESS);
        return rsp;
    }
}
