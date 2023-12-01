package fpoly.edu.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.duanmau.Database.DbHelper;
import fpoly.edu.duanmau.Model.LoaiSach;


public class LoaiSachDAO {
    private SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // insert
    public long insert(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("maLoai", obj.getMaLoai());
        values.put("tenLoai", obj.getTenLoai());
        return db.insert("ThuThu",null,values);
    }
    //Update
    public long updtae(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        return db.update("LoaiSach",values,"maLoai=?",new String[]{String.valueOf(obj.getMaLoai())});
    }
    //delete
    public long delete(String id){
        return db.delete("LoaiSach","maLoai=?", new String[]{id});
    }
    //
    @SuppressLint("Range")
    public List<LoaiSach> getDate(String sql, String...selectionArgs){
        List<LoaiSach> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            LoaiSach obj = new LoaiSach();
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
            obj.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));

            list.add(obj);
        }
        return list;
    }
    // get tat ca data
    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getDate(sql);
    }
    // get data theo id
    public LoaiSach getid(String id){
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=?";
        List<LoaiSach> list = getDate(sql,id);

        return list.get(0);
    }
}
