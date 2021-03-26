package net.codingme.boot.service.impl;

import com.alibaba.druid.util.StringUtils;
import net.codingme.boot.domain.SysUser;
import net.codingme.boot.domain.mapper.SysUserMapper;
import net.codingme.boot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liujinhui
 * date 2021/3/25 10:10
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    public void setBaseMapper() {
        super.setBaseMapper(sysUserMapper);
    }

    @Override
    public List<SysUser> queryUserAndRoles() {
        List<SysUser> userList = sysUserMapper.queryUserAndRoles();
        return userList;
    }

    @Override
    public List<SysUser> querySelectByExample() {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        SysUser sysUser = new SysUser();
        sysUser.setUsername("test2");
        //模糊查询
        if (!StringUtils.isEmpty(sysUser.getUsername())) {
            criteria.andLike("username", "%" + sysUser.getUsername() + "%");
        }
        List<SysUser> userList = sysUserMapper.selectByExample(example);
        return userList;
    }
}
