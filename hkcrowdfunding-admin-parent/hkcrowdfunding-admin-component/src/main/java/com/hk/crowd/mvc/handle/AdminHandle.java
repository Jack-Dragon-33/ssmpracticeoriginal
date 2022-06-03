package com.hk.crowd.mvc.handle;

import com.github.pagehelper.PageInfo;
import com.hk.crowd.constant.CrowdConstant;
import com.hk.crowd.entity.Admin;
import com.hk.crowd.service.AdminService;
import com.hk.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Controller
public class AdminHandle {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String username,
                          @RequestParam("userPswd") String password,
                          HttpSession session){
        Admin admin = adminService.getAdminByLoginAcct(username);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGINADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }
    @RequestMapping("/admin/do/loginout.html")
    public String doLoginOut(HttpSession session){
//        强制Session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
    @RequestMapping("/admin/do/getPageInfo.html")
    public String getPageInfo(@RequestParam(value = "keyWord",defaultValue = "") String keyWord,
                              @RequestParam(value = "pageNum",defaultValue = "1") String pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "5") String pageSize,
                              ModelMap modelMap){
        PageInfo<Admin> pageInfo=adminService.getPageInfo(keyWord,pageNum,pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }
    @RequestMapping("admin/remove/{id}/{pageNum}/{keyWord}.html")
    public String removeAdmin(@PathVariable("id") Integer id,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("keyWord") String keyWord,
                              HttpSession session){
        Admin amdin = (Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGINADMIN);
        if(amdin.getId()==id){
            throw new RuntimeException("您不能删除自己的账户...");
        }
        int b=adminService.removeAdmin(id);
        if (Objects.equals(keyWord,"undefined")){
            keyWord="";
        }
        return "redirect:/admin/do/getPageInfo.html?pageNum="+pageNum+"&keyWord="+keyWord;
    }
    @RequestMapping("/admin/remove/more.html")
    public String removeMoreAdmin(@RequestParam("admins") String[] admins,Integer pageNum,String keyWord,HttpSession session){
        Admin adminLog = (Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGINADMIN);
        List<Integer> list=new ArrayList<>();
        for (String admin : admins) {
            int id = Integer.parseInt(admin);
            if(id==adminLog.getId()){
                throw new RuntimeException("您不能删除自己的账户...");
            }
            list.add(id);
        }
        if (Objects.equals(keyWord,"undefined")){
            keyWord="";
        }
        int i=adminService.deleteMoreAdmin(list);
        return "redirect:/admin/do/getPageInfo.html?pageNum="+pageNum+"&keyWord="+keyWord;
    }
    @RequestMapping("/admin/add.html")
    public String addAdmin(Admin admin){
        admin.setCreateTime(CrowdUtil.getDate());
        adminService.saveAdmin(admin);
        return "redirect:/admin/do/getPageInfo.html?pageNum="+Integer.MAX_VALUE;
    }
    @RequestMapping("admin/update/{id}.html")
    public String getAdminById(@PathVariable("id") Integer id,
                               ModelMap modelMap){
        Admin admin=adminService.getAdminById(id);
        modelMap.addAttribute(CrowdConstant.ATTR_UPDATE_ADMIN,admin);
        return "admin-update";
    }
    @RequestMapping("admin/do/update.html")
    public String update(Admin admin){
       int i= adminService.update(admin);
       return "redirect:/admin/do/getPageInfo.html";
    }
}
