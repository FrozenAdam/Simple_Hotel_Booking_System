/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import system.daos.BookingDAO;
import system.daos.BookingDetailDAO;
import system.dtos.BookingDTO;
import system.dtos.RegistrationDTO;
import system.dtos.RoomDTO;
import system.dtos.ShoppingCartDTO;

/**
 *
 * @author Admin
 */
public class ConfirmCartController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ConfirmCartController.class);
    private static final String SUCCESS = "GetHotelController";
    private static final String FAIL = "cart.jsp";

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
        String url = FAIL;
        try {
            HttpSession session = request.getSession(false);
            String checkIn = (String) session.getAttribute("CHECKIN");
            String checkOut = (String) session.getAttribute("CHECKOUT");
            String code = request.getParameter("txtCode");
            String checkCode = request.getParameter("txtCheck");
            boolean valid = true;
            if (!code.equals(checkCode)) {
                valid = false;
                request.setAttribute("CODE", checkCode);
                request.setAttribute("WARN", "Invalid Confirmation Code. Please try again");
            }
            if (valid) {
                ShoppingCartDTO cart = (ShoppingCartDTO) session.getAttribute("CART");
                if (cart != null) {
                    RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");

                    if (user != null) {
                        BookingDTO bookingDTO = new BookingDTO(user.getUsername(), cart.getTotal(), 1);
                        BookingDAO bookingDAO = new BookingDAO();

                        if (bookingDAO.insertBooking(bookingDTO)) {
                            String bookingID = bookingDAO.getBookingID(user.getUsername());
                            Iterator iterator = cart.getCart().entrySet().iterator();
                            BookingDetailDAO detail = new BookingDetailDAO();
                            while (iterator.hasNext()) {
                                Map.Entry entry = (Map.Entry) iterator.next();
                                RoomDTO roomDTO = (RoomDTO) entry.getValue();
                                detail.insertBookingDetail(bookingID, roomDTO, checkIn, checkOut);
                            }
                            url = SUCCESS;
                            request.setAttribute("WARN", "Booking Completed. Your Booking ID is: " + bookingID);
                            session.removeAttribute("CART");
                            session.removeAttribute("CHECKIN");
                            session.removeAttribute("CHECKOUT");
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error at ConfirmCartController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
