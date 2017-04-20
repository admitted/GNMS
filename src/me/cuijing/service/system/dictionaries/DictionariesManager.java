package me.cuijing.service.system.dictionaries;

import me.cuijing.entity.Page;
import me.cuijing.entity.system.Dictionaries;
import me.cuijing.util.PageData;

import java.util.List;

/**
 * 数据字典接口类
 * @author CuiJing 
 * @date   2015/12/16
 */
public interface DictionariesManager{

	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;
	
	/**
	 * 修改
	 * @param pd
	 * @throws Exception
	 */
	void edit(PageData pd)throws Exception;
	
	/**
	 * 列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> list(Page page)throws Exception;
	
	/**
	 * 通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;

	/**
	 * 通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findByBianma(PageData pd)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<Dictionaries> listSubDictByParentId(String parentId) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	List<Dictionaries> listAllDict(String parentId) throws Exception;
	
	/**
	 * 排查表检查是否被占用
	 * @param pd
	 * @throws Exception
	 */
	PageData findFromTbs(PageData pd)throws Exception;
	
}

