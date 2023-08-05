package com.boilerplate.server.Service;

import com.boilerplate.server.dao.AreaMapper;
import com.boilerplate.server.model.Area;
import com.boilerplate.server.model.AreaExample;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService implements AreaMapper {
    @Override
    public int countByExample(AreaExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(AreaExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Area record) {
        return 0;
    }

    @Override
    public int insertSelective(Area record) {
        return 0;
    }

    @Override
    public List<Area> selectByExample(AreaExample example) {
        return null;
    }

    @Override
    public Area selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(Area record, AreaExample example) {
        return 0;
    }

    @Override
    public int updateByExample(Area record, AreaExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Area record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Area record) {
        return 0;
    }
}
