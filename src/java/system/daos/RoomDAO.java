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
import system.dtos.RoomDTO;

/**
 *
 * @author Admin
 */
public class RoomDAO implements Serializable {

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

    public List<RoomDTO> getRoomByHotelID(int id, String inDate, String outDate) throws Exception {
        List<RoomDTO> list = new ArrayList<>();
        RoomDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT tblRoom.RoomID, tblRoomType.TypeName, tblRoom.Quantity, COALESCE(SUM(tblBookingDetails.Quantity), 0) as [Booked Room], tblRoom.Quantity - COALESCE(SUM(tblBookingDetails.Quantity), 0) as [Available Room], tblRoom.Price\n"
                    + "	FROM tblRoom LEFT JOIN tblBookingDetails ON tblRoom.RoomID = tblBookingDetails.RoomID AND tblBookingDetails.CheckOut >= ? AND tblBookingDetails.CheckIn <= ?, tblRoomType\n"
                    + "	WHERE tblRoom.HotelID = ? AND tblRoom.TypeID = tblRoomType.TypeID\n"
                    + "	GROUP BY tblRoom.RoomID, tblRoom.Quantity, tblRoomType.TypeName, tblRoom.Price";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, inDate);
            preStm.setString(2, outDate);
            preStm.setInt(3, id);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int roomID = rs.getInt("RoomID");
                String name = rs.getString("TypeName");
                int quantity = rs.getInt("Quantity");
                int booked = rs.getInt("Booked Room");
                int available = rs.getInt("Available Room");
                float price = rs.getFloat("Price");
                dto = new RoomDTO(roomID, name, quantity, booked, available, price);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public RoomDTO getRoomByID(int id) throws Exception {
        RoomDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT tblRoom.HotelID, tblRoom.TypeID, Price, Quantity, StatusID FROM tblRoom WHERE tblRoom.RoomID = ? AND StatusID = 1";
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int hotelID = rs.getInt("HotelID");
                int type = rs.getInt("TypeID");
                float price = rs.getFloat("Price");
                int quantity = rs.getInt("Quantity");
                int status = rs.getInt("StatusID");
                dto = new RoomDTO(id, hotelID, type, price, quantity, status);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
}
