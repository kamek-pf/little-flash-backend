package com.littleflash.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.ObjectifyService;
import com.littleflash.backend.Discount;
import com.littleflash.backend.Item;

import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class DiscountServlet extends HttpServlet {
    // Registering class using Objectify
    static {
        ObjectifyService.register(Discount.class);
        ObjectifyService.register(Item.class);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Objectify Request
        	ofy().clear();
            List<Discount> discounts = ofy().load().type(Discount.class).list();
            List<Item> items = ofy().load().type(Item.class).list();
            
            req.setAttribute("discounts", discounts);
            req.setAttribute("items", items);
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
            // Creating objects
        	Item item = new Item(req.getParameter("item_id"), req.getParameter("item_name"), "",
                    Double.parseDouble(req.getParameter("item_price")));
        	
            Discount discount = new Discount(req.getParameter("item_id"), req.getParameter("message"), 
                    Double.parseDouble(req.getParameter("rate")));

            // Saving objects in DataStore using Objectify
            ofy().save().entity(item).now();
            ofy().save().entity(discount).now();

            // Getting ready to query flashes
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Query q = new Query("Flash");
            PreparedQuery pq = datastore.prepare(q);

            // Send mail to everyone interested
            for (Entity result : pq.asIterable())
            {
            	String msgBody = "An item you want is on sale !"
                        + "\n \n"
                		+ req.getParameter("item_id") + " - " + req.getParameter("rate") + "% off"
                        + "\n \n"
                		+ req.getParameter("message");
            	
                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props, null);
                
            	String resultEmail = (String) result.getProperty("userEmail");
            	String resultItemId = (String) result.getProperty("itemId");
            	
            	if(resultItemId.equals(req.getParameter("item_id")))
            	{
            		 Message msg = new MimeMessage(session);
                     msg.setFrom(new InternetAddress("noreply@little-flash.appspotmail.com", "LittleFlash"));
                     msg.addRecipient(Message.RecipientType.TO, new InternetAddress(resultEmail));
            		 msg.setSubject("Item on sale !");
                     msg.setText(msgBody);
                     Transport.send(msg);
            	}
            }
           
           resp.sendRedirect("/admin/");
        } 
        catch (IOException e){ 
            e.printStackTrace();
        } catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
