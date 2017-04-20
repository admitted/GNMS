package me.cuijing.controller.system.fhbutton;

import me.cuijing.controller.base.BaseController;
import me.cuijing.entity.Page;
import me.cuijing.service.system.fhbutton.FhbuttonManager;
import me.cuijing.util.AppUtil;
import me.cuijing.util.Jurisdiction;
import me.cuijing.util.ObjectExcelView;
import me.cuijing.util.PageData;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明：按钮管理
 * @author cui
 * @date 2017/01/15
 */
@Controller
@RequestMapping(value = "/fhbutton")
public class FhbuttonController extends BaseController {

    String menuUrl = "fhbutton/list.do"; //菜单地址(权限用)

    @Resource(name = "fhbuttonService")
    private FhbuttonManager fhbuttonService;

    /**
     * 保存新增
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + " >> 新增Fhbutton");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {         //校验用户是否有 add 权限
            return null;
        }
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("FHBUTTON_ID", this.get32UUID());                        //主键
        fhbuttonService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除按钮
     * @param out
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + " >> 删除Fhbutton");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {         //校验用户是否有 del 权限
            return;
        }
        PageData pd = new PageData();
        pd = this.getPageData();
        fhbuttonService.delete(pd);
        out.write("success");
        out.close();
    }

    /**
     * 修改
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + " >> 修改Fhbutton");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {        //校验用户是否有 edit 权限
            return null;
        }
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        fhbuttonService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     * @param page
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + " >> 列表Fhbutton");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String keywords = pd.getString("keywords");                     // 关键词检索条件
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
        }
        page.setPd(pd);
        List<PageData> varList = fhbuttonService.list(page);            // 列出Fhbutton列表
        mv.setViewName("system/fhbutton/fhbutton_list");
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC());                       // 按钮权限
        return mv;
    }

    /**
     * 去新增页面
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("system/fhbutton/fhbutton_edit");
        mv.addObject("msg", "save");       // mv 键值对(msg,save)  可在JSP页面用 EL 表达式 ${msg} 获得
        mv.addObject("pd", pd);            // pd 的值用            可在JSP页面用 EL 表达式 ${pd.NAME} 获得
        return mv;
    }

    /**
     * 去修改页面
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = fhbuttonService.findById(pd);    //根据ID读取
        mv.setViewName("system/fhbutton/fhbutton_edit");
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 批量删除
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + " >> 批量删除Fhbutton");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return null;
        } //校验权限
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        pd = this.getPageData();
        List<PageData> pdList = new ArrayList<PageData>();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (null != DATA_IDS && !"".equals(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            fhbuttonService.deleteAll(ArrayDATA_IDS);
            pd.put("msg", "ok");
        } else {
            pd.put("msg", "no");
        }
        pdList.add(pd);
        map.put("list", pdList);
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 导出到excel
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + " >> 导出Fhbutton到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("名称");                                //1
        titles.add("权限标识");                            //2
        titles.add("备注");                                //3
        dataMap.put("titles", titles);
        List<PageData> varOList = fhbuttonService.listAll(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("NAME"));         //1
            vpd.put("var2", varOList.get(i).getString("QX_NAME"));      //2
            vpd.put("var3", varOList.get(i).getString("BZ"));           //3
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}