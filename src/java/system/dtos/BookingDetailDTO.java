/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.dtos;

/**
 *
 * @author Admin
 */
public class BookingDetailDTO {

    private int roomID, quantity;
    private String inDate, outDate, bookingID, hotelName, typeName;
    private float total, price;
    
    public BookingDetailDTO(String hotelName, String typeName, String inDate, String outDate, float price, int quantity, float total){
        this.hotelName = hotelName;
        this.typeName = typeName;
        this.inDate = inDate;
        this.outDate = outDate;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public BookingDetailDTO(String bookingID, int roomID, String inDate, String outDate, int quantity, float total) {
        this.bookingID = bookingID;
        this.roomID = roomID;
        this.inDate = inDate;
        this.outDate = outDate;
        this.quantity = quantity;
        this.total = total;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
