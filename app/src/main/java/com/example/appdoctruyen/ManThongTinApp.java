package com.example.appdoctruyen;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ManThongTinApp extends AppCompatActivity {

    TextView txtThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_thong_tin_app);

        txtThongTin = findViewById(R.id.textviewthongtin);

        String thongtin = "Ứng dụng demo";
        txtThongTin.setText(thongtin);
    }
}