package fpoly.edu.duanmau.fragment;

import android.companion.WifiDeviceFilter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.edu.duanmau.DAO.ThuThuDAO;
import fpoly.edu.duanmau.LoginActivity;
import fpoly.edu.duanmau.Model.ThuThu;
import fpoly.edu.duanmau.R;
import fpoly.edu.duanmau.SignUpActivity;
import fpoly.edu.duanmau.utill.SendMail;


public class ForgotFragment extends Fragment {
    private ThuThuDAO thuThuDAO;
    private LoginActivity loginActivity = new LoginActivity();
    private SendMail sendMail;
    private EditText edPassold, edPassChange, edRePassChange;
    private TextView tvForgotPassChange;
    private Button btnSaveUserChange, btnCancelUserChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forgot, container, false);
        // ánh xạ
        edPassold = v.findViewById(R.id.edPassold);
        edPassChange = v.findViewById(R.id.edPassChange);
        edRePassChange = v.findViewById(R.id.edRePassChange);
        btnSaveUserChange = v.findViewById(R.id.btnSaveUserChange);
        btnCancelUserChange = v.findViewById(R.id.btnCancelUserChange);
        tvForgotPassChange = v.findViewById(R.id.tvForgotpassChang);
        thuThuDAO = new ThuThuDAO(getActivity());
        sendMail = new SendMail();
        // sự kiện cancel
        btnCancelUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassold.setText("");
                edPassChange.setText("");
                edRePassChange.setText("");
            }
        });
        // sự kiện save
        btnSaveUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = preferences.getString("USERNAME", "");
                if (validate() > 0){
                    ThuThu thuThu = thuThuDAO.getId(user);
                    thuThu.setMatKhau(edPassChange.getText().toString());
                    if (thuThuDAO.updtaePass(thuThu) > 0){
                        Toast.makeText(getActivity(), "changed password successfully", Toast.LENGTH_SHORT).show();
                        edPassold.setText("");
                        edPassChange.setText("");
                        edRePassChange.setText("");
                    }else {
                        Toast.makeText(getActivity(), "changed password failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        // sự kiện forgot password
        tvForgotPassChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogFogot();
            }
        });
        return v;
    }

    public int validate() {
        // validate
        int check = 1;
        if (loginActivity.vatlidateFom(edPassold, "old password") || loginActivity.vatlidateFom(edPassChange, "new password") || loginActivity.vatlidateFom(edRePassChange, "password")) {
            Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            // dọc user, pass trong ShaedPreferences
            SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passold = preferences.getString("PASSWORD", "");
            String pass = edPassChange.getText().toString();
            String repass = edRePassChange.getText().toString();
            if (!passold.equals(edPassold.getText().toString())) {
                Toast.makeText(getContext(), "check password again", Toast.LENGTH_SHORT).show();
                edPassold.setError("check password again");
                check = -1;
            }
            if (!pass.equals(repass)) {
                Toast.makeText(getContext(), "Passwords are not duplicates", Toast.LENGTH_SHORT).show();
                edRePassChange.setError("check password again");
                check = -1;
            }
        }
        return check;
    }
    public void ShowDialogFogot(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        // ánh xạ
        EditText edtEmail = view.findViewById(R.id.edEmail);
        Button btnGui = view.findViewById(R.id.btnSend);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        // send Mail
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String matkhau = thuThuDAO.ForgotPassword(email);
                if (matkhau.equals("")){
                    Toast.makeText(getContext(), "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                }else {
                    sendMail.Send(getContext(),email,"Lấy Lại Mật khẩu","Mật Khẩu Của bản là: "+matkhau);
                    alertDialog.dismiss();
                }
            }
        });
    }
}