package com.cibnvideo.common.serviceImple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibnvideo.common.dao.RoleDao;
import com.cibnvideo.common.dao.RoleMenuDao;
import com.cibnvideo.common.dao.UserDao;
import com.cibnvideo.common.dao.UserRoleDao;
import com.cibnvideo.common.entity.Role;
import com.cibnvideo.common.entity.RoleMenu;
import com.cibnvideo.common.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleDao roleDao;
    @Autowired
    RoleMenuDao roleMenuDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserRoleDao userRoleDao;

    @Override
    public List<Role> list() {
        List<Role> roles = roleDao.list(new HashMap<String, Object>(16));
        return roles;
    }


    @Override
    public List<Role> list(Long userId) {
        List<Long> rolesIds = userRoleDao.listRoleId(userId);
        List<Role> roles = roleDao.list(new HashMap<String, Object>(16));
        for (Role roleDO : roles) {
            roleDO.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(roleDO.getRoleId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }
    @Transactional
    @Override
    public int save(Role role) {
        int count = roleDao.save(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getRoleId();
        List<RoleMenu> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenu rmDo = new RoleMenu();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        roleMenuDao.removeByRoleId(roleId);
        if (rms.size() > 0) {
            for(RoleMenu rm:rms) {
            	roleMenuDao.save(rm);
            }
        }
        return count;
    }

    @Transactional
    @Override
    public int remove(Long id) {
        int count = roleDao.remove(id);
        roleMenuDao.removeByRoleId(id);
        return count;
    }

    @Override
    public Role get(Long id) {
        Role role = roleDao.get(id);
        return role;
    }

    @Override
    public int update(Role role) {
        int r = roleDao.update(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getRoleId();
        roleMenuDao.removeByRoleId(roleId);
        List<RoleMenu> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenu rmDo = new RoleMenu();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            for(RoleMenu rm:rms) {
            	roleMenuDao.save(rm);
            }
        }
        return r;
    }

    @Override
    public int batchremove(Long[] ids) {
        int r = 0;
        for(Long l:ids) {
        		r = roleDao.remove(l);
        }
        return r;
    }

}
