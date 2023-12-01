package fpoly.edu.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.edu.duanmau.Database.DbHelper;
import fpoly.edu.duanmau.Model.PhieuMuon;
import fpoly.edu.duanmau.Model.Sach;
import fpoly.edu.duanmau.Model.Top;


public class PhieuMuonDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    // insert
    public long insert(PhieuMuon obj){
        ContentValues values = new ContentValues();
        values.put("maTT", obj.getMaTT());
        values.put("maTV", obj.getMaTV());
        values.put("maSach", obj.getMaSach());
        values.put("ngay", sdf.format(obj.getNgay()));
        values.put("tienThue", obj.getTienThue());
        values.put("traSach", obj.getTraSach());
        return db.insert("PhieuMuon",null,values);
    }
    //Update
    public long updtae(PhieuMuon obj){
        ContentValues values = new ContentValues();
        values.put("maTT", obj.getMaTT());
        values.put("maTV", obj.getMaTV());
        values.put("maSach", obj.getMaSach());
        values.put("ngay", sdf.format(obj.getNgay()));
        values.put("tienThue", obj.getTienThue());
        values.put("traSach", obj.getTraSach());
        return db.update("PhieuMuon",values,"maPM=?",new String[]{String.valueOf(obj.getMaPM())});
    }
    //delete
    public long delete(String id){
        return db.delete("PhieuMuon","maPM=?", new String[]{id});
    }
    //
    @SuppressLint("Range")
    public List<PhieuMuon> getDate(String sql, String...selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            PhieuMuon obj = new PhieuMuon();
            obj.setMaPM(Integer.parseInt(c.getString(c.getColumnIndex("maPM"))));
            obj.setMaTT(c.getString(c.getColumnIndex("maTT")));
            obj.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            obj.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            obj.setTienThue(Integer.parseInt(c.getString(c.getColumnIndex("tienThue"))));
            try {
                obj.setNgay(sdf.parse(c.getString(c.getColumnIndex("ngay"))));
            } catch (ParseException e) {

            }
            obj.setTraSach(Integer.parseInt(c.getString(c.getColumnIndex("traSach"))));

            list.add(obj);
        }
        return list;
    }
    // get tat ca data
    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getDate(sql);
    }
    // get data theo id
    public PhieuMuon getid(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list = getDate(sql,id);
        return list.get(0);
    }
    // thống kê top 10
    @SuppressLint("Range")
    public List<Top> getTop(){
        String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor c = db.rawQuery(sqlTop,null);

        while (c.moveToNext()){
            Top top  = new Top();
            Sach sach = sachDAO.getid(c.getString(c.getColumnIndex("maSach")));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
            list.add(top);
        }
        return list;
    }
    // thống kê danh thu
    @SuppressLint("Range")
    public int getDanhThu(String tuNgay, String denNgay){
        String sqlDanhThu = "SELECT SUM(tienThue) as danhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDanhThu, new String[]{tuNgay, denNgay});
        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("danhThu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);

    }
}
