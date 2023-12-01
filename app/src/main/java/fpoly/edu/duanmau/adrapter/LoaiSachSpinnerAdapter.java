package fpoly.edu.duanmau.adrapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

import fpoly.edu.duanmau.DAO.LoaiSachDAO;
import fpoly.edu.duanmau.Model.LoaiSach;
import fpoly.edu.duanmau.R;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    private ArrayList<LoaiSach> list;
    private TextView tvMaLoaiSach, tvTenLoaiSach;

    public LoaiSachSpinnerAdapter(@NonNull Context context, ArrayList<LoaiSach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.loai_sach_item_spinner, null);
        }
        final LoaiSach item = list.get(position);
        if (item != null){
            tvMaLoaiSach = view.findViewById(R.id.tvMaLoaiSachSp);
            tvMaLoaiSach.setText(+item.getMaLoai() + ". ");

            tvTenLoaiSach = view.findViewById(R.id.tvTenLoaiSachSp);
            tvTenLoaiSach.setText(item.getTenLoai());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.loai_sach_item_spinner, null);
        }
        final LoaiSach item = list.get(position);
        if (item != null){
            tvMaLoaiSach = view.findViewById(R.id.tvMaLoaiSachSp);
            tvTenLoaiSach.setText(+item.getMaLoai() + ". ");

            tvTenLoaiSach = view.findViewById(R.id.tvTenLoaiSachSp);
            tvTenLoaiSach.setText(item.getTenLoai());
        }
        return view;
    }
}
