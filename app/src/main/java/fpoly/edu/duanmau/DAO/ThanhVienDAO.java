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
import fpoly.edu.duanmau.Model.ThanhVien;


public class ThanhVienDAO {
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("namSinh", obj.getNamSinh());
        return db.insert("ThanhVien",null, values);
    }

    public int update(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("namSinh", obj.getNamSinh());
        int check =   db.update("ThanhVien", values,"maTV=?", new String[]{String.valueOf(obj.getMaTv())});
        if (check == 0){
            check = -1;
        }
        return check = 1;
    }

    public int delete(String id){
        return db.delete("ThanhVien","maTV=?", new String[]{id});
    }
//hien thi tat ca thanh vien
    @SuppressLint("Range")
    public List<ThanhVien> getData(String sql, String...selectionArgs){
        List<ThanhVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ThanhVien obj = new ThanhVien();
            obj.setMaTv(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setNamSinh(c.getString(c.getColumnIndex("namSinh")));
            Log.i("//====",obj.toString());
            list.add(obj);
        }
        return list;
    }
    //get tat ca data
    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }
    //get data theo id
    public ThanhVien getID(String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> list = getData(sql,id);
        return list.get(0);
    }
}
