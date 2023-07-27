/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ADMIN
 */
public class DBConnection {

    private static DBConnection instance = null;

    private static final String dbURL = "jdbc:sqlserver://115.73.212.222:8888;databaseName=sem2_ngan;encrypt=true;trustServerCertificate=true;";
    private static final String username = "ngan";
    private static final String password = "123";

    //<editor-fold defaultstate="collapsed" desc="Query">
    private static final String SELECT_DEPOSIT = "SELECT dc.mahd,dc.sotien,kh.fullname,sp.tensp,dc.status FROM HopDongDatCoc dc "
            + "JOIN KhachHang kh ON kh.id=dc.idkh "
            + "JOIN SanPham sp ON sp.id=dc.idsp";
    private static final String INFO_HDDC = "SELECT kh.fullname,kh.address,dc.sotien FROM "
            + "HopDongDatCoc dc JOIN KhachHang kh on kh.id=dc.idkh WHERE dc.mahd=";
    private static final String STATUS_HDDC = "UPDATE HopDongDatCoc set Status=1 WHERE mahd=";
    private static final String INSERT_RECEIPT = "INSERT INTO Phieu_thu(MAHD,dot,sotien,thoigian,nv_lap_phieu,lydo,loai_hd)"
            + " VALUES(?,?,?,CURRENT_TIMESTAMP,?,?,?)";
    private static final String SELECT_TRANSFER = "SELECT cn.mahd,cn.giatrichuyennhuong,cn.status,kh.fullname,sp.tensp FROM HopDongChuyenNhuong cn"
            + " JOIN KhachHang  kh on cn.idkhmua=kh.id"
            + " JOIN HopDongMuaBan mb on cn.hd_muaban= mb.mahd"
            + " JOIN HopDongDatCoc dc on mb.hddc=dc.mahd "
            + " JOIN SanPham sp on dc.idsp=sp.id";
    private static final String INFO_HDCN = "SELECT kh.fullname,kh.address FROM HopDongChuyenNhuong cn "
            + "JOIN KhachHang kh ON kh.id=cn.idkhmua WHERE cn.mahd=";
    private static final String STATUS_HDCN = "UPDATE HopDongChuyenNhuong SET Status=1 WHERE mahd=";
    private static final String SELECT_SALE = "SELECT mb.dot_tt,kh.fullname,sp.tensp,mb.mahd FROM HopDongMuaBan mb "
            + " JOIN HopDongDatCoc dc ON mb.hddc=dc.mahd"
            + " JOIN KhachHang kh ON kh.id= dc.idkh"
            + " JOIN SanPham sp ON dc.idsp=sp.id";
    private static final String CHECK_RECEIPT = "SELECT COUNT(MAHD) as 'Count' FROM Phieu_thu WHERE MAHD=";
    private static final String INFO_HDMB = "SELECT mb.dot_tt,kh.fullname,kh.address FROM HopDongMuaBan mb"
            + " JOIN HopDongDatCoc dc ON mb.hddc=dc.mahd"
            + " JOIN KhachHang kh ON dc.idkh=kh.id WHERE mb.mahd=";
    private static final String CHANGE_JSON = "UPDATE HopDongMuaBan SET dot_tt=? WHERE mahd=";
    private static final String DEBT = "SELECT mb.mahd,mb.dot_tt,kh.fullname,kh.address,kh.phone,sp.tensp  FROM HopDongMuaBan mb"
            + " JOIN HopDongDatCoc dc ON mb.hddc=dc.mahd"
            + " JOIN KhachHang kh ON dc.idkh=kh.id"
            + " JOIN SanPham  sp ON dc.idsp=sp.id";
    private static final String CHECK_REMINER = "SELECT COUNT(hd_muaban) as 'Count' FROM PhieuNhacNo WHERE hd_muaban=";
    private static final String FRIST_DEBT = "SELECT mb.mahd,mb.ngay,kh.fullname FROM HopDongMuaBan mb "
            + "JOIN HopDongDatCoc  dc ON dc.mahd=mb.hddc "
            + "JOIN KhachHang kh ON dc.idkh=kh.id WHERE mb.mahd=";
    private static final String COUNT_REMINDER = "SELECT COUNT(*) as 'Count' FROM PhieuNhacNo";
    private static final String INSERT_REMINDER = "INSERT INTO PhieuNhacNo(maphieu,ngaylap,nvlapphieu,dot,hd_muaban,loai)"
            + " VALUES(?,CURRENT_TIMESTAMP,?,?,?,?)";
    private static final String FIND_RECOVER = "SELECT kh.fullname,kh.address,kh.ngaycap,kh.noicap,kh.socmnd,mb.ngay,mb.dot_tt "
            + "FROM HopDongMuaBan mb JOIN HopDongDatCoc dc ON mb.hddc=dc.mahd "
            + "JOIN KhachHang kh ON kh.id=dc.idkh WHERE mb.mahd=";
    private static final String CHECK_LOGIN = "SELECT * FROM NhanVien WHERE username=";
    private final String SELECT_SANPHAM = "SELECT sp.*,k.tenkhu FROM SanPham sp JOIN Khu k ON sp.idkhu = k.id";
    private final String SELECT_SANPHAM_TrangThai = "SELECT * FROM SanPham WHERE trangthai = 1";
    private final String SELECT_SANPHAM_HDDC = "SELECT sp.tensp,sp.dientich,sp.dongia,sp.vitri FROM HopDongDatCoc dc join SanPham sp ON dc.mahd = ? and dc.idsp = sp.id;";
    private final String updateSanPham = "UPDATE SanPham SET trangthai = ? WHERE id = ?";
    private final String getHDDatCoc_ID = "SELECT * FROM HopDongDatCoc WHERE mahd = ?";
    private final String getDatCoc_KhachHang = "SELECT d.mahd,d.ngaylap,k.* FROM HopDongDatCoc d JOIN Khachhang k ON d.idkh = k.id;";
    private final String insertHDDC = "INSERT INTO HopDongDatCoc (mahd,idkh,sotien,thoihan,batdau,ngaylap,idsp,idnv,status,filehd)"
            + "VALUES (?,?,?,?,?,?,?,?,?,?);";
    private final String getHDMuaBan = "SELECT * FROM HopDongMuaBan";
    private final String getMuaBan_KhachHang = "SELECT mb.mahd,mb.ngay,mb.dot_tt,k.fullname,k.ngaysinh,k.ngaycap,k.noicap,k.socmnd,k.hokhau FROM dbo.HopDongMuaBan mb join (dbo.HopDongDatCoc dc join dbo.KhachHang k on dc.idkh = k.id) on mb.hddc = dc.mahd;";
    private final String getHDMuaBan_ID = "SELECT * FROM HopDongMuaBan WHERE mahd = ?";
    private final String insertHDMuaBan = "INSERT INTO HopDongMuaBan (mahd,ngay,dot_tt,tinhtrangphaply,hddc,filehd,idnv)"
            + "VALUES (?,?,?,?,?,?,?);";
    private final String getHDChuyenNhuong_ID = "SELECT * FROM HopDongChuyenNhuong WHERE mahd = ?";
    private final String getChuyenNhuong_KhachHang = "SELECT k.*,mb.mahd,mb.dot_tt FROM dbo.KhachHang k JOIN (dbo.HopDongMuaBan mb JOIN dbo.HopDongDatCoc dc ON mb.hddc = dc.mahd) ON k.id = dc.idkh;";
    private final String insertHDChuyenNhuong = "INSERT INTO HopDongChuyenNhuong (mahd,idkhmua,hanche,giatrichuyennhuong,thoidiem,status,hd_muaban,filehd,idnv)"
            + "VALUES (?,?,?,?,?,?,?,?,?);";
    private final String getPhuLucHD_ID = "SELECT * FROM PhuLucHopDong WHERE mahd = ?";
    private final String insertPhuLucHD = "INSERT INTO PhuLucHopDong (mahd,kingay,khoan,dieu,dieukhoancu,dieukhoanmoi,hd_muaban,filehd,idnv)"
            + "VALUES (?,?,?,?,?,?,?,?,?);";
    private final String getHDThanhLy_ID = "SELECT * FROM HopDongThanhLy WHERE mahd = ?";
    private final String insertHDThanhLy = "INSERT INTO HopDongThanhLy (mahd,ngay,idnguoilap,idnguoiduyet,hd_muaban,filehd)"
            + "VALUES (?,?,?,?,?,?);";
    private final String getNhanVien_ID = "SELECT * FROM NhanVien WHERE id = ?";
    private final String getKhachHang = "SELECT * FROM KhachHang";
    private final String getKhachHang_ID = "SELECT * FROM KhachHang WHERE id = ?";
    private final String updateKhachHang = "UPDATE KhachHang SET phanloai = ? WHERE id = ?";
    private final String getIDKH_CMND = "SELECT * FROM KhachHang WHERE socmnd = ?";
    private final String insertKhachHang = "INSERT INTO KhachHang (fullname,phone,email,hokhau,address,ngaysinh,phanloai,ngaycap,noicap,socmnd)"
            + "VALUES (?,?,?,?,?,?,?,?,?,?);";
    private final String insertKHMuaBan = "INSERT INTO KhMuaBan (idkh,idsp,thoigian)"
            + "VALUES (?,?,?);";
    private static final String CHECK_EMAIL = "SELECT * FROM KhachHang WHERE email=";
    private static final String CHECK_CMND = "SELECT * FROM KhachHang WHERE socmnd=";
    private static final String CHECK_PHONE = "SELECT * FROM KhachHang WHERE phone=";
    //Khach Hang
    private static final String INSERT_KHACHHANG = "INSERT INTO KhachHang(fullname,ngaysinh,phone,email,hokhau,address,socmnd,ngaycap,noicap,phanloai)"
            + " VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_KHACHHANG = "SELECT id,fullname,phone,email,phanloai FROM KhachHang";

