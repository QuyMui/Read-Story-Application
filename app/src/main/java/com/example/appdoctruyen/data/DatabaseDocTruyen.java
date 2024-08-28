package com.example.appdoctruyen.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.appdoctruyen.object.TaiKhoan;
import com.example.appdoctruyen.object.Truyen;

public class DatabaseDocTruyen extends SQLiteOpenHelper {
    Context context1;
    private static final String DATABASE_NAME = "doctruyen";
    private static final int VERSION = 2;

    // Table TaiKhoan
    public static final String TABLE_TAIKHOAN = "taikhoan";
    public static final String ID_TAI_KHOAN = "idtaikhoan";
    public static final String TEN_TAI_KHOAN = "tentaikhoan";
    public static final String MAT_KHAU = "matkhau";
    public static final String PHAN_QUYEN = "phanquyen";
    public static final String EMAIL = "email";


    public static final String TABLE_TRUYEN = "truyen";
    public static final String ID_TRUYEN = "idtruyen";
    public static final String TEN_TRUYEN = "tieude";
    public static final String CHI_TIET = "chitiet";
    public static final String IMAGE = "anh";
    public static final String ID_TAI_KHOAN_FK = "idtaikhoan";
    public static final String ID_DANH_MUC_FK = "iddanhmuc";

    // Table Chapter
    public static final String TABLE_CHAPTER = "chapter";
    public static final String ID_CHAPTER = "idchapter";
    public static final String TEN_CHAPTER = "tenchapter";
    public static final String NOI_DUNG_CHAPTER = "noidungchapter";
    public static final String ID_TRUYEN_FK = "idtruyen";

    // Table DanhMuc
    public static final String TABLE_DANH_MUC = "danhmuc";
    public static final String ID_DANH_MUC = "iddanhmuc";
    public static final String TEN_DANH_MUC = "tendanhmuc";


    private final Context context;

    // SQL to create tables
    private static final String SQL_CREATE_TABLE_TAIKHOAN = "CREATE TABLE " + TABLE_TAIKHOAN + " ( "
            + ID_TAI_KHOAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TEN_TAI_KHOAN + " TEXT UNIQUE, "
            + MAT_KHAU + " TEXT, "
            + EMAIL + " TEXT, "
            + PHAN_QUYEN + " INTEGER)";

    private static final String SQL_CREATE_TABLE_DANH_MUC = "CREATE TABLE " + TABLE_DANH_MUC + " ( "
            + ID_DANH_MUC + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TEN_DANH_MUC + " TEXT UNIQUE)";

    // Các SQL câu lệnh tạo bảng cũ và câu lệnh tạo bảng mới đã thay đổi
    private static final String SQL_CREATE_TABLE_TRUYEN = "CREATE TABLE " + TABLE_TRUYEN + " ( "
            + ID_TRUYEN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TEN_TRUYEN + " TEXT UNIQUE, "
            + CHI_TIET + " TEXT, "
            + IMAGE + " TEXT, "
            + ID_TAI_KHOAN_FK + " INTEGER, "
            + ID_DANH_MUC_FK + " INTEGER, "
            + "FOREIGN KEY (" + ID_TAI_KHOAN_FK + ") REFERENCES " + TABLE_TAIKHOAN + "(" + ID_TAI_KHOAN + "), "
            + "FOREIGN KEY (" + ID_DANH_MUC_FK + ") REFERENCES " + TABLE_DANH_MUC + "(" + ID_DANH_MUC + "))";

    private static final String SQL_CREATE_TABLE_CHAPTER = "CREATE TABLE " + TABLE_CHAPTER + " ( "
            + ID_CHAPTER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TEN_CHAPTER + " TEXT, "
            + NOI_DUNG_CHAPTER + " TEXT, "
            + ID_TRUYEN_FK + " INTEGER, "
            + "FOREIGN KEY (" + ID_TRUYEN_FK + ") REFERENCES " + TABLE_TRUYEN + "(" + ID_TRUYEN + "))";

    // SQL to insert initial data
    private static final String SQL_INSERT_ADMIN = "INSERT INTO " + TABLE_TAIKHOAN + " VALUES (null,'admin','admin','admin@gmail.com',2)";
    private static final String SQL_INSERT_DUY = "INSERT INTO " + TABLE_TAIKHOAN + " VALUES (null,'duy','duy123','duy@gmail.com',1)";

