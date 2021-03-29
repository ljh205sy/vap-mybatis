package net.codingme.boot.dao.mapper;

import net.codingme.boot.domain.SysRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author liujinhui
 * date 2021/3/28 20:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysRoleMapperTest {

    @Resource
    private SysRoleMapper sysRoleMapper;
    
    @Test
    public void queryRoleByRid() {
        SysRole sysRole = sysRoleMapper.queryRoleByRid(6);
        System.out.println(sysRole);
    }


}