package fpoly.edu.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.edu.duanmau.DAO.ThuThuDAO;

public class SignUpActivity extends AppCompatActivity {
    private ThuThuDAO thuThuDAO;
    private LoginActivity loginActivity = new LoginActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // ánh xạ
        EditText edUserNameSignUp = findViewById(R.id.edUserNameSignUp);
        EditText edPassWordSignUp = findViewById(R.id.edPassWordSignUp);
        EditText edRePassWordSignUp = findViewById(R.id.edRePassWordSignUp);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnBack = findViewById(R.id.btnBack);

        thuThuDAO = new ThuThuDAO(this);


        // sự kiện Sign up
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate
                if (loginActivity.vatlidateFom(edUserNameSignUp, "username") || loginActivity.vatlidateFom(edPassWordSignUp, "password") || loginActivity.vatlidateFom(edUserNameSignUp, "Comfim password")) {
                    return;
                } else {
                    String user = edUserNameSignUp.getText().toString();
                    String pass = edPassWordSignUp.getText().toString();
                    String repass = edRePassWordSignUp.getText().toString();

                    if (!pass.equals(repass)) {
                        Toast.makeText(SignUpActivity.this, "check password again", Toast.LENGTH_SHORT).show();
                        edRePassWordSignUp.setError("check password again");
                    } else {
                        boolean check =thuThuDAO.SignUp(user, pass);
                        if (check) { // check == true
                            Toast.makeText(SignUpActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sign Up field", Toast.LENGTH_SHORT).show();

                        }
                    }

                }

            }
        });
        // sự kiện back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}