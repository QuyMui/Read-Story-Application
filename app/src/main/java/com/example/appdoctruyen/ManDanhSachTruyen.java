package com.example.appdoctruyen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.adaptertruyen;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.object.Truyen;

import java.util.ArrayList;

public class ManDanhSachTruyen extends AppCompatActivity {

    private GridView gdvDSTruyen;
    private ArrayList<Truyen> truyen;
    private adaptertruyen adapter;
    private DatabaseDocTruyen databaseDocTruyen;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);

        // Kết nối GridView với giao diện
        gdvDSTruyen = findViewById(R.id.gdvDSTruyen);

        // Nhận ID của danh mục được chọn
        categoryId = getIntent().getIntExtra("categoryId", -1);

        // Khởi tạo danh sách truyện và adapter
        truyen = new ArrayList<>();
        adapter = new adaptertruyen(this, R.layout.new_truyen, truyen); // Cung cấp layout ID
        gdvDSTruyen.setAdapter(adapter);

        // Lấy dữ liệu truyện từ cơ sở dữ liệu
        databaseDocTruyen = new DatabaseDocTruyen(this);
        loadStoriesFromDatabase();

        // Xử lý sự kiện khi nhấp vào một mục truyện
        gdvDSTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy ID của truyện được chọn
                Truyen selectedStory = truyen.get(position);
                int storyId = selectedStory.getID();

                // Chuyển sang Activity hiển thị chi tiết truyện
                Intent intent = new Intent(ManDanhSachTruyen.this, ManChiTietTruyen.class);
                intent.putExtra("storyId", storyId);
                intent.putExtra("tentruyen", selectedStory.getTenTruyen());
                intent.putExtra("chitiet", selectedStory.getChiTiet());
                intent.putExtra("anhtruyen", selectedStory.getAnh());

                startActivity(intent);
            }
        });
    }

    private void loadStoriesFromDatabase() {
        Cursor cursor = databaseDocTruyen.getStoriesByCategory(categoryId);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("idtruyen")); // Sử dụng 'idtruyen' thay vì 'id'
                String tenTruyen = cursor.getString(cursor.getColumnIndexOrThrow("tieude"));
                String chiTiet = cursor.getString(cursor.getColumnIndexOrThrow("chitiet"));
                String anh = cursor.getString(cursor.getColumnIndexOrThrow("anh"));
                int idTK = cursor.getInt(cursor.getColumnIndexOrThrow("idtaikhoan"));

                // Thêm truyện vào danh sách
                truyen.add(new Truyen(id, tenTruyen, chiTiet, anh, idTK));
            } while (cursor.moveToNext());

            // Cập nhật GridView sau khi có dữ liệu
            adapter.notifyDataSetChanged();
        } else {
            Log.d("Database", "Cursor is null or empty");
        }
    }


}
