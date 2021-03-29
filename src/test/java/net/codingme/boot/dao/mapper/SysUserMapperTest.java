package net.codingme.boot.dao.mapper;


import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.codingme.boot.domain.SysRole;
import net.codingme.boot.domain.SysUser;
import net.codingme.boot.domain.SysUserRole;
import net.codingme.boot.exception.UserNotExistException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author liujinhui
 * date 2021/3/24 10:56
 */
// https://blog.csdn.net/danxiaodeshitou/article/details/104848871
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserMapperTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 级联插入
     * 新增用户的时候，插入角色信息
     */
    @Test
    public void testInsertUserAndRoles() {
        SysUser sysUser = new SysUser();
        sysUser.setName("testuser");
        sysUser.setUsername("testuseruname");
        // 返回带有自动增长列的id值
//        int one = sysUserMapper.insertSelective(sysUser);  // 返回的是操作的条数
        System.out.println("id值:" + sysUser.getId());
        int two = sysUserMapper.insertUseGeneratedKeys(sysUser);
//        int three = sysUserMapper.insert(sysUser);
//        sysUserMapper.insertList(list);

        SysRole sysRole = sysRoleMapper.queryRoleByRid(6);
        ArrayList<SysRole> rlist = new ArrayList<>();
        rlist.add(sysRole);
        sysUser.setRoles(rlist);

        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(sysRole.getId());
        userRole.setUserId(sysUser.getId());
        sysUserRoleMapper.insert(userRole);
    }

    @Test
    public void updateUserAndRoles() {
        SysUser sysUser = sysUserMapper.selectOne(new SysUser(252));

        // 通过用户查询，该用户拥有的角色
        List<SysRole> roles = sysRoleMapper.queryRoleCollectionUid(sysUser.getId());

        List<Integer> ids = new ArrayList<>();
        roles.stream().forEach(role -> {
            ids.add(role.getId());
        });

        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        //模糊查询
        if (!StringUtils.isEmpty(sysUser.getId())) {
            criteria.andEqualTo("userId", sysUser.getId());
        }

        if(!CollectionUtil.isEmpty(ids)) {
            criteria.andIn("roleId", ids);
        }
        // 删除中间表中该用户的角色信息
        sysUserRoleMapper.deleteByExample(example);

        // 插入该用户的角色信息
        List<SysUserRole> userRoles = getSysUserRoles(sysUser.getId());
        sysUserRoleMapper.insertList(userRoles);
    }

    private List<SysUserRole> getSysUserRoles(Integer uid) {
        SysRole sysRole1 = sysRoleMapper.queryRoleByRid(6);
        SysRole sysRole2 = sysRoleMapper.queryRoleByRid(17);

        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(sysRole1.getId());
        userRole.setUserId(uid);

        SysUserRole userRole2 = new SysUserRole();
        userRole2.setRoleId(sysRole2.getId());
        userRole2.setUserId(uid);

        List<SysUserRole> sysUserRoles = new ArrayList<>();
        sysUserRoles.add(userRole);
        sysUserRoles.add(userRole2);
        return sysUserRoles;
    }

    /**
     * 延迟加载
     * 一个用户，拥有多个角色，延迟加载，通过参数传递，然后第二次查询
     */
    @Test
    public void queryUserAndRoles() {
        List<SysUser> userList = sysUserMapper.queryUserAndRoles();
        for (SysUser sysUser : userList) {
            logger.info(String.format("用户信息： %s", sysUser));
            List<SysRole> roleList = sysUser.getRoles();
            for (SysRole sysRole : roleList) {
                String u = sysUser.getId() + "," + sysUser.getUsername();
                String r = sysRole.getId() + "," + sysRole.getName();
                logger.info(String.format("用户信息： %s  , 角色信息： %s", u, r));
            }
        }
    }

    /**
     * 非延迟加载
     * 通过用户，查询所有角色，非延迟加载
     */
    @Test
    public void queryUserAndRolesById() {
        List<SysUser> userList = sysUserMapper.queryUserAndRolesById(48);
        for (SysUser sysUser : userList) {
            logger.info(String.format("用户信息： %s", sysUser));
            List<SysRole> roleList = sysUser.getRoles();
            for (SysRole sysRole : roleList) {
                String u = sysUser.getId() + "," + sysUser.getUsername();
                String r = sysRole.getId() + sysRole.getName();
                logger.info(String.format("用户信息： %s  , 角色信息： %s", u, r));
            }
        }
    }


    /**
     * 查询所有数据
     */
    @Test
    public void querySelectAll() {
        List<SysUser> sysUsers = sysUserMapper.selectAll();
        for (SysUser sysUser : sysUsers) {
            logger.info(sysUser.getName());
        }
    }

