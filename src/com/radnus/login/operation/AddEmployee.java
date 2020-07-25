package com.radnus.login.operation;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDate;

/**
 * Servlet implementation class AddEmployee
 */
@WebServlet("/AddEmployee")
public class AddEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //private static int empCount=0;
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String empName=request.getParameter("ename");
		String empDesignation=request.getParameter("designation");
		String dob=request.getParameter("dateofbirth");
		LocalDate empDOB=LocalDate.of(Integer.parseInt(dob.substring(0,4)),Integer.parseInt(dob.substring(5,7)),
				Integer.parseInt(dob.substring(8,10)));
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		try 
		{
			ResultSet result=addRecord(empName,empDesignation,empDOB);
			ResultSetMetaData resMet=result.getMetaData();
			out.print("<table>"+"<tr>"+"<th>"+resMet.getColumnName(1)+"</td>");
			out.print("<th>"+resMet.getColumnName(2)+"</th>");
			out.print("<th>"+resMet.getColumnName(3)+"</th>");
			out.print("<th>"+resMet.getColumnName(4)+"</th>");
			out.print("<th>"+resMet.getColumnName(5)+"</th>");
			out.print("<th>"+resMet.getColumnName(6)+"</th></tr>");
			
			while(result.next())
			{
				out.print("<tr><td>"+result.getString(1)+"</td>");
				out.print("<td>"+result.getString(2)+"</td>");
				out.print("<td>"+result.getString(3)+"</td>");
				out.print("<td>"+result.getString(4)+"</td>");
				out.print("<td>"+result.getString(5)+"</td>");
				out.print("<td>"+result.getString(6)+"</td></tr>");
			}
			out.print("</table>");
		}
		catch(NullPointerException | SQLException e)
		{
			e.printStackTrace();
			out.print("<script>alert(\"Problem occurs while retrieving data\");</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String generate_empId(int lastid) {
		lastid++;
		String eId="emp";
		int i=0;
		if(lastid<10)
			i=3;
		else if(lastid<100)
			i=2;
		else
			i=1;
		if(lastid<999)
		{
			while(i>0)
			{
				eId+="0";
				i--;
			}
		}		
		eId+=lastid;
		return eId;
	}
	
	public ResultSet addRecord(String name,String designation,LocalDate dob)
	{
		int lastid=0;
		String eId=null;
		ResultSet result=null;
		try(Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/training","postgres","sndr26ms"))
		{
			Class.forName("org.postgresql.Driver");
			PreparedStatement ret=con.prepareStatement("select substring(id from 4) from employeerecords order by id desc limit 1");
			result=ret.executeQuery();
			while(result.next()) {
				if(result.getString(1)!=null)
					lastid=Integer.parseInt(result.getString(1));
			}
			eId=generate_empId(lastid);
			PreparedStatement add=con.prepareStatement("insert into employeerecords(id,name,designation,dob) values(?,?,?,?)");
			add.setString(1,eId);
			add.setString(2,name);
			add.setString(3,designation);
			add.setObject(4,dob);
			add.executeUpdate();
			add=con.prepareStatement("update employeerecords set age=age(employeerecords.dob) where id='"+eId+"'");
			add.executeUpdate();
			add=con.prepareStatement("select * from employeerecords where id='"+eId+"'");
			result=add.executeQuery();
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}