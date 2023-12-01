package fpoly.edu.duanmau.Database;

public class Data_SQLite {
    public static final String INSERT_THU_THU = "INSERT INTO ThuThu(maTT,hoTen,matKhau) VALUES" +
            "('admin','Nguyen ADMin','admin')," +
            "('lexuanthao.tk7@gmail.com','le xuan thao','thao123')," +
            "('hungvi2210@gmail.com','le xuan thao','viNgu123')," +
            "('thaolxpd08350@fpt.edu.vn','le xuan thao','thao1234')," +
            "('binhnt','Nguyen Thanh Binh','12345')," +
            "('trungnt','Nguyen Thanh Trung','12345')," +
            "('tungnt','Nguyen Thanh Tung','12345')," +
            "('taint','Nguyen Thanh Tai','12345')," +
            "('chinhnt','Nguyen Thanh Chinh','12345')";
    public static final String INSERT_THANH_VIEN ="INSERT INTO ThanhVien(hoTen,namSinh) VALUES" +
            "('Nguyễn Thanh Binh','2004')," +
            "('Trần Văn Tỉnh','2004')," +
            "('Trần Văn Tài','2004')," +
            "('Nguyễn Văn Thái','2004')," +
            "('Nguyễn Thị Hồng','2004')," +
            "('Nguyễn Thị Xuân','2004')," +
            "('Nguyễn Thị Hài Thu','2004')," +
            "('Hoàng Thị Hồng','2004')," +
            "('Trần Thị Hoa','2004')," +
            "('Trần Thị Hoài Thương','2004')," +
            "('Hoàng Long Bảo','2004')," +
            "('Hoàng Long Chung','2004')," +
            "('Hoàng Văn Thái Bảo','2004')," +
            "('Hoàng Văn Thái Hoàng','2004')," +
            "('Hoàng Thị Tuyết Nhung','2004')," +
            "('Trần Hoàng Thái Bảo','2004')," +
            "('Lê Quang Trị','2004')," +
            "('Lê Văng Trị','2004')," +
            "('Lê Thị Ly Na','2004')";
    public static final String INSERT_LOAI_SACH ="INSERT INTO LoaiSach(tenLoai) VALUES" +
            "('Tiếng ANh cơ bản')," +
            "('Tiếng Anh nâng cao')," +
            "('Lập trình cơ bản')," +
            "('Lập trình nâng cao')," +
            "('Lập trình Androi')," +
            "('Lập trình Java')," +
            "('Lập trình web')";

    public static final String INSERT_SACH ="INSERT INTO Sach(tenSach,giaThue,maLoai) VALUES" +
            "('Tiếng ANh cơ bản','2000','1')," +
            "('Tiếng Anh nâng cao','2000','2')," +
            "('Lập trình cơ bản','2000','3')," +
            "('Lập trình nâng cao','2000','4')," +
            "('Lập trình Androi','2000','5')," +
            "('Lập trình Java','2000','6')," +
            "('Lập trình web','2000','7')";

    public static final String INSERT_PHIEU_MUON ="INSERT INTO PhieuMuon(maTT,maTV,masach,tienThue,ngay,traSach) VALUES" +
            "('admin','1','1','2000','2023/5/9',0)," +
            "('admin','2','2','2000','2023/7/10',1)," +
            "('admin','3','3','2000','2023/6/11',1)," +
            "('admin','4','4','2000','2023/12/9',0)," +
            "('admin','5','5','2000','2023/10/19',0)," +
            "('admin','6','6','2000','2023/6/29',1)," +
            "('admin','7','7','2000','2023/2/9',0)";
}
