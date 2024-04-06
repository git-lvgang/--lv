package cn.com.essence.icbm.sys.service.mix.impl;

import cn.com.essence.icbm.sys.service.mix.SysMenuService;
import cn.com.essence.wefa.component.Constants;
import cn.com.essence.wefa.component.WefaComponentUtil;
import cn.com.essence.wefa.component.bean.Menu;
import cn.com.essence.wefa.component.mapper.MenuMapper;
import cn.com.essence.wefa.component.mapper.result.MenuTreeResultHandler;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.exception.ExceptionBuilder;
import cn.com.essence.wefa.core.util.TreeParser;
import cn.com.essence.wefa.enums.VersionFlag;
import cn.com.essence.wefa.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserService userService;

    @Override
    public boolean update(Menu menu) {
        return menuMapper.update(menu) == 1;
    }

    @Override
    public boolean addChildren(Menu menu) {
        String menuId = getNextChildrenId(menu.getParentId());
        menu.setMenuId(menuId);
        menu.setVersion(VersionFlag.MIX.getVersion());
        return menuMapper.insert(menu) == 1;
    }

    @Override
    public boolean deleteLinkage(String menuId) {
        return menuMapper.delete(menuId) > 1;
    }

    public List<Menu> getUserMenus() {
        String uid = userService.getCurrentUserId();
        return loadUserMenus(Long.valueOf(uid), "");
    }

    @Override
    public List<Menu> findAllModule() {
        return filterMenu(menuMapper.findAllModule());
    }

    @Override
    public List<Menu> loadModuleMenuTree(String moduleMenuId) {
        MenuTreeResultHandler handler = new MenuTreeResultHandler();
        menuMapper.findModuleMenuTree(moduleMenuId, handler);
        return filterMenu(handler.getResult());
    }

    @Override
    public boolean setShow(String menuId, String show) {
        return menuMapper.setShow(menuId, show) == 1;
    }

    @Override
    public List<Menu> loadUserMenus(Long userId) {
        return filterMenu(loadUserMenus( userId,""));
    }

    @Override
    public List<Menu> loadAllMenu(boolean showDisable) {
        MenuTreeResultHandler handler = new MenuTreeResultHandler();
        menuMapper.findTree(showDisable, handler);
        return filterMenu(handler.getResult());
    }

    @Override
    public boolean addModule(Menu menu) {
        String menuId = getNextChildrenId("root");
        menu.setMenuId(menuId);
        menu.setMenuType(Constants.MENU_TYPE.Module.ordinal());
        return menuMapper.insert(menu) == 1;
    }

    @Override
    public boolean updateModule(Menu menu) {
        menu.setMenuType( Constants.MENU_TYPE.Module.ordinal());
        return menuMapper.updateModule(menu) == 1;
    }

    private List<Menu> loadUserMenus(Long userId, String moduleMenuId) {
        List<Menu> menus;
        if (userId == null || userId == 100000L) { // admin
            menus = menuMapper.findAdminMenuTree(moduleMenuId + "%");
        } else {
            menus = menuMapper.findAppMenuTree(userId, moduleMenuId + "%");
        }
        menus = filterMenu(menus);
        return TreeParser.getTreeList("root", menus);
    }

    @Override
    public boolean delete(String menuId) {
        List<Menu> childList = filterMenu(menuMapper.findChildren(menuId));
        if (childList.size() > 0) {
            throw ExceptionBuilder.builder()
                    .errorCode(WefaComponentUtil.getModuleCode(), ResultCode.C_EXCEPTION)
                    .msg("当前菜单已挂载子级菜单,不允许直接删除")
                    .crateFrameEx();
        }
        return menuMapper.delete(menuId) == 1;
    }

    private String getNextChildrenId(String parentId) {
        String maxId = menuMapper.findMaxId(parentId);
        if (parentId.equals("root")) {
            if (maxId == null) {
                return "10";
            }
            return String.format("%02d", Integer.parseInt(maxId) + 1);
        }
        String nextId = maxId == null ? "101" : "1" + String.format("%02d", Integer.parseInt(maxId.replaceAll(parentId, "")) + 1);
        return parentId + nextId;
    }

    /*
     *拦截所以常规版本菜单信息
     */
    private List<Menu> filterMenu(List<Menu> menus) {
        return menus.stream().filter(menu -> VersionFlag.MIX.getVersion().equals(menu.getVersion())).collect(Collectors.toList());
    }
}
