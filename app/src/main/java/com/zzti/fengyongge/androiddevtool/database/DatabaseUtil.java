package com.zzti.fengyongge.androiddevtool.database;

/**
 * Created by fengyongge on 2016/7/19 0019.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.orhanobut.logger.Logger;
import com.zzti.fengyongge.androiddevtool.bean.DataBaseBean;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private MyHelper helper;

    public DatabaseUtil(Context context) {
        super();
        helper = new MyHelper(context);
    }


    /**
     * 插入数据
     *
     */
    public boolean Insert(DataBaseBean DataBaseBean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into " + MyHelper.TABLE_NAME
                + "(content) values ("
                + "'" + DataBaseBean.getSearchConten() + "'"
                + ")";
        Logger.i("sql"+sql);
        try {
            db.execSQL(sql);
            return true;
        } catch (SQLException e) {

            Logger.i("insert failed");

            return false;
        } finally {
            db.close();
        }

    }


    /**
     * 更新数据
     *
     */

    public void Update(DataBaseBean DataBaseBean, int id) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", DataBaseBean.getSearchConten());
        int rows = db.update(MyHelper.TABLE_NAME, values, "_id=?", new String[]{id + ""});

        db.close();
    }

    /**
     * 删除数据
     *
     */

    public void Delete(int id) {

        SQLiteDatabase db = helper.getWritableDatabase();
        int raw = db.delete(MyHelper.TABLE_NAME, "_id=?", new String[]{id + ""});
        db.close();
    }

    /**
     * 查询所有数据
     */
    public List<DataBaseBean> queryAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<DataBaseBean> list = new ArrayList<DataBaseBean>();
        Cursor cursor = db.query(MyHelper.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            DataBaseBean DataBaseBean = new DataBaseBean();
            DataBaseBean.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            DataBaseBean.setSearchConten(cursor.getString(cursor.getColumnIndex("content")));
            list.add(DataBaseBean);
        }
        db.close();
        return list;
    }



    public void deleteDatabase(Context context) {
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(MyHelper.TABLE_NAME,null,null);

        Logger.i("0000");
    }



}

