package com.littleflash.admin;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.googlecode.objectify.ObjectifyService;
import com.littleflash.backend.Flash;

import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class DiscountServlet extends HttpServlet {
    // Registering class using Objectify
    static {
        ObjectifyService.register(Discount.class);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Objectify Request
            List<Discount> discounts = ofy().load().type(Discount.class).order("-date").limit(5).list();

            req.setAttribute("discounts", discounts);
            this.getServletContext().getRequestDispatcher("/admin/discountmgr.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) {	
        try 
        {
            // Creating object
            Discount discount = new Discount(req.getParameter("item_id"), req.getParameter("message"), 
                    Double.parseDouble(req.getParameter("rate")));

            // Saving object in DataStore using Objectify
            ofy().save().entity(discount).now();

            /*
            // Getting a list of everybody interested in the item
            List<Flash> flashes = ofy().load().type(Flash.class).order("-date").limit(5).list();
            Iterator<Flash> iterator = flashes.iterator();
            while (iterator.hasNext()) 
            {
                if(iterator.next().getItemId() != req.getParameter("item_id"))
                    iterator.remove();
            }
            */
            // Sending mail to everybody who flashed the item
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);

            String msgBody = "An item you want is on sale !"
                    + "\n \n"
            		+ req.getParameter("item_id") + " - " + req.getParameter("rate") + "% off"
                    + "\n \n"
            		+ req.getParameter("message");

            try 
            {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("noreply@little-flash.appspotmail.com", "LittleFlash"));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress("b.kamek@gmail.com"));
                msg.setSubject("Item on sale !");
                msg.setText(msgBody);
                Transport.send(msg);

            } 
            catch (AddressException e) {} 
            catch (MessagingException e) {}

            resp.sendRedirect("/admin/");
        } 
        catch (IOException e){ 
            e.printStackTrace();
        }
    }
}
