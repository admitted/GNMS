package me.cuijing.controller.system.login;

import me.cuijing.controller.base.BaseController;
import me.cuijing.entity.system.Menu;
import me.cuijing.entity.system.Role;
import me.cuijing.entity.system.User;
import me.cuijing.service.system.appuser.AppuserManager;
import me.cuijing.service.system.buttonrights.ButtonrightsManager;
import me.cuijing.service.system.fhbutton.FhbuttonManager;
import me.cuijing.service.system.menu.MenuManager;
import me.cuijing.service.system.role.RoleManager;
import me.cuijing.service.system.user.UserManager;
import me.cuijing.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台总入口
 * @author: cui
 * @date： 2017/3/19
 */
@Controller
public class LoginController extends BaseController {

	@Resource(name="userService")
	private UserManager userService;

	@Resource(name="menuService")
	private MenuManager menuService;

	@Resource(name="roleService")
	private RoleManager roleService;

	@Resource(name="buttonrightsService")
	private ButtonrightsManager buttonrightsService;

	@Resource(name="fhbuttonService")
	private FhbuttonManager fhbuttonService;

	@Resource(name="appuserService")
	private AppuserManager appuserService;


	/**
	 * 访问登录页 (web.xml 因没有配置welcome页面 Tomcat默认找到根目录下的index.jsp)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin() throws Exception{
		ModelAndView mv = this.getModelAndView();
		// request 参数封装的 map 对象
		PageData pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 在显示登录之前 获取 本管理系统的名称 : PNMS
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
		return mv;
	}

	/**
	 * 请求登录，验证用户
	 * 将用户有用信息放入 session 中 (ID,NAME,PASSWORD,RIGHTS,ROLE_ID等9个)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("qq876198439cui", "").split(",");
		if(null != KEYDATA && KEYDATA.length == 3){
			Session session = Jurisdiction.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE); //获取session中的验证码
			String code = KEYDATA[2];
			if(null == code || "".equals(code)){
				errInfo = "nullcode";
			}else{
				String USERNAME  = KEYDATA[0];	//登录过来的用户名
				String PASSWORD  = KEYDATA[1];	//登录过来的密码
				pd.put("USERNAME", USERNAME);
				if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){		//判断登录验证码是否正确
					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();	//密码加密
					pd.put("PASSWORD", passwd);
					pd = userService.getUserByNameAndPwd(pd);	//根据用户名和密码去读取用户信息
					if(pd != null){
						pd.put("LAST_LOGIN",DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						User user = new User();
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));     			// sys_user 表中的 RIGHTS
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						session.setAttribute(Const.SESSION_USER, user);			// 把用户信息放session中 : 供以后使用
						session.removeAttribute(Const.SESSION_SECURITY_CODE);	// 清除登录验证码的session
						// shiro加入身份验证
						Subject subject = SecurityUtils.getSubject();
						UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
						try {
							subject.login(token);
						} catch (AuthenticationException e) {
							errInfo = ">> Authentication failed 身份验证失败！";
						}
					}else{
						errInfo = "usererror"; 				// 用户名或密码有误
						logBefore(logger, USERNAME+">> login error password or username 登录系统密码或用户名错误");
					}
				}else{
					errInfo = "codeerror";				 	// 验证码输入有误
				}
				if(Tools.isEmpty(errInfo)){
					errInfo = "success";					// 验证成功
					logBefore(logger, USERNAME+" >> login success ");
				}
			}
		}else{
			errInfo = "error";	//缺少参数
		}
		// 单把错误信息放到 map 里去
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 登录验证成功后 , 访问系统首页
	 * 用户角色中有 访问菜单权限 RIGHTS 和 每个菜单的增删改查(ADD_QX,DEL_QX,EDIT_QX,CHA_QX)
	 * @param changeMenu：切换菜单参数
	 * @return
	 */
	@RequestMapping(value="/main/{changeMenu}")
	@SuppressWarnings("unchecked")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu){
		ModelAndView mv = this.getModelAndView();
		PageData     pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);				 	 //读取 session 存的用户信息 (9 fields)
			if (user != null) {
				//先看 session 有没有角色信息 , 没有再从数据库取 减少数据库查询
				User userAndRole = (User)session.getAttribute(Const.SESSION_USERROL);	 //读取 session  中用户所有信息(含详细角色)
				if(null == userAndRole){
					user = userService.getUserAndRoleById(user.getUSER_ID());		 	 //通过用户ID读取 此用户所有信息(含详细角色)
					session.setAttribute(Const.SESSION_USERROL, user);				 	 //存入 session
				}else{
					user = userAndRole;
				}
				// 以下的 user 包含用户的所有信息
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole();											 	 //获取用户角色
				String roleRights = ((role != null) ? role.getRIGHTS() : "");		 	 //获取用户角色中的权限(菜单权限)
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights);  //将用户角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);				 	 //将用户名存入session
				List<Menu> MenuList = new ArrayList<Menu>();
				if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){
					MenuList = menuService.listAllMenuQx("0");					 	     // "0" : 获取所有菜单
					if("admin".equals(USERNAME)){										 //若是 admin 登录 , 获取所有菜单
						MenuList = this.readAllMenu(MenuList);
					}else{
						if(Tools.notEmpty(roleRights)){
							MenuList = this.readMenu(MenuList, roleRights);		 	     //根据用户的角色的权限获取相应菜单列表
						}
					}
					session.setAttribute(USERNAME + Const.SESSION_allmenuList, MenuList);//菜单权限放入session中
				}else{
					MenuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
				}
				// 切换菜单处理=====start
				List<Menu> menuList = new ArrayList<Menu>();
				if(null == session.getAttribute(USERNAME + Const.SESSION_menuList) || ("yes".equals(changeMenu))) {
					List<Menu> menuList1 = new ArrayList<Menu>();
					List<Menu> menuList2 = new ArrayList<Menu>();
					// 拆分菜单 MENU_TYPE  1:系统菜单 2:业务菜单
					for (int i = 0; i < MenuList.size(); i++) {
						Menu menu = MenuList.get(i);
						if ("1".equals(menu.getMENU_TYPE())) {
							menuList1.add(menu);
						} else {
							menuList2.add(menu);
						}
					}
					session.removeAttribute(USERNAME + Const.SESSION_menuList);
					// changeMenu 切换业务菜单\系统菜单  2变1; 1变2
					if ("2".equals(session.getAttribute("changeMenu"))) {
						session.setAttribute(USERNAME + Const.SESSION_menuList, menuList1);
						session.removeAttribute("changeMenu");
						session.setAttribute("changeMenu", "1");
						menuList = menuList1;
					} else {
						session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
						session.removeAttribute("changeMenu");
						session.setAttribute("changeMenu", "2");
						menuList = menuList2;
					}
				}else{
					menuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_menuList);
				}
				// 切换菜单处理=====end
				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
					// 按钮权限(增删改查) 放到session中
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));
				}
				this.getRemortIP(USERNAME);						// 更新登录IP
				mv.setViewName("system/index/main");
				mv.addObject("user", user);						// user 包含用户的所有信息
				mv.addObject("menuList", menuList);				// 业务菜单 或 系统菜单
			}else {
				// session 失效时间 600分钟 (web.xml 中设置)
				mv.setViewName("system/index/login");			// session失效后跳转登录页面
			}
		} catch(Exception e){
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); 	// 读取系统名称
		mv.addObject("pd",pd);
		return mv;
	}

	/**
	 * 根据角色权限获取本权限的菜单列表(递归处理)
	 * @param menuList：   传入的总菜单
	 * @param roleRights： 加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList, String roleRights) {
		for (int i = 0; i < menuList.size(); i++) {
			// 判断当前用户 是否有此菜单权限 , 若有此菜单权限 ,设置 hasMenu 为 TRUE
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			if (menuList.get(i).isHasMenu()) {
				this.readMenu(menuList.get(i).getSubMenu(), roleRights); // 若有此菜单权限,继续排查其子菜单权限
			}
		}
		return menuList;
	}

	/**
	 * 获取 admin 全部菜单列表(递归处理)
	 * @param allMenuList：传入的总菜单
	 * @return
	 */
	public List<Menu> readAllMenu(List<Menu> allMenuList) {
		for (int i = 0; i < allMenuList.size(); i++) {
			// 判断是否有此菜单权限 , 若有此菜单权限 ,设置 hasMenu 为 TRUE
			allMenuList.get(i).setHasMenu(true);
			if (allMenuList.get(i).isHasMenu()) {
				this.readAllMenu(allMenuList.get(i).getSubMenu()); // 若有此菜单权限,继续排查其子菜单权限
			}
		}
		return allMenuList;
	}

	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "system/index/tab";
	}

	/**
	 * 进入首页后的默认页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_default")
	public ModelAndView defaultPage() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("userCount", Integer.parseInt(userService.getUserCount("").get("userCount").toString()) - 1);            //系统用户数
		pd.put("appUserCount", Integer.parseInt(appuserService.getAppUserCount("").get("appUserCount").toString()));    //会员数
		mv.addObject("pd", pd);
		mv.setViewName("system/index/default");
		return mv;
	}

	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout() {
		String USERNAME = Jurisdiction.getUsername();    // 当前登录的用户名
		logBefore(logger, USERNAME + " >> 退出系统 <<");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Session session = Jurisdiction.getSession();     // 以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		// shiro销毁登录
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		pd = this.getPageData();
		pd.put("msg", pd.getString("msg"));
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/index/login");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 获取用户权限 QX(CRUD)
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME) {
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			pd.put("ROLE_ID", userService.findByUsername(pd).get("ROLE_ID").toString());// 根据 用户名 admin 获取角色ID
			pd = roleService.findObjectById(pd);                                        // 获取角色信息
			map.put("adds",  pd.getString("ADD_QX"));    // 增
			map.put("dels",  pd.getString("DEL_QX"));    // 删
			map.put("edits", pd.getString("EDIT_QX"));   // 改
			map.put("chas",  pd.getString("CHA_QX"));    // 查
			// 按钮权限 (导出,导入,站内信,短信,邮件)
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if ("admin".equals(USERNAME)) {
				buttonQXnamelist = fhbuttonService.listAll(pd);                    // admin用户拥有所有增删改查权限
			} else {
				buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd);     // 此角色拥有的 增删改查权限 List
			}
			for (int i = 0; i < buttonQXnamelist.size(); i++) {
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"), "1");        // 按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return map;
	}

	/**
	 * 更新登录用户的IP
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}

}
