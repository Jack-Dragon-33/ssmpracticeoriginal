package com.hk.crowd.mvc.handle;

import com.hk.crowd.entity.Auth;
import com.hk.crowd.entity.Role;
import com.hk.crowd.mapper.RoleMapper;
import com.hk.crowd.service.AuthService;
import com.hk.crowd.service.RoleService;
import com.hk.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import  java.util.Map;
import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Controller
public class AssignHandler {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @RequestMapping("assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("id") Integer id,
                                   ModelMap modelMap){
        List<Role> assignedRole = roleService.getAssignedRole(id);
        List<Role> unAssignedRole = roleService.getUnAssignedRole(id);
        modelMap.addAttribute("assign",assignedRole);
        modelMap.addAttribute("unAssign",unAssignedRole);
        return "assign-role";
    }
    @RequestMapping("assign/do/role/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                            @RequestParam(value = "pageNum") Integer pageNum,
                                            @RequestParam(value = "keyword",defaultValue = "") String keyWord,
                                            @RequestParam(value = "roleIdList",required = false) List<Integer> roleIdList){
        roleService.saveAdminRoleRelationship(adminId,roleIdList);
        return "redirect:/admin/do/getPageInfo.html?pageNum="+pageNum+"&keyWord="+keyWord;
    }
    @RequestMapping("assgin/get/all/auth.json")
    @ResponseBody
    public ResultEntity<List<Auth>> getAll(){
        List<Auth> all = authService.getAll();
        return ResultEntity.successWithData(all);
    }
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    @ResponseBody
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId){
        List<Integer> list=authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(list);
    }
    @RequestMapping("assign/do/role/assign/auth.json")
    @ResponseBody
    public ResultEntity<String> saveRoleAuthRelathinship(@RequestBody Map<String,List<Integer>> map){
        authService.saveRoleAuthRelathinship(map);
        return ResultEntity.successWithoutData();
    }
}
