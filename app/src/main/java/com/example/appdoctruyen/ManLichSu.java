package com.example.appdoctruyen;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.AdapterHistory;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.object.HistoryItem;

import java.util.List;

public class ManLichSu extends AppCompatActivity {

        private ListView listView;
        private AdapterHistory adapterHistory;
        private DatabaseDocTruyen dbHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

            listView = findViewById(R.id.listViewHistory);
            dbHelper = new DatabaseDocTruyen(this);

            // Lấy lịch sử đọc từ cơ sở dữ liệu
            List<HistoryItem> readingHistory = dbHelper.getReadingHistory();

            // Khởi tạo và gán adapter
            adapterHistory = new AdapterHistory(this, readingHistory);
            listView.setAdapter(adapterHistory);
        }
    }


