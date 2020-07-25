package com.radnus.login.operation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.PrintWriter;

/**
 * Servlet implementation class SearchRecord
 */
@WebServlet("/SearchRecord")
public class SearchRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchRecord() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String id=request.getParameter("empId").toLowerCase();
		PrintWriter out=response.getWriter();
		ResultSet result=search(id);
		response.setContentType("text/html");
		try
		{
			boolean flag=true;
			while(result.next())
			{
				if(flag)
				{
					ResultSetMetaData resMet=result.getMetaData();
					out.print("<table><tr><th>"+resMet.getColumnName(1)+"</th>");
					out.print("<th>"+resMet.getColumnName(2)+"</th>");
					out.print("<th>"+resMet.getColumnName(3)+"</th>");
					out.print("<th>"+resMet.getColumnName(4)+"</th>");
					out.print("<th>"+resMet.getColumnName(5)+"</th>");
					out.print("<th>"+resMet.getColumnName(6)+"</th></tr>");
					flag=false;
				}
				out.print("<tr><td>"+result.getString(1)+"</td>");
				out.print("<td>"+result.getString(2)+"</td>");
				out.print("<td>"+result.getString(3)+"</td>");
				out.print("<td>"+result.getString(4)+"</td>");
				out.print("<td>"+result.getString(5)+"</td>");
				out.print("<td>"+result.getString(6)+"</td></tr>");
				
			}	
			if(flag)
				out.print("<script>alert(\""+id+" record doesn't exist\");</script>");
			else
				out.print("</table>");
			
		}
		catch(SQLException e)
		{
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

	public ResultSet search(String id)
	{
		ResultSet result=null;
		try(Connection connect=DriverManager.getConnection("jdbc:postgresql://localhost:5432/training","postgres","sndr26ms");)
		{
			Class.forName("org.postgresql.Driver");
			PreparedStatement searchRecord=null;
			if(id.length()==0)
				searchRecord=connect.prepareStatement("select * from employeerecords order by id ASC");
			else
				searchRecord=connect.prepareStatement("select * from employeerecords where id='"+id+"'");
			result=searchRecord.executeQuery();
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
