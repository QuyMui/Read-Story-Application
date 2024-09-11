package com.example.appdoctruyen.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.appdoctruyen.object.HistoryItem;
import com.example.appdoctruyen.object.TaiKhoan;
import com.example.appdoctruyen.object.Truyen;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDocTruyen extends SQLiteOpenHelper {
    Context context1;
    private static final String DATABASE_NAME = "doctruyen";
    private static final int VERSION = 76;

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
    public static final String VI_TRI_CHUONG = "vitri_chuong";


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

    public static final String TABLE_LICH_SU = "lich_su";
    public static final String ID_LICH_SU = "idlichsu";
    public static final String ID_TRUYEN_FF = "idtruyen"; // Khóa ngoại liên kết với bảng truyện
    public static final String VI_TRI_CHUONG_FK = "vitri_chuong"; // Lưu vị trí chương đang đọc
    public static final String THOI_GIAN_XEM = "thoigian_xem"; // Thời gian xem chương
    public static final String TIEU_DE_TRUYEN = "tieude"; // Tiêu đề truyện




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

    private static final String SQL_CREATE_TABLE_TRUYEN = "CREATE TABLE " + TABLE_TRUYEN + " ( "
            + ID_TRUYEN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TEN_TRUYEN + " TEXT UNIQUE, "
            + CHI_TIET + " TEXT, "
            + IMAGE + " TEXT, "
            + ID_TAI_KHOAN_FK + " INTEGER, "
            + ID_DANH_MUC_FK + " INTEGER, "
            + VI_TRI_CHUONG + " INTEGER, " // Vị trí chương đang đọc
            + "FOREIGN KEY (" + ID_TAI_KHOAN_FK + ") REFERENCES " + TABLE_TAIKHOAN + "(" + ID_TAI_KHOAN + "), "
            + "FOREIGN KEY (" + ID_DANH_MUC_FK + ") REFERENCES " + TABLE_DANH_MUC + "(" + ID_DANH_MUC + "))";



    private static final String SQL_CREATE_TABLE_CHAPTER = "CREATE TABLE " + TABLE_CHAPTER + " ( "
            + ID_CHAPTER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TEN_CHAPTER + " TEXT, "
            + NOI_DUNG_CHAPTER + " TEXT, "
            + ID_TRUYEN_FK + " INTEGER, "
            + "FOREIGN KEY (" + ID_TRUYEN_FK + ") REFERENCES " + TABLE_TRUYEN + "(" + ID_TRUYEN + "))";



    // Câu lệnh SQL để tạo bảng
    public static final String SQL_CREATE_TABLE_LICH_SU = "CREATE TABLE " + TABLE_LICH_SU + " (" +
            ID_LICH_SU + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ID_TRUYEN_FF + " INTEGER, " +
            VI_TRI_CHUONG_FK + " INTEGER, " +
            THOI_GIAN_XEM + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            TIEU_DE_TRUYEN + " TEXT" +
            ");";
    private static final String SQL_CREATE_USER_PROGRESS_TABLE = "CREATE TABLE IF NOT EXISTS user_progress (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "storyId INTEGER, " +
            "chapterId INTEGER, " +
            "FOREIGN KEY (storyId) REFERENCES truyen(id));";



    // SQL to insert initial data
    private static final String SQL_INSERT_ADMIN = "INSERT INTO " + TABLE_TAIKHOAN + " VALUES (null,'admin','admin','admin@gmail.com',2)";
    private static final String SQL_INSERT_DUY = "INSERT INTO " + TABLE_TAIKHOAN + " VALUES (null,'duy','duy123','duy@gmail.com',1)";

    private static final String SQL_INSERT_DANH_MUC_1 = "INSERT INTO " + TABLE_DANH_MUC + " VALUES (null, 'Truyện cổ tích')";
    private static final String SQL_INSERT_DANH_MUC_2 = "INSERT INTO " + TABLE_DANH_MUC + " VALUES (null, 'Truyện ngụ ngôn')";
    private static final String SQL_INSERT_DANH_MUC_3 = "INSERT INTO " + TABLE_DANH_MUC + " VALUES (null, 'Truyện cười')";

    private static final String SQL_INSERT_TRUYEN_1 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Rùa và Thỏ','Chi tiết về cuộc thi chạy giữa Rùa và Thỏ...','https://toplist.vn/images/800px/rua-va-tho-230179.jpg',1,1, 1)";
    private static final String SQL_INSERT_TRUYEN_2 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Củ cải trắng','Một câu chuyện mùa đông về củ cải trắng...','https://toplist.vn/images/800px/cu-cai-trang-230181.jpg',1,2, 1)";
    private static final String SQL_INSERT_TRUYEN_3 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Dê đen và dê trắng','Câu chuyện về hai chú Dê đen và Dê trắng...','https://toplist.vn/images/800px/de-den-va-de-trang-230182.jpg',1,2, 1)";
    private static final String SQL_INSERT_TRUYEN_4 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Chú bé chăn cừu','Chuyện về chú bé chăn cừu và bài học nhớ đời...','https://toplist.vn/images/800px/chu-be-chan-cuu-230183.jpg',1,1, 1)";
    private static final String SQL_INSERT_TRUYEN_5 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Cậu bé chăn cừu và cây đa cổ thụ','Ngày xửa ngày xưa, một cậu bé chăn cừu và cây đa cổ thụ...','https://toplist.vn/images/800px/chu-be-chan-cuu-230183.jpg',1,1, 1)";
    private static final String SQL_INSERT_TRUYEN_6 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Conan','Câu chuyện về thám tử lừng danh Conan...','https://example.com/conan.jpg',1,3, 1)";
    private static final String SQL_INSERT_TRUYEN_7 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Naruto','Câu chuyện về cậu bé Naruto và hành trình trở thành Hokage...','https://stbhatay.com.vn/wp-content/uploads/2023/02/th-1nrt.jpg',1,3, 1)";
    private static final String SQL_INSERT_TRUYEN_8 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Pokemon','Những chuyến phiêu lưu của Ash và các Pokémon...','https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEi2Mj0Gu_ksWef8fooIdcM79MJbkYyVNp1vBDOqpv6xTyDSdDDKdsmx2guh21q2T___inZVJQT10lRDCvpAERZ5ox8kAme5GioNUsRsudviFCdmAg7k3PeFBj_76czkvYkdZYghhlAVJoWFjBuxIv1Or_pXohvIFZzYPfcVJvYHwENgU4aPAMMPm3S06Fm7/s16000/000.jpg',1,3, 1)";
    private static final String SQL_INSERT_TRUYEN_9 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Shin','Câu chuyện về cậu bé Shin và những trò nghịch ngợm...','https://kollersi.com/wp-content/uploads/2024/03/mv5bmja5yjg1ytqtyza5zs00zwnjltkwnjytote5mzqwzdm4ymiwxkeyxkfqcgdeqxvymtezmti1mjk3-_v1_fmjpg_ux1000_.jpg',1,3, 1)";
    private static final String SQL_INSERT_TRUYEN_10 = "INSERT INTO " + TABLE_TRUYEN + " VALUES (null,'Doraemon','Chuyện về chú mèo máy Doraemon và những cuộc phiêu lưu cùng Nobita...','https://cn-e-pic.itoon.org/internet-search/f60ad1167ea260fa35f719ec39a2ca1f.webp',1,3, 1)";

    private static final String SQL_INSERT_CHAPTER_1_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 1', 'Ngày xửa ngày xưa, trong một khu rừng xanh tươi, có một con thỏ rất nhanh nhẹn và một con rùa chậm chạp. Thỏ thường xuyên khoe khoang về khả năng chạy nhanh của mình, chế giễu rùa chậm chạp. Rùa không phản ứng mà bình tĩnh nói: “Tôi thách bạn đua một cuộc đua, để chứng minh rằng sự kiên trì cũng quan trọng.” Các con vật trong rừng đều ngạc nhiên, nhưng thỏ cười lớn và chấp nhận thách đấu. Ngày đua được ấn định, và tất cả các con vật trong rừng háo hức chờ đợi xem ai sẽ chiến thắng.', 1)";
    private static final String SQL_INSERT_CHAPTER_1_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 2', 'Cuối cùng, ngày đua đã đến. Mặt trời chiếu sáng, và các loài vật tụ tập quanh vạch xuất phát. Khi hiệu lệnh được phát ra, thỏ lao đi như một mũi tên, để lại rùa ở lại phía sau. Thỏ nghĩ rằng nó sẽ thắng dễ dàng, nên quyết định dừng lại nghỉ ngơi bên gốc cây. Trong khi đó, rùa chậm rãi tiến về phía trước, không hề vội vàng. Mặc dù thỏ nhanh chóng, nhưng nó đã sai lầm khi nghỉ ngơi, trong khi rùa vẫn tiếp tục bước từng bước một.', 1)";
    private static final String SQL_INSERT_CHAPTER_1_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 3', 'Sau một giấc ngủ dài, thỏ tỉnh dậy và nhận ra rằng rùa đã tiến rất gần đến vạch đích. Thỏ hoảng hốt chạy hết tốc lực, nhưng nó đã quá muộn. Rùa với sự kiên trì của mình đã vượt qua vạch đích trước. Tất cả các loài vật đều reo hò chúc mừng rùa. Thỏ nhận ra rằng tự phụ không phải là điều tốt, và nó đã quá tự mãn vào tốc độ của mình. Rùa đã dạy cho thỏ một bài học quý giá về sự kiên nhẫn và quyết tâm.', 1)";
    private static final String SQL_INSERT_CHAPTER_1_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 4', 'Khi cuộc đua kết thúc, thỏ rất xấu hổ và xin lỗi rùa vì đã chế giễu nó trước đây. Rùa mỉm cười và nói: “Tôi không giận bạn. Điều quan trọng là bạn đã học được một bài học. Tốc độ không phải là tất cả; sự kiên trì và quyết tâm mới là điều quyết định.” Từ đó, thỏ và rùa trở thành bạn bè. Thỏ quyết định không còn khoe khoang về tốc độ của mình nữa và bắt đầu tôn trọng những con vật khác.', 1)";
    private static final String SQL_INSERT_CHAPTER_1_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Rùa và Thỏ - Phần 5', 'Các loài vật trong rừng, sau khi chứng kiến cuộc đua, đã học được một bài học quan trọng: không nên đánh giá người khác chỉ qua vẻ bề ngoài hay khả năng của họ. Mỗi con vật đều có điểm mạnh và điểm yếu riêng. Rùa không chỉ thắng cuộc đua mà còn truyền cảm hứng cho tất cả các loài vật trong rừng. Từ đó trở đi, rùa và thỏ thường chơi cùng nhau, và rừng trở nên hòa bình và đoàn kết hơn.', 1)";

    private static final String SQL_INSERT_CHAPTER_2_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 1', 'Ngày xửa ngày xưa, ở một ngôi làng nhỏ, có một cô bé rất thích làm vườn. Cô bé đã trồng rất nhiều loại rau củ, nhưng đặc biệt yêu thích củ cải trắng. Một ngày, cô bé thấy củ cải mình trồng đã lớn và quyết định thu hoạch. Cô bé háo hức mời bạn bè đến xem và chia sẻ củ cải trắng với mọi người. Cô bé và bạn bè cùng nhau chế biến nhiều món ăn ngon từ củ cải và cảm thấy rất vui vẻ. Qua câu chuyện này, cô bé học được cách chăm sóc cây trồng và giá trị của việc chia sẻ.', 2)";
    private static final String SQL_INSERT_CHAPTER_2_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 2', 'Sau khi chia sẻ củ cải trắng, cô bé nhận được rất nhiều lời khen ngợi từ bạn bè. Họ cảm thấy thích thú với những món ăn mới mẻ và muốn học hỏi cách trồng củ cải từ cô. Cô bé đã tổ chức một buổi học nhỏ để hướng dẫn bạn bè cách trồng và chăm sóc củ cải. Họ cùng nhau làm đất, gieo hạt và chăm sóc cho cây lớn lên. Mọi người cảm thấy vui vẻ và háo hức chờ đợi ngày thu hoạch củ cải.', 2)";
    private static final String SQL_INSERT_CHAPTER_2_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 3', 'Thời gian trôi qua, những củ cải trắng mà cô bé và bạn bè trồng đã lớn rất nhanh. Một buổi sáng, họ cùng nhau thu hoạch củ cải. Mỗi người đều háo hức và tự hào về thành quả của mình. Họ tổ chức một buổi tiệc nhỏ để ăn mừng và cùng nhau thưởng thức các món ăn làm từ củ cải. Những món ăn này không chỉ ngon mà còn chứa đựng tình bạn và những kỷ niệm đẹp.', 2)";
    private static final String SQL_INSERT_CHAPTER_2_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 4', 'Buổi tiệc diễn ra rất vui vẻ với nhiều tiếng cười và niềm vui. Mọi người cùng nhau chia sẻ những câu chuyện về quá trình trồng củ cải. Cô bé cảm thấy hạnh phúc khi thấy bạn bè của mình cũng yêu thích việc làm vườn như mình. Họ quyết định sẽ tiếp tục trồng nhiều loại rau củ khác nhau và cùng nhau tổ chức các buổi tiệc vào những lần thu hoạch sau.', 2)";
    private static final String SQL_INSERT_CHAPTER_2_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Củ cải trắng - Phần 5', 'Câu chuyện về cô bé và củ cải trắng không chỉ dừng lại ở đó. Họ đã truyền cảm hứng cho những người khác trong làng cùng tham gia trồng rau. Ngôi làng dần dần trở thành một nơi trồng rau xanh tốt, và mọi người cùng nhau học hỏi và phát triển kỹ năng làm vườn. Cô bé nhận ra rằng, niềm vui sẽ nhân đôi khi được chia sẻ và tình bạn sẽ càng bền chặt hơn khi có chung sở thích.', 2)";

    private static final String SQL_INSERT_CHAPTER_3_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 1', 'Ngày xửa ngày xưa, trong một vùng đất xanh tươi, có hai con dê sống bên cạnh nhau. Dê đen và dê trắng, mặc dù có màu sắc khác nhau, nhưng lại rất thân thiết. Mỗi sáng, chúng thường cùng nhau đi ăn cỏ và kể cho nhau nghe những câu chuyện thú vị. Một hôm, dê đen hỏi dê trắng rằng tại sao nó lại thích màu trắng hơn màu đen.', 3)";
    private static final String SQL_INSERT_CHAPTER_3_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 2', 'Dê trắng mỉm cười và trả lời: “Màu trắng của tôi tượng trưng cho sự tinh khiết và ánh sáng. Nó khiến tôi cảm thấy vui vẻ hơn.” Dê đen suy nghĩ một chút và nói: “Còn tôi, màu đen khiến tôi cảm thấy mạnh mẽ và bí ẩn. Tôi tự hào về bản thân mình.” Cả hai cùng cười và tiếp tục hành trình của mình.', 3)";
    private static final String SQL_INSERT_CHAPTER_3_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 3', 'Dần dần, chúng bắt đầu khám phá những điều mới lạ. Dê trắng dẫn dê đen đến những đồng cỏ rộng lớn nơi có nhiều hoa đẹp, còn dê đen dẫn dê trắng đến những nơi có bóng mát và nhiều thức ăn ngon. Cả hai đều học được rằng mỗi màu sắc đều có vẻ đẹp riêng và đều có những điều tốt đẹp để chia sẻ.', 3)";
    private static final String SQL_INSERT_CHAPTER_3_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 4', 'Một ngày nọ, khi đang đi dạo, chúng gặp phải một con sói. Sói rất đáng sợ và đe dọa chúng. Dê đen và dê trắng không biết phải làm gì. Nhưng thay vì hoảng sợ, chúng quyết định phối hợp với nhau. Dê trắng kêu gọi sự chú ý của sói trong khi dê đen lén lút tìm cách thoát thân.', 3)";
    private static final String SQL_INSERT_CHAPTER_3_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Dê đen và dê trắng - Phần 5', 'Nhờ sự phối hợp và tình bạn, chúng đã thoát khỏi nguy hiểm. Sau đó, chúng ngồi lại và chia sẻ những gì đã học được từ trải nghiệm này. Chúng nhận ra rằng, mặc dù có những khác biệt, nhưng khi hợp tác và hỗ trợ lẫn nhau, chúng có thể vượt qua mọi khó khăn. Tình bạn của chúng ngày càng khăng khít hơn.', 3)";

    private static final String SQL_INSERT_CHAPTER_4_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 1', 'Có một chú bé chăn cừu sống ở một ngôi làng nhỏ, nơi có những đồng cỏ xanh mướt và đàn cừu đông đúc. Chú bé rất yêu quý những con cừu của mình và luôn chăm sóc chúng chu đáo. Mỗi ngày, chú dẫn đàn cừu ra đồng, nơi chúng có thể ăn cỏ và vui chơi thoải mái.', 4)";
    private static final String SQL_INSERT_CHAPTER_4_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 2', 'Một hôm, khi đang chăn cừu, chú bé nhìn thấy một con sói đang rình rập. Chú biết rằng nếu không cẩn thận, đàn cừu của mình sẽ gặp nguy hiểm. Chú bé quyết định không chỉ trông chừng đàn cừu mà còn phải nghĩ cách xua đuổi con sói. Chú bé la to và vung tay để dọa con sói.', 4)";
    private static final String SQL_INSERT_CHAPTER_4_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 3', 'Con sói bị dọa đã bỏ chạy, nhưng chú bé vẫn không yên tâm. Chú quyết định về làng và thông báo cho mọi người biết về sự xuất hiện của con sói. Nghe tin, người dân trong làng đã cùng nhau tổ chức một cuộc truy tìm con sói để bảo vệ đàn cừu. Chú bé cảm thấy rất tự hào khi mình đã giúp bảo vệ các bạn của mình.', 4)";
    private static final String SQL_INSERT_CHAPTER_4_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 4', 'Sau khi mọi người trong làng cùng nhau tìm kiếm, cuối cùng họ cũng đã tìm thấy con sói và đuổi nó đi. Từ đó, chú bé chăn cừu trở thành một người hùng trong mắt mọi người. Chú nhận ra rằng việc bảo vệ bạn bè là rất quan trọng và không thể thiếu.', 4)";
    private static final String SQL_INSERT_CHAPTER_4_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Chú bé chăn cừu - Phần 5', 'Kể từ đó, chú bé không chỉ chăm sóc đàn cừu mà còn thường xuyên thông báo cho người dân trong làng về những điều bất thường. Chú trở thành người dẫn dắt và là tấm gương cho những trẻ em khác. Câu chuyện của chú bé chăn cừu đã trở thành một bài học quý giá về sự dũng cảm và trách nhiệm.', 4)";

    private static final String SQL_INSERT_CHAPTER_5_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 1', 'Ngày xửa ngày xưa, có một cậu bé chăn cừu sống ở một ngôi làng nhỏ bên cạnh một cây đa cổ thụ to lớn. Mỗi ngày, cậu dẫn đàn cừu ra đồng cỏ để ăn cỏ. Cây đa không chỉ là nơi cậu nghỉ ngơi mà còn là người bạn thân thiết của cậu. Cậu thường ngồi dưới gốc cây, kể cho nó nghe những câu chuyện về cuộc sống, về những ước mơ và hoài bão của mình. Cậu rất yêu cây đa vì nó mang lại bóng mát và sự bình yên cho cậu.', 5)";
    private static final String SQL_INSERT_CHAPTER_5_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 2', 'Một ngày nọ, cậu bé nhận thấy cây đa có dấu hiệu lạ. Các tán lá của nó bắt đầu héo úa và có vẻ như đang đau ốm. Cậu rất lo lắng và không biết phải làm gì để cứu cây. Cậu quyết định hỏi những người lớn trong làng. Họ nói với cậu rằng cây đa cần được chăm sóc đặc biệt và phải tưới nước thường xuyên. Cậu bé ngay lập tức chạy về nhà, lấy nước và quay lại tưới cho cây.', 5)";
    private static final String SQL_INSERT_CHAPTER_5_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 3', 'Cậu bé chăm sóc cây đa mỗi ngày, tưới nước và nói chuyện với nó. Dần dần, cây bắt đầu hồi phục. Những chiếc lá xanh tươi trở lại, và cây đa dần dần lấy lại sức sống. Cậu cảm thấy vui mừng và tự hào vì đã làm điều tốt cho người bạn của mình. Cậu nhận ra rằng tình bạn và sự chăm sóc có thể mang lại những điều kỳ diệu.', 5)";
    private static final String SQL_INSERT_CHAPTER_5_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 4', 'Một hôm, trong lúc cậu đang chăm sóc cây đa, một cơn bão lớn ập đến. Gió mạnh thổi bay mọi thứ xung quanh, và cậu bé lo lắng cho cây đa. Cậu quyết định đứng chắn trước cây, không để cho bão làm hư hại nó. Với tất cả sức lực của mình, cậu cố gắng bảo vệ cây khỏi cơn bão. Cuối cùng, cơn bão qua đi, nhưng cây đa vẫn đứng vững, và cậu bé cảm thấy rất tự hào.', 5)";
    private static final String SQL_INSERT_CHAPTER_5_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Cậu bé chăn cừu và cây đa cổ thụ - Phần 5', 'Kể từ đó, cậu bé và cây đa trở thành một biểu tượng của tình bạn và lòng dũng cảm. Cậu bé đã học được rằng việc chăm sóc và bảo vệ những điều quý giá là rất quan trọng. Ngày qua ngày, cậu vẫn dẫn đàn cừu ra đồng, nhưng không quên dành thời gian chăm sóc cho cây đa. Cậu bé nhận ra rằng, mỗi chúng ta đều cần có trách nhiệm với những gì mình yêu thương, và tình bạn có thể vượt qua mọi thử thách.', 5)";

    private static final String SQL_INSERT_CHAPTER_6_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 1', 'Hình ảnh: https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg', 6)";
    private static final String SQL_INSERT_CHAPTER_6_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 2', 'Hình ảnh: https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg', 6)";
    private static final String SQL_INSERT_CHAPTER_6_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 3', 'Hình ảnh: https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg', 6)";
    private static final String SQL_INSERT_CHAPTER_6_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 4', 'Hình ảnh: https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg', 6)";
    private static final String SQL_INSERT_CHAPTER_6_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Conan - Phần 5', 'Hình ảnh: https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg, https://i.pinimg.com/736x/53/e5/9a/53e59aee3d8428faa690202f78762f1e.jpg', 6)";

    private static final String SQL_INSERT_CHAPTER_7_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 1', 'Hình ảnh: https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png', 7)";
    private static final String SQL_INSERT_CHAPTER_7_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 2', 'Hình ảnh: https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png', 7)";
    private static final String SQL_INSERT_CHAPTER_7_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 3', 'Hình ảnh: https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png', 7)";
    private static final String SQL_INSERT_CHAPTER_7_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 4', 'Hình ảnh: https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png', 7)";
    private static final String SQL_INSERT_CHAPTER_7_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Naruto - Phần 5', 'Hình ảnh: https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png, https://genk.mediacdn.vn/k:thumb_w/640/2015/0002-1425462191042/khuon-mat-cua-kakashi-lo-dien-trong-truyen-tranh-naruto-moi.png', 7)";

    private static final String SQL_INSERT_CHAPTER_8_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 1', 'Hình ảnh: https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png', 8)";
    private static final String SQL_INSERT_CHAPTER_8_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 2', 'Hình ảnh: https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png', 8)";
    private static final String SQL_INSERT_CHAPTER_8_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 3', 'Hình ảnh: https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png', 8)";
    private static final String SQL_INSERT_CHAPTER_8_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 4', 'Hình ảnh: https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png', 8)";
    private static final String SQL_INSERT_CHAPTER_8_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Pokemon - Phần 5', 'Hình ảnh: https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png, https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiX7ivOY-YvEXy12q9fdhWsr1NFL1gjwxfcIX1J69suWpaNxwfIb5BYjhrndmFR7l75rXkBq3w1zeYDAnZXDsmr4L5MuM6nvmyyL9y9eNNfXhx4EEWs6oA_VZbOUKezxY2H3d2yURq0jpI/s1600/003.png', 8)";

    private static final String SQL_INSERT_CHAPTER_9_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 1', 'https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483', 9)";
    private static final String SQL_INSERT_CHAPTER_9_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 2', 'https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483', 9)";
    private static final String SQL_INSERT_CHAPTER_9_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 3', 'https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483', 9)";
    private static final String SQL_INSERT_CHAPTER_9_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 4', 'https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483', 9)";
    private static final String SQL_INSERT_CHAPTER_9_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Shin - Phần 5', 'https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483, https://bizweb.dktcdn.net/100/445/986/products/image1920-047bff12-38f4-4138-9497-430dc05019d4.jpg?v=1695262915483', 9)";

    private static final String SQL_INSERT_CHAPTER_10_1 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 1', 'https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png', 10)";
    private static final String SQL_INSERT_CHAPTER_10_2 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 2', 'https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png', 10)";
    private static final String SQL_INSERT_CHAPTER_10_3 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 3', 'https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png', 10)";
    private static final String SQL_INSERT_CHAPTER_10_4 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 4', 'https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png', 10)";
    private static final String SQL_INSERT_CHAPTER_10_5 = "INSERT INTO " + TABLE_CHAPTER + " VALUES (null, 'Doraemon - Phần 5', 'https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png, https://tuoithocon.wordpress.com/wp-content/uploads/2014/12/daichohen_doraemon_v01_002.png', 10)";

    private static final String SQL_INSERT_LICH_SU_1 = "INSERT INTO " + TABLE_LICH_SU + " (ID_TRUYEN_FF, VI_TRI_CHUONG_FK, TIEU_DE_TRUYEN) VALUES (1, 1, 'Rùa và Thỏ')";
    private static final String SQL_INSERT_LICH_SU_2 = "INSERT INTO " + TABLE_LICH_SU + " (ID_TRUYEN_FF, VI_TRI_CHUONG_FK, TIEU_DE_TRUYEN) VALUES (1, 2, 'Rùa và Thỏ')";
    private static final String SQL_INSERT_LICH_SU_3 = "INSERT INTO " + TABLE_LICH_SU + " (ID_TRUYEN_FF, VI_TRI_CHUONG_FK, TIEU_DE_TRUYEN) VALUES (2, 1, 'Củ cải trắng')";
    private static final String SQL_INSERT_LICH_SU_4 = "INSERT INTO " + TABLE_LICH_SU + " (ID_TRUYEN_FF, VI_TRI_CHUONG_FK, TIEU_DE_TRUYEN) VALUES (2, 2, 'Củ cải trắng')";
    private static final String SQL_INSERT_LICH_SU_5 = "INSERT INTO " + TABLE_LICH_SU + " (ID_TRUYEN_FF, VI_TRI_CHUONG_FK, TIEU_DE_TRUYEN) VALUES (3, 1, 'Dê đen và dê trắng')";


    // SQL để thêm cột category
    private static final String SQL_ALTER_TABLE_ADD_CATEGORY = "ALTER TABLE " + TABLE_TRUYEN + " ADD COLUMN category TEXT";

    // SQL để thêm cột TIEU_DE_TRUYEN vào bảng lich_su
    private static final String SQL_ALTER_TABLE_ADD_TIEU_DE_TRUYEN = "ALTER TABLE " + TABLE_LICH_SU  + " ADD COLUMN TIEU_DE_TRUYEN TEXT";



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
            db.execSQL(SQL_CREATE_TABLE_LICH_SU);
            db.execSQL(SQL_CREATE_USER_PROGRESS_TABLE);


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

            db.execSQL(SQL_INSERT_LICH_SU_1);
            db.execSQL(SQL_INSERT_LICH_SU_2);
            db.execSQL(SQL_INSERT_LICH_SU_3);
            db.execSQL(SQL_INSERT_LICH_SU_4);
            db.execSQL(SQL_INSERT_LICH_SU_5);



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
            db.execSQL("ALTER TABLE " + TABLE_LICH_SU + " ADD COLUMN VI_TRI_CHUONG_FK INTEGER");
        }

