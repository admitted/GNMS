package me.cuijing.controller.base;


import me.cuijing.entity.Page;
import me.cuijing.util.Logger;
import me.cuijing.util.PageData;
import me.cuijing.util.UuidUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器基类
 * @author CuiJing
 * @date： 2017/1/11
 */
public class BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;

	/**
	 * 获取当前 request 中所有请求参数 (Hashmap)
	 * @return
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}

	/**
	 * 新生 new ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}

	/**
	 * 得到分页列表的信息
	 * @return
	 */
	public Page getPage(){
		return new Page();
	}

	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info(">> start ");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger){
		logger.info("end << ");
		logger.info("");
	}

}
