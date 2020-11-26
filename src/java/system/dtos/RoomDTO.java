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
public class RoomDTO {

    private String roomType, hotelName;
    private float price;
    private int roomID, quantity, hotelID, typeID, cartQuantity, booked, available, status;

    public RoomDTO(int roomID, int hotelID, int typeID, float price, int quantity, int status){
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.typeID = typeID;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }
    
    public RoomDTO(int roomID, String roomType, int quantity, int booked, int available, float price){
        this.roomID = roomID;
        this.roomType = roomType;
        this.quantity = quantity;
        this.booked = booked;
        this.available = available;
        this.price = price;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    
    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
