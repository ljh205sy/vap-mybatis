package net.codingme.boot.domain.mapper;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.codingme.boot.domain.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujinhui
 * date 2021/3/24 10:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserMapperTest {

    @Resource
    private SysUserMapper sysUserMapper;

    @Test
    public void querySelectAll() {
        List<SysUser> sysUsers = sysUserMapper.selectAll();
        for (SysUser sysUser : sysUsers) {
            System.out.println(sysUser.getName());
        }
    }

    @Test
    // 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号。
    public void querySelectOne() {
        SysUser user = new SysUser();
        user.setName("test2");
        user.setId(1);
        SysUser sysUser = sysUserMapper.selectOne(user);
        System.out.println(sysUser);
    }

    @Test
    //通用Mapper接口,根据ids查询
    public void querySelectByIds(){
//        String[] ids = new String[]{ "1","2"};
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
       Integer[] ids = list.toArray(new Integer[list.size()]);
        List<SysUser> userList = sysUserMapper.selectByIds(ids);
        for (SysUser sysUser : userList) {
            System.out.println(sysUser);
        }
    }

    @Test
    //根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
    public void querySelectByPrimaryKey() {
        SysUser sysUser = new SysUser();
        sysUser.setId(46);
        SysUser user = sysUserMapper.selectByPrimaryKey(sysUser);
        System.out.println(user);
    }

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