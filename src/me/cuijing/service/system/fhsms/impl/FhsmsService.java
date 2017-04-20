package me.cuijing.service.system.fhsms.impl;

import me.cuijing.dao.DaoSupport;
import me.cuijing.entity.Page;
import me.cuijing.service.system.fhsms.FhsmsManager;
import me.cuijing.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 站内信
 * @author CuiJing 
 * @date   2016/1/17
 */
@Service("fhsmsService")
public class FhsmsService implements FhsmsManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("FhsmsMapper.save", pd);
	}
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("FhsmsMapper.delete", pd);
	}
	
	/**
	 * 修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("FhsmsMapper.edit", pd);
	}
	
	/**
	 * 列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FhsmsMapper.datalistPage", page);
	}
	
	/**
	 * 列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FhsmsMapper.listAll", pd);
	}
	
	/**
	 * 通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FhsmsMapper.findById", pd);
	}
	
	/**
	 * 获取未读总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findFhsmsCount(String USERNAME)throws Exception{
		return (PageData)dao.findForObject("FhsmsMapper.findFhsmsCount", USERNAME);
	}
	
	/**
	 * 批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("FhsmsMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

