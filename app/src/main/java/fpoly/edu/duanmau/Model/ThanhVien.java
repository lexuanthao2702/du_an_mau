package fpoly.edu.duanmau.Model;

public class ThanhVien {
    private int maTv;
    private String hoTen, namSinh;

    public ThanhVien() {
    }

    public ThanhVien(int maTv, String hoTen, String namSinh) {
        this.maTv = maTv;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }

    public int getMaTv() {
        return maTv;
    }

    public void setMaTv(int maTv) {
        this.maTv = maTv;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    @Override
    public String toString() {
        return "ThanhVien{" +
                "maTv=" + maTv +
                ", hoTen='" + hoTen + '\'' +
                ", namSinh='" + namSinh + '\'' +
                '}';
    }
}
