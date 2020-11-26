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
import system.dtos.BookingDetailDTO;
import system.dtos.RoomDTO;

/**
 *
 * @author Admin
 */
public class BookingDetailDAO implements Serializable {

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

    public boolean insertBookingDetail(String bookingID, RoomDTO dto, String in, String out) throws Exception {
        boolean check = true;
        try {
            openConnection();
            String sql = "INSERT tblBookingDetails(BookingID, RoomID, CheckIn, CheckOut, Quantity, Total) VALUES (?, ?, ?, ?, ?, ?)";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, bookingID);
            preStm.setInt(2, dto.getRoomID());
            preStm.setString(3, in);
            preStm.setString(4, out);
            preStm.setInt(5, dto.getCartQuantity());
            preStm.setFloat(6, dto.getPrice() * dto.getCartQuantity());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<BookingDetailDTO> getBookingDetailByBookingID(String id) throws Exception {
        List<BookingDetailDTO> list = new ArrayList<>();
        BookingDetailDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT tblHotel.HotelName, tblRoomType.TypeName, CheckIn, CheckOut, tblRoom.Price, tblBookingDetails.Quantity, tblBookingDetails.Total  \n"
                    + "FROM tblBookingDetails, tblHotel, tblRoom, tblRoomType \n"
                    + "WHERE BookingID = ? AND tblHotel.HotelID = tblRoom.HotelID AND tblRoom.TypeID = tblRoomType.TypeID AND tblBookingDetails.RoomID = tblRoom.RoomID\n"
                    + "GROUP BY tblHotel.HotelName, tblRoomType.TypeName, CheckIn, CheckOut, tblRoom.Price, tblBookingDetails.Quantity, tblBookingDetails.Total";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, id);
            rs = preStm.executeQuery();
            while (rs.next()) {
                String hotelName = rs.getString("HotelName");
                String typeName = rs.getString("TypeName");
                String in = rs.getString("CheckIn");
                String out = rs.getString("CheckOut");
                float price = rs.getFloat("Price");
                int quantity = rs.getInt("Quantity");
                float total = rs.getFloat("Total");
                dto = new BookingDetailDTO(hotelName, typeName, in, out, price, quantity, total);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
