package com.example.appdoctruyen;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.adapterchaptruyen;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.object.Chapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ManChiTietTruyen extends AppCompatActivity {

    private ImageView imgTruyen;
    private TextView txtTenTruyen;
    private RatingBar ratingTruyen;
    private TextView txtChiTiet;
    private ListView listViewChapters;
    private Button btnDocTruyen;
    private ArrayList<Chapter> arrChap = new ArrayList<>();
    private adapterchaptruyen adapter;
    private DatabaseDocTruyen databaseDocTruyen;
    private int storyId;
    private int chapterId;
    private int categoryId;  // New variable for categoryId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_truyen);

        // Ánh xạ các thành phần giao diện
        anhXa();

        // Khởi tạo cơ sở dữ liệu
        databaseDocTruyen = new DatabaseDocTruyen(this);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        storyId = intent.getIntExtra("storyId", -1);
        String tenTruyen = intent.getStringExtra("tentruyen");
        String chiTiet = intent.getStringExtra("chitiet");
        String anhTruyen = intent.getStringExtra("anhtruyen");  // Receiving the image URL
        categoryId = intent.getIntExtra("categoryId", -1);  // Receiving the category ID

        // Hiển thị dữ liệu truyện lên giao diện
        txtTenTruyen.setText(tenTruyen);
        txtChiTiet.setText(chiTiet);
        loadAnhTruyen(anhTruyen);  // Use the image URL to load the image

        // Tải dữ liệu chapter từ cơ sở dữ liệu và thiết lập adapter cho ListView
        loadChapterData();
        setupListViewAdapter();

        // Lấy vị trí chương đã đọc trước đó (nếu có)
        int lastPosition = databaseDocTruyen.getLastReadPosition(storyId);
        if (lastPosition != -1) {
            // Nếu có chương đã đọc trước đó, mở chương đó
            docChapter(lastPosition);
        }

        // Xử lý sự kiện click vào nút "Đọc"
        btnDocTruyen.setOnClickListener(v -> docChapterDauTien());
    }

    private void docChapter(int lastPosition) {

            Intent chapterIntent = new Intent(ManChiTietTruyen.this, ManNoiDungChuong.class);
            chapterIntent.putExtra("chapterId", chapterId);
            chapterIntent.putExtra("storyId", storyId);
            startActivity(chapterIntent);


    }

    private void anhXa() {
        imgTruyen = findViewById(R.id.imgTruyen);
        txtTenTruyen = findViewById(R.id.txtTenTruyen);
        ratingTruyen = findViewById(R.id.ratingTruyen);
        txtChiTiet = findViewById(R.id.txtChiTiet);
        listViewChapters = findViewById(R.id.listViewChapters);
        btnDocTruyen = findViewById(R.id.btnDocTruyen);
    }

    private void loadAnhTruyen(String anhTruyen) {
        if (anhTruyen != null && !anhTruyen.isEmpty()) {
            Picasso.get().load(anhTruyen)
                    .placeholder(R.drawable.baseline_cloud_download_24)
                    .error(R.drawable.baseline_image_not_supported_24)
                    .into(imgTruyen);
        } else {
            imgTruyen.setImageResource(R.drawable.baseline_image_not_supported_24);
        }
    }

    @SuppressLint("Range")
    private void loadChapterData() {
        Cursor cursor = databaseDocTruyen.getChaptersByStoryId(storyId);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseDocTruyen.ID_CHAPTER));
                    String name = cursor.getString(cursor.getColumnIndex(DatabaseDocTruyen.TEN_CHAPTER));
                    String content = cursor.getString(cursor.getColumnIndex(DatabaseDocTruyen.NOI_DUNG_CHAPTER));
                    int storyId = cursor.getInt(cursor.getColumnIndex(DatabaseDocTruyen.ID_TRUYEN_FK));

                    // Log dữ liệu để kiểm tra
                    Log.d("ChapterData", "ID: " + id + ", Name: " + name + ", Content: " + content + ", StoryID: " + storyId);

                    arrChap.add(new Chapter(id, name, content, storyId));
                }
            } finally {
                cursor.close();
            }
        }
    }

    private void setupListViewAdapter() {
        adapter = new adapterchaptruyen(this, R.layout.item_chapter, arrChap);
        listViewChapters.setAdapter(adapter);

        listViewChapters.setOnItemClickListener((parent, view, position, id) -> {
            Chapter selectedChapter = arrChap.get(position);

            // Lưu vị trí chương mà người dùng đã chọn và truyền tên truyện (storyTitle)
            String storyTitle = txtTenTruyen.getText().toString();  // Lấy tên truyện từ TextView
            databaseDocTruyen.saveReadingPosition(storyId, selectedChapter.getId(), storyTitle);

            Intent chapterIntent = new Intent(ManChiTietTruyen.this, ManNoiDungChuong.class);
            chapterIntent.putExtra("chapterId", selectedChapter.getId());
            chapterIntent.putExtra("storyId", storyId); // Truyền storyId
            startActivity(chapterIntent);
        });
    }



    private void docChapterDauTien() {
        if (!arrChap.isEmpty()) {
            Intent chapterIntent = new Intent(ManChiTietTruyen.this, ManNoiDungChuong.class);
            chapterIntent.putExtra("chapterId", arrChap.get(0).getId());
            chapterIntent.putExtra("storyId", storyId); // Pass the storyId
            startActivity(chapterIntent);
        }
    }




}
