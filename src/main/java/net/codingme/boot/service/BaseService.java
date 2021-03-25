package net.codingme.boot.service;

import java.io.Serializable;

/**
 * @author liujinhui
 * date 2021/3/25 10:02
 */
public interface BaseService<T, ID extends Serializable> {

    int deleteByPrimaryKey(ID id);

    int insert(T record);

    T selectByPrimaryKey(ID id);

    int updateByPrimaryKey(T record) ;
}
