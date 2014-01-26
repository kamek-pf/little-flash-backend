<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.littleflash.backend.Discount" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.littleflash.backend.Item" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" content="" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="" />
        <meta name="author" content="" />

        <title>Little flash admin panel</title>

        <!-- Bootstrap core CSS -->
        <link href="/bootstrap/css/bootstrap.css" rel="stylesheet" type="" />

        <!-- Custom styles for this template -->
        <link href="/bootstrap/css/jumbotron-narrow.css" rel="stylesheet" type="" />
    </head>

    <body>

        <div class="container">
            <div class="header">
                <ul class="nav nav-pills pull-right">
                	<li class="active"><a href="#">Manage Discounts</a></li>
                    <li><a href="/index.html">Client Frontend</a></li>
                    <li><a href="https://appengine.google.com/datastore/explorer?&app_id=s~little-flash">AppEngine</a></li>
                </ul>
                <h3 class="text-muted">Little Flash</h3>
            </div>

            <section>
            	<h2>Manage Discounts</h2>
                <p>
                    You can manage your sales from this page.
                    When adding new sales, people who flashed the item's QR code will be notified by email automatically.
                </p>
                <p>
                    First, enter the item's data :
                </p>
                <form action="/admin/" method="post" role="form">
					<div class="form-group">
						<label>Reference: </label>
						<input type="text" name="item_id" class="form-control" />
					</div>
                    <div class="form-group">
						<label>Name : </label>
						<input type="text" name="item_name" class="form-control" />
					</div>
                    <div class="form-group">
						<label>Original price : </label>
						<input type="text" name="item_price" class="form-control" />
					</div>
                    <p>
                        Then, enter the sale's information :
                    </p>
					<div class="form-group">
                        <label>Discount rate (Ex: 40 means 40% off): </label>
						<input type="number" name="rate" class="form-control" />
					</div>
					<div class="form-group">
						<label>Say something about it  :</label>
						<textarea name="message" class="form-control" rows="3"></textarea>
					</div>
					
					<input type="submit" class="btn btn-default center-block" />
				
				</form>
                <h4>Current discounts : </h4>
                
                <table class="table">
					<thead>
						<tr>
							<th>Reference</th>
							<th>Name</th>
							<th>Original price</th>
							<th>Rate</th>
							<th>New price</th>
							<th>Message</th>
						</tr>
					</thead>
					<tbody>
		                <%
			                List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
			                List<Item> items = (List<Item>) request.getAttribute("items");
			                Iterator<Discount> dI = discounts.iterator();
			                Iterator<Item> iI = items.iterator();
			                
			                while(iI.hasNext() && dI.hasNext())
			                {
			                	Discount discount = dI.next();
			                	Item item = iI.next();
			                	
			                	double newPrice = item.getPrice() - (item.getPrice() * discount.getRate() / 100);
						%>				
						<tr>
							<td><%= item.getItemId() %></td>
							<td><%= item.getItemName() %></td>
							<td><%= item.getPrice() %></td>
							<td><%= discount.getRate() %> %</td>
							<td><%= newPrice %></td>
							<td><%= discount.getMessage() %></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
						
            </section>
                
            <div class="footer">
                <p>&copy; Don't steal my shit 2013</p>
            </div>

        </div> 

    </body>
</html>