    //Don Vi Thi Cong
    private static final String INSERT_DVTHICONG = "INSERT INTO DonViThiCong(id,ten,logo,soluong,diachi,sdt)" + " VALUES(?,?,?,?,?,?)";
    private static final String SELECT_DVTHICONG = "SELECT id,ten,soluong,diachi,sdt FROM DonViThiCong";

    //Chu Dau Tu 
    private static final String INSERT_CHUDAUTU = "INSERT INTO ChuDauTu(fullname,logo,sdt,email,diachi,descrip,duansapmo,duandangmo,duanbangiao)"
            + "VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String CHECK_EMAIL_CDT = "SELECT * FROM ChuDauTu WHERE email=";
    private static final String SELECT_CHUDAUTU = "SELECT * FROM ChuDauTu";

    //Du An
    private static final String INSERT_DUAN = "INSERT INTO DuAn(id,tenduan,gtduan,matban,vitri,dientich,phaply,matdo,tienich,idchudautu,iddvthicong,dvquanly,trangthai)"
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_DUAN = "SELECT da.*,dv.ten,cdt.fullname FROM DuAn da JOIN DonViThiCong dv ON dv.id=da.iddvthicong JOIN ChuDauTu cdt ON cdt.id=da.idchudautu";
    private static final String UPDATE_SAP = "UPDATE DuAn SET trangthai = 0 WHERE id = ?";
    private static final String UPDATE_DANG = "UPDATE DuAn SET trangthai = 1 WHERE id = ?";

    //Khu Du An
    private static final String INSERT_KHU = "INSERT INTO Khu(tenkhu,huong,dientich,idduan,trangthai)" + "VALUES(?,?,?,?,?)";
    private static final String SELECT_KHU = "SELECT k.*,da.tenduan FROM Khu k JOIN DuAn da ON k.idduan=da.id";

    //San Pham 
    private static final String INSERT_SANPHAM = "INSERT INTO SanPham(tensp,dientich,mota,dongia,vitri,huong,thuadatso,bandoso,diachi,idkhu,trangthai)"
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

    //CEO
    private static final String SELECT_INF_EMP = "SELECT * FROM NhanVien WHERE id = ?";
    
//</editor-fold>
    Connection conn;

