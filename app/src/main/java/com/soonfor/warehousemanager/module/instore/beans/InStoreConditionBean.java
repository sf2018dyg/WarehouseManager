package com.soonfor.warehousemanager.module.instore.beans;

import com.soonfor.warehousemanager.tools.CommUtil;

import java.io.Serializable;

/**
 * 作者：DC-DingYg on 2018/12/18
 * 类用途：出库条件自定义集合
 */
public class InStoreConditionBean implements Serializable {

    private String fbillCode;//单据代号
    private String fbillName;//单据名称
    private String fbinlocationCode;//储位代号
    private String fbinlocationName;//储位名称
    private String freasonCode;//原因代号
    private String freasonDesc;//原因描述
    private String fdeptCode;//部门代号
    private String fdeptName;//部门名称
    private String frukubillCode;//入库单据代号
    private String frukubillName;//入库单据名称

    public String getFbillCode() {
        return CommUtil.formatStr(fbillCode);
    }

    public void setFbillCode(String fbillCode) {
        this.fbillCode = fbillCode;
    }

    public String getFbillName() {
        return CommUtil.formatStr(fbillName==null?fbillCode:fbillName);
    }

    public void setFbillName(String fbillName) {
        this.fbillName = fbillName;
    }

    public String getFbinlocationCode() {
        return CommUtil.formatStr(fbinlocationCode);
    }

    public void setFbinlocationCode(String fbinlocationCode) {
        this.fbinlocationCode = fbinlocationCode;
    }

    public String getFbinlocationName() {
        return CommUtil.formatStr(fbinlocationName==null?fbinlocationCode:fbinlocationName);
    }

    public void setFbinlocationName(String fbinlocationName) {
        this.fbinlocationName = fbinlocationName;
    }

    public String getFreasonCode() {
        return CommUtil.formatStr(freasonCode);
    }

    public void setFreasonCode(String freasonCode) {
        this.freasonCode = freasonCode;
    }

    public String getFreasonDesc() {
        return CommUtil.formatStr(freasonDesc==null?freasonCode:freasonDesc);
    }

    public void setFreasonDesc(String freasonDesc) {
        this.freasonDesc = freasonDesc;
    }

    public String getFdeptCode() {
        return CommUtil.formatStr(fdeptCode);
    }

    public void setFdeptCode(String fdeptCode) {
        this.fdeptCode = fdeptCode;
    }

    public String getFdeptName() {
        return CommUtil.formatStr(fdeptName==null?fdeptCode:fdeptName);
    }

    public void setFdeptName(String fdeptName) {
        this.fdeptName = fdeptName;
    }

    public String getFrukubillCode() {
        return CommUtil.formatStr(frukubillCode);
    }

    public void setFrukubillCode(String frukubillCode) {
        this.frukubillCode = frukubillCode;
    }

    public String getFrukubillName() {
        return CommUtil.formatStr(frukubillName==null?frukubillCode:frukubillName);
    }

    public void setFrukubillName(String frukubillName) {
        this.frukubillName = frukubillName;
    }
}
