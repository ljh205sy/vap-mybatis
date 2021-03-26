package net.codingme.boot.controller;

import net.codingme.boot.domain.SysUser;
import net.codingme.boot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liujinhui
 * date 2021/3/26 11:47
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    public List<SysUser> queryUsersByRoles() {
        List<SysUser> userList = sysUserService.queryUserAndRoles();
        return userList;
    }
    
    @PostMapping
    public int insertUser(@RequestBody SysUser sysUser){
        int insert = sysUserService.insert(sysUser);
        return insert;
    }
}
