package com.hk.crowd.mvc.handle;

import com.github.pagehelper.PageInfo;
import com.hk.crowd.entity.Auth;
import com.hk.crowd.entity.Role;
import com.hk.crowd.service.AuthService;
import com.hk.crowd.service.RoleService;
import com.hk.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Controller
public class RoleHandle {
    @Autowired
    private RoleService roleService;
    @ResponseBody
    @PreAuthorize("hasAuthority('role:get')")
    @RequestMapping("role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "keyword",defaultValue = "") String keyword){
        //如果抛出异常交由spring的异常映射机制处理；
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }
    @RequestMapping("role/save.json")
    @ResponseBody
    public ResultEntity<String> saveRole(Role role){
        roleService.save(role);
        return ResultEntity.successWithoutData();
    }
    @RequestMapping("role/get.json")
    @ResponseBody
    public ResultEntity<Role> getRole(Integer id){
       Role role= roleService.getRole(id);
       return ResultEntity.successWithData(role);
    }
    @RequestMapping("role/update.json")
    @ResponseBody
    public ResultEntity<String> updateRole(Role role){
       roleService.updateRole(role);
       return ResultEntity.successWithoutData();
    }
    @RequestMapping("role/remove/by/role/id/array.json")
    @ResponseBody
    public ResultEntity<String> deleteRole(@RequestBody List<Integer> list){
       roleService.deleteRole(list);
       return ResultEntity.successWithoutData();
    }

}
