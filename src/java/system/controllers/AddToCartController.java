/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import system.daos.HotelDAO;
import system.daos.RoomDAO;
import system.dtos.RoomDTO;
import system.dtos.RegistrationDTO;
import system.dtos.ShoppingCartDTO;

/**
 *
 * @author Admin
 */
public class AddToCartController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddToCartController.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String strRoomID = request.getParameter("txtRoomID");
            if (strRoomID != null) {
                String typeName = request.getParameter("txtRoomType");
                String in = request.getParameter("txtIn");
                String out = request.getParameter("txtOut");
                int RoomID = Integer.parseInt(strRoomID);
                int available = Integer.parseInt(request.getParameter("txtAvailable"));
                RoomDAO roomDAO = new RoomDAO();
                RoomDTO roomDTO = roomDAO.getRoomByID(RoomID);
                HotelDAO hotelDAO = new HotelDAO();
                roomDTO.setCartQuantity(1);
                roomDTO.setRoomType(typeName);
                roomDTO.setHotelName(hotelDAO.getHotelNameByID(roomDTO.getHotelID()));
                roomDTO.setAvailable(available);

                HttpSession session = request.getSession();
                RegistrationDTO userDTO = (RegistrationDTO) session.getAttribute("USER");
                ShoppingCartDTO cart = (ShoppingCartDTO) session.getAttribute("CART");

                if (cart == null) {
                    if (userDTO != null) {
                        cart = new ShoppingCartDTO(userDTO.getUsername());
                        cart.setCheckinDate(in);
                        cart.setCheckoutDate(out);
                    }
                }

                if (cart != null) {
                    boolean valid = true;
                    if (cart.getCart().get(RoomID) != null) {
                        if (cart.getCart().get(RoomID).getCartQuantity() + 1 > available) {
                            valid = false;
                            request.setAttribute("WARN", "Needed Room Exceeded Available Room");
                            request.setAttribute("HOTELID", roomDTO.getHotelID());
                            request.setAttribute("NAME", roomDTO.getHotelName());
                            request.setAttribute("ROOM", roomDAO.getRoomByHotelID(roomDTO.getHotelID(), in, out));
                        }
                    }
                    if (available == 0) {
                        valid = false;
                        request.setAttribute("WARN", "Can Not Book This " + typeName + " Room.");
                        request.setAttribute("HOTELID", roomDTO.getHotelID());
                        request.setAttribute("NAME", roomDTO.getHotelName());
                        request.setAttribute("ROOM", roomDAO.getRoomByHotelID(roomDTO.getHotelID(), in, out));
                    }

                    if (valid) {
                        cart.addToCart(roomDTO);
                        cart.setTotal(cart.getTotal());
                        session.setAttribute("CART", cart);
                        request.setAttribute("HOTELID", roomDTO.getHotelID());
                        request.setAttribute("NAME", roomDTO.getHotelName());
                        request.setAttribute("ROOM", roomDAO.getRoomByHotelID(roomDTO.getHotelID(), in, out));
                    }
                }
            } else {
                request.setAttribute("WARN", "Room not found.");
            }
        } catch (Exception e) {
            LOGGER.error("Error at AddToCartController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("viewdetail.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