//
//            if (oldVersion < newVersion) {
////                db.execSQL(SQL_CREATE_TABLE_LICH_SU);
//////                // Xóa bảng taikhoan
//////                db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAIKHOAN);
//////                // Xóa bảng truyen nếu cần
//////                db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRUYEN);
//////                // Xóa bảng chapter nếu cần
//////                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTER);
//////
//////                db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANH_MUC);
//////
//////                db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICH_SU);
////// If you want to drop the history table as well, do it here
//////                db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICH_SU);
////
////
////                db.execSQL("ALTER TABLE lich_su ADD COLUMN ID_TRUYEN_FF INTEGER");
////                // Tạo lại bảng từ đầu
////                onCreate(db);
//            }


////                db.execSQL("ALTER TABLE lich_su ADD COLUMN ID_TRUYEN_FF INTEGER");
////             Tạo lại bảng từ đầu
//            onCreate(db);
////                db.execSQL("ALTER TABLE lich_su ADD COLUMN ID_TRUYEN_FF INTEGER");
//            }



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

//    //Xóa truyện với id = i
//    public int Delete(int i){
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        int res = db.delete("truyen",ID_TRUYEN+" = "+i,null);
//        return res;
//
//    }
    //Lấy truyen mới nhất
    public Cursor getData1(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from "+TABLE_TRUYEN+" ORDER BY "+ID_TRUYEN+" DESC LIMIT 4" , null );
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



    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT iddanhmuc, tendanhmuc FROM danhmuc", null);
    }


    public Cursor getStoriesByCategory(int iddanhmuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM truyen WHERE iddanhmuc = ?";
        return db.rawQuery(query, new String[]{String.valueOf(iddanhmuc)});
    }



    public Cursor getNextOrPreviousChapter(int storyId, int currentChapterId, int direction) {
        // Truy vấn chương trước hoặc sau
        String query = "SELECT * FROM chapter WHERE " + ID_TRUYEN_FK + " = ? " +
                "AND " + ID_CHAPTER + (direction == 1 ? " > ?" : " < ?") +
                " ORDER BY " + ID_CHAPTER + (direction == 1 ? " ASC" : " DESC") +
                " LIMIT 1";

        return getReadableDatabase().rawQuery(query, new String[] {String.valueOf(storyId), String.valueOf(currentChapterId)});
    }

    public void insertLichSu(int idTruyen, int viTriChuong) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID_TRUYEN_FF", idTruyen);
        values.put("VI_TRI_CHUONG_FK", viTriChuong);

        db.insert("lich_su", null, values);
    }

    public void updateLichSu(int idTruyen, int viTriChuong) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("VI_TRI_CHUONG_FK", viTriChuong);

        db.update("lich_su", values, "ID_TRUYEN_FF = ?", new String[]{String.valueOf(idTruyen)});
    }

    public boolean isTruyenInLichSu(int idTruyen) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lich_su WHERE ID_TRUYEN_FF = ?", new String[]{String.valueOf(idTruyen)});

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }



    public void saveReadingPosition(int storyId, int chapterId, String storyTitle) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra xem đã có lịch sử đọc cho truyện này chưa
        Cursor cursor = db.rawQuery("SELECT * FROM lich_su WHERE ID_TRUYEN_FF = ?", new String[]{String.valueOf(storyId)});

        if (cursor.moveToFirst()) {
            // Nếu đã có lịch sử đọc, cập nhật chương hiện tại
            ContentValues values = new ContentValues();
            values.put("VI_TRI_CHUONG_FK", chapterId); // Lưu chapterId (ID chương hiện tại)
            db.update("lich_su", values, "ID_TRUYEN_FF = ?", new String[]{String.valueOf(storyId)});
        } else {
            // Nếu chưa có, thêm lịch sử mới
            ContentValues values = new ContentValues();
            values.put("ID_TRUYEN_FF", storyId); // Lưu storyId (ID truyện)
            values.put("VI_TRI_CHUONG_FK", chapterId); // Lưu chapterId (ID chương hiện tại)
            values.put("TIEU_DE_TRUYEN", storyTitle); // Lưu tiêu đề truyện
            db.insert("lich_su", null, values);
        }
        cursor.close();
    }

    public int getLastReadPosition(int storyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT VI_TRI_CHUONG_FK FROM lich_su WHERE ID_TRUYEN_FF = ?", new String[]{String.valueOf(storyId)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int lastPosition = cursor.getInt(cursor.getColumnIndex("VI_TRI_CHUONG_FK"));
            cursor.close();
            return lastPosition;
        }
        cursor.close();
        return -1; // Nếu không có lịch sử, trả về -1
    }

    public void saveUserProgress(int storyId, int chapterId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("storyId", storyId);   // Lưu ID truyện mà người dùng đang đọc
        values.put("chapterId", chapterId);  // Lưu ID chương mà người dùng đang đọc
//        values.put("thoigian_xem", System.currentTimeMillis());  // Lưu thời gian xem chương

        // Chèn hoặc cập nhật tiến trình đọc vào bảng user_progress
        db.insertWithOnConflict("user_progress", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<HistoryItem> getReadingHistory() {
        List<HistoryItem> historyItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lich_su", null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                // Kiểm tra các cột trong cursor
                String[] columnNames = cursor.getColumnNames();
                for (String columnName : columnNames) {
                    Log.d("DatabaseColumn", columnName);
                }

                // Lấy các chỉ số cột
                int storyIdIndex = cursor.getColumnIndex("ID_TRUYEN_FF");
                int chapterIdIndex = cursor.getColumnIndex("VI_TRI_CHUONG_FK");
                int storyTitleIndex = cursor.getColumnIndex("TIEU_DE_TRUYEN");
                int readTimeIndex = cursor.getColumnIndex("THOI_GIAN_XEM");

                // Kiểm tra chỉ số cột có hợp lệ không
                if (storyIdIndex == -1 || chapterIdIndex == -1 || storyTitleIndex == -1 || readTimeIndex == -1) {
                    Log.e("DatabaseError", "Một hoặc nhiều cột không tìm thấy trong cursor");
                    return historyItems;
                }

                do {
                    // Lấy dữ liệu từ cursor
                    int storyId = cursor.getInt(storyIdIndex);
                    int chapterId = cursor.getInt(chapterIdIndex);
                    String storyTitle = cursor.getString(storyTitleIndex);
                    String readTime = cursor.getString(readTimeIndex);

                    // Tạo một đối tượng HistoryItem và thêm vào danh sách
                    historyItems.add(new HistoryItem(storyId, chapterId, storyTitle, readTime));
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
        } else {
            Log.e("DatabaseError", "Cursor bị null hoặc không có dữ liệu");
        }

        return historyItems;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + ID_TAI_KHOAN + ", " + TEN_TAI_KHOAN + ", " + EMAIL + ", " + PHAN_QUYEN +
                " FROM " + TABLE_TAIKHOAN, null);
    }

    public void updateUserRole(int userId, int newRole) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHAN_QUYEN, newRole);
        db.update(TABLE_TAIKHOAN, values, ID_TAI_KHOAN + " = ?", new String[]{String.valueOf(userId)});
    }

    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAIKHOAN, ID_TAI_KHOAN + " = ?", new String[]{String.valueOf(userId)});
    }



}

