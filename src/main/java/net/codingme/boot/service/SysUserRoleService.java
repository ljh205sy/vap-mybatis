package net.codingme.boot.service;

import net.codingme.boot.domain.SysUserRole;

import java.util.List;

/**
 * @author liujinhui
 * date 2021/3/25 10:02
 */
public interface SysUserRoleService extends BaseService<SysUserRole, Integer>{

    /**
     *  删除用户角色表中的数据
     * @param uid
     * @param roles
     * @return
     */
    int deleteUserAndRoles(Integer uid, List<Integer> roles);

    /**
     * 新增用户角色表中的数据
     * @param uid
     * @param roles
     * @return
     */
    int insertUserAndRoles(Integer uid, List<Integer> roles);

}
