package me.cuijing.service.fhdb.timingbackup;

import me.cuijing.entity.Page;
import me.cuijing.util.PageData;

import java.util.List;

/**
 * 定时备份接口
 * @author CuiJing 
 * @date   2016/4/9
 */
public interface TimingBackUpManager{

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
	 * 批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 切换状态
	 * @param pd
	 * @throws Exception
	 */
	void changeStatus(PageData pd)throws Exception;
	
}

