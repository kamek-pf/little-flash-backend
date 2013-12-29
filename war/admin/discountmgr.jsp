<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.littleflash.admin.Discount" %>

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
                	You can manage the discounts of your store currently available in Little Flash from
                	this page.
                </p>
                <form action="/admin/" method="post" role="form">
					<div class="form-group">
						<label>Item ID : </label>
						<input type="text" name="item_id" class="form-control" />
					</div>
					<div class="form-group">
						<label>Discount rate : </label>
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
							<th>Item ID</th>
							<th>Rate</th>
							<th>Info</th>
						</tr>
					</thead>
					<tbody>
		                <%
							List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
							for (Discount discount : discounts) {
						%>				
						<tr>
							<td><%= discount.getItemID() %></td>
							<td><%= discount.getRate() %> %</td>
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
