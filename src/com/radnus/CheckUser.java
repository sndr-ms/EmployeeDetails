package com.radnus;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class CheckUser
 */
@WebServlet("/CheckUser")
public class CheckUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String username=request.getParameter("uname");
		String password=request.getParameter("pswd");
		PrintWriter out=response.getWriter();
		boolean check=false;
		response.setContentType("text/html");
		check=checkUser(username,password);
		if(check)
		{
			out.print("true");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public boolean checkUser(String uname,String pwd)
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection connect=DriverManager.getConnection("jdbc:postgresql://localhost:5432/training","postgres","sndr26ms");
			PreparedStatement select=connect.prepareStatement("select password from adminlogin where username=?");
			select.setString(1,uname);
			ResultSet result=select.executeQuery();
			String pswd="";
			while(result.next())
			{
				pswd=result.getString(1);
			}
			if(pwd.equals(pswd))
				return true;
			else
				return false;
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
