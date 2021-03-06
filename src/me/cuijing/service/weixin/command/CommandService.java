package me.cuijing.service.weixin.command;

import me.cuijing.dao.DaoSupport;
import me.cuijing.entity.Page;
import me.cuijing.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CommandService
 * @author CuiJing 
 * @date   2015/5/9
 */
@Service("commandService")
public class CommandService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CommandMapper.save", pd);
	}
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CommandMapper.delete", pd);
	}
	
	/**
	 * 修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CommandMapper.edit", pd);
	}
	
	/**
	 * 列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CommandMapper.datalistPage", page);
	}
	
	/**
	 * 列表(全部)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CommandMapper.listAll", pd);
	}
	
	/**
	 * 通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CommandMapper.findById", pd);
	}
	
	/**
	 * 批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CommandMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 匹配关键词
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByKw(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CommandMapper.findByKw", pd);
	}
}

