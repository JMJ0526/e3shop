package org.jmj.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jmj.bean.TbItemParmItem;
import org.jmj.bean.TbItemParmItemExample;

public interface TbItemParmItemMapper {
    long countByExample(TbItemParmItemExample example);

    int deleteByExample(TbItemParmItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItemParmItem record);

    int insertSelective(TbItemParmItem record);

    List<TbItemParmItem> selectByExampleWithBLOBs(TbItemParmItemExample example);

    List<TbItemParmItem> selectByExample(TbItemParmItemExample example);

    TbItemParmItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItemParmItem record, @Param("example") TbItemParmItemExample example);

    int updateByExampleWithBLOBs(@Param("record") TbItemParmItem record, @Param("example") TbItemParmItemExample example);

    int updateByExample(@Param("record") TbItemParmItem record, @Param("example") TbItemParmItemExample example);

    int updateByPrimaryKeySelective(TbItemParmItem record);

    int updateByPrimaryKeyWithBLOBs(TbItemParmItem record);

    int updateByPrimaryKey(TbItemParmItem record);
}