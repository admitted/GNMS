package me.cuijing.interceptor;

import me.cuijing.entity.system.User;
import me.cuijing.util.Const;
import me.cuijing.util.Jurisdiction;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类名称：登录过滤，权限验证
 * @author CUI
 * @date ：2017/1/2
 * @version 1.0
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		// 获取请求的 Serlvet 的映射路径
		String path = request.getServletPath();
		// 有些页面不需要权限过滤
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if(user!=null){
				path = path.substring(1, path.length());        // 去除 '/'  [1 , path.length())
				boolean b = Jurisdiction.hasJurisdiction(path); // 访问权限校验
				// 如果没有此权限 跳转到登录页面
				if(!b){
					response.sendRedirect(request.getContextPath() + Const.LOGIN);
				}
				return b;
			}else{
				// 跳转到登录页面 (登陆过滤)
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;
			}
		}
	}

}
