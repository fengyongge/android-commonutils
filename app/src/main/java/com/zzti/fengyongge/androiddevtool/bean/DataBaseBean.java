package com.zzti.fengyongge.androiddevtool.bean;

import java.io.Serializable;

/**
 * @author fengyongge
 * @Description
 */
public class DataBaseBean implements Serializable {

    private String searchConten;
    private int id;


    public String getSearchConten() {
        return searchConten;
    }

    public void setSearchConten(String searchConten) {
        this.searchConten = searchConten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
