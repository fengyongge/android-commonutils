package com.zzti.fengyongge.androiddevtool.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengyongge
 * @date 2017/5/23 0003
 * @description
 * 登录bean
 */

public class LoginBean implements Serializable {

    private String email;
    private String gender;
    private String id;
    private String is_admin;
    private String login_ip;
    private String mobile;
    private String password;
    private String status;
    private String supplier_id;
    private String username;
    List<BelongDepBean> belongDep = new ArrayList<>();
    List<BelongDepBean> adminDep = new ArrayList<>();
    private String header_img;
    private String staff_image;


    public List<BelongDepBean> getAdminDep() {
        return adminDep;
    }

    public void setAdminDep(List<BelongDepBean> adminDep) {
        this.adminDep = adminDep;
    }

    public String getStaff_image() {
        return staff_image;
    }

    public void setStaff_image(String staff_image) {
        this.staff_image = staff_image;
    }

    public String getHeader_img() {
        return header_img;
    }

    public void setHeader_img(String header_img) {
        this.header_img = header_img;
    }

    public List<BelongDepBean> getBelongDep() {
        return belongDep;
    }

    public void setBelongDep(List<BelongDepBean> belongDep) {
        this.belongDep = belongDep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public String getLogin_ip() {
        return login_ip;
    }

    public void setLogin_ip(String login_ip) {
        this.login_ip = login_ip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }







}
