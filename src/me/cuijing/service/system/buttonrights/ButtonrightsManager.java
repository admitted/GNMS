package me.cuijing.service.system.buttonrights;

import me.cuijing.util.PageData;

import java.util.List;

/**
 * 按钮权限 接口
 * @author CuiJing 
 * @date   2016/1/16
 */
public interface ButtonrightsManager{

	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;
	
	/**
	 * 通过(角色ID和按钮ID)获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;
	
	/**
	 * 列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 列表(全部)左连接按钮表,查出安全权限标识
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAllBrAndQxname(PageData pd)throws Exception;
	
}

