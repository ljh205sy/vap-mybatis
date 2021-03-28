package net.codingme.boot.dao.mapper;

import net.codingme.boot.dao.MybatisMapper;
import net.codingme.boot.domain.SysUser;

import java.util.List;

/**
 * @author wh1107066
 */
public interface SysUserMapper extends MybatisMapper<SysUser, Integer> {

    /**
     *  通过username查询用户信息
     * @param username
     * @return SysUser
     */
     SysUser queryByUsername(String username);

    List<SysUser> queryUserList();

//    List<SysUser> selectByIds(@Param("ids") Object[] ids);

    List<SysUser> queryUserAndRoles();
}