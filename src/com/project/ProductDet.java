package com.project;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Servlet implementation class ProductDet
 */
@WebServlet("/ProductDet")
public class ProductDet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ID = request.getParameter("ID");
		try {
        	PrintWriter out = response.getWriter();
            out.println("<html><body>");
                 
            InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
            Properties props = new Properties();
            props.load(in);
                
            DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
        	Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rst = stmt.executeQuery("select * from products where ID =" + ID);
   
            out.println("<table><tr><th>Name</th><th>Color</th><th>Price</th></tr>");
            
            /*while (rst.next()) {
                out.println("<tr><td>" + rst.getString("name") + "</td>" + "<td>" +
                		rst.getString("color") + "</td><td>" + rst.getBigDecimal("price") + "</td></tr>");
            }
            
            out.println("</table>");*/
            if (rst.next()) {
				out.println("ID number " + rst.getInt("ID") + " is there! " + "<Br>" + "Name: " + rst.getString("name") + "<Br>" + "Price: " + rst.getBigDecimal("price") + "<Br>");
				out.println("<a href=\"index.html\"> Go Home</a>");
			}
			else {
				out.println("ID number " + rst.getInt("ID") + " is not there" + "<Br>" + "<a href=\"index.html\"> Go Home</a>");
			}
            
            stmt.close();        
   
        
            out.println("</body></html>");
            conn.closeConnection();
        
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
