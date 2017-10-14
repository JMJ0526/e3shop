package org.jmj.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jmj.bean.TbOrderIetm;
import org.jmj.bean.TbOrderIetmExample;

public interface TbOrderIetmMapper {
	
	void insertList( @Param("list") List<TbOrderIetm> list);
	
    long countByExample(TbOrderIetmExample example);

    int deleteByExample(TbOrderIetmExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbOrderIetm record);

    int insertSelective(TbOrderIetm record);

    List<TbOrderIetm> selectByExample(TbOrderIetmExample example);

    TbOrderIetm selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbOrderIetm record, @Param("example") TbOrderIetmExample example);

    int updateByExample(@Param("record") TbOrderIetm record, @Param("example") TbOrderIetmExample example);

    int updateByPrimaryKeySelective(TbOrderIetm record);

    int updateByPrimaryKey(TbOrderIetm record);
}