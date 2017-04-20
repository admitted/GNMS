package me.cuijing.service.system.menu;

import me.cuijing.entity.system.Menu;
import me.cuijing.util.PageData;

import java.util.List;

/**
 * MenuService 菜单处理接口
 * @author CuiJing 
 * @date   2015/10/20
 */
public interface MenuManager {

	/**
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<Menu> listSubMenuByParentId(String parentId)throws Exception;
	
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getMenuById(PageData pd) throws Exception;
	
	/**
	 * @param menu
	 * @throws Exception
	 */
	void saveMenu(Menu menu) throws Exception;
	
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findMaxId(PageData pd) throws Exception;
	
	/**
	 * @param MENU_ID
	 * @throws Exception
	 */
	void deleteMenuById(String MENU_ID) throws Exception;
	
	/**
	 * @param menu
	 * @throws Exception
	 */
	void edit(Menu menu) throws Exception;
	
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData editicon(PageData pd) throws Exception;
	
	/**
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	List<Menu> listAllMenu(String MENU_ID) throws Exception;
	
	/**
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	List<Menu> listAllMenuQx(String MENU_ID) throws Exception;
}
