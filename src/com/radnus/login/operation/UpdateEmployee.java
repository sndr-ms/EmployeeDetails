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
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
/**
 * Servlet implementation class UpdateEmployee
 */
@WebServlet("/UpdateEmployee")
public class UpdateEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String empId=request.getParameter("empId");
		String empName=request.getParameter("empName");
		String empDesignation=request.getParameter("empDesignation");
		String empDob=request.getParameter("empDob");
		empName=(empName.length()==0)?null:empName;
		empDesignation=(empDesignation.length()==0)?null:empDesignation;
		empDob=(empDob.length()==0)?null:empDob;
		PrintWriter out=response.getWriter();
		ResultSet result=update(empId,empName,empDesignation,empDob);
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
				out.print("<script>alert(\""+empId+" record doesn't exist\");</script>");
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
	
	public ResultSet update(String empId,String empName,String empDesignation,String empDob)
	{
		boolean empDobState=false;
		ResultSet result=null;
		try(Connection connect=DriverManager.getConnection("jdbc:postgresql://localhost:5432/training","postgres","sndr26ms");)
		{
			Class.forName("org.postgresql.Driver");
			PreparedStatement updateRecord=null;
			if(empName!=null && empDesignation!=null && empDob!=null)
			{
				updateRecord=connect.prepareStatement("update employeerecords set name=?,designation=?,dob=? where id='"+empId+"'");
				updateRecord.setString(1,empName);
				updateRecord.setString(2,empDesignation);
				LocalDate empDOB=LocalDate.of(Integer.parseInt(empDob.substring(0,4)),Integer.parseInt(empDob.substring(5,7)),
						Integer.parseInt(empDob.substring(8,10)));
				updateRecord.setObject(3,empDOB);
				empDobState=true;
			}	
			else if(empName==null && empDesignation!=null && empDob!=null)
			{
				updateRecord=connect.prepareStatement("update employeerecords set designation=?,dob=? where id='"+empId+"'");
				updateRecord.setString(1,empDesignation);
				LocalDate empDOB=LocalDate.of(Integer.parseInt(empDob.substring(0,4)),Integer.parseInt(empDob.substring(5,7)),
						Integer.parseInt(empDob.substring(8,10)));
				updateRecord.setObject(2,empDOB);
				empDobState=true;
			}
			
			else if(empName!=null && empDesignation==null && empDob!=null)
			{
				updateRecord=connect.prepareStatement("update employeerecords set name=?,dob=? where id='"+empId+"'");
				updateRecord.setString(1,empName);
				LocalDate empDOB=LocalDate.of(Integer.parseInt(empDob.substring(0,4)),Integer.parseInt(empDob.substring(5,7)),
						Integer.parseInt(empDob.substring(8,10)));
				updateRecord.setObject(2,empDOB);
				empDobState=true;
			}
			else if(empName!=null && empDesignation!=null && empDob==null)
			{
				updateRecord=connect.prepareStatement("update employeerecords set name=?,designation=? where id='"+empId+"'");
				updateRecord.setString(1,empName);
				updateRecord.setString(2,empDesignation);
			}
			else if(empName==null && empDesignation==null && empDob!=null)
			{
				updateRecord=connect.prepareStatement("update employeerecords set dob=? where id='"+empId+"'");
				LocalDate empDOB=LocalDate.of(Integer.parseInt(empDob.substring(0,4)),Integer.parseInt(empDob.substring(5,7)),
						Integer.parseInt(empDob.substring(8,10)));
				updateRecord.setObject(1,empDOB);
				empDobState=true;
			}
			else if(empName!=null && empDesignation==null && empDob==null)
			{
				updateRecord=connect.prepareStatement("update employeerecords set name=? where id='"+empId+"'");
				updateRecord.setString(1,empName);
				
			}
			else if(empName==null && empDesignation!=null && empDob==null)
			{
				updateRecord=connect.prepareStatement("update employeerecords set designation=? where id='"+empId+"'");
				updateRecord.setString(1,empDesignation);
			}
			updateRecord.executeUpdate();
			if(empDobState)
			{
				updateRecord=connect.prepareStatement("update employeerecords set age=age(employeerecords.dob) where id='"+empId+"'");
				updateRecord.executeUpdate();
			}
			updateRecord=connect.prepareStatement("select * from employeerecords where id='"+empId+"'");
			result=updateRecord.executeQuery();
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
}
	