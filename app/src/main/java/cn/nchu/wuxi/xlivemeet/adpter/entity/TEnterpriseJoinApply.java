package cn.nchu.wuxi.xlivemeet.adpter.entity;

import java.io.Serializable;

public class TEnterpriseJoinApply implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_join_apply.enterprise_id
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    private Integer enterpriseId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_join_apply.phone
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_join_apply.state
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_join_apply.apply_name
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    private String applyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_enterprise_join_apply
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_join_apply.enterprise_id
     *
     * @return the value of t_enterprise_join_apply.enterprise_id
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_join_apply.enterprise_id
     *
     * @param enterpriseId the value for t_enterprise_join_apply.enterprise_id
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_join_apply.phone
     *
     * @return the value of t_enterprise_join_apply.phone
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_join_apply.phone
     *
     * @param phone the value for t_enterprise_join_apply.phone
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_join_apply.state
     *
     * @return the value of t_enterprise_join_apply.state
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_join_apply.state
     *
     * @param state the value for t_enterprise_join_apply.state
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_join_apply.apply_name
     *
     * @return the value of t_enterprise_join_apply.apply_name
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    public String getApplyName() {
        return applyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_join_apply.apply_name
     *
     * @param applyName the value for t_enterprise_join_apply.apply_name
     *
     * @mbg.generated Fri Apr 24 11:23:46 CST 2020
     */
    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }
}