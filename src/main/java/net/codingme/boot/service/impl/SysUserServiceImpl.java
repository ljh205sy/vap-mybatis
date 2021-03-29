package net.codingme.boot.service.impl;

import com.alibaba.druid.util.StringUtils;
import net.codingme.boot.dao.mapper.SysUserMapper;
import net.codingme.boot.domain.SysUser;
import net.codingme.boot.service.SysUserService;
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

    // 每个类都需要重写此方法
//    @Override
//    public MybatisMapper<SysUser, Integer> getMapper() {
//        return sysUserMapper;
//    }

    @Override
    public List<SysUser> queryUserAndRoles() {
        return sysUserMapper.queryUserAndRoles();
    }

    @Override
    public List<SysUser> querySelectByUsernameExample(String username) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        //模糊查询
        if (!StringUtils.isEmpty(username)) {
            criteria.andLike("username", "%" + username + "%");
        }
        return sysUserMapper.selectByExample(example);
    }

    @Override
    public int deleteUser(SysUser sysUser) {
        return sysUserMapper.deleteByExample(sysUser);
    }

}
