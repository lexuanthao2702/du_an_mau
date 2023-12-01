package fpoly.edu.duanmau.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.duanmau.DAO.LoaiSachDAO;
import fpoly.edu.duanmau.DAO.SachDAO;
import fpoly.edu.duanmau.Model.LoaiSach;
import fpoly.edu.duanmau.Model.Sach;
import fpoly.edu.duanmau.R;
import fpoly.edu.duanmau.adrapter.LoaiSachSpinnerAdapter;
import fpoly.edu.duanmau.adrapter.SachAdapter;


public class SachFragment extends Fragment {
    private ListView lvSach;
    private SachDAO sachDAO;
    private SachAdapter adapter;
    private Sach item;
    private List<Sach> list;

    private FloatingActionButton fab;
    private Dialog dialog;
    private EditText edMaSach, edTenSach, edGiaThue;
    private Spinner spinner;
    private Button btnSave, btnCancel;

    private ArrayList<LoaiSach> listLoaiSach;
    private LoaiSachDAO loaiSachDAO;
    private LoaiSach loaiSach;
    private LoaiSachSpinnerAdapter spinnerAdapter;
    int maLoaiSach, pos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach2, container, false);
        lvSach = view.findViewById(R.id.lvSach);
        sachDAO = new SachDAO(getActivity());
        capNhapLV();
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opdialog(getActivity(), 0);
            }
        });
        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                opdialog(getActivity(), 1);
                return false;
            }
        });
        return view;
    }

    void capNhapLV() {
        list = sachDAO.getAll();
        adapter = new SachAdapter(getActivity(), this, list);
        lvSach.setAdapter(adapter);
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
                    sachDAO.delete(ID);
                    Toast.makeText(getContext(), "delete successfuly", Toast.LENGTH_SHORT).show();
                    capNhapLV();
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

    public void opdialog(final Context context, final int type) {
        // custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.sach_dialog);
        edMaSach = dialog.findViewById(R.id.edMaSach);
        edTenSach = dialog.findViewById(R.id.edTenSach);
        edGiaThue = dialog.findViewById(R.id.edGiaThue);
        spinner = dialog.findViewById(R.id.spLoaiSach);
        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        listLoaiSach = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();

        spinnerAdapter = new LoaiSachSpinnerAdapter(context, listLoaiSach);
        spinner.setAdapter(spinnerAdapter);
        // lấy maLoaiSach
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLoaiSach.get(position).getMaLoai();
                Toast.makeText(context, "Chọn " + listLoaiSach.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // kiểm tra type inser 0 hay update 1
        edMaSach.setEnabled(false);
        dialog.setCancelable(false);
        if (type != 0) {
            // set dử liêệu lên form dialog
            edMaSach.setText(String.valueOf(item.getMaSach()));
            edTenSach.setText(item.getTenSach());
            edGiaThue.setText(String.valueOf(item.getGiaThue()));
            for (int i = 0; i < listLoaiSach.size(); i++) {
                if (item.getMaLoai() == (listLoaiSach.get(i).getMaLoai())) {
                    pos = i;
                }
                Log.i("demo", "posSach" + pos);
                spinner.setSelection(pos);
            }
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new Sach();
                item.setTenSach(edTenSach.getText().toString());
                item.setGiaThue(Integer.parseInt(edGiaThue.getText().toString()));
                item.setMaLoai(maLoaiSach);
                if (validate() > 0) {
                    if (type == 0) {
                        // type = 0 insert
                        if (sachDAO.insert(item) > 0) {
                            Toast.makeText(context, "Add successfuli", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Add fail", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // type = 1 insert
                        item.setMaSach(Integer.parseInt(edMaSach.getText().toString()));
                        if (sachDAO.updtae(item) > 0) {
                            Toast.makeText(context, "Update successfuli", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Update fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhapLV();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int validate() {
        int check = 1;
        if (edTenSach.getText().length() == 0 || edGiaThue.getText().length() == 0) {
            Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}