    private static final String SQL_INSERT_DANH_MUC_1 = "INSERT INTO " + TABLE_DANH_MUC + " VALUES (null, 'Truyện cổ tích')";
    private static final String SQL_INSERT_DANH_MUC_2 = "INSERT INTO " + TABLE_DANH_MUC + " VALUES (null, 'Truyện ngụ ngôn')";
    private static final String SQL_INSERT_DANH_MUC_3 = "INSERT INTO " + TABLE_DANH_MUC + " VALUES (null, 'Truyện cười')";

    private static final String SQL_INSERT_TRUYEN_1 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Rùa và Thỏ','Chi tiết về cuộc thi chạy giữa Rùa và Thỏ...','https://toplist.vn/images/800px/rua-va-tho-230179.jpg',1,1)";
    private static final String SQL_INSERT_TRUYEN_2 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Củ cải trắng','Một câu chuyện mùa đông về củ cải trắng...','https://toplist.vn/images/800px/cu-cai-trang-230181.jpg',1,2)";
    private static final String SQL_INSERT_TRUYEN_3 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Dê đen và dê trắng','Câu chuyện về hai chú Dê đen và Dê trắng...','https://toplist.vn/images/800px/de-den-va-de-trang-230182.jpg',1,2)";
    private static final String SQL_INSERT_TRUYEN_4 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Chú bé chăn cừu','Chuyện về chú bé chăn cừu và bài học nhớ đời...','https://toplist.vn/images/800px/chu-be-chan-cuu-230183.jpg',1,1)";
    private static final String SQL_INSERT_TRUYEN_5 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Cậu bé chăn cừu và cây đa cổ thụ','Ngày xửa ngày xưa, một cậu bé chăn cừu và cây đa cổ thụ...','https://toplist.vn/images/800px/chu-be-chan-cuu-230183.jpg',1,1)";
    private static final String SQL_INSERT_TRUYEN_6 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Conan','Câu chuyện về thám tử lừng danh Conan...','https://example.com/conan.jpg',1,3)";
    private static final String SQL_INSERT_TRUYEN_9 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Shin','Câu chuyện về cậu bé Shin và những trò nghịch ngợm...','https://kollersi.com/wp-content/uploads/2024/03/mv5bmja5yjg1ytqtyza5zs00zwnjltkwnjytote5mzqwzdm4ymiwxkeyxkfqcgdeqxvymtezmti1mjk3-_v1_fmjpg_ux1000_.jpg',1,3)";
    private static final String SQL_INSERT_TRUYEN_7 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Naruto','Câu chuyện về cậu bé Naruto và hành trình trở thành Hokage...','https://stbhatay.com.vn/wp-content/uploads/2023/02/th-1nrt.jpg',1,3)";
    private static final String SQL_INSERT_TRUYEN_8 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Pokemon','Những chuyến phiêu lưu của Ash và các Pokémon...','https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEi2Mj0Gu_ksWef8fooIdcM79MJbkYyVNp1vBDOqpv6xTyDSdDDKdsmx2guh21q2T___inZVJQT10lRDCvpAERZ5ox8kAme5GioNUsRsudviFCdmAg7k3PeFBj_76czkvYkdZYghhlAVJoWFjBuxIv1Or_pXohvIFZzYPfcVJvYHwENgU4aPAMMPm3S06Fm7/s16000/000.jpg',1,3)";
    private static final String SQL_INSERT_TRUYEN_10 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Doraemon','Chuyện về chú mèo máy Doraemon và những cuộc phiêu lưu cùng Nobita...','https://cn-e-pic.itoon.org/internet-search/f60ad1167ea260fa35f719ec39a2ca1f.webp',1,3)";

    private static final String SQL_INSERT_CHAPTER_1_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 1', 'Nội dung chương 1...', 1)";
    private static final String SQL_INSERT_CHAPTER_1_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 2', 'Nội dung chương 2...', 1)";
    private static final String SQL_INSERT_CHAPTER_1_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 3', 'Nội dung chương 3...', 1)";
    private static final String SQL_INSERT_CHAPTER_1_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 4', 'Nội dung chương 4...', 1)";
    private static final String SQL_INSERT_CHAPTER_1_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 5', 'Nội dung chương 5...', 1)";

