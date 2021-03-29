package net.codingme.boot.dao.mapper;

import net.codingme.boot.dao.MybatisMapper;
import net.codingme.boot.domain.SysRole;

import java.util.List;

/**
 * @author wh1107066
 */
public interface SysRoleMapper extends MybatisMapper<SysRole, Integer> {

    /**
     *  根据rid查询角色
     * @param rid
     * @return
     */
    SysRole queryRoleByRid(Integer rid);


    List<SysRole> queryRoleCollectionUid(Integer uid);
}