    public DBConnection() {
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("SUCCESS Connection");
        } catch (SQLException e) {
            System.out.println("ERROR Connection");
            System.out.println(e.getMessage());
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("SUCCESS Connection");
        } catch (Exception e) {
            System.out.println("ERROR Connection");
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public ObservableList<Deposit> findDeposit() {
        ObservableList<Deposit> ls = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_DEPOSIT);
            while (rs.next()) {
                Deposit dp = new Deposit();
                dp.setMaHD(rs.getString("mahd"));
                dp.setNameClient(rs.getString("fullname"));
                dp.setNameProduct(rs.getString("tensp"));
                dp.setDeposits(rs.getString("sotien"));
                dp.setStatus(rs.getByte("status"));
                ls.add(dp);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public ObservableList<Deposit> HDDC(String MaHD) {
        ObservableList<Deposit> ls = FXCollections.observableArrayList();
        try {
            String rMaHD = "'" + MaHD + "'";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(INFO_HDDC + rMaHD);
            while (rs.next()) {
                Deposit dp = new Deposit();
                dp.setNameClient(rs.getString("fullname"));
                dp.setAddress(rs.getString("address"));
                dp.setDeposits(rs.getString("sotien"));
                ls.add(dp);
            }
            sm.close();
        } catch (Exception e) {
            System.out.println("ERROR HDDC");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public void changeStatusHDDC() {
        try {
            String rHDDC = "'" + ValueCheck.HDDC + "'";
            PreparedStatement pm = conn.prepareCall(STATUS_HDDC + rHDDC);
            pm.execute();
            pm.close();
        } catch (SQLException e) {
            System.out.println("ERROR changeStatusHDDC");
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Transfer> findTransfer() {
        ObservableList<Transfer> ls = FXCollections.observableArrayList();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(SELECT_TRANSFER);
            while (rs.next()) {
                Transfer tf = new Transfer();
                tf.setMaHD(rs.getString("mahd"));
                tf.setNameClient(rs.getString("fullname"));
                tf.setNameProduct(rs.getString("tensp"));
                tf.setMoney(rs.getString("giatrichuyennhuong"));
                tf.setStatus(rs.getByte("status"));
                ls.add(tf);
            }
            sm.close();
        } catch (SQLException e) {
            System.out.println("ERROR HDCN");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public ObservableList<Transfer> HDCN(String MaHD) {
        ObservableList<Transfer> ls = FXCollections.observableArrayList();
        try {
            String rMaHd = "'" + MaHD + "'";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(INFO_HDCN + rMaHd);
            while (rs.next()) {
                Transfer tf = new Transfer();
                tf.setNameClient(rs.getString("fullname"));
                tf.setAddress(rs.getString("address"));
                ls.add(tf);
            }
            sm.close();
        } catch (SQLException e) {
            System.out.println("ERROR HDCN");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public void changeStatusHDCN() {
        try {
            String rHDDC = "'" + ValueCheck.HDCN + "'";
            PreparedStatement pm = conn.prepareCall(STATUS_HDCN + rHDDC);
            pm.execute();
            pm.close();
        } catch (SQLException e) {
            System.out.println("ERROR changeStatusHDCN");
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Sale> findSale() {
        ObservableList<Sale> ls = FXCollections.observableArrayList();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(SELECT_SALE);
            while (rs.next()) {
                Sale s = new Sale();
                s.setMaHD(rs.getString("mahd"));
                s.setNameClient(rs.getString("fullname"));
                s.setNameProduct(rs.getString("tensp"));
                s.setDotThanhToan(rs.getString("dot_tt"));
                ls.add(s);
            }
            sm.close();
        } catch (SQLException e) {
            System.out.println("ERROR findSale");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public ObservableList<Sale> HDMB(String MaHD) {
        ObservableList<Sale> ls = FXCollections.observableArrayList();
        try {
            String rMaHD = "'" + MaHD + "'";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(INFO_HDMB + rMaHD);
            while (rs.next()) {
                Sale s = new Sale();
                s.setNameClient(rs.getString("fullname"));
                s.setDotThanhToan(rs.getString("dot_tt"));
                s.setAddress(rs.getString("address"));
                ls.add(s);
            }
            sm.close();
        } catch (Exception e) {
            System.out.println("ERROR HDMB");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public Integer checkReceipt(String MaHD) {
        Integer Count = 0;
        try {
            String rMaHD = "'" + MaHD + "'";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(CHECK_RECEIPT + rMaHD);
            while (rs.next()) {
                Count = rs.getInt("Count");
            }
        } catch (SQLException e) {
            System.out.println("ERROR checkReceipt");
            System.out.println(e.getMessage());
        }
        return Count;
    }

    public void changeJsonStatus(String jsonString) {
        try {
            String rMaHD = "'" + ValueCheck.HDMB + "'";
            PreparedStatement pm = conn.prepareStatement(CHANGE_JSON + rMaHD);
            pm.setString(1, jsonString);
            pm.execute();
            pm.close();
        } catch (SQLException e) {
            System.out.println("ERROR changeJsonStatus");
            System.out.println(e.getMessage());
        }
    }

    public void InsertReceipt(String MaHD, Integer Dot, Long SoTien, Integer IDNV, String LyDo, String LoaiHD) {
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_RECEIPT);
            ps.setString(1, MaHD);
            ps.setInt(2, Dot);
            ps.setLong(3, SoTien);
            ps.setInt(4, IDNV);
            ps.setString(5, LyDo);
            ps.setString(6, LoaiHD);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("ERROR InsertReceipt");
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Announce> GET_DEBT() {
        ObservableList<Announce> ls = FXCollections.observableArrayList();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(DEBT);
            while (rs.next()) {
                Announce an = new Announce();
                an.setAddress(rs.getString("address"));
                an.setDotThanhToan(rs.getString("dot_tt"));
                an.setMaHD(rs.getString("mahd"));
                an.setNameClient(rs.getString("fullname"));
                an.setPhone(rs.getString("phone"));
                an.setProduct(rs.getString("tensp"));

                ls.add(an);
            }
        } catch (SQLException e) {
            System.out.println("ERROR GET_ANNOUNCE");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public Integer checkReminer(String MaHD, String Dot) {
        Integer Count = 0;
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(CHECK_REMINER + "'" + MaHD + "'" + " AND dot=" + "'" + Dot + "'");
            while (rs.next()) {
                Count = rs.getInt("Count");
            }
        } catch (SQLException e) {
            System.out.println("ERROR checkReminer");
            System.out.println(e.getMessage());
        }
        return Count;
    }

    public ObservableList<Reminder> getInfoReminder(String MaHD) {
        ObservableList<Reminder> ls = FXCollections.observableArrayList();
        try {
            String rMaHD = "'" + MaHD + "'";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(FRIST_DEBT + rMaHD);
            while (rs.next()) {
                Reminder rd = new Reminder();
                rd.setMaHD(rs.getString("MaHD"));
                rd.setNameClient(rs.getString("fullname"));
                rd.setDate(rs.getString("ngay"));

                ls.add(rd);
            }
        } catch (Exception e) {
            System.out.println("ERROR getInfoReminder");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public Integer CountReminder() {
        Integer Count = 0;
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(COUNT_REMINDER);
            while (rs.next()) {
                Count = rs.getInt("Count");
            }
            sm.close();
        } catch (SQLException e) {
            System.out.println("ERROR CountReminder");
            System.out.println(e.getMessage());
        }
        return Count;
    }

    public void InsertReminder(String MaPhieu, Integer IDNV, Integer Batch, String MaHD, String LoaiHD) {
        try {
            PreparedStatement pm = conn.prepareStatement(INSERT_REMINDER);
            pm.setString(1, MaPhieu);
            pm.setInt(2, IDNV);
            pm.setInt(3, Batch);
            pm.setString(4, MaHD);
            pm.setString(5, LoaiHD);
            pm.execute();
            pm.close();
        } catch (SQLException e) {
            System.out.println("ERROR InsertReminder");
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Recover> GET_RECOVER(String MaHD) {
        ObservableList<Recover> ls = FXCollections.observableArrayList();
        try {
            String rMaHD = "'" + MaHD + "'";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(FIND_RECOVER + rMaHD);
            while (rs.next()) {
                Recover rc = new Recover();
                rc.setAddress(rs.getString("address"));
                rc.setCMND(rs.getString("socmnd"));
                rc.setDotThanhToan(rs.getString("dot_tt"));
                rc.setNameClient(rs.getString("fullname"));
                rc.setNgayCap(rs.getString("ngaycap"));
                rc.setNgayKi(rs.getString("ngay"));
                rc.setNoiCap(rs.getString("noicap"));
                ls.add(rc);
            }
            sm.close();
        } catch (SQLException e) {
            System.out.println("ERROR GET_RECOVER");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public ObservableList<Login> checkLogin(String UserName, String Password) {
        ObservableList<Login> ls = FXCollections.observableArrayList();
        try {
            String rUserName = "'" + UserName + "'";
            String rPassword = "'" + Password + "'";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(CHECK_LOGIN + rUserName + " AND password=" + rPassword);
            while (rs.next()) {
                Login item = new Login();
                item.setID(rs.getInt("ID"));
                item.setFullName(rs.getString("fullname"));
                item.setPosition(rs.getString("position"));
                item.setDepartment(rs.getInt("idpb"));
                item.setStatus(rs.getByte("status"));
                ls.add(item);
            }
            sm.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR checkLogin");
        }
        return ls;
    }

    public ObservableList<SanPham> showSanPham() {
        ObservableList<SanPham> ls = FXCollections.observableArrayList();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(SELECT_SANPHAM);
            while (rs.next()) {
                SanPham item = new SanPham();
                //item.setId(rs.getInt("id"));
                item.setIdkhu(rs.getString("tenkhu"));
                item.setTensp(rs.getString("tensp"));
                item.setDientich(rs.getString("dientich"));
                item.setMota(rs.getString("mota"));
                item.setDongia(rs.getString("dongia"));
                item.setVitri(rs.getString("vitri"));
                item.setHuong(rs.getString("huong"));
                item.setThuadatso(rs.getString("thuadatso"));
                item.setBandoso(rs.getString("bandoso"));
                item.setDiachi(rs.getString("diachi"));
                item.setTrangthai(rs.getString("trangthai"));
                ls.add(item);
            }
            //--------------------------------------
            sm.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public ObservableList<SanPham> showSanPham_TrangThai() {
        ObservableList<SanPham> ls = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_SANPHAM_TrangThai);
            while (rs.next()) {
                SanPham item = new SanPham();
                item.setId(rs.getInt("id"));
                item.setTensp(rs.getString("tensp"));
                item.setDientich(rs.getString("dientich"));
                item.setMota(rs.getString("mota"));
                item.setDongia(rs.getString("dongia"));
                item.setVitri(rs.getString("vitri"));
                item.setHuong(rs.getString("huong"));
                item.setThuadatso(rs.getString("thuadatso"));
                item.setBandoso(rs.getString("bandoso"));
                item.setDiachi(rs.getString("diachi"));
                item.setTrangthai("Dang mo ban");
                ls.add(item);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public SanPham showSanPham_HDDC(String mahd) {
        SanPham item = new SanPham();
        try {
            PreparedStatement preS = conn.prepareStatement(SELECT_SANPHAM_HDDC);
            preS.setString(1, mahd);
            ResultSet rs = preS.executeQuery();
            while (rs.next()) {
                item.setDientich(rs.getString("dientich"));
                item.setVitri(rs.getString("vitri"));
                item.setTensp(rs.getString("tensp"));
                item.setDongia(rs.getString("dongia"));
            }
            preS.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return item;
    }

    public void updateSanPham(String trangthai, int id) {
        try {
            PreparedStatement preS = conn.prepareStatement(updateSanPham);
            preS.setString(1, trangthai);
            preS.setInt(2, id);
            preS.execute();
            preS.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<KhachHang> getKhachHang() {
        ObservableList<KhachHang> ob = FXCollections.observableArrayList();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(getKhachHang);
            while (rs.next()) {
                KhachHang item = new KhachHang();
                item.setId(rs.getInt("id"));
                item.setAddress(rs.getString("address"));
                item.setEmail(rs.getString("email"));
                item.setFullname(rs.getString("fullname"));
                item.setHokhau(rs.getString("hokhau"));
                item.setNgaycap(LocalDate.parse(rs.getString("ngaycap")));
                item.setNgaysinh(LocalDate.parse(rs.getString("ngaysinh")));
                item.setNoicap(rs.getString("noicap"));
                item.setPhanloai(rs.getString("phanloai"));
                item.setPhone(rs.getString("phone"));
                item.setSocmnd(rs.getString("socmnd"));
                ob.add(item);
            }
            stm.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    public ObservableList<KhachHang> getKhachHang_ID(int id) {
        ObservableList<KhachHang> ob = FXCollections.observableArrayList();
        try {
            PreparedStatement preS = conn.prepareStatement(getKhachHang_ID);
            preS.setInt(1, id);
            ResultSet rs = preS.executeQuery();
            while (rs.next()) {
                KhachHang item = new KhachHang();
                item.setId(rs.getInt("id"));
                item.setAddress(rs.getString("address"));
                item.setEmail(rs.getString("email"));
                item.setFullname(rs.getString("fullname"));
                item.setHokhau(rs.getString("hokhau"));
                item.setNgaycap(LocalDate.parse(rs.getString("ngaycap")));
                item.setNgaysinh(LocalDate.parse(rs.getString("ngaysinh")));
                item.setNoicap(rs.getString("noicap"));
                item.setPhanloai(rs.getString("phanloai"));
                item.setPhone(rs.getString("phone"));
                item.setSocmnd(rs.getString("socmnd"));
                ob.add(item);
            }
            preS.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    public int getIDKH_CMND(String cmnd) {
        int id = 0;
        try {
            PreparedStatement preS = conn.prepareStatement(getIDKH_CMND);
            preS.setString(1, cmnd);
            ResultSet rs = preS.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
            preS.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public void insertKhachHang(String fullname, String phone, String email,
            String hokhau, String address, String ngaysinh, String phanloai, String ngaycap, String noicap, String socmnd) {
        try {
            PreparedStatement preS = conn.prepareStatement(insertKhachHang);
            preS.setString(1, fullname);
            preS.setString(2, phone);
            preS.setString(3, email);
            preS.setString(4, hokhau);
            preS.setString(5, address);
            preS.setString(6, ngaysinh);
            preS.setString(7, phanloai);
            preS.setString(8, ngaycap);
            preS.setString(9, noicap);
            preS.setString(10, socmnd);
            preS.executeUpdate();
            preS.close();
            System.out.println("Insert Success");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateKhachHang(String trangthai, int id) {
        try {
            PreparedStatement preS = conn.prepareStatement(updateKhachHang);
            preS.setString(1, trangthai);
            preS.setInt(2, id);
            preS.execute();
            preS.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertKHMuaBan(int idkh, int idsp, String thoigian) {
        try {
            PreparedStatement preS = conn.prepareStatement(insertKHMuaBan);
            preS.setInt(1, idkh);
            preS.setInt(2, idsp);
            preS.setString(3, thoigian);
            preS.executeUpdate();
            preS.close();
            System.out.println("Insert Success");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean getEmail(String Email) {
        Boolean checkIsset = true;
        try {
            PreparedStatement ps = conn.prepareStatement(CHECK_EMAIL + "'" + Email + "'");
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                checkIsset = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return checkIsset;

    }

    public boolean getCMND(String CMND) {
        Boolean checkIsset = true;
        try {
            PreparedStatement ps = conn.prepareStatement(CHECK_CMND + "'" + CMND + "'");
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                checkIsset = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return checkIsset;

    }

    public boolean getPhone(String phone) {
        Boolean checkIsset = true;
        try {
            PreparedStatement ps = conn.prepareStatement(CHECK_CMND + "'" + phone + "'");
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                checkIsset = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return checkIsset;
    }

    public ObservableList<HopDongDatCoc> getHDDatCoc_ID(String id) {
        ObservableList<HopDongDatCoc> ob = FXCollections.observableArrayList();
        try {
            PreparedStatement preS = conn.prepareStatement(getHDDatCoc_ID);
            preS.setString(1, id);
            ResultSet rs = preS.executeQuery();
            while (rs.next()) {
                HopDongDatCoc item = new HopDongDatCoc();
                item.setMahd(rs.getString("mahd"));
                item.setIdkh(rs.getInt("idkh"));
                item.setIdsp(rs.getInt("idsp"));
                item.setSotien(rs.getInt("sotien"));
                item.setThoihan(rs.getString("thoihan"));
                item.setBatdau(LocalDate.parse(rs.getString("batdau")));
                item.setCamkettai(rs.getString("camkettai"));
                item.setThanhtoan(rs.getString("thanhtoan"));
                item.setPhongcongchung(rs.getString("phongcongchung"));
                item.setStatus(rs.getBoolean("status"));
                item.setNgaylap(LocalDate.parse(rs.getString("ngaylap")));
                item.setIdnv(rs.getInt("idnv"));
                ob.add(item);
            }
            preS.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    public ObservableList<DatCoc_Khachhang> getDatCoc_KhachHang() {
        ObservableList<DatCoc_Khachhang> ob = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(getDatCoc_KhachHang);
            while (rs.next()) {
                DatCoc_Khachhang item = new DatCoc_Khachhang();
                item.setId(rs.getInt("id"));
                item.setMahd(rs.getString("mahd"));
                item.setNgaylap(LocalDate.parse(rs.getString("ngaylap")));
                item.setFullname(rs.getString("fullname"));
                item.setNgaysinh(LocalDate.parse(rs.getString("ngaysinh")));
                item.setSocmnd(rs.getString("socmnd"));
                item.setNgaycap(LocalDate.parse(rs.getString("ngaycap")));
                item.setTai(rs.getString("noicap"));
                item.setHokhau(rs.getString("hokhau"));
                ob.add(item);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    public void insertHDDC(String mahd, int idkh, String sotien, String thoihan, String batdau, String ngaylap, int idsp, int idnv, boolean status, String filehd) {
        try {
            PreparedStatement preS = conn.prepareStatement(insertHDDC);
            preS.setString(1, mahd);
            preS.setInt(2, idkh);
            preS.setString(3, sotien);
            preS.setString(4, thoihan);
            preS.setString(5, batdau);
            preS.setString(6, ngaylap);
            preS.setInt(7, idsp);
            preS.setInt(8, idnv);
            preS.setBoolean(9, status);
            preS.setString(10, filehd);
            preS.execute();
            preS.close();
            System.out.println("Insert Success");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<HopDongMuaBan> getHDMuaBan() {
        ObservableList<HopDongMuaBan> ob = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(getHDMuaBan);
            while (rs.next()) {
                HopDongMuaBan item = new HopDongMuaBan();
                item.setMahd(rs.getString("mahd"));
                item.setNgay(LocalDate.parse(rs.getString("ngay")));
                item.setDot_tt(rs.getString("dot_tt"));
                item.setTinhtrangphaply(rs.getString("tinhtrangphaply"));
                item.setHddc(rs.getString("hddc"));
                ob.add(item);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    public ObservableList<MuaBan_KhachHang> getMuaBan_KhachHang() {
        ObservableList<MuaBan_KhachHang> ob = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(getMuaBan_KhachHang);
            while (rs.next()) {
                MuaBan_KhachHang item = new MuaBan_KhachHang();
                item.setMahd(rs.getString("mahd"));
                item.setNgay(LocalDate.parse(rs.getString("ngay")));
                item.setDot_tt(rs.getString("dot_tt"));
                item.setFullname(rs.getString("fullname"));
                item.setHokhau(rs.getString("hokhau"));
                item.setNgaysinh(LocalDate.parse(rs.getString("ngaysinh")));
                item.setNgaycap(LocalDate.parse(rs.getString("ngaycap")));
                item.setSocmnd(rs.getString("socmnd"));
                item.setNoicap(rs.getString("noicap"));
                ob.add(item);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    public ObservableList<HopDongMuaBan> getHDMuaBan_ID(String id) {
        ObservableList<HopDongMuaBan> ob = FXCollections.observableArrayList();
        try {
            PreparedStatement preS = conn.prepareStatement(getHDMuaBan_ID);
            preS.setString(1, id);
            ResultSet rs = preS.executeQuery();
            while (rs.next()) {
                HopDongMuaBan item = new HopDongMuaBan();
                item.setMahd(rs.getString("mahd"));
                item.setNgay(LocalDate.parse(rs.getString("ngay")));
                item.setDot_tt(rs.getString("dot_tt"));
                item.setTinhtrangphaply(rs.getString("tinhtrangphaply"));
                item.setHddc(rs.getString("hddc"));
                ob.add(item);
            }
            preS.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    public void insertHDMuaBan(String mahd, String ngay, String dot_tt, String tinhtrangphaply, String hddc, String filehd, int idnv) {
        try {
            PreparedStatement preS = conn.prepareStatement(insertHDMuaBan);
            preS.setString(1, mahd);
            preS.setString(2, ngay);
            preS.setString(3, dot_tt);
            preS.setString(4, tinhtrangphaply);
            preS.setString(5, hddc);
            preS.setString(6, filehd);
            preS.setInt(7, idnv);
            preS.execute();
            preS.close();
            System.out.println("Insert Success");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean getHDChuyenNhuong_ID(String id) {
        try {
            PreparedStatement preS = conn.prepareStatement(getHDChuyenNhuong_ID);
            preS.setString(1, id);
            ResultSet rs = preS.executeQuery();
            while (rs.isBeforeFirst()) {
                return true;
            }
            preS.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return false;
    }

    public ObservableList<ChuyenNhuong_KhachHang> getChuyenNhuong_KhachHang() {
        ObservableList<ChuyenNhuong_KhachHang> ob = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(getChuyenNhuong_KhachHang);
            while (rs.next()) {
                ChuyenNhuong_KhachHang item = new ChuyenNhuong_KhachHang();
                item.setMahd(rs.getString("mahd"));
                item.setDot_tt(rs.getString("dot_tt"));
                item.setId(rs.getInt("id"));
                item.setAddress(rs.getString("address"));
                item.setEmail(rs.getString("email"));
                item.setFullname(rs.getString("fullname"));
                item.setHokhau(rs.getString("hokhau"));
                item.setNgaycap(LocalDate.parse(rs.getString("ngaycap")));
                item.setNgaysinh(LocalDate.parse(rs.getString("ngaysinh")));
                item.setNoicap(rs.getString("noicap"));
                item.setPhanloai(rs.getString("phanloai"));
                item.setPhone(rs.getString("phone"));
                item.setSocmnd(rs.getString("socmnd"));
                ob.add(item);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    public void insertHDChuyenNhuong(String mahd, int idkhmua, String hanche, String giatrichuyennhuong, String thoidiem, boolean status, String hd_muaban, String filehd,int idnv) {
        try {
            PreparedStatement preS = conn.prepareStatement(insertHDChuyenNhuong);
            preS.setString(1, mahd);
            preS.setInt(2, idkhmua);
            preS.setString(3, hanche);
            preS.setString(4, giatrichuyennhuong);
            preS.setString(5, thoidiem);
            preS.setBoolean(6, status);
            preS.setString(7, hd_muaban);
            preS.setString(8, filehd);
            preS.setInt(9, idnv);
            preS.execute();
            preS.close();
            System.out.println("Insert Success");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<PhuLucHopDong> getPhuLucHD_ID(String id) {
        ObservableList<PhuLucHopDong> ob = FXCollections.observableArrayList();
        try {
            PreparedStatement preS = conn.prepareStatement(getPhuLucHD_ID);
            preS.setString(1, id);
            ResultSet rs = preS.executeQuery();
            while (rs.next()) {
                PhuLucHopDong item = new PhuLucHopDong();
                item.setMahd(rs.getString("mahd"));
                item.setKingay(LocalDate.parse(rs.getString("kingay")));
                item.setKhoan(rs.getInt("khoan"));
                item.setDieu(rs.getInt("dieu"));
                item.setDieukhoancu(rs.getString("dieukhoancu"));
                item.setDieukhoanmoi(rs.getString("dieukhoanmoi"));
                item.setHdMuaBan(rs.getString("hd_muaBan"));
                ob.add(item);
            }
            preS.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    public void insertPhuLucHD(String mahd, String kingay, String khoan, String dieu, String dieukhoancu, String dieukhoanmoi, String hd_muaban, String filehd,int idnv) {
        try {
            PreparedStatement preS = conn.prepareStatement(insertPhuLucHD);
            preS.setString(1, mahd);
            preS.setString(2, kingay);
            preS.setString(3, khoan);
            preS.setString(4, dieu);
            preS.setString(5, dieukhoancu);
            preS.setString(6, dieukhoanmoi);
            preS.setString(7, hd_muaban);
            preS.setString(8, filehd);
            preS.setInt(9, idnv);
            preS.execute();
            preS.close();
            System.out.println("Insert Success");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean getHDThanhLy_ID(String id) {
        try {
            PreparedStatement preS = conn.prepareStatement(getHDThanhLy_ID);
            preS.setString(1, id);
            ResultSet rs = preS.executeQuery();
            while (rs.isBeforeFirst()) {
                return true;
            }
            preS.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return false;
    }

    public void insertHDThanhLy(String mahd, LocalDate ngay, int idnguoilap, int idnguoiduyet, String hd_muaban, String filehd) {
        try {
            PreparedStatement preS = conn.prepareStatement(insertHDThanhLy);
            preS.setString(1, mahd);
            preS.setString(2, ngay.toString());
            preS.setInt(3, idnguoilap);
            preS.setInt(4, idnguoiduyet);
            preS.setString(5, hd_muaban);
            preS.setString(6, filehd);
            preS.execute();
            preS.close();
            System.out.println("Insert Success");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<NhanVien> getNhanVien_ID(int id) {
        ObservableList<NhanVien> ob = FXCollections.observableArrayList();
        try {
            PreparedStatement preS = conn.prepareStatement(getNhanVien_ID);
            preS.setInt(1, id);
            ResultSet rs = preS.executeQuery();
            while (rs.next()) {
                NhanVien item = new NhanVien();
                item.setId(rs.getInt("id"));
                item.setUsername(rs.getString("username"));
                item.setPassword(rs.getString("password"));
                item.setFullname(rs.getString("fullname"));
                item.setBirth(LocalDate.parse(rs.getString("birth")));
                item.setGender(rs.getBoolean("gender"));
                item.setAddress(rs.getString("address"));
                item.setPhone(rs.getString("phone"));
                item.setEmail(rs.getString("email"));
                item.setPosition(rs.getString("position"));
                item.setIdpb(rs.getInt("idpb"));
                item.setStatus(rs.getBoolean("Status"));
                ob.add(item);
            }
            preS.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ob;
    }

    //KHACH HANG
    public Boolean InsertKhachHang(String fullname, LocalDate ngaysinh, String phone, String email, String hokhau, String address, String socmnd, LocalDate ngaycap, String noicap, Short phanloai) {
        try {
            //fullname,ngaysinh,phone,email,hokhau,address,socmnd,ngaycap,noicap,phanloai
            PreparedStatement ps = conn.prepareStatement(INSERT_KHACHHANG);
            ps.setString(1, fullname);
            ps.setDate(2, Date.valueOf(ngaysinh));
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, hokhau);
            ps.setString(6, address);
            ps.setString(7, socmnd);
            ps.setDate(8, Date.valueOf(ngaycap));
            ps.setString(9, noicap);
            ps.setShort(10, phanloai);
            ps.execute();

            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ObservableList<KhachHang> showKhachHang() {

        ObservableList<KhachHang> ls = FXCollections.observableArrayList();
        try {

            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(SELECT_KHACHHANG);
            //----------------------------
            //fullname,ngaysinh,phone,email,address,phanloai
            while (rs.next()) {
                KhachHang item = new KhachHang();
                item.setId(rs.getInt("id"));
                item.setFullname(rs.getString("fullname"));
                item.setPhone(rs.getString("phone"));
                item.setEmail(rs.getString("email"));
                if (rs.getShort("phanloai") == 0) {
                    item.setPhanloai("KH tiềm năng");
                } else {
                    item.setPhanloai("KH hiện hữu");
                };

                ls.add(item);
            }
            //--------------------------------------
            sm.close();
        } catch (Exception e) {

        }
        return ls;
    }

    // DON VI THI CONG
    public Boolean InsertDVThiCong(String iddv, String ten, String logo, String soluong, String diachi, String sdt) {
        try {
            //ten,logo,soluong,diachi,sdt
            PreparedStatement ps = conn.prepareStatement(INSERT_DVTHICONG);
            ps.setString(1, iddv);
            ps.setString(2, ten);
            ps.setString(3, logo);
            ps.setString(4, soluong);
            ps.setString(5, diachi);
            ps.setString(6, sdt);
            ps.execute();

            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ObservableList<DonViThiCong> showDVThiCong() {

        ObservableList<DonViThiCong> ls = FXCollections.observableArrayList();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(SELECT_DVTHICONG);
            //----------------------------
            //ten,soluong,diachi,sdt
            while (rs.next()) {
                DonViThiCong item = new DonViThiCong();
                item.setId(rs.getString("id"));
                item.setTen(rs.getString("ten"));
                item.setSoluong(rs.getInt("soluong"));
                item.setDiachi(rs.getString("diachi"));
                item.setSdt((rs.getString("sdt")));
                ls.add(item);
            }
            //--------------------------------------
            sm.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ls;
    }

    //CHU DAU TU
    public Boolean InsertChuDauTu(String fullname, String logo, String sdt, String email, String diachi, String descrip, String duansapmo, String duandangmo, String duanbangiao) {
        try {
            //fullname,logo,sdt,email,diachi,descrip,duansapmo,duandangmo,duanbangiao
            PreparedStatement ps = conn.prepareStatement(INSERT_CHUDAUTU);
            ps.setString(1, fullname);
            ps.setString(2, logo);
            ps.setString(3, sdt);
            ps.setString(4, email);
            ps.setString(5, diachi);
            ps.setString(6, descrip);
            ps.setString(7, duansapmo);
            ps.setString(8, duandangmo);
            ps.setString(9, duanbangiao);
            ps.execute();

            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean getEmailCDT(String Email) {
        Boolean checkIsset = true;
        try {
            PreparedStatement ps = conn.prepareStatement(CHECK_EMAIL_CDT + "'" + Email + "'");
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                checkIsset = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return checkIsset;

    }

    public ObservableList<ChuDauTu> showChuDauTu() {

        ObservableList<ChuDauTu> ls = FXCollections.observableArrayList();
        try {

            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(SELECT_CHUDAUTU);
            //----------------------------
            //fullname,sdt,email,diachi,duansapmo,duandangmo,duanbangiao
            while (rs.next()) {
                ChuDauTu item = new ChuDauTu();
                item.setId(rs.getString("id"));
                item.setFullname(rs.getString("fullname"));
                item.setSdt(rs.getString("sdt"));
                item.setEmail(rs.getString("email"));
                item.setDiachi(rs.getString("diachi"));
                item.setDuansapmo(rs.getInt("duansapmo"));
                item.setDuandangmo(rs.getInt("duandangmo"));
                item.setDuanbangiao(rs.getInt("duanbangiao"));

                ls.add(item);
            }
            //--------------------------------------
            sm.close();
        } catch (Exception e) {

        }
        return ls;
    }

    //DU AN
    public Boolean InsertDuAn(String id, String ten, String gtduan, String matbang, String vitri, String dientich, String phaply, String matdo, String tienich, String idchudautu, String iddvthicong, String dvquanly, Short trangthai) {
        try {
            //id,tenduan,gtduan,matban,vitri,dientich,phaply,matdo,tienich,idchudautu,iddvthicong,dvquanly,trangthai
            PreparedStatement ps = conn.prepareStatement(INSERT_DUAN);
            ps.setString(1, id);
            ps.setString(2, ten);
            ps.setString(3, gtduan);
            ps.setString(4, matbang);
            ps.setString(5, vitri);
            ps.setString(6, dientich);
            ps.setString(7, phaply);
            ps.setString(8, matdo);
            ps.setString(9, tienich);
            ps.setString(10, idchudautu);
            ps.setString(11, iddvthicong);
            ps.setString(12, dvquanly);
            ps.setShort(13, trangthai);
            ps.execute();

            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ObservableList<DuAn> showDuAn() {

        ObservableList<DuAn> ls = FXCollections.observableArrayList();
        try {

            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(SELECT_DUAN);
            //----------------------------
            //tenduan,matban,vitri,dientich,phaply,matdo,idchudautu,iddvthicong,dvquanly,trangthai
            while (rs.next()) {
                DuAn item = new DuAn();
                item.setId(rs.getString("id"));
                item.setTenduan(rs.getString("tenduan"));
                item.setMatban(rs.getString("matban"));
                item.setVitri(rs.getString("vitri"));
                item.setDientich(rs.getString("dientich"));
                item.setPhaply(rs.getString("phaply"));
                item.setMatdo(rs.getString("matdo"));
                item.setTenchudautu(rs.getString("fullname"));
                item.setTendvthicong(rs.getString("ten"));
                item.setDvquanly(rs.getString("dvquanly"));
                item.setTrangthai(rs.getString("trangthai"));

                ls.add(item);
            }
            //--------------------------------------
            sm.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public void changeStatusDuAN(String ID, String Status) {
        String rID = "'" + ID + "'";
        try {
            Statement sm = conn.createStatement();
            switch (Status) {
                case "0"://1
                    sm.execute("UPDATE DuAn SET trangthai=1 WHERE id= " + rID);
                    sm.close();
                    break;
                case "1"://2
                    sm.execute("UPDATE DuAn SET trangthai=2 WHERE id= " + rID);
                    sm.close();
                    break;
                case "2"://0
                    sm.execute("UPDATE DuAn SET trangthai=0 WHERE id= " + rID);
                    sm.close();
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (SQLException e) {
            System.out.println("ERROR changeStatusDuAN");
            System.out.println(e.getMessage());
        }
    }

    //KHU DU AN
    public Boolean InsertKhuDuAn(String tenkhu, String huong, String dientich, String idduan, Short trangthai) {
        try {
            //tenkhu,huong,dientich,idduan,trangthai
            PreparedStatement ps = conn.prepareStatement(INSERT_KHU);
            ps.setString(1, tenkhu);
            ps.setString(2, huong);
            ps.setString(3, dientich);
            ps.setString(4, idduan);
            ps.setShort(5, trangthai);
            ps.execute();

            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ObservableList<Khu> showKhu() {

        ObservableList<Khu> ls = FXCollections.observableArrayList();
        try {

            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(SELECT_KHU);
            //----------------------------
            //tenkhu,huong,dientich,idduan,trangthai
            while (rs.next()) {
                Khu item = new Khu();
                item.setId(rs.getString("id"));
                item.setIdduan(rs.getString("tenduan"));
                item.setTenkhu(rs.getString("tenkhu"));
                item.setHuong(rs.getString("huong"));
                item.setDientich(rs.getString("dientich"));
                item.setTrangthai(rs.getString("trangthai"));

                ls.add(item);
            }
            //--------------------------------------
            sm.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ls;
    }

    public void changeStatusKhu(String ID, String Status) {
        String rID = "'" + ID + "'";
        try {
            Statement sm = conn.createStatement();
            switch (Status) {
                case "0"://1
                    sm.execute("UPDATE Khu SET trangthai=1 WHERE id= " + rID);
                    sm.close();
                    break;
                case "1"://2
                    sm.execute("UPDATE Khu SET trangthai=2 WHERE id= " + rID);
                    sm.close();
                    break;
                case "2"://0
                    sm.execute("UPDATE Khu SET trangthai=0 WHERE id= " + rID);
                    sm.close();
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (SQLException e) {
            System.out.println("ERROR changeStatusKhu");
            System.out.println(e.getMessage());
        }
    }

    //SAN PHAM
    public Boolean InsertSanPham(String tensp, String dientich, String mota, String dongia, String vitri, String huong, String thuadatso, String bandoso, String diachi, String idkhu, Short trangthai) {
        try {
            //tensp,dientich,mota,dongia,vitri,huong,thuadatso,bandoso,diachi,idkhu,trangthai
            PreparedStatement ps = conn.prepareStatement(INSERT_SANPHAM);
            ps.setString(1, tensp);
            ps.setString(2, dientich);
            ps.setString(3, mota);
            ps.setString(4, dongia);
            ps.setString(5, vitri);
            ps.setString(6, huong);
            ps.setString(7, thuadatso);
            ps.setString(8, bandoso);
            ps.setString(9, diachi);
            ps.setString(10, idkhu);
            ps.setShort(11, trangthai);
            ps.execute();

            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public void changeStatusSP(Integer ID, String Status) {
        String rID = "'" + ID + "'";
        try {
            Statement sm = conn.createStatement();
            switch (Status) {
                case "0"://1
                    sm.execute("UPDATE SanPham SET trangthai=1 WHERE id= " + rID);
                    sm.close();
                    break;
                case "1"://2
                    sm.execute("UPDATE SanPham SET trangthai=2 WHERE id= " + rID);
                    sm.close();
                    break;
                case "2"://0
                    sm.execute("UPDATE SanPham SET trangthai=0 WHERE id= " + rID);
                    sm.close();
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (SQLException e) {
            System.out.println("ERROR changeStatusSP");
            System.out.println(e.getMessage());
        }
    }
    
    public NhanVien GetInfEmp(int id) {
        NhanVien item = new NhanVien();
        try {
            PreparedStatement preS = conn.prepareStatement(SELECT_INF_EMP);
            preS.setInt(1, id);
            ResultSet rs = preS.executeQuery();
            while (rs.next()) {
                item.setFullname(rs.getString("fullname"));
                item.setPosition(rs.getString("position"));
            }
            //--------------------------------------
            preS.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return item;
    }
}
