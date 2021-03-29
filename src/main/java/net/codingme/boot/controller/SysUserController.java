package net.codingme.boot.controller;

import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.*;
import net.codingme.boot.domain.SysRole;
import net.codingme.boot.domain.SysUser;
import net.codingme.boot.exception.UserNotExistException;
import net.codingme.boot.service.SysUserRoleService;
import net.codingme.boot.service.SysUserService;
import net.codingme.boot.util.Result;
import net.codingme.boot.util.ResultCodeEnum;
import net.codingme.boot.util.ResultUtil;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liujinhui
 * date 2021/3/26 11:47
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
public class SysUserController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @ApiOperation(value = "获取用户并加载角色")
    @GetMapping
    public List<SysUser> queryUsersByRoles() {
        List<SysUser> userList = sysUserService.queryUserAndRoles();
        return userList;
    }

    /**
     * 新增用户
     *
     * @return int
     */
    @ApiOperation("新增用户")
    @PostMapping
    public Result<Boolean> insertUser(@Valid @RequestBody SysUser sysUser, BindingResult result) {
        checkParameters(result);
        logger.info(ReflectionToStringBuilder.toString(sysUser, ToStringStyle.MULTI_LINE_STYLE));
        sysUserService.insertSelective(sysUser);
        templateOperator(sysUser);
        return ResultUtil.success(true);
    }

    @ResponseBody
    @PutMapping
    @ApiOperation(value = "用户修改", notes = "用户修改", hidden = false)
    public Result<Boolean> updateUser(@Valid @RequestBody SysUser sysUser, BindingResult result) {
        checkParameters(result);
        logger.info(ReflectionToStringBuilder.toString(sysUser, ToStringStyle.MULTI_LINE_STYLE));
        sysUserService.updateSelective(sysUser);
        templateOperator(sysUser);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/{uid}}")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "userid", value = "用户id")})
    @ApiOperation(value = "用户删除", notes = "删除用户")
    public Result<Boolean> delete(@PathVariable @ApiParam("用户id") Integer uid) {
        try {
            SysUser queryUser = new SysUser();
            queryUser.setId(uid);
            SysUser sysUser = sysUserService.selectOne(queryUser);
            if (sysUser == null) {
                throw new UserNotExistException();
            }
            // 这个是真删除，注意这块可以写自己的业务逻辑
            int number = sysUserService.deleteUser(sysUser);
            // 删除用户的角色信息，一般是假删除，所以中间表中信息可以不删除

        } catch (Exception e) {
            return ResultUtil.error(ResultCodeEnum.UNKNOW_FAILED);
        }
        return ResultUtil.success(true);
    }


    /**
     * 检查参数
     * @param result
     */
    private void checkParameters(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            FieldError error = (FieldError) list.get(0);
            logger.info(error.getObjectName() + "" + error.getField() + "" + error.getDefaultMessage());
        }
    }

    /**
     * 模版模式：
     * 1.先对用户角色表进行删除
     * 2.在对角色用户表进行新增
     * @param sysUser
     */
    private void templateOperator(SysUser sysUser) {
        List<SysRole> roles = sysUser.getRoles();
        if (!CollectionUtil.isEmpty(roles)) {
            List<Integer> rids = roles.stream().map(p -> p.getId()).collect(Collectors.toList());
            sysUserRoleService.deleteUserAndRoles(sysUser.getId(), rids);
            sysUserRoleService.insertUserAndRoles(sysUser.getId(), rids);
        } else {
            sysUserRoleService.deleteUserAndRoles(sysUser.getId(), null);
        }
    }
}
