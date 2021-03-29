package net.codingme.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import net.codingme.boot.dao.mapper.SysUserRoleMapper;
import net.codingme.boot.domain.SysUserRole;
import net.codingme.boot.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liujinhui
 * date 2021/3/29 14:09
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole, Integer> implements SysUserRoleService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteUserAndRoles(Integer uid, List<Integer> roles) {
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        //模糊查询
        if (!StringUtils.isEmpty(uid)) {
            criteria.andEqualTo("userId", uid);
        }

        if (!CollectionUtil.isEmpty(roles)) {
            criteria.andIn("roleId", roles);
        }
        // 删除中间表中该用户的角色信息
        return sysUserRoleMapper.deleteByExample(example);
    }

    @Override
    public int insertUserAndRoles(Integer uid, List<Integer> roles) {
        List<SysUserRole> list = new ArrayList<>();
        for (Integer rid : roles) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(uid);
            userRole.setRoleId(rid);
            userRole.setCreateBy("admin");
            userRole.setCreateTime(new Date());
            list.add(userRole);
        }
        return sysUserRoleMapper.insertList(list);
    }
}
