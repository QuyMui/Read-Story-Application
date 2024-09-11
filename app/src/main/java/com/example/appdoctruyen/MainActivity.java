package com.example.appdoctruyen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appdoctruyen.adapter.adapterchuyenmuc;
import com.example.appdoctruyen.adapter.adapterthongtin;
import com.example.appdoctruyen.adapter.adaptertruyen;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.object.TaiKhoan;
import com.example.appdoctruyen.object.Truyen;
import com.example.appdoctruyen.object.chuyenmuc;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gdvDSTruyen;
    adaptertruyen adapter;
    ArrayList<Truyen> truyenList; // Sử dụng ArrayList cho danh sách truyện
    ViewFlipper viewFlipper;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigation;
    ListView listView,listviewThongtin;
    DatabaseDocTruyen databaseDocTruyen;
    String email;
    String tentaikhoan;
    BottomNavigationView bottomNavigationView;


    ArrayList<chuyenmuc> chuyenmucArrayList;
    ArrayList<TaiKhoan> taiKhoanArrayList;

    adapterthongtin adapterthongtin;
    adapterchuyenmuc adapterchuyenmuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Khởi tạo cơ sở dữ liệu
        databaseDocTruyen = new DatabaseDocTruyen(this);

        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq",0);
        int idd = intentpq.getIntExtra("idd",0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        // Ánh xạ các view từ layout
        anhXa();

        // Cài đặt ActionBar và ViewFlipper
        actionbar();
        setupViewFlipper();

        // Tải dữ liệu truyện từ cơ sở dữ liệu và khởi tạo adapter
        loadTruyenData();

        // Cài đặt sự kiện click cho GridView
        setupGridViewClickListener();

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // Set a listener for item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.category) {
                    Intent categoryIntent = new Intent(MainActivity.this, ManDanhMuc.class);
                    startActivity(categoryIntent);
                    return true;
                } else if (itemId == R.id.history) {
                    Intent historyIntent = new Intent(MainActivity.this, ManLichSu.class);
                    startActivity(historyIntent);
                    return true;
                }
                return false;
            }
        });


//        //listview chuyên mục
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){

                    if(i == 2) {
                        Intent intent = new Intent(MainActivity.this, ManAdmin.class);
                        intent.putExtra("Id",idd);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Bạn không có quyền ",Toast.LENGTH_SHORT).show();
                        Log.e("Đăng bài : ","Bạn không có quyền ");
                    }
                }
                else if(position == 1){
                    Intent intent = new Intent(MainActivity.this,ManThongTinApp.class);
                    startActivity(intent);
                }
                else if(position == 2){
                    finish();
                }
            }
        });
    }




    private void actionbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void setupViewFlipper() {
        ArrayList<String> truyenmoinhat = new ArrayList<>();
        truyenmoinhat.add("https://cn-e-pic.itoon.org/internet-search/f60ad1167ea260fa35f719ec39a2ca1f.webp");
        truyenmoinhat.add("https://kollersi.com/wp-content/uploads/2024/03/mv5bmja5yjg1ytqtyza5zs00zwnjltkwnjytote5mzqwzdm4ymiwxkeyxkfqcgdeqxvymtezmti1mjk3-_v1_fmjpg_ux1000_.jpg");
        truyenmoinhat.add("https://stbhatay.com.vn/wp-content/uploads/2023/02/th-1nrt.jpg");
        truyenmoinhat.add("https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEi2Mj0Gu_ksWef8fooIdcM79MJbkYyVNp1vBDOqpv6xTyDSdDDKdsmx2guh21q2T___inZVJQT10lRDCvpAERZ5ox8kAme5GioNUsRsudviFCdmAg7k3PeFBj_76czkvYkdZYghhlAVJoWFjBuxIv1Or_pXohvIFZzYPfcVJvYHwENgU4aPAMMPm3S06Fm7/s16000/000.jpg");

        for (String url : truyenmoinhat) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(url).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        Animation animationSlideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animationSlideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animationSlideIn);
        viewFlipper.setOutAnimation(animationSlideOut);
    }

    private void anhXa() {
        gdvDSTruyen = findViewById(R.id.gdvDSTruyen);
        viewFlipper = findViewById(R.id.viewFlipper);
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        navigation = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);
        listView = findViewById(R.id.listviewmanhinhchinh);
        listviewThongtin = findViewById(R.id.listviewthongtin);



//        //Thong tin
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan,email));
        adapterthongtin = new adapterthongtin(this,R.layout.nav_thongtin,taiKhoanArrayList);
        listviewThongtin.setAdapter(adapterthongtin);

        //chuyên mục
        chuyenmucArrayList = new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("Đăng bài",R.drawable.ic_post));
        chuyenmucArrayList.add(new chuyenmuc("Thông tin",R.drawable.ic_face));
        chuyenmucArrayList.add(new chuyenmuc("Đăng xuất",R.drawable.ic_login));

        adapterchuyenmuc = new adapterchuyenmuc(this,R.layout.chuyen_muc,chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);


    }

    private void loadTruyenData() {
        // Khởi tạo danh sách truyện
        truyenList = new ArrayList<>();

        // Lấy dữ liệu từ cơ sở dữ liệu
        Cursor cursor1 = databaseDocTruyen.getData1();
        while (cursor1.moveToNext()) {
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String chitiet = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);
            truyenList.add(new Truyen(id, tentruyen, chitiet, anh, id_tk));
        }
        cursor1.close();

        // Initialize the adapter with the list of stories and layout resource ID
        adapter = new adaptertruyen(this, R.layout.new_truyen, truyenList);
        gdvDSTruyen.setAdapter(adapter);
    }

    private void setupGridViewClickListener() {
        gdvDSTruyen.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, ManChiTietTruyen.class);

            // Lấy dữ liệu từ truyện được chọn
            int idTruyen = truyenList.get(position).getID(); // Giả sử có phương thức getId()
            String tent = truyenList.get(position).getTenTruyen();
            String chitiet = truyenList.get(position).getChiTiet();
            String anhTruyen = truyenList.get(position).getAnh();

            // Truyền dữ liệu vào intent
            intent.putExtra("storyId", idTruyen); // Sử dụng đúng key tại đây
            intent.putExtra("tentruyen", tent);
            intent.putExtra("chitiet", chitiet);
            intent.putExtra("anhtruyen", anhTruyen);

            startActivity(intent);
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu1) {
            Intent intent = new Intent(MainActivity.this, ManTimKiem.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
