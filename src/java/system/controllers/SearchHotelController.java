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
import system.daos.AreaDAO;
import system.daos.HotelDAO;

/**
 *
 * @author theFrozenAdam
 */
public class SearchHotelController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchHotelController.class);

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
            int amount;
            String hotelName = request.getParameter("txtSearchName");
            int hotelArea = Integer.parseInt(request.getParameter("txtArea"));
            String checkin = request.getParameter("txtCheckinDate");
            String checkout = request.getParameter("txtCheckoutDate");
            String amountStr = request.getParameter("txtAmount");
            if (amountStr.isEmpty()) {
                amount = 0;
            } else {
                amount = Integer.parseInt(amountStr);
            }
            HttpSession session = request.getSession();
            HotelDAO dao = new HotelDAO();
            AreaDAO area = new AreaDAO();
            request.setAttribute("AREA", area.getAllArea());
            request.setAttribute("HOTEL", dao.searchHotel(hotelName, hotelArea, checkin, checkout, amount));
            String sesIn = (String) session.getAttribute("CHECKIN");
            String sesOut = (String) session.getAttribute("CHECKOUT");
            session.setAttribute("CHECKIN", checkin);
            session.setAttribute("CHECKOUT", checkout);
            //Compare checkin - checkout date to keep the cart or not
            if (!(sesIn == null && sesOut == null)) {
                if (!(sesIn.equals(checkin) && sesOut.equals(checkout))) {
                    session.removeAttribute("CART");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error at SearchHotelController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("home.jsp").forward(request, response);
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
