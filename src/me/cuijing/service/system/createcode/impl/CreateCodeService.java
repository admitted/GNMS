package me.cuijing.service.system.createcode.impl;
import me.cuijing.dao.DaoSupport;
import me.cuijing.entity.Page;
import me.cuijing.service.system.createcode.CreateCodeManager;
import me.cuijing.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CreateCodeService 代码生成器
 * @author CuiJing 
 * @date   2015/11/24
 */
@Service("createcodeService")
public class CreateCodeService implements CreateCodeManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CreateCodeMapper.save", pd);
	}
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CreateCodeMapper.delete", pd);
	}
	
	/**
	 * 列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CreateCodeMapper.datalistPage", page);
	}
	
	/**
	 * 通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CreateCodeMapper.findById", pd);
	}
	
	/**
	 * 批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CreateCodeMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

