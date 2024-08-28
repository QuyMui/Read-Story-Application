package com.example.appdoctruyen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.data.DatabaseDocTruyen;

public class ManNoiDungChuong extends AppCompatActivity {

    private TextView txtTenChuong;
    private TextView txtNoiDungChuong;
    private DatabaseDocTruyen databaseDocTruyen;
    private int chapterId;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_chuong);

        // Khởi tạo cơ sở dữ liệu
        databaseDocTruyen = new DatabaseDocTruyen(this);

        // Lấy các view
        txtTenChuong = findViewById(R.id.txtTenChuong);
        txtNoiDungChuong = findViewById(R.id.txtNoiDungChuong);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        chapterId = intent.getIntExtra("chapterId", -1);
        // Lấy dữ liệu từ Intent



        // Tải dữ liệu chương từ cơ sở dữ liệu và hiển thị lên giao diện
        if (chapterId != -1) {
            loadChapterData(chapterId);
        }
    }
    @SuppressLint("Range")
    private void loadChapterData(int chapterId) {
        Cursor cursor = databaseDocTruyen.getChapterById(chapterId);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {

                    String tenChuong = cursor.getString(cursor.getColumnIndex(DatabaseDocTruyen.TEN_CHAPTER));
                    String noiDungChuong = cursor.getString(cursor.getColumnIndex(DatabaseDocTruyen.NOI_DUNG_CHAPTER));

                    // Hiển thị dữ liệu
                    txtTenChuong.setText(tenChuong);
                    txtNoiDungChuong.setText(noiDungChuong);
                }
            } finally {
                cursor.close();
            }
        }
    }
}
