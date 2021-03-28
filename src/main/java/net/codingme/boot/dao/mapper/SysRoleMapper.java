package net.codingme.boot.dao.mapper;

import net.codingme.boot.domain.SysRole;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author wh1107066
 */
public interface SysRoleMapper extends MySqlMapper<SysRole>, Mapper<SysRole>, IdsMapper<SysRole> {
}