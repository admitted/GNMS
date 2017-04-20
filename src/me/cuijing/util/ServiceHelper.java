package me.cuijing.util;

import me.cuijing.service.system.menu.impl.MenuService;
import me.cuijing.service.system.role.impl.RoleService;
import me.cuijing.service.system.user.UserManager;

/**
 * 获取Spring容器中的service bean
 * @author CuiJing 
 * @date   2017/4/20
 */
public final class ServiceHelper {
	
	public static Object getService(String serviceName){
		//WebApplicationContextUtils.
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}
	
	public static UserManager getUserService(){
		return (UserManager) getService("userService");
	}
	
	public static RoleService getRoleService(){
		return (RoleService) getService("roleService");
	}
	
	public static MenuService getMenuService(){
		return (MenuService) getService("menuService");
	}
}
