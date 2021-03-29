package net.codingme.boot.service;

import net.codingme.boot.domain.SysUser;

import java.util.List;

/**
 * @author liujinhui
 * date 2021/3/25 10:02
 */
public interface SysUserService extends BaseService<SysUser, Integer>{

    /**
     * 获取所有的用户，并携带所有的角色信息
     * @return
     */
    List<SysUser> queryUserAndRoles();

    /**
     * 通过Example进行查询操作
     */
    List<SysUser>  querySelectByUsernameExample(String username) ;

    int deleteUser(SysUser sysUser);

}
