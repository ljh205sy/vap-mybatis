package net.codingme.boot.service.impl;

import net.codingme.boot.domain.SysUser;
import net.codingme.boot.domain.mapper.SysUserMapper;
import net.codingme.boot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void setBaseMapper(){
        super.setBaseMapper(sysUserMapper);
    }

    @Override
    public List<SysUser> queryUserAndRoles() {
        List<SysUser> userList = sysUserMapper.queryUserAndRoles();
        return userList;
    }
}