    private static final String SQL_INSERT_CHAPTER_2_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 1', 'Nội dung chương 1...', 2)";
    private static final String SQL_INSERT_CHAPTER_2_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 2', 'Nội dung chương 2...', 2)";
    private static final String SQL_INSERT_CHAPTER_2_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 3', 'Nội dung chương 3...', 2)";
    private static final String SQL_INSERT_CHAPTER_2_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 4', 'Nội dung chương 4...', 2)";
    private static final String SQL_INSERT_CHAPTER_2_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 5', 'Nội dung chương 5...', 2)";

    private static final String SQL_INSERT_CHAPTER_3_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 1', 'Nội dung chương 1...', 3)";
    private static final String SQL_INSERT_CHAPTER_3_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 2', 'Nội dung chương 2...', 3)";
    private static final String SQL_INSERT_CHAPTER_3_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 3', 'Nội dung chương 3...', 3)";
    private static final String SQL_INSERT_CHAPTER_3_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 4', 'Nội dung chương 4...', 3)";
    private static final String SQL_INSERT_CHAPTER_3_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 5', 'Nội dung chương 5...', 3)";

    private static final String SQL_INSERT_CHAPTER_4_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 1', 'Nội dung chương 1...', 4)";
    private static final String SQL_INSERT_CHAPTER_4_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 2', 'Nội dung chương 2...', 4)";
    private static final String SQL_INSERT_CHAPTER_4_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 3', 'Nội dung chương 3...', 4)";
    private static final String SQL_INSERT_CHAPTER_4_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 4', 'Nội dung chương 4...', 4)";
    private static final String SQL_INSERT_CHAPTER_4_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 5', 'Nội dung chương 5...', 4)";

    private static final String SQL_INSERT_CHAPTER_5_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 1', 'Nội dung chương 1...', 5)";
    private static final String SQL_INSERT_CHAPTER_5_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 2', 'Nội dung chương 2...', 5)";
    private static final String SQL_INSERT_CHAPTER_5_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 3', 'Nội dung chương 3...', 5)";
    private static final String SQL_INSERT_CHAPTER_5_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 4', 'Nội dung chương 4...', 5)";
    private static final String SQL_INSERT_CHAPTER_5_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 5', 'Nội dung chương 5...', 5)";

    private static final String SQL_INSERT_CHAPTER_6_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 1', 'Nội dung chương 1...', 6)";
    private static final String SQL_INSERT_CHAPTER_6_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 2', 'Nội dung chương 2...', 6)";
    private static final String SQL_INSERT_CHAPTER_6_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 3', 'Nội dung chương 3...', 6)";
    private static final String SQL_INSERT_CHAPTER_6_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 4', 'Nội dung chương 4...', 6)";
    private static final String SQL_INSERT_CHAPTER_6_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 5', 'Nội dung chương 5...', 6)";

    private static final String SQL_INSERT_CHAPTER_7_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 1', 'Nội dung chương 1...', 7)";
    private static final String SQL_INSERT_CHAPTER_7_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 2', 'Nội dung chương 2...', 7)";
    private static final String SQL_INSERT_CHAPTER_7_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 3', 'Nội dung chương 3...', 7)";
    private static final String SQL_INSERT_CHAPTER_7_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 4', 'Nội dung chương 4...', 7)";
    private static final String SQL_INSERT_CHAPTER_7_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 5', 'Nội dung chương 5...', 7)";

    private static final String SQL_INSERT_CHAPTER_8_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 1', 'Nội dung chương 1...', 8)";
    private static final String SQL_INSERT_CHAPTER_8_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 2', 'Nội dung chương 2...', 8)";
    private static final String SQL_INSERT_CHAPTER_8_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 3', 'Nội dung chương 3...', 8)";
    private static final String SQL_INSERT_CHAPTER_8_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 4', 'Nội dung chương 4...', 8)";
    private static final String SQL_INSERT_CHAPTER_8_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 5', 'Nội dung chương 5...', 8)";

    private static final String SQL_INSERT_CHAPTER_9_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 1', 'Nội dung chương 1...', 9)";
    private static final String SQL_INSERT_CHAPTER_9_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 2', 'Nội dung chương 2...', 9)";
    private static final String SQL_INSERT_CHAPTER_9_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 3', 'Nội dung chương 3...', 9)";
    private static final String SQL_INSERT_CHAPTER_9_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 4', 'Nội dung chương 4...', 9)";
    private static final String SQL_INSERT_CHAPTER_9_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 5', 'Nội dung chương 5...', 9)";

