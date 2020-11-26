/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.utility;

import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class SendingMail {
    
    private static final Logger LOGGER = Logger.getLogger(SendingMail.class);
    
    public static String sendMail(String to, String mailType) {
        
        String msg = "";
        for (int i = 0; i < 6; i++) {
            msg += new Random().nextInt(10);
        }
        
        final String username = "tonngokhong830@gmail.com";
        final String password = "Abc123456@@";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            if (mailType.equalsIgnoreCase("CONFIRM")) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(to));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("Confirmation For Your Booking Order");
                message.setText("Your Confirmation Booking Code is: " + msg + "\n Please do not reply this email.");
                Transport.send(message);
            } else if (mailType.equalsIgnoreCase("RESET")) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(to));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("Your Code For Reset Password");
                message.setText("Your Reset Password Code is: " + msg + "\n Please do not reply this email.");
                Transport.send(message);
            }
        } catch (Exception e) {
            LOGGER.error("Error at SendingMail: " + e.getMessage());
        }
        return msg;
    }
}