//    @Test
//    //通用Mapper接口,根据ids查询
//    public void querySelectByIds() {
////        String[] ids = new String[]{ "1","2"};
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        Integer[] ids = list.toArray(new Integer[list.size()]);
//        List<SysUser> userList = sysUserMapper.selectByIds(ids);
//        for (SysUser sysUser : userList) {
//            System.out.println(sysUser);
//        }
//    }

    @Test
    public void querySelectPageInfo() {
        PageHelper.startPage(1, 3);
        List<SysUser> userList = sysUserMapper.selectAll();
        for (SysUser sysUser : userList) {
            System.out.println(sysUser);
        }
        Assert.assertNotNull(userList);
        System.out.println("查询出数量：" + userList.size());
        //pageInfo:将分页数据和显示的数据封装到PageInfo当中
        PageInfo<SysUser> pageInfo = PageInfo.of(userList);
        //将封装好的数据返回到前台页面， 返回ResponseBody
        System.out.println("总数量：" + pageInfo.getTotal());
        System.out.println("总页数：" + pageInfo.getPages());
        System.out.println("每页显示条数：" + pageInfo.getPageSize());
        System.out.println("第几页：" + pageInfo.getPageNum());
        System.out.println("当前页面的总数量：" + pageInfo.getSize());
    }


    @Test
    public void querySelectByExample() {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        SysUser sysUser = new SysUser();
        sysUser.setUsername("test2");
        //模糊查询
        if (!StringUtils.isEmpty(sysUser.getUsername())) {
            criteria.andLike("username", "%" + sysUser.getUsername() + "%");
        }
        List<SysUser> userList = sysUserMapper.selectByExample(example);
        for (SysUser user : userList) {
            System.out.println(user);
        }
    }

    /**
     * ==> Preparing: SELECT id,avatar,username,password,salt,name,birthday,sex,email,phone,status,create_time,update_time FROM sys_user WHERE id = ? AND avatar = ? AND username = ? AND password = ? AND salt = ? AND name = ? AND birthday = ? AND sex = ? AND email = ? AND phone = ? AND status = ? AND create_time = ? AND update_time = ?
     * ==> Parameters: 46(Integer), 46(Integer), 46(Integer), 46(Integer), 46(Integer), 46(Integer), 46(Integer), 46(Integer), 46(Integer), 46(Integer), 46(Integer), 46(Integer), 46(Integer)
     * <==      Total: 0
     * selectByPrimaryKey 不是靠组件进行的查找，而是把所有字段都填充成了46,
     */
    @Test
    public void testdeleteByPrimaryKey() {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(46);
        if (sysUser == null) {
            throw new UserNotExistException();
        }
    }

    /**
     * 查询不到用户，抛出异常并捕获
     */
    @Test(expected = UserNotExistException.class)
    public void testSelectOneException() {
        SysUser queryUser = new SysUser();
        queryUser.setId(46);
        SysUser sysUser = sysUserMapper.selectOne(queryUser);
        if (!Optional.ofNullable(sysUser).isPresent()) {
            throw new UserNotExistException();
        }
    }

    /**
     * 根据id进行查询
     */
    @Test
    public void testSelectOne() {
        SysUser queryUser = new SysUser();
        queryUser.setId(155);
        SysUser sysUser = sysUserMapper.selectOne(queryUser);
        System.out.println(sysUser);
    }

    /**
     * 删除sysUser表中的所有数据，【使用时需要注意】
     */
    @Test
    public void testDelete() {
//        int delete = sysUserMapper.delete(new SysUser());
//        System.out.println(delete);
    }

    /**
     * Preparing: DELETE FROM sys_user WHERE id = ? AND avatar = ? AND username = ? AND password = ? AND salt = ? AND name = ? AND birthday = ? AND sex = ? AND email = ? AND phone = ? AND status = ? AND create_time = ? AND update_time = ?
     * Parameters: null, null, mmmm(String), null, null, nn(String), null, null, null, null, null, null, null
     * <==    Updates: 0
     * <p>
     * 删除对象
     */
    @Test
    public void testDeleteByPrimaryKey() {
        SysUser sysUser = new SysUser();
        sysUser.setName("nn");
        sysUser.setUsername("mmmm");
        sysUserMapper.deleteByPrimaryKey(sysUser);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号。
     * 根据对象的属性的设值，然后进行查询
     */
    @Test
    public void querySelectOne() {
        SysUser user = new SysUser();
        user.setName("test2");
        SysUser sysUser = sysUserMapper.selectOne(user);
        System.out.println(sysUser);
    }

    /**
     * 依靠条件的删除
     * 删除usename 是like 字符串为"username"的数据
     */
    @Test
    public void testDeleteByExample() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("username");

        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        //模糊查询
        if (!StringUtils.isEmpty(sysUser.getUsername())) {
            criteria.andLike("username", "%" + sysUser.getUsername() + "%");
        }
        sysUserMapper.deleteByExample(example);
    }


    /**
     * 通过条件的example的查询方式，也可以在mapper中自定义sql语句并做java的对象映射
     */
    @Test
    public void testSelectOneByExample() {
        SysUser sysUser = new SysUser();
        sysUser.setName("nn");
        sysUser.setUsername("mmmm");
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("username", "%" + sysUser.getUsername() + "%");
        criteria.andEqualTo("name", sysUser.getName());
        sysUserMapper.selectOneByExample(example);
    }


    /**
     * ==>  Preparing: SELECT id,avatar,username,password,salt,name,birthday,sex,email,phone,status,create_time,update_time FROM sys_user WHERE id = ? AND avatar = ? AND username = ? AND password = ? AND salt = ? AND name = ? AND birthday = ? AND sex = ? AND email = ? AND phone = ? AND status = ? AND create_time = ? AND update_time = ?
     * ==> Parameters: 46(Integer), null, null, null, null, null, null, null, null, null, null, null, null
     * <==      Total: 0
     * 如上查询方式，会把所有的字段的值作为查询条件，如果不设置，属性即为空
     */
    @Test
    public void querySelectByPrimaryKey() {
        SysUser sysUser = new SysUser();
        sysUser.setId(46);
        SysUser user = sysUserMapper.selectByPrimaryKey(sysUser);
        System.out.println(user);
    }

    @Test
    public void queryUserByUsername() {
        SysUser sysUser = sysUserMapper.queryByUsername("test2");
        Assert.assertNotNull(sysUser);
        System.out.println(sysUser);
    }

    @Test
    public void testQueryUserList() {
        List<SysUser> userList = sysUserMapper.queryUserList();
        Assert.assertNotNull(userList);
        for (SysUser sysUser : userList) {
            System.out.println(sysUser);
        }
    }


}