    private static final String SQL_INSERT_CHAPTER_10_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 1', 'Nội dung chương 1...', 10)";
    private static final String SQL_INSERT_CHAPTER_10_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 2', 'Nội dung chương 2...', 10)";
    private static final String SQL_INSERT_CHAPTER_10_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 3', 'Nội dung chương 3...', 10)";
    private static final String SQL_INSERT_CHAPTER_10_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 4', 'Nội dung chương 4...', 10)";
    private static final String SQL_INSERT_CHAPTER_10_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 5', 'Nội dung chương 5...', 10)";

    // SQL để thêm cột category
    private static final String SQL_ALTER_TABLE_ADD_CATEGORY = "ALTER TABLE " + TABLE_TRUYEN + " ADD COLUMN category TEXT";

    public DatabaseDocTruyen(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }


//    public DatabaseDocTruyen(MainActivity context, Context context1) {
//        super(context, DATABASE_NAME, null, VERSION);
//        this.context = context1;
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
        db.execSQL(SQL_CREATE_TABLE_TAIKHOAN);
        db.execSQL(SQL_CREATE_TABLE_DANH_MUC);
        db.execSQL(SQL_CREATE_TABLE_TRUYEN);
        db.execSQL(SQL_CREATE_TABLE_CHAPTER);

        // Insert initial data
        db.execSQL(SQL_INSERT_ADMIN);
        db.execSQL(SQL_INSERT_DUY);

        db.execSQL(SQL_INSERT_DANH_MUC_1);
        db.execSQL(SQL_INSERT_DANH_MUC_2);
        db.execSQL(SQL_INSERT_DANH_MUC_3);

        db.execSQL(SQL_INSERT_TRUYEN_1);
        db.execSQL(SQL_INSERT_TRUYEN_2);
        db.execSQL(SQL_INSERT_TRUYEN_3);
        db.execSQL(SQL_INSERT_TRUYEN_4);
        db.execSQL(SQL_INSERT_TRUYEN_5);
        // Insert new stories
        db.execSQL(SQL_INSERT_TRUYEN_6);
        db.execSQL(SQL_INSERT_TRUYEN_7);
        db.execSQL(SQL_INSERT_TRUYEN_8);
        db.execSQL(SQL_INSERT_TRUYEN_9);
        db.execSQL(SQL_INSERT_TRUYEN_10);



        db.execSQL(SQL_INSERT_CHAPTER_1_1);
        db.execSQL(SQL_INSERT_CHAPTER_1_2);
        db.execSQL(SQL_INSERT_CHAPTER_1_3);
        db.execSQL(SQL_INSERT_CHAPTER_1_4);
        db.execSQL(SQL_INSERT_CHAPTER_1_5);

        db.execSQL(SQL_INSERT_CHAPTER_2_1);
        db.execSQL(SQL_INSERT_CHAPTER_2_2);
        db.execSQL(SQL_INSERT_CHAPTER_2_3);
        db.execSQL(SQL_INSERT_CHAPTER_2_4);
        db.execSQL(SQL_INSERT_CHAPTER_2_5);

        db.execSQL(SQL_INSERT_CHAPTER_3_1);
        db.execSQL(SQL_INSERT_CHAPTER_3_2);
        db.execSQL(SQL_INSERT_CHAPTER_3_3);
        db.execSQL(SQL_INSERT_CHAPTER_3_4);
        db.execSQL(SQL_INSERT_CHAPTER_3_5);

        db.execSQL(SQL_INSERT_CHAPTER_4_1);
        db.execSQL(SQL_INSERT_CHAPTER_4_2);
        db.execSQL(SQL_INSERT_CHAPTER_4_3);
        db.execSQL(SQL_INSERT_CHAPTER_4_4);
        db.execSQL(SQL_INSERT_CHAPTER_4_5);

        db.execSQL(SQL_INSERT_CHAPTER_5_1);
        db.execSQL(SQL_INSERT_CHAPTER_5_2);
        db.execSQL(SQL_INSERT_CHAPTER_5_3);
        db.execSQL(SQL_INSERT_CHAPTER_5_4);
        db.execSQL(SQL_INSERT_CHAPTER_5_5);

        db.execSQL(SQL_INSERT_CHAPTER_6_1);
        db.execSQL(SQL_INSERT_CHAPTER_6_2);
        db.execSQL(SQL_INSERT_CHAPTER_6_3);
        db.execSQL(SQL_INSERT_CHAPTER_6_4);
        db.execSQL(SQL_INSERT_CHAPTER_6_5);


