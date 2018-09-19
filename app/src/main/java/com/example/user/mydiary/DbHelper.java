package com.example.user.mydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/21.
 */

public class DbHelper extends SQLiteOpenHelper {
    public final static String TABLE_NAME = "contact";
    public final static String DATABASE_NAME = "john";
    public final static String ID = "_id";
    public final static String NAME = "name";
    public final static String PHONE = "phone";
    public final static String C_DATE ="c_date";
    private DbHelper helper;
    private Context context;
    public SQLiteDatabase db;
    private final static int VERSION = 1;

    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXIST " + TABLE_NAME  + " ("
            + ID + " LONG PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + NAME + " TEXT NOT NULL, "
            + PHONE + "TEXT NOT NULL,"
            + C_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    public DbHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);


    }

    public SQLiteDatabase open(){

        if(helper == null){
            helper = new DbHelper(context);
        }else {
            helper = new DbHelper(context);
            db = helper.getReadableDatabase();
            db = helper.getWritableDatabase();
        }

        return db;
    }

//    public DbHelper(Context context) {
//
//        super(context, TABLE_NAME, null, VERSION);
//    }

    /**
     * 新增連絡人
     * @param entity
     * @return
     */
    public long insertContact (ContactBean entity){
        ContentValues cv = new ContentValues();
        cv.put(NAME, entity.getName());
        cv.put(PHONE, entity.getPhone());
        cv.put(C_DATE, entity.getTime());
        long row = open().insert(TABLE_NAME, null, cv);
        entity.setId(row);
        return row;
    }


    /**
     * 查詢的Cursor
     * @param cursor
     * @return
     */
    public ContactBean read(Cursor cursor){
        ContactBean contactBean = new ContactBean();
        contactBean.setId(cursor.getLong(0));
        contactBean.setName(cursor.getString(1));
        contactBean.setPhone(cursor.getString(2));
        return contactBean;
    }

    /**
     * 找全部聯絡人
     * @return
     */
    public List<ContactBean> findAll(){
        List<ContactBean> beanList = new ArrayList<>();
        Cursor cursor = open().query(TABLE_NAME,null,null,null,null,null,null,null );

        while(cursor.moveToNext()){
            beanList.add(read(cursor));
        }
        cursor.close();
        return beanList;
    }

    public List<ContactBean> findByKeyword(String keyword){

        List<ContactBean> beanList = new ArrayList<>();
        String selection = NAME + " =? and" + PHONE + " =?";
        String [] selectionArgs = {keyword};

        Cursor cursor = open().query(TABLE_NAME,null,selection,selectionArgs,null,null,null,null);
        while (cursor.moveToNext()){
            beanList.add(read(cursor));
        }
        cursor.close();
        return beanList;
    }


    /**
     * 透過ID找單筆連絡人
     * @param id
     * @return
     */
    public ContactBean findOne(long id){
        ContactBean contact =  new ContactBean();
        String selection = ID + " =?";
        String selectionArgs[] = {String.valueOf(id)};
        Cursor cursor = open().query(TABLE_NAME,null,selection,selectionArgs,null,null,null,null);
        while (cursor.moveToNext()){
           contact= read(cursor);
        }
        return contact;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
