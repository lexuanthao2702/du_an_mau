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
import fpoly.edu.duanmau.Model.Sach;


public class SachDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    // insert
    public long insert(Sach obj){
        ContentValues values = new ContentValues();
        values.put("tenSach", obj.getTenSach());
        values.put("giaThue", obj.getGiaThue());
        values.put("maLoai", obj.getMaLoai());
        return db.insert("Sach",null,values);
    }
    //Update
    public long updtae(Sach obj){
        ContentValues values = new ContentValues();
        values.put("tenSach", obj.getTenSach());
        values.put("giaThue", obj.getGiaThue());
        values.put("maLoai", obj.getMaLoai());
        return db.update("Sach",values,"MaSach=?",new String[]{String.valueOf(obj.getMaSach())});
    }
    //delete
    public long delete(String id){
        return db.delete("Sach","maSach=?", new String[]{id});
    }
    //get data nhieu tham so
    @SuppressLint("Range")
    public List<Sach> getDate(String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<Sach>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            Sach obj = new Sach();
            obj.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            obj.setTenSach(c.getString(c.getColumnIndex("tenSach")));
            obj.setGiaThue(Integer.parseInt(c.getString(c.getColumnIndex("giaThue"))));
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));

            list.add(obj);
        }
        return list;
    }
    // get tat ca data
    public List<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getDate(sql);
    }
    // get data theo id
    public Sach getid(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list = getDate(sql,id);
        return list.get(0);
    }
    // lấy danh sách sản phẩm
    public ArrayList<Sach> getListSach() {
        ArrayList<Sach> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("Select * from Sach", null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
        return list;
    }

}