        db.execSQL(SQL_INSERT_CHAPTER_7_1);
        db.execSQL(SQL_INSERT_CHAPTER_7_2);
        db.execSQL(SQL_INSERT_CHAPTER_7_3);
        db.execSQL(SQL_INSERT_CHAPTER_7_4);
        db.execSQL(SQL_INSERT_CHAPTER_7_5);

        db.execSQL(SQL_INSERT_CHAPTER_8_1);
        db.execSQL(SQL_INSERT_CHAPTER_8_2);
        db.execSQL(SQL_INSERT_CHAPTER_8_3);
        db.execSQL(SQL_INSERT_CHAPTER_8_4);
        db.execSQL(SQL_INSERT_CHAPTER_8_5);

        db.execSQL(SQL_INSERT_CHAPTER_9_1);
        db.execSQL(SQL_INSERT_CHAPTER_9_2);
        db.execSQL(SQL_INSERT_CHAPTER_9_3);
        db.execSQL(SQL_INSERT_CHAPTER_9_4);
        db.execSQL(SQL_INSERT_CHAPTER_9_5);

        db.execSQL(SQL_INSERT_CHAPTER_10_1);
        db.execSQL(SQL_INSERT_CHAPTER_10_2);
        db.execSQL(SQL_INSERT_CHAPTER_10_3);
        db.execSQL(SQL_INSERT_CHAPTER_10_4);
        db.execSQL(SQL_INSERT_CHAPTER_10_5);
            db.setTransactionSuccessful();
        }
        finally {
                db.endTransaction();
            }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.beginTransaction();
            try {
                // Thực hiện thay đổi cấu trúc cơ sở dữ liệu
                if (oldVersion < 2) {
                    db.execSQL(SQL_ALTER_TABLE_ADD_CATEGORY);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }


    //Lấy tất cả tài khoản
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_TAIKHOAN , null );
        return res;
    }
    //them tai khoan
    public void AddTaiKhoan(TaiKhoan taiKhoan){
        SQLiteDatabase db = this.getWritableDatabase();

        //không thể lưu trực tiếp xuống insert nên thông qua contentvalues
        ContentValues values = new ContentValues();
        values.put(TEN_TAI_KHOAN,taiKhoan.getmTenTaiKhoan());
        values.put(MAT_KHAU,taiKhoan.getmMatKhau());
        values.put(EMAIL,taiKhoan.getmEmail());
        values.put(PHAN_QUYEN,taiKhoan.getmPhanQuyen());

        db.insert(TABLE_TAIKHOAN,null,values);
        //đóng lại db cho an toàn
        db.close();
        //Log.e("Add Tai Khoan ","thành công");
    }
    //Thêm truyện
    public void AddTruyen(Truyen truyen){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TEN_TRUYEN,truyen.getTenTruyen());
        values.put(CHI_TIET,truyen.getChiTiet());
        values.put(IMAGE,truyen.getAnh());
        values.put(ID_TAI_KHOAN,truyen.getID_TK());

        db.insert(TABLE_TRUYEN,null,values);
        db.close();
        Log.e("Add Truyện : ","Thành công");
    }


    //Lấy truyen mới nhất
    public Cursor getData1(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from "+TABLE_TRUYEN+" ORDER BY "+ID_TRUYEN+" DESC LIMIT 3" , null );
        return res;
    }
    //Lấy tất cả truyện
    public Cursor getData2(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_TRUYEN,null);
        return res;
    }
    public Cursor getChaptersByStoryId(int storyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CHAPTER + " WHERE " + ID_TRUYEN_FK + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(storyId)});

        return cursor;
    }
    //Xóa truyện với id = i
    public int Delete(int i){
        SQLiteDatabase db = this.getReadableDatabase();

        int res = db.delete("truyen",ID_TRUYEN+" = "+i,null);
        return res;

    }


    public Cursor getChapterById(int chapterId) {

            SQLiteDatabase db = this.getReadableDatabase(); // Mở cơ sở dữ liệu để đọc

            // Câu lệnh SQL để lấy thông tin của một chương dựa trên ID
            String query = "SELECT idchapter, tenchapter, noidungchapter FROM chapter WHERE idchapter = ?";

            // Thực hiện truy vấn với tham số là ID của chương
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(chapterId)});

            return cursor; // Trả về đối tượng Cursor chứa kết quả của truy vấn
    }

    public Cursor getStoriesByCategory(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM truyen WHERE category = ?", new String[]{categoryName});
    }






}
