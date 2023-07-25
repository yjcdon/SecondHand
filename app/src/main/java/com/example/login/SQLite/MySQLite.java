package com.example.login.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.login.Student;


/*
1.继承SQLiteOpenHelper,alt+enter出现两个函数;onCreate一般只有创建表的时候才会用,onUpgrade用于更新表
2.再写一个狗仔函数,尽量写成只用传上下文的形式
3.开始写增删改查函数
 */
public class MySQLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "info";

    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME +
                "(stuNum TEXT primary key, " +
                " phone TEXT, " +
                " password TEXT)";

        sqLiteDatabase.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    /*
    插入函数,肯定是有多个数据,所以参数为一个对象
    获取好数据,然后put,注意put里的key是要修改的列名,所以需要和数据库的列名一致
    最后使用内置函数即可,要判断是否成功,就return
     */
    public long insertData(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String stuNum = student.getStuNum();
        String phone = student.getPhone();
        String password = student.getPassword();

        values.put("stuNum", stuNum);
        values.put("phone", phone);
        values.put("password", password);

        return db.insert(TABLE_NAME, null, values);
    }

    /*
    删除函数,传入你要删除的数据
     */
    public long deleteByStuNum(String stuNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "stuNum = ?", new String[]{stuNum});
    }

    /*
    修改函数,这里put是要修改的列名
    update函数中的参数:表名,更新的数据,更新行的条件,更新行的参数(用于替换条件中的?)
     */
    public long updateToPsw(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String phone=student.getPhone();
        String newPsw = student.getPassword();

        values.put("password", newPsw);

        return db.update(TABLE_NAME, values, "phone=?", new String[]{phone});
    }

    public Student searchByStuNum(Student student) {
        SQLiteDatabase db = this.getReadableDatabase();
        String stuNum = student.getStuNum();

        Cursor cursor = db.query(TABLE_NAME, new String[]{"stuNum", "password"}, "stuNum=?", new String[]{stuNum},
                null, null, null);

        if (cursor.moveToFirst()) {
            stuNum = cursor.getString(cursor.getColumnIndexOrThrow("stuNum"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            student.setStuNum(stuNum);
            student.setPassword(password);
        } else {
            student = null;
        }
        cursor.close();

        return student;
    }
}