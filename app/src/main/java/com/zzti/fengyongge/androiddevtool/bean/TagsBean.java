package com.zzti.fengyongge.androiddevtool.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengyonggge
 * @date 2017/5/23
 */
public class TagsBean {


    List<TagBean> list = new ArrayList<>();


    public List<TagBean> getList() {
        return list;
    }

    public void setList(List<TagBean> list) {
        this.list = list;
    }

    public class TagBean{

        private String number;

        private String updated_at;

        private String staff_id;

        private String name;

        private String created_at;

        private String remark;

        private String id;

        private String type;

        private String supplier_id;

        private String mark;

        private String is_delete;


        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }
    }



}
