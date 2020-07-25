package com.radnus.login.operation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import java.sql.ResultSet;

/**
 * Servlet implementation class DeleteRecord
 */
@WebServlet("/DeleteRecord")
public class DeleteRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRecord() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String id=request.getParameter("empId").toLowerCase();
		String record=fetchRecord(id);
		response.setContentType("text");
		PrintWriter out=response.getWriter();
		if(record!=null) {
			out.print(record);					
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String fetchRecord(String empId) {
		
		String record=null;
		try(Connection connect=DriverManager.getConnection("jdbc:postgresql://localhost:5432/training","postgres","sndr26ms"))
		{
			Class.forName("org.postgresql.Driver");
			PreparedStatement fetch=connect.prepareStatement("select designation,name from employeerecords where id='"+empId+"'");
			ResultSet result=fetch.executeQuery();
			while(result.next()) {
				
				record=result.getString(1)+" "+result.getString(2);
			}
		}
		catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return record;
	}

}
