package me.cuijing.service.system.fhbutton.impl;

import me.cuijing.dao.DaoSupport;
import me.cuijing.entity.Page;
import me.cuijing.service.system.fhbutton.FhbuttonManager;
import me.cuijing.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 按钮管理
 * @author CuiJing 
 * @date   2016/1/15
 */
@Service("fhbuttonService")
public class FhbuttonService implements FhbuttonManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("FhbuttonMapper.save", pd);
	}
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("FhbuttonMapper.delete", pd);
	}
	
	/**
	 * 修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("FhbuttonMapper.edit", pd);
	}
	
	/**
	 * 列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FhbuttonMapper.datalistPage", page);
	}
	
	/**
	 * 列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FhbuttonMapper.listAll", pd);
	}
	
	/**
	 * 通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FhbuttonMapper.findById", pd);
	}
	
	/**
	 * 批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("FhbuttonMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

