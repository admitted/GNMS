package me.cuijing.service.system.role;

import me.cuijing.entity.system.Role;
import me.cuijing.util.PageData;

import java.util.List;

/**
 * 角色接口类
 * @author CuiJing 
 * @date   2015/11/6
 */
public interface RoleManager {
	
	/**
	 * 列出此组下级角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<Role> listAllRolesByPId(PageData pd) throws Exception;
	
	/**
	 * 通过id查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findObjectById(PageData pd) throws Exception;
	
	/**
	 * 添加
	 * @param pd
	 * @throws Exception
	 */
	void add(PageData pd) throws Exception;
	
	/**
	 * 保存修改
	 * @param pd
	 * @throws Exception
	 */
	void edit(PageData pd) throws Exception;
	
	/**
	 * 删除角色
	 * @param ROLE_ID
	 * @throws Exception
	 */
	void deleteRoleById(String ROLE_ID) throws Exception;
	
	/**
	 * 给当前角色附加菜单权限
	 * @param role
	 * @throws Exception
	 */
	void updateRoleRights(Role role) throws Exception;
	
	/**
	 * 通过id查找
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	Role getRoleById(String ROLE_ID) throws Exception;
	
	/**
	 * 给全部子角色加菜单权限
	 * @param pd
	 * @throws Exception
	 */
	void setAllRights(PageData pd) throws Exception;
	
	/**
	 * 权限(增删改查)
	 * @param msg 区分增删改查
	 * @param pd
	 * @throws Exception
	 */
	void saveB4Button(String msg,PageData pd) throws Exception;

}
