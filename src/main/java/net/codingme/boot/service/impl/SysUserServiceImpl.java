package net.codingme.boot.service.impl;

import net.codingme.boot.domain.SysUser;
import net.codingme.boot.domain.mapper.SysUserMapper;
import net.codingme.boot.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liujinhui
 * date 2021/3/25 10:10
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer> implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    public void setBaseMapper(){
        super.setBaseMapper(sysUserMapper);
    }
}
