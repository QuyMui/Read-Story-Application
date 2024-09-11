package com.example.appdoctruyen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.adapterdanhmuc;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.object.DanhMuc;

import java.util.ArrayList;
import java.util.HashMap;

public class ManDanhMuc extends AppCompatActivity {

    private ListView listViewCategories;
    private ArrayList<DanhMuc> categories;
    private adapterdanhmuc adapter;
    private DatabaseDocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_stories);

        // Kết nối ListView với giao diện
        listViewCategories = findViewById(R.id.listViewCategories);

        // Khởi tạo danh sách các danh mục và adapter
        categories = new ArrayList<>();
        adapter = new adapterdanhmuc(this, categories);
        listViewCategories.setAdapter(adapter);

        // Lấy dữ liệu danh mục từ cơ sở dữ liệu
        databaseDocTruyen = new DatabaseDocTruyen(this);
        loadCategoriesFromDatabase();

        // Xử lý sự kiện khi nhấp vào một mục danh mục
        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy ID của danh mục được chọn
                DanhMuc selectedCategory = categories.get(position);
                int categoryId = selectedCategory.getId();

                // Chuyển sang Activity hiển thị chi tiết truyện theo danh mục
                Intent intent = new Intent(ManDanhMuc.this, ManDanhSachTruyen.class);
                intent.putExtra("categoryId", categoryId);
                startActivity(intent);
            }
        });
    }

    // Hàm để lấy danh sách các danh mục từ cơ sở dữ liệu
    private void loadCategoriesFromDatabase() {
        Cursor cursor = databaseDocTruyen.getAllCategories();

        if (cursor != null && cursor.moveToFirst()) {
            // Ensure column indexes are valid
            int idColumnIndex = cursor.getColumnIndex("iddanhmuc");
            int nameColumnIndex = cursor.getColumnIndex("tendanhmuc");

            // Check if the column indices are valid
            if (idColumnIndex == -1 || nameColumnIndex == -1) {
                // Handle the case where the column does not exist in the result
                throw new IllegalStateException("Invalid column name in the query result.");
            }

            // Loop through the cursor
            do {
                // Get the data from the cursor safely
                int iddanhmuc = cursor.getInt(idColumnIndex);
                String tendanhmuc = cursor.getString(nameColumnIndex);

                // Add the data to the categories list
                categories.add(new DanhMuc(iddanhmuc, tendanhmuc));
            } while (cursor.moveToNext());

            // Notify the adapter to update the ListView
            adapter.notifyDataSetChanged();
        }
    }

}