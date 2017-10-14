package org.jmj.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jmj.bean.TbItemParm;
import org.jmj.bean.TbItemParmExample;

public interface TbItemParmMapper {
	
    long countByExample(TbItemParmExample example);

    int deleteByExample(TbItemParmExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItemParm record);

    int insertSelective(TbItemParm record);

    List<TbItemParm> selectByExampleWithBLOBs(TbItemParmExample example);

    List<TbItemParm> selectByExample(TbItemParmExample example);

    TbItemParm selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItemParm record, @Param("example") TbItemParmExample example);

    int updateByExampleWithBLOBs(@Param("record") TbItemParm record, @Param("example") TbItemParmExample example);

    int updateByExample(@Param("record") TbItemParm record, @Param("example") TbItemParmExample example);

    int updateByPrimaryKeySelective(TbItemParm record);

    int updateByPrimaryKeyWithBLOBs(TbItemParm record);

    int updateByPrimaryKey(TbItemParm record);
}