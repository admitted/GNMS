package me.cuijing.service.system.fhsms;

import me.cuijing.entity.Page;
import me.cuijing.util.PageData;

import java.util.List;

/**
 * 站内信接口
 * @author CuiJing 
 * @date   2016/1/17
 */
public interface FhsmsManager{

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
	 * 列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;
	
	/**
	 * 获取未读总数
	 * @param pd
	 * @throws Exception
	 */
	PageData findFhsmsCount(String USERNAME)throws Exception;
	
	/**
	 * 批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

