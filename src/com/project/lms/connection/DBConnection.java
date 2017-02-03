package com.project.lms.connection;
import java.sql.*;

public class DBConnection 
{
  static Connection conn=null;	
  public static Connection getConnection()
  {
	  try
	  {
		  Class.forName("oracle.jdbc.driver.OracleDriver");
		  conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","mallikarjun","angadi");
	  }
	  catch(ClassNotFoundException cnfe)
	  {
		  cnfe.printStackTrace();
	  }
	  catch(SQLException e)
	  {
		  e.printStackTrace();
	  }
	return conn;
	  
  }
}