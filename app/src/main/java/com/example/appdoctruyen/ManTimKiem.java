package com.example.appdoctruyen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdoctruyen.adapter.adaptertruyen;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.object.Truyen;

import java.util.ArrayList;

public class ManTimKiem extends AppCompatActivity {

    private EditText editTimKiem;
    private ListView listviewtimkiem;
    private adaptertruyen adapter;
    private ArrayList<Truyen> truyenTranhArrayList;
    private DatabaseDocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_tim_kiem);

        databaseDocTruyen = new DatabaseDocTruyen(this);  // Initialize DatabaseDocTruyen
        anhXa();
        init();
        setUp();
        setClick();

        // Apply insets to avoid content being obscured by system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setClick() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Ensure adapter is not null
                if (adapter != null) {
                    adapter.sortTruyen(s.toString());
                } else {
                    // Handle case where adapter is null
                    Log.e("ManTimKiem", "Adapter is null. Cannot sort data.");
                }
            }
        };

        // Apply TextWatcher to EditText
        editTimKiem.addTextChangedListener(textWatcher);
    }

    private void init() {
        truyenTranhArrayList = new ArrayList<>();

        // Retrieve data from the database
        Cursor cursor = null;
        try {
            cursor = databaseDocTruyen.getData2();  // Assuming you have this method in DatabaseDocTruyen
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String tenTruyen = cursor.getString(1);
                String chiTiet = cursor.getString(2);
                String anh = cursor.getString(3);
                int id_tk = cursor.getInt(4);
                truyenTranhArrayList.add(new Truyen(id, tenTruyen, chiTiet, anh, id_tk));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Initialize adapter with the list of data loaded
        adapter = new adaptertruyen(this, R.layout.list_item_truyen, truyenTranhArrayList);

        // Set up ListView click event
        listviewtimkiem.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ManTimKiem.this, ManChiTietTruyen.class);
            int idTruyen = truyenTranhArrayList.get(position).getID();
            String tent = truyenTranhArrayList.get(position).getTenTruyen();
            String chiTiet = truyenTranhArrayList.get(position).getChiTiet();
            String anhTruyen = truyenTranhArrayList.get(position).getAnh();
            intent.putExtra("storyId", idTruyen);
            intent.putExtra("tentruyen", tent);
            intent.putExtra("chitiet", chiTiet);
            intent.putExtra("anhtruyen", anhTruyen);
            startActivity(intent);
        });

        // Set up the adapter
        listviewtimkiem.setAdapter(adapter);
    }


    private void setUp() {
        listviewtimkiem.setAdapter(adapter);
    }

    private void anhXa() {
        listviewtimkiem = findViewById(R.id.listviewtimkiem);
        editTimKiem = findViewById(R.id.editTimKiem);
    }
}
