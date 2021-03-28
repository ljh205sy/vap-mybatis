package net.codingme.boot.service.impl;

import com.github.pagehelper.PageInfo;
import net.codingme.boot.domain.SysUser;
import net.codingme.boot.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author liujinhui
 * date 2021/3/28 11:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private SysUserService sysUserService;

    @Test
    public void testQueryByProperty() {
        List<SysUser> userList = sysUserService.findByProperty("name", "liujinhui");
        print(userList);
    }

    private void print(List<SysUser> userList) {
        if (!CollectionUtils.isEmpty(userList)) {
            userList.stream().forEach(sysUser -> {
                logger.info(String.format("%s", sysUser));
            });
        }
    }

    @Test
    public void testQueryByProperty2() {
        List<SysUser> userList = sysUserService.findByProperty(SysUser.class, "name", "刘进");
        print(userList);
    }

    @Test
    public void testFindByPropertyAndSort() {
        String sortOrderByClause = "id desc, name asc";
        List<SysUser> userList = sysUserService.findByPropertyAndSort("name", "刘进", sortOrderByClause);
        print(userList);
    }

    // 查询出多条，或者未空时会抛出异常
    @Test
    public void testSelectOne() {
        SysUser sysUser = new SysUser();
        sysUser.setName("刘进3");
        sysUser.setUsername("jock1");
        SysUser one = sysUserService.selectOne(sysUser);
    }

    @Test(expected = FileNotFoundException.class)
    public void usingExpected() throws FileNotFoundException {
        // 不会抛出FileNotFoundException的代码写在上面
        new FileInputStream("不存在的文件路径");
    }


    /**
     * 删除数据，并回滚数据
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteByIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(238);
        ids.add(240);
        Integer id = sysUserService.deleteByIds("id", ids);
        System.out.println(id);
    }

    @Test
    public void testPageInfoByExample() {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        SysUser sysUser = new SysUser();
        sysUser.setUsername("jock");
        //模糊查询
        if (!StringUtils.isEmpty(sysUser.getUsername())) {
            criteria.andLike("username", "%" + sysUser.getUsername() + "%");
        }
        example.setOrderByClause("id desc, name asc");
        PageInfo<SysUser> pageInfo = sysUserService.findPageExample(1, 3, example);
        //将封装好的数据返回到前台页面， 返回ResponseBody
        System.out.println("总数量：" + pageInfo.getTotal());
        System.out.println("总页数：" + pageInfo.getPages());
        System.out.println("每页显示条数：" + pageInfo.getPageSize());
        System.out.println("第几页：" + pageInfo.getPageNum());
        System.out.println("当前量：" + pageInfo.getSize());
        List<SysUser> list = pageInfo.getList();
        print(list);
    }


    @Test
    public void testInsertList() {
        List<SysUser> userList = new ArrayList<>();
        for (int i = 0, j = 4; i < 4; i++, j--) {
            SysUser user = new SysUser();
            user.setUsername("jock" + i);
            user.setName("刘进" + j);
            user.setPassword("12345" + i);
            user.setPhone(getPhotoString());
            userList.add(user);
        }
        int number = sysUserService.insertList(userList);
        System.out.println(number);
    }

    private String getPhotoString() {
        String phone = "139";//定义电话号码以139开头
        Random random = new Random();//定义random，产生随机数
        for (int j = 0; j < 8; j++) {
            //生成0~9 随机数
            phone += random.nextInt(9);
        }
        return phone;
    }
}
