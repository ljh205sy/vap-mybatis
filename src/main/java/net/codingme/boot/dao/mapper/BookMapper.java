package net.codingme.boot.dao.mapper;

import net.codingme.boot.domain.Book;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author wh1107066
 */
public interface BookMapper extends MySqlMapper<Book>, Mapper<Book> {

     List<Book> selectListByName(String author);
}