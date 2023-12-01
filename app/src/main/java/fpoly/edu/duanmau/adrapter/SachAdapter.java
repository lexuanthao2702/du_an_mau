package fpoly.edu.duanmau.adrapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.duanmau.DAO.LoaiSachDAO;
import fpoly.edu.duanmau.Model.LoaiSach;
import fpoly.edu.duanmau.Model.Sach;
import fpoly.edu.duanmau.Model.ThanhVien;
import fpoly.edu.duanmau.R;
import fpoly.edu.duanmau.fragment.SachFragment;

public class SachAdapter extends ArrayAdapter<Sach> {
    private Context context;
    private SachFragment sachFragment;
    private List<Sach> list;
    private TextView tvMaSach, tvTenSach, tvGiaThue, tvLoaiSach, btnXoa;

    public SachAdapter(@NonNull Context context, SachFragment sachFragment, List<Sach> list) {
        super(context, 0, list);
        this.context = context;
        this.sachFragment = sachFragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.sachi_item, null);
        }
        final Sach item  = list.get(position);
        if (item != null){
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
            LoaiSach loaiSach = loaiSachDAO.getid(String.valueOf(item.getMaLoai()));
            tvMaSach = v.findViewById(R.id.tvMaSach);
            tvMaSach.setText("Mã Sách: "+item.getMaSach());

            tvTenSach = v.findViewById(R.id.tvTenSach);
            tvTenSach.setText("Tên Sách: "+item.getTenSach());
            tvGiaThue = v.findViewById(R.id.tvGiaThue);
            tvGiaThue.setText("Giá Thuê: "+item.getGiaThue());
            tvLoaiSach = v.findViewById(R.id.tvLoaiSach);
            tvLoaiSach.setText("Loại Sách: "+item.getMaLoai());

            btnXoa = v.findViewById(R.id.btnDelete);
        }
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // xóa
                sachFragment.Detele(String.valueOf(item.getMaSach()));
            }
        });
        return v;
    }
}
