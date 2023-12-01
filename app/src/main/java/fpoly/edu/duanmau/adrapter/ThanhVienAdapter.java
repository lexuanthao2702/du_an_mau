package fpoly.edu.duanmau.adrapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.sql.RowId;
import java.util.ArrayList;
import java.util.List;

import fpoly.edu.duanmau.DAO.ThanhVienDAO;
import fpoly.edu.duanmau.Model.ThanhVien;
import fpoly.edu.duanmau.R;
import fpoly.edu.duanmau.fragment.ThanhVienFragment;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    private ThanhVienFragment thanhVienFragment;
    private ThanhVien thanhVien;
    private ThanhVienDAO dao;
    private ArrayList<ThanhVien> list;
    private TextView tvMaTv, tvTenTv, tvNamSinh, tvDetele, tvUpdate;

    public ThanhVienAdapter(@NonNull Context context, ThanhVienFragment thanhVienFragment, ArrayList<ThanhVien> list) {
        super(context, 0, list);
        this.context = context;
        this.thanhVienFragment = thanhVienFragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.thanh_vien_item, null);
        }
        final ThanhVien item  = list.get(position);
        if (item != null){
            tvMaTv = view.findViewById(R.id.tvMaTV);
            tvMaTv.setText("Mã Thành Viên: "+item.getMaTv());

            tvTenTv = view.findViewById(R.id.tvTenTV);
            tvTenTv.setText("Tên Thành Viên: "+item.getHoTen());

            tvNamSinh = view.findViewById(R.id.tvNamSinh);
            tvNamSinh.setText("Năm Sinh: "+item.getNamSinh());

            tvDetele = view.findViewById(R.id.btnDelete);
        }

        tvDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gọi phương thức xóa
                thanhVienFragment.Detele(String.valueOf(item.getMaTv()));
            }
        });
        return view;
    }
}
