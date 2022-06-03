package com.hk.crowd.mvc.handle;

import com.hk.crowd.entity.Menu;
import com.hk.crowd.service.MenuService;
import com.hk.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dragon
 * @version 1.0.0
 */
//@Controller
//RestController相当于Conrtoller+ResponseBody
@RestController
public class MenuHandle {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/menu/get/hole/tree.json")
    public ResultEntity<Menu> getWholeTree(){
       List<Menu> list=menuService.getAll();
       //定义一个根节点
        Menu root=null;
        //先将所有的menu放到map中
        Map<Integer,Menu> map=new HashMap<>();
        for (Menu menu : list) {
            map.put(menu.getId(),menu);
        }
        //找到根节点,并且将儿子节点都加到children中
        for (Menu menu : list) {
            if(menu.getPid()==null){
                root=menu;
                continue;
            }
            map.get(menu.getPid()).getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }
    @RequestMapping("/menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }
    @RequestMapping("menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu){
        menuService.update(menu);
        return ResultEntity.successWithoutData();
    }
    @RequestMapping("menu/remove.json")
    public ResultEntity<String> removeMenu(Integer id){
        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();
    }
}
