package fpoly.edu.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.duanmau.Database.DbHelper;
import fpoly.edu.duanmau.Model.ThuThu;


public class ThuThuDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // insert
    public boolean insert(ThuThu obj){
        ContentValues values = new ContentValues();
        values.put("maTT", obj.getMaTT());
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        long check =  db.insert("ThuThu",null,values);
        return check != 1;// check = true
    }
    //Update
    public long updtaePass(ThuThu obj){
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        return db.update("ThuThu",values,"maTT=?",new String[]{obj.getMaTT()});
    }
    //delete
    public long delete(String id){
        return db.delete("ThuThu","maTT=?", new String[]{id});
    }
    @SuppressLint("Range")
    public List<ThuThu> getDate(String sql, String...selectionArgs){
        List<ThuThu> list = new ArrayList<ThuThu>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThuThu obj = new ThuThu();
            obj.setMaTT((c.getString(c.getColumnIndex("maTT"))));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            Log.i("//=====",obj.toString());
            list.add(obj);
        }
        return list;
    }
    // get tat ca data
    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM ThuThu";
        return getDate(sql);
    }
    // get data theo id
    public ThuThu getId(String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list = getDate(sql,id);
        return list.get(0);
    }
    // check login
    public boolean CheckLogin(String id, String password){
        db = dbHelper.getReadableDatabase(); // lấy dử liệu

        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE maTT = ? AND matKhau = ?", new String[]{id, password});
        return cursor.getCount() > 0; //trả về giá trị true
    }
    // Sign up
    public boolean SignUp(String user, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase(); // thêm dử liệu

        ContentValues values = new ContentValues();
        values.put("maTT", user);
        values.put("matKhau", pass);
        values.put("hoTen", pass);

        long check = sqLiteDatabase.insert("ThuThu", null, values);
        return check != -1;
    }
    public String ForgotPassword(String Email){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase(); // thêm dử liệu
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT matKhau FROM ThuThu WHERE maTT =?",new String[]{Email});
        if (cursor.getCount() > 0){
            cursor.moveToFirst(); // con trỏ lên đầu
            return cursor.getString(0);
        }else {
            return "";
        }
    }

}
