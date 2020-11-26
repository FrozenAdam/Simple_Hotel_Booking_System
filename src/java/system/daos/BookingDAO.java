/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import system.dtos.BookingDTO;

/**
 *
 * @author Admin
 */
public class BookingDAO implements Serializable {

    private Connection con;
    private PreparedStatement preStm;
    private ResultSet rs;

    private void openConnection() throws Exception {
        Context ctx = new InitialContext();
        Context envCtx = (Context) ctx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("DBCon");
        con = ds.getConnection();
    }

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public String getBookingID(String username) throws Exception {
        String id = null;
        try {
            openConnection();
            String sql = "SELECT TOP 1 BookingID FROM tblBooking WHERE Username LIKE ? ORDER BY BookingDate DESC";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, "%" + username + "%");
            rs = preStm.executeQuery();
            if (rs.next()) {
                id = rs.getString("BookingID");
            }
        } finally {
            closeConnection();
        }
        return id;
    }

    public boolean insertBooking(BookingDTO dto) throws Exception {
        boolean check = false;
        try {
            openConnection();
            String sql = "INSERT tblBooking(Username, Total, BookingDate, StatusID) VALUES (?, ?, GETDATE(), ?)";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, dto.getUsername());
            preStm.setFloat(2, dto.getTotal());
            preStm.setInt(3, dto.getStatus());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<BookingDTO> getAllBookingByUsername(String username) throws Exception {
        List<BookingDTO> list = new ArrayList<>();
        BookingDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT BookingID, BookingDate, Total, Username FROM tblBooking WHERE Username = ? AND StatusID = 1 ORDER BY BookingDate DESC";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, username);
            rs = preStm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("BookingID");
                String bookingDate = rs.getString("BookingDate");
                float total = rs.getFloat("Total");
                dto = new BookingDTO(id, bookingDate, total);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<BookingDTO> searchBooking(String id, String date, String username) throws Exception {
        List<BookingDTO> list = new ArrayList<>();
        BookingDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT BookingID, BookingDate, Total FROM tblBooking WHERE Username = ? AND BookingID LIKE ? AND BookingDate LIKE ? AND StatusID = 1";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, username);
            preStm.setString(2, "%" + id + "%");
            preStm.setString(3, "%" + date + "%");
            rs = preStm.executeQuery();
            while (rs.next()) {
                String bookingID = rs.getString("BookingID");
                String bookingDate = rs.getString("BookingDate");
                float total = rs.getFloat("Total");
                dto = new BookingDTO(bookingID, bookingDate, total);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public boolean removeBooking(String id, String user) throws Exception {
        boolean check = false;
        try {
            openConnection();
            String sql = "UPDATE tblBooking SET StatusID = -1 WHERE BookingID = ? AND Username = ?";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, id);
            preStm.setString(2, user);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
