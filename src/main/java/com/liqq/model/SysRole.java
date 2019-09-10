package com.liqq.model;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table sys_role
 */
public class SysRole {
    /**
     * Database Column Remarks:
     *   主键
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.id
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   代码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.code
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    private String code;

    /**
     * Database Column Remarks:
     *   名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.name
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    private String name;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.create_time
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   更新时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.update_time
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.id
     *
     * @return the value of sys_role.id
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.id
     *
     * @param id the value for sys_role.id
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.code
     *
     * @return the value of sys_role.code
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.code
     *
     * @param code the value for sys_role.code
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.name
     *
     * @return the value of sys_role.name
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.name
     *
     * @param name the value for sys_role.name
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.create_time
     *
     * @return the value of sys_role.create_time
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.create_time
     *
     * @param createTime the value for sys_role.create_time
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.update_time
     *
     * @return the value of sys_role.update_time
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.update_time
     *
     * @param updateTime the value for sys_role.update_time
     *
     * @mbg.generated Tue Sep 10 19:31:15 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}