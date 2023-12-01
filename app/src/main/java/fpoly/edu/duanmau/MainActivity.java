package fpoly.edu.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import fpoly.edu.duanmau.fragment.ForgotFragment;
import fpoly.edu.duanmau.fragment.SachFragment;
import fpoly.edu.duanmau.fragment.ThanhVienFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    TextView edUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar1);
        // set toolbar thay thế actionbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(R.drawable.menu);
        ab.setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.nvView);
        mHeaderView = nv.getHeaderView(0);
        edUser = mHeaderView.findViewById(R.id.txtUser);
        Intent i = getIntent();
        edUser.setText("Welcome! ");
        // click sự kiện navigation
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();

                if (item.getItemId() == R.id.nav_PhieuMuon) {
                    setTitle("Quản lý phiếu mượn");
                    Toast.makeText(MainActivity.this, "Quản lý phiếu mượn", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.nav_LoaiSach) {
                    setTitle("Quản lý loại sách");
                    Toast.makeText(MainActivity.this, "Quản lý loại sách", Toast.LENGTH_SHORT).show();

                } else if (item.getItemId() == R.id.nav_Sach) {
                    setTitle("Quản lý sách");
                    SachFragment sachFragment = new SachFragment();
                    manager.beginTransaction().replace(R.id.flContent, sachFragment).commit();
                    Toast.makeText(MainActivity.this, "Quản lý sách", Toast.LENGTH_SHORT).show();

                } else if (item.getItemId() == R.id.nav_ThanhVien) {
                    setTitle("Quản lý thành viên");
                    ThanhVienFragment thanhVienFragment = new ThanhVienFragment();
                    manager.beginTransaction().replace(R.id.flContent, thanhVienFragment).commit();
                    Toast.makeText(MainActivity.this, "Quản lý thành viên", Toast.LENGTH_SHORT).show();

                } else if (item.getItemId() == R.id.sub_Top) {
                    setTitle("Tóp 10 cuốn sách bán nhiều nhất");
                    Toast.makeText(MainActivity.this, "Tóp 10 cuốn sách bán nhiều nhất", Toast.LENGTH_SHORT).show();

                } else if (item.getItemId() == R.id.sub_DoanhThu) {
                    setTitle("Thống kê doanh thu");
                    Toast.makeText(MainActivity.this, "Thống kê doanh thu", Toast.LENGTH_SHORT).show();

                } else if (item.getItemId() == R.id.sub_AddUser) {
                    setTitle("Thêm người dùng");
                    Toast.makeText(MainActivity.this, "Thêm người dùng", Toast.LENGTH_SHORT).show();

                } else if (item.getItemId() == R.id.sub_Pass) {
                    setTitle("Thay đổi mật khẩu");
                    ForgotFragment forgotFragment = new ForgotFragment();
                    manager.beginTransaction().replace(R.id.flContent, forgotFragment).commit();
                    Toast.makeText(MainActivity.this, "Thay đổi mật khẩu", Toast.LENGTH_SHORT).show();

                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    Toast.makeText(MainActivity.this, "đăng xuất thành công", Toast.LENGTH_SHORT).show();

                }

                drawer.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            drawer.openDrawer(GravityCompat.START);

        return super.onOptionsItemSelected(item);
    }
}