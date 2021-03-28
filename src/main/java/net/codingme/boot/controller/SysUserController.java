package net.codingme.boot.controller;

import io.swagger.annotations.*;
import net.codingme.boot.domain.SysUser;
import net.codingme.boot.exception.UserNotExistException;
import net.codingme.boot.service.SysUserService;
import net.codingme.boot.util.Result;
import net.codingme.boot.util.ResultCodeEnum;
import net.codingme.boot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author liujinhui
 * date 2021/3/26 11:47
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "获取用户并加载角色")
    @GetMapping
    public List<SysUser> queryUsersByRoles() {
        List<SysUser> userList = sysUserService.queryUserAndRoles();
        return userList;
    }

    /**
     * 新增用户
     * @return int
     */
    @ApiOperation("新增用户")
    @PostMapping
    public int insertUser(@RequestBody SysUser sysUser){
        int insert = sysUserService.insert(sysUser);
        return insert;
    }


    @ResponseBody
    @PutMapping
    @ApiOperation(value = "用户修改", notes = "用户修改", hidden = false)
    public Result<Boolean> updateUser(@Valid @RequestBody SysUser sysUser, BindingResult errors) {
        return null;
    }

    @DeleteMapping("/{userid}}")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "userid",value = "用户id")})
    @ApiOperation(value = "用户删除", notes = "删除用户")
    public Result<Boolean> delete(@PathVariable @ApiParam("用户id") Integer userid) {
        try {
            SysUser queryUser = new SysUser();
            queryUser.setId(userid);
            SysUser sysUser = sysUserService.selectOne(queryUser);
            if(sysUser == null) {
                throw new UserNotExistException();
            }
            int number = sysUserService.deleteUser(sysUser);
        } catch (Exception e) {
            return ResultUtil.error(ResultCodeEnum.UNKNOW_FAILED);
        }
        return ResultUtil.success(true);
    }
}
