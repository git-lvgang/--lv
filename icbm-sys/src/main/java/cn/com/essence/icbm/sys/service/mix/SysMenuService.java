package cn.com.essence.icbm.sys.service.mix;

import cn.com.essence.wefa.component.bean.Menu;

import java.util.List;

/**
 * 菜单服务
 */
public interface SysMenuService {

  boolean update(Menu menu);

  boolean addChildren(Menu menu);

  boolean deleteLinkage(String menuId);

  boolean delete(String menuId);

  List<Menu> getUserMenus();

  List<Menu> findAllModule();

  List<Menu> loadModuleMenuTree(String moduleMenuId);

  boolean setShow(String menuId, String show);

  List<Menu> loadUserMenus(Long userId);

  List<Menu> loadAllMenu(boolean showDisable);

  boolean addModule(Menu menu);

  boolean updateModule(Menu menu);
}
