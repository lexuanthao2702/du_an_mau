package fpoly.edu.duanmau;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.edu.duanmau.DAO.ThuThuDAO;
import fpoly.edu.duanmau.utill.SendMail;

public class LoginActivity extends AppCompatActivity {
    private ThuThuDAO thuThuDAO;
    private SendMail sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ánh xạ
        EditText edUserName = findViewById(R.id.edUserName);
        EditText edPassWord = findViewById(R.id.edPassWord);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView btnSignup = findViewById(R.id.btnSignUp);
        TextView tvForgotpassword = findViewById(R.id.tvForgotpassword);
        CheckBox checkBox = findViewById(R.id.cboLuu);

        thuThuDAO = new ThuThuDAO(this);
        sendMail = new SendMail();
        // doc user, pass trong SharePrefenrenss
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = preferences.getString("USERNAME", "");
        String pass = preferences.getString("PASSWORD", "");
        boolean rem = preferences.getBoolean("REMENBER", false);

        edUserName.setText(user);
        edPassWord.setText(pass);
        checkBox.setChecked(rem);
        // sự kiện login ------------------------------------------
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate
                if (vatlidateFom(edUserName, "username") || vatlidateFom(edPassWord, "password")) {
                    return;
                } else {
                    String user = edUserName.getText().toString();
                    String pass = edPassWord.getText().toString();

                    boolean check = thuThuDAO.CheckLogin(user, pass);

                    if (check) { // check == true
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        remenberUser(user, pass, checkBox.isChecked());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Field", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // sự kiện đăng ký -------------------------------------------------
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        // forgot password -------------------------------------------------
        tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogFogot();

            }
        });

    }

    //validate
    public boolean vatlidateFom(EditText editText, String fileName) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            editText.setError("Please enter " + fileName);
            return true;
        }
        return false;

    }
    // check remenber
    public void remenberUser(String u, String p, boolean status){
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status){
            // xóa tình trạng lữu trữ trước đó
            editor.clear();
        }else {
            // lưu dử liệu
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMENBER",status);
        }
        // lưu toàn bộ dử liệu
        editor.commit();
    }
    // show dialog forgot password
    public void ShowDialogFogot(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                    Toast.makeText(getApplicationContext(), "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                }else {
                    sendMail.Send(getApplicationContext(),email,"Lấy Lại Mật khẩu","Mật Khẩu Của bản là: "+matkhau);
                    alertDialog.dismiss();
                }
            }
        });
    }
}