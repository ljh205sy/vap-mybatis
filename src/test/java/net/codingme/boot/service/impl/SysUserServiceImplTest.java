package net.codingme.boot.service.impl;

import net.codingme.boot.domain.SysRole;
import net.codingme.boot.domain.SysUser;
import net.codingme.boot.service.SysUserService;
import net.codingme.boot.vo.SysUserVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liujinhui
 * date 2021/3/25 10:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private SysUserService sysUserService;

    @Test
    public void queryUserAndRoles() {
        List<SysUser> userList = sysUserService.queryUserAndRoles();
        Assert.assertNotNull(userList);
        System.out.println(userList);
        for (SysUser sysUser : userList) {
            List<SysRole> roleList = sysUser.getRoleList();
            for (SysRole sysRole : roleList) {
                System.out.println(sysRole);
            }
        }
    }

    /**
     * 通过example进行查询,模糊查询
     */
    @Test
    public void querySelectByExample() {
        List<SysUser> userList = sysUserService.querySelectByExample();
        for (SysUser user : userList) {
            System.out.println("查询的用户信息:" + user);
        }
    }

    @Test
    public void querySelectByPrimaryKey() {
        SysUser sysUser = sysUserService.selectByPrimaryKey(48);
        Assert.assertNotNull(sysUser);
        System.out.println(sysUser);
    }

    @Test
    public void insertSuccess() {
        SysUserVO source = new SysUserVO(2, "username", "password", "name");
        SysUser target = new SysUser();
        BeanUtils.copyProperties(source, target);
        int insert = sysUserService.insert(target);
        System.out.println(insert);
    }

    @Test
    public void updateSuccess() {
        SysUserVO source = new SysUserVO(2, "update-username", "update-password", "update-name");
        SysUser target = new SysUser();
        BeanUtils.copyProperties(source, target);
        int number = sysUserService.updateByPrimaryKey(target);
        System.out.println(number);
    }

    @Test
    public void deleteSuccess() {
        int number = sysUserService.deleteByPrimaryKey(2);
        System.out.println(number);
    }


}