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
import system.dtos.HotelDTO;

/**
 *
 * @author Admin
 */
public class HotelDAO implements Serializable {

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

    public List<HotelDTO> searchHotel(String search, int area, String checkin, String checkout, int amount) throws Exception {
        List<HotelDTO> list = new ArrayList<>();
        HotelDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT tblHotel.HotelID, tblHotel.HotelName, tblHotel.HotelImage, tblArea.AreaName, tblAvailableRoom.Quantity, [Booked Room], [Available Room]\n"
                    + "	FROM (SELECT T1.HotelID, [Quantity], [Booked Room], [Quantity] - [Booked Room] AS [Available Room]\n"
                    + "			FROM (SELECT tblRoom.HotelID, COALESCE(SUM(tblBookingDetails.Quantity),0) AS [Booked Room]\n"
                    + "			FROM tblRoom LEFT JOIN tblBookingDetails ON tblRoom.RoomID = tblBookingDetails.RoomID AND tblBookingDetails.CheckOut >= ? AND tblBookingDetails.CheckIn <= ? JOIN tblHotel ON tblRoom.HotelID = tblHotel.HotelID AND tblHotel.HotelName LIKE ? AND tblHotel.AreaID = ?\n"
                    + "			GROUP BY tblRoom.HotelID)\n"
                    + "			T1 JOIN(SELECT tblRoom.HotelID, SUM(tblRoom.Quantity) as [Quantity]\n"
                    + "					FROM tblRoom\n"
                    + "					GROUP BY tblRoom.HotelID\n"
                    + "					)\n"
                    + "			T2 ON T1.HotelID = T2.HotelID\n"
                    + "			GROUP BY T1.HotelID, Quantity, [Booked Room]\n"
                    + "			HAVING [Quantity] - [Booked Room] >= ?) AS tblAvailableRoom, tblHotel, tblArea\n"
                    + "	WHERE tblHotel.HotelID = tblAvailableRoom.HotelID AND tblArea.AreaID = tblHotel.AreaID";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, checkin);
            preStm.setString(2, checkout);
            preStm.setString(3, "%" + search + "%");
            preStm.setInt(4, area);
            preStm.setInt(5, amount);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int hotelID = rs.getInt("HotelID");
                String hotelName = rs.getString("HotelName");
                String hotelImage = rs.getString("HotelImage");
                String areaName = rs.getString("AreaName");
                int quantity = rs.getInt("Quantity");
                int booked = rs.getInt("Booked Room");
                int available = rs.getInt("Available Room");
                if(available < 0) {
                    available = 0;
                }
                dto = new HotelDTO(hotelID, hotelName, hotelImage, areaName, quantity, booked, available);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public String getHotelNameByID(int id) throws Exception {
        String name = null;
        try {
            openConnection();
            String sql = "SELECT HotelName FROM tblHotel WHERE HotelID = ?";
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                name = rs.getString("HotelName");
            }
        } finally {
            closeConnection();
        }
        return name;
    }
}
