package com.example.appdoctruyen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.data.DatabaseDocTruyen;

public class ManNoiDungChuong extends AppCompatActivity {

    private TextView txtTenChuong;
    private TextView txtNoiDungChuong;
    private Button btnPrevChapter, btnNextChapter;
    private DatabaseDocTruyen databaseDocTruyen;
    private int chapterId;
    private int storyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_chuong);

        // Initialize the database
        databaseDocTruyen = new DatabaseDocTruyen(this);

        // Get views
        txtTenChuong = findViewById(R.id.txtTenChuong);
        txtNoiDungChuong = findViewById(R.id.txtNoiDungChuong);
        btnPrevChapter = findViewById(R.id.btnPrevChapter);
        btnNextChapter = findViewById(R.id.btnNextChapter);

        // Get data from Intent
        Intent intent = getIntent();
        chapterId = intent.getIntExtra("chapterId", -1);
        storyId = intent.getIntExtra("storyId", -1);  // Ensure storyId is received

        Log.d("ManNoiDungChuong", "Received chapterId: " + chapterId + ", storyId: " + storyId);

        if (chapterId != -1) {
            loadChapterData(chapterId);
        } else {
            Toast.makeText(this, "Chapter ID is invalid.", Toast.LENGTH_SHORT).show();
        }

        // Set listeners for Previous and Next buttons
        btnPrevChapter.setOnClickListener(v -> navigateToChapter(-1)); // Previous chapter
        btnNextChapter.setOnClickListener(v -> navigateToChapter(1));  // Next chapter
    }

    // Load chapter data by ID
    private void loadChapterData(int chapterId) {
        Cursor cursor = databaseDocTruyen.getChapterById(chapterId);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String tenChuong = cursor.getString(cursor.getColumnIndex(DatabaseDocTruyen.TEN_CHAPTER));
            @SuppressLint("Range") String noiDungChuong = cursor.getString(cursor.getColumnIndex(DatabaseDocTruyen.NOI_DUNG_CHAPTER));

            Log.d("ManNoiDungChuong", "Chapter Title: " + tenChuong + ", Content: " + noiDungChuong);
            // Display the data
            txtTenChuong.setText(tenChuong);
            txtNoiDungChuong.setText(noiDungChuong);

            // Save user progress
            databaseDocTruyen.saveUserProgress(storyId, chapterId); // Lưu tiến trình đọc
        } else {
            Toast.makeText(this, "Invalid chapter", Toast.LENGTH_SHORT).show();
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    @SuppressLint("Range")
    private void navigateToChapter(int direction) {
        Cursor cursor = databaseDocTruyen.getNextOrPreviousChapter(storyId, chapterId, direction);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                chapterId = cursor.getInt(cursor.getColumnIndex(DatabaseDocTruyen.ID_CHAPTER)); // Update chapterId
                loadChapterData(chapterId);  // Load new chapter data
            } else {
                // No more chapters
                String message = (direction == -1) ? "This is the first chapter." : "This is the last chapter.";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Error retrieving chapter data.", Toast.LENGTH_SHORT).show();
        }
    }
}
