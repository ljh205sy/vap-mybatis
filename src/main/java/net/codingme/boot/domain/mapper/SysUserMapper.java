package net.codingme.boot.domain.mapper;

import net.codingme.boot.domain.SysUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author wh1107066
 */
public interface SysUserMapper extends MySqlMapper<SysUser>, Mapper<SysUser> {

    /**
     *  通过username查询用户信息
     * @param username
     * @return SysUser
     */
     SysUser queryByUsername(String username);

    List<SysUser> queryUserList();

    List<SysUser> selectByIds(@Param("ids") Object[] ids);
}