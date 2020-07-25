package com.radnus.login.operation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.PrintWriter;

/**
 * Servlet implementation class DeleteRecordMain
 */
@WebServlet("/DeleteRecordMain")
public class DeleteRecordMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRecordMain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String empId=request.getParameter("empId").toLowerCase();
		PrintWriter out=response.getWriter();
		response.setContentType("text");
		String result=deleteRecord(empId);
		out.print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String deleteRecord(String empId) 
	{
		try(Connection connect=DriverManager.getConnection("jdbc:postgresql://localhost:5432/training","postgres","sndr26ms")){
			Class.forName("org.postgresql.Driver");
			PreparedStatement del=connect.prepareStatement("delete from employeerecords where id='"+empId+"'");
			del.executeUpdate();
		}
		catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return "Problem occurs in deletion, Try again!";
		}
		return "record deleted successfully";
	}
}
