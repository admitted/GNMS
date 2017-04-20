package me.cuijing.service.system.createcode;

import me.cuijing.entity.Page;
import me.cuijing.util.PageData;

import java.util.List;

/**
 * 代码生成器接口类
 * @author CuiJing 
 * @date   2015/11/24
 */
public interface CreateCodeManager {
	
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
	 * 批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}
