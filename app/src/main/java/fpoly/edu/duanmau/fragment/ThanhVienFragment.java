package fpoly.edu.duanmau.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.edu.duanmau.DAO.ThanhVienDAO;
import fpoly.edu.duanmau.LoginActivity;
import fpoly.edu.duanmau.Model.ThanhVien;
import fpoly.edu.duanmau.R;
import fpoly.edu.duanmau.adrapter.ThanhVienAdapter;

public class ThanhVienFragment extends Fragment {
    ListView lvThanhVien;
    ArrayList<ThanhVien> list;
    static ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    ThanhVien item;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaTV, edTenTV, edNamSinh;
    Button btnAddTV, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        lvThanhVien = view.findViewById(R.id.lvThanhVien);
        fab = view.findViewById(R.id.fab);
        dao = new ThanhVienDAO(getActivity());
        UpdateLV();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        lvThanhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(), 1); // 1 update
                return false;
            }
        });
        return view;
    }

    public void UpdateLV() {
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(), this, list);
        lvThanhVien.setAdapter(adapter);
    }

    public void Detele(final String ID) {
        // sủ dụng alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setCancelable(true);

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // gọi function detele
                boolean check = true;
                if (check) {
                    dao.delete(ID);
                    Toast.makeText(getContext(), "delete successfuly", Toast.LENGTH_SHORT).show();
                    UpdateLV();
                    dialog.cancel();
                } else {
                    Toast.makeText(getContext(), "delete fail", Toast.LENGTH_SHORT).show();
                }


            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog dialog = builder.create();
        builder.show();
    }
    public void openDialog(final Context context, final int TYPE) {
        // custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.thanh_vien_dialog);
        edMaTV = dialog.findViewById(R.id.edMaTV);
        edTenTV = dialog.findViewById(R.id.edTenTV);
        edNamSinh = dialog.findViewById(R.id.edNamSinh);
        btnAddTV = dialog.findViewById(R.id.btnAddTV);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        // kiểm tra type insert = hay update 1
        edMaTV.setEnabled(false);
        dialog.setCancelable(false);
        if (TYPE != 0) {
            edMaTV.setText(String.valueOf(item.getMaTv()));
            edTenTV.setText(item.getHoTen());
            edNamSinh.setText(item.getNamSinh());
        }
        //
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAddTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new ThanhVien();
                item.setHoTen(edTenTV.getText().toString());
                item.setNamSinh(edNamSinh.getText().toString());
                if (validate() > 0) {
                    if (TYPE == 0) {
                        // type == 0 insert
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Add successfuly", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Add fail", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        item.setMaTv(Integer.parseInt(edMaTV.getText().toString()));
                        if (dao.update(item) > 0) {
                            Toast.makeText(context, "Update successfuly", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Updtae fail", Toast.LENGTH_SHORT).show();

                        }
                    }
                    UpdateLV();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int validate() {
        int check = 1;
        if (edTenTV.getText().toString().length() == 0 || edNamSinh.getText().toString().length() == 0) {
            Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}