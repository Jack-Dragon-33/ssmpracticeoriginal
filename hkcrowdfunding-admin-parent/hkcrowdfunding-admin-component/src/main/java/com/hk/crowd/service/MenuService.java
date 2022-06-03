package com.hk.crowd.service;

import com.hk.crowd.entity.Menu;

import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
public interface MenuService {
    List<Menu> getAll();
    void saveMenu(Menu menu);

    void update(Menu menu);
    void removeMenu(Integer id);
}
