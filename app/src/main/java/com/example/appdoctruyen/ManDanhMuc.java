package com.example.appdoctruyen;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.adapter.adaptertruyen;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.object.Truyen;

import java.util.ArrayList;
import java.util.List;

public class ManDanhMuc extends AppCompatActivity {
}

//    DatabaseDocTruyen databaseDocTruyen;
//    List<Truyen> storyList;
//    RecyclerView recyclerView;
//    adaptertruyen storyAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category_stories);
//
//        // Ánh xạ RecyclerView
//        recyclerView = findViewById(R.id.recyclerViewStories);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Nhận danh mục đã chọn từ intent
//        String category = getIntent().getStringExtra("CATEGORY");
//
//        // Khởi tạo cơ sở dữ liệu
//        databaseDocTruyen = new DatabaseDocTruyen(this);
//
//        // Lấy danh sách truyện theo danh mục
//        storyList = getStoriesByCategory(category); // Phương thức để lấy truyện từ cơ sở dữ liệu dựa trên danh mục
//
//        // Thiết lập adapter cho RecyclerView
//        storyAdapter = new adaptertruyen(this, R.layout.list_item_truyen, storyList);
//        recyclerView.setAdapter(storyAdapter);
//    }
//
//    private List<Truyen> getStoriesByCategory(String category) {
//        List<Truyen> stories = new ArrayList<>();
//        Cursor cursor = databaseDocTruyen.getStoriesByCategory(category);
//
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String title = cursor.getString(1);
//            String detail = cursor.getString(2);
//            String image = cursor.getString(3);
//            int accountId = cursor.getInt(4);
//            stories.add(new Truyen(id, title, detail, image, accountId));
//        }
//        cursor.close();
//
//        return stories;
//    }
//}
//
