package com.liqq.dao.mysql;

import com.liqq.model.SysDict;
import com.liqq.model.SysDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysDictMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    long countByExample(SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    int deleteByExample(SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    int insert(SysDict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    int insertSelective(SysDict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    List<SysDict> selectByExample(SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    SysDict selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    int updateByExampleSelective(@Param("record") SysDict record, @Param("example") SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    int updateByExample(@Param("record") SysDict record, @Param("example") SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    int updateByPrimaryKeySelective(SysDict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbg.generated Tue Aug 20 21:11:45 CST 2019
     */
    int updateByPrimaryKey(SysDict record);
}