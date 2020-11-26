/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.dtos;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class ShoppingCartDTO implements Serializable {

    private String user;
    private HashMap<Integer, RoomDTO> cart;
    String checkinDate, checkoutDate;
    float total;

    public String getUser() {
        return user;
    }

    public HashMap<Integer, RoomDTO> getCart() {
        return cart;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public ShoppingCartDTO(String userId) {
        this.user = userId;
        this.cart = new HashMap<Integer, RoomDTO>();
        this.total = 0;
    }

    public void addToCart(RoomDTO dto) throws Exception {
        if (this.cart.containsKey(dto.getRoomID())) {
            int cartQty = this.cart.get(dto.getRoomID()).getCartQuantity() + dto.getCartQuantity();
            dto.setCartQuantity(cartQty);
        }
        this.cart.put(dto.getRoomID(), dto);
    }

    public void removeFromCart(int id) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
    }

    public void updateCart(int id, int qty) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.get(id).setCartQuantity(qty);
        }
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTotal() throws Exception {
        this.total = 0;
        for (RoomDTO dto : this.cart.values()) {
            this.total = this.total + dto.getPrice() * dto.getCartQuantity();
        }
        return this.total;
    }
}
