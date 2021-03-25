package net.codingme.boot.domain.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.codingme.boot.domain.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookMapperTest {

    @Resource
    private BookMapper bookMapper;

    @Test
    public void testSelectOne() {
        Book book = new Book();
        book.setId(2);
        Book selectOne = bookMapper.selectOne(book);
        Assert.assertNotNull(selectOne);
        System.out.println(selectOne);
    }

    @Test
    public void testSelectByPrimaryKey() {
        Book book = bookMapper.selectByPrimaryKey(2);
        Assert.assertNotNull(book);
        System.out.println(book);
    }

    /**
     * 分页测试
     */
    @Test
    public void testSelectPageInfo() {
        //获取第2页，3条内容，默认查询总数count
        // pageNum:当前页码数，第一次进来时默认为1（首页）， pageNum都是从前端传递过来的。
        PageHelper.startPage(1, 10);
//      list:页面要展示的数据的集合
//      List<Book> bookList = bookMapper.selectAll();

        List<Book> bookList = bookMapper.selectListByName("罗贯中");

        Assert.assertNotNull(bookList);

        System.out.println("查询出数量：" + bookList.size());
        //pageInfo:将分页数据和显示的数据封装到PageInfo当中
        PageInfo<Book> pageInfo = PageInfo.of(bookList);
        //将封装好的数据返回到前台页面， 返回ResponseBody
        System.out.println("总数量：" + pageInfo.getTotal());
        System.out.println("总页数：" + pageInfo.getPages());
        System.out.println("每页显示条数：" + pageInfo.getPageSize());
        System.out.println("第几页：" + pageInfo.getPageNum());
        System.out.println("当前量：" + pageInfo.getSize());
    }

    /**
     * 分页测试
     */
    @Test
    public void testSelectPage() {
        PageHelper.startPage(2, 3);
        List<Book> bookList = bookMapper.selectAll();
        Assert.assertNotNull(bookList);
        System.out.println("查询出数量：" + bookList.size());
        System.out.println("总数量：" + ((Page) bookList).getTotal());
        System.out.println("总页数：" + ((Page) bookList).getPages());
        System.out.println("第几页：" + ((Page) bookList).getPageNum());
    }

}