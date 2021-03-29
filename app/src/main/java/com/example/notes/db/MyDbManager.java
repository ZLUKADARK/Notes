package com.example.notes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notes.adapter.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbhelper myDbhelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context) {
        this.context = context;
        myDbhelper = new MyDbhelper(context);
    }
    public void openDb(){
        db = myDbhelper.getWritableDatabase();
    }

    public void insertToDb(String title, String desc, String imguri){
        ContentValues cv = new ContentValues();
        cv.put(MyConstans.TITLE, title);
        cv.put(MyConstans.DESC, desc);
        cv.put(MyConstans.IMGURI, imguri);
        db.insert(MyConstans.TABLE_NAME, null, cv);
    }

    public void updateitem(String title, String desc, String imguri, int id){
        String select = MyConstans._ID + "=" + id;
        ContentValues cv = new ContentValues();
        cv.put(MyConstans.TITLE, title);
        cv.put(MyConstans.DESC, desc);
        cv.put(MyConstans.IMGURI, imguri);
        db.update(MyConstans.TABLE_NAME,cv, select,null);
    }

    public void deleteitem(int id){
        String select = MyConstans._ID + "=" + id;
        db.delete(MyConstans.TABLE_NAME,select,null);
    }

    public List<ListItem> getDb(String search){
        List<ListItem> tempList = new ArrayList<>();
        String selection = MyConstans.TITLE + " like ?";
        Cursor cursor = db.query(MyConstans.TABLE_NAME, null, selection,
                new String[]{"%" + search + "%"}, null, null, null);
        while (cursor.moveToNext()){
            ListItem item = new ListItem();
            String title = cursor.getString(cursor.getColumnIndex(MyConstans.TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(MyConstans.DESC));
            String imguri = cursor.getString(cursor.getColumnIndex(MyConstans.IMGURI));
            int _id = cursor.getInt(cursor.getColumnIndex(MyConstans._ID));
            item.setTitle(title);
            item.setDesc(desc);
            item.setImguri(imguri);
            item.setId(_id);
            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }
    public void closeDb(){
        myDbhelper.close();
    }

}
