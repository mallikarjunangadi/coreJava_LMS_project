package com.project.lms.staff;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.project.lms.adminMenu.AdminMenu;
import com.project.lms.connection.DBConnection;

public class Staff 
{
	 Scanner scr=new Scanner(System.in);
	 Connection con=null;
	 PreparedStatement pstmt=null;
			 
	 public void create_staff() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	 try
	 {
	  System.out.println("-----------NEW STAFF ENTRY------------");
	  System.out.println("Enter The staff id no.");
	  int id=scr.nextInt();
	  System.out.println("Enter The Name of The Staff");
	  String name=scr.next();
	  System.out.println("Enter The Department");
	  String dept=scr.next();
	  pstmt=con.prepareStatement("select * from staff where Staff_Id="+id+"");
	  int i=pstmt.executeUpdate();
	  if(i==0)
	  {
	  
	  String quary="insert into staff(Staff_Id,Staff_Name,Staff_Department,Card_Used) values('"+id+"','"+name+"','"+dept+"',0)";
	  PreparedStatement pstmt=con.prepareStatement(quary);
	  int r=pstmt.executeUpdate();
	   if(r>0)
	   {
	     System.out.println("Records syccessfully inserted");
	     System.out.println("do you want to insert more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 create_staff();
	     }
	     else
	     {
	    	 aobj.admin_menu();
	     }
	   }
	   else
	   {
	     System.out.println("no records inserted"); 
	   }
	  }
	  else
	   {
	     System.out.println("id already exists"); 
	     create_staff();
	   }
	 }
	 catch(InputMismatchException ime)
	 { 
		 System.out.println("enter valid number");
		 aobj.admin_menu();
	 }
	 finally
		{  try
		   {
			 if(!(pstmt==null)){pstmt.close();}
		     if(!(con==null)){con.close();}
		   }catch(Exception e){e.printStackTrace();}  
		}
	  return;
	 }

	//------------------------------------------------

	 public void display_allstaff() throws IOException,SQLException,NullPointerException
	 { 
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	  try
	  {
	  System.out.println("-----------------ALL STAFF DETAILS----------------- ");
	  pstmt=con.prepareStatement("select * from staff");
	  ResultSet rs=pstmt.executeQuery();
	  while(rs.next())
	  {
	    int id=rs.getInt(1);
	    String name=rs.getString(2);
	    String dept=rs.getString(3);
	    int cardUsed=rs.getInt(4);

	    System.out.println("Staff Id: "+id);        
	    System.out.println("Staff Name: "+name);
	    System.out.println("department: "+dept);
	    System.out.println("Cards Used: "+cardUsed);
            System.out.println();
	   }
	    System.out.println("press any key to menu option");
	     char c=scr.next().charAt(0);
	     if(c=='m'||c=='M')
	     {
	    	 aobj.admin_menu();
	     }
	     else
	     {
	    	 aobj.admin_menu();
	     }
	    }
		 catch(InputMismatchException ime)
		 { 
			 System.out.println("enter valid number");
			 aobj.admin_menu();
		 }
	  finally
		{  try
		   {
			 if(!(pstmt==null)){pstmt.close();}
		     if(!(con==null)){con.close();}
		   }catch(Exception e){e.printStackTrace();}  
		}
	  return;
	 }

	//-----------------------------------------

	 public void display_spstaff() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	 try
	 {
	  System.out.println("-------------------STAFF DETAILS-----------------");
	  System.out.println("Please Enter staff ID No.");
	  int id=scr.nextInt();   
	  pstmt=con.prepareStatement("select * from staff where Staff_Id=?");
	  pstmt.setInt(1,id);
	  int r=pstmt.executeUpdate();
	   if(r>0)
	   {
	    ResultSet rs=pstmt.executeQuery();
	    while(rs.next())
	    {
	    id=rs.getInt(1);
	    String name=rs.getString(2);
	    String dept=rs.getString(3);
	    int cardUsed=rs.getInt(4);

	    System.out.println("Staff id: "+id);
	    System.out.println("Staff name: "+name); 
	    System.out.println("department: "+dept); 
	    System.out.println("Cards Used: "+cardUsed);
	    } 
	    System.out.println("do you want to view more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 display_spstaff();
	     }
	     else
	     {
	    	 aobj.admin_menu();
	     }
	   }
	   else
	   {
	     System.out.println("id no. not exist");
	     aobj.admin_menu();
	   }
	 }
	 catch(InputMismatchException ime)
	 { 
		 System.out.println("enter valid number");
		 aobj.admin_menu();
	 }
	 finally
		{  try
		   {
			 if(!(pstmt==null)){pstmt.close();}
		     if(!(con==null)){con.close();}
		   }catch(Exception e){e.printStackTrace();}  
		}
	  return;   
	 }

	//-----------------------------------------

	 public void modify_staff() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	  try
	  {
	  System.out.println("----------------UPDATE STAFF RECORD----------------");
	  System.out.println("Enter the staff ID No.");    
	  int id=scr.nextInt();
	  System.out.println("Enter The new Name of The Staff");
	  String name=scr.next();
	  System.out.println("Enter The Department");
	  String dept=scr.next();
	  System.out.println("Reset cards Used");
	  int cardsUsed=scr.nextInt();
	  
	  String quary="update staff set Staff_Name='"+name+"',Staff_Department='"+dept+"',Card_Used='"+cardsUsed+"' WHERE Staff_Id="+id+"";
	  pstmt=con.prepareStatement(quary);
	  int r=pstmt.executeUpdate();
	  if(r>0)
	   {
	     System.out.println("Records syccessfully updated");
	     System.out.println("do you want to view more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 modify_staff();
	     }
	     else
	     {
	    	 aobj.admin_menu();
	     }
	   }
	   else
	   {
	     System.out.println("Id no. not exists"); 
	   }
	  }
		 catch(InputMismatchException ime)
		 { 
			 System.out.println("enter valid number");
			 aobj.admin_menu();
		 }
	  finally
		{  try
		   {
			 if(!(pstmt==null)){pstmt.close();}
		     if(!(con==null)){con.close();}
		   }catch(Exception e){e.printStackTrace();}  
		}
	  return;
	 }

	//----------------------------------------------

	 public void delete_staff() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	 try
	 {
	  System.out.println("Enter the staff ID No. to delete record"); 
	  int id=scr.nextInt();
	  pstmt=con.prepareStatement("delete from staff where Staff_Id=?");
	  pstmt.setInt(1,id);
	  int r=pstmt.executeUpdate();
	  if(r>0)
	   {
	     System.out.println("Records syccessfully deleted");
	     System.out.println("do you want to delete more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 delete_staff();
	     }
	     else
	     {
	    	 aobj.admin_menu();
	     }
	   }
	   else
	   {
	     System.out.println("Id no. not exists"); 
	   }
	 }
	 catch(InputMismatchException ime)
	 { 
		 System.out.println("enter valid number");
		 aobj.admin_menu();
	 }
	 finally
		{  try
		   {
			 if(!(pstmt==null)){pstmt.close();}
		     if(!(con==null)){con.close();}
		   }catch(Exception e){e.printStackTrace();}  
		}
	  return; 
	 }


}