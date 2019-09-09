package com.liqq.model;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table sys_cache
 */
public class SysCache {
    /**
     * Database Column Remarks:
     *   主键
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_cache.id
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   key
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_cache.k
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    private String k;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_cache.create_time
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   过期时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_cache.expire_time
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    private Date expireTime;

    /**
     * Database Column Remarks:
     *   value
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_cache.v
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    private String v;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_cache.id
     *
     * @return the value of sys_cache.id
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_cache.id
     *
     * @param id the value for sys_cache.id
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_cache.k
     *
     * @return the value of sys_cache.k
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public String getK() {
        return k;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_cache.k
     *
     * @param k the value for sys_cache.k
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public void setK(String k) {
        this.k = k == null ? null : k.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_cache.create_time
     *
     * @return the value of sys_cache.create_time
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_cache.create_time
     *
     * @param createTime the value for sys_cache.create_time
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_cache.expire_time
     *
     * @return the value of sys_cache.expire_time
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_cache.expire_time
     *
     * @param expireTime the value for sys_cache.expire_time
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_cache.v
     *
     * @return the value of sys_cache.v
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public String getV() {
        return v;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_cache.v
     *
     * @param v the value for sys_cache.v
     *
     * @mbg.generated Mon Sep 09 14:29:17 CST 2019
     */
    public void setV(String v) {
        this.v = v == null ? null : v.trim();
    }
}