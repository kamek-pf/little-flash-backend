package com.littleflash.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class DiscountServlet extends HttpServlet {
    // Enregistrement de la classe persistable auprès d'Objectify
    static {
        ObjectifyService.register(Discount.class);
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Requête Objectify
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
        try {
            // Création de l'objet
            Discount discount = new Discount(req.getParameter("item_id"), req.getParameter("message"), 
            		Double.parseDouble(req.getParameter("rate")));
            
            // Enregistrement de l'objet dans le Datastore avec Objectify
            ofy().save().entity(discount).now();

            resp.sendRedirect("/admin/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}