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
public class BookingDTO {
    
    private String bookingID, username, bookedDate;
    private float total;
    private int status;
    
    public BookingDTO(String username, float total, int status) {
        this.username = username;
        this.total = total;
        this.status = status;
    }
    
    public BookingDTO(String bookingID, String bookedDate, float total) {
        this.bookingID = bookingID;
        this.bookedDate = bookedDate;
        this.total = total;
    }
    
    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
