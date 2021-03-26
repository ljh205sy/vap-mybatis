package net.codingme.boot.service.impl;

import net.codingme.boot.service.BaseService;
import tk.mybatis.mapper.common.BaseMapper;

import java.io.Serializable;

/**
 * @author liujinhui
 * date 2021/3/25 10:02
 */
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    private BaseMapper<T> baseMapper;

    /**
     *  spring中进行Mapper的注入
     * @param baseMapper
     */
    public void setBaseMapper(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public int deleteByPrimaryKey(ID id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return baseMapper.insert(record);
    }

    @Override
    public T selectByPrimaryKey(ID id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

}
