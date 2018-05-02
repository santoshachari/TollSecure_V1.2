package com.seoniproject.testdb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

/**
 * Servlet implementation class TestDb
 */
@WebServlet("/TestDb")
public class TestDb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//setup connection variables
		String user = "seoni_user";
		String pass = "seoni_password";
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/seoni_db?useSSL=false";
		String driver = "com.mysql.jdbc.Driver";
		
		//set connection to database
		try {
			PrintWriter out = response.getWriter();
			
			out.println("Connecting to databbase: "+jdbcUrl);
			
			Class.forName(driver);
			
			Connection myConne = DriverManager.getConnection(jdbcUrl, user, pass);
		
			out.println("SUCCESS!!");
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new ServletException(exc);
		}
		
		
	}

}
