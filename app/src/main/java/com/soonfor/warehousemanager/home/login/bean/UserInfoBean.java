package com.soonfor.warehousemanager.home.login.bean;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/14 0014 11:20
 * 邮箱：suibozhu@139.com
 * 当前登录用户
 */

public class UserInfoBean {

    /**
     * {"usercode":"Admin","username":"系统管理员","isadmin":true,"sex":"","empname":"","deptname":""}
     */
    private String usercode;
    private String username;
    private boolean isadmin;
    private String sex;
    private String empname;//员工姓名
    private String deptname;//员工所在部门
    private String fempno;


    public String getUsercode() {
        return usercode==null?"":usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUsername() {
        return username==null?"":username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex==null?"":sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmpname() {
        return empname==null?"":empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getDeptname() {
        return deptname==null?"":deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public boolean isIsadmin() {
        return isadmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isadmin = isadmin;
    }

    public String getFempno() {
        return fempno==null?"":fempno;
    }

    public void setFempno(String fempno) {
        this.fempno = fempno;
    }
}
