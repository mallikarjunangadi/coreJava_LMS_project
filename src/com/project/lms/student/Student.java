package com.project.lms.student;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.project.lms.adminMenu.AdminMenu;
import com.project.lms.connection.DBConnection;

public class Student 
{  	
	 Scanner scr=new Scanner(System.in);
	 Connection con=null;
	 PreparedStatement pstmt=null;
	 
	 public void create_student() throws SQLException,IOException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection();  
	 try
	 {
	  System.out.println("-----------NEW STUDENT ENTRY------------");
	  System.out.println("Enter The id no.");
	  int id=scr.nextInt();
	  System.out.println("Enter The Name of The Student");
	  String name=scr.next();
	  System.out.println("Enter The Course");
	  String course=scr.next();
	  System.out.println("Enter The semister");
	  int sem=scr.nextInt();
	  pstmt=con.prepareStatement("select * from student where Student_Id="+id+"");
	  int i=pstmt.executeUpdate();
	  if(i==0)
	  {	  
	  String quary="insert into student(Student_Id,Student_Name,Course_Name,Semister,Card_Used) values('"+id+"','"+name+"','"+course+"','"+sem+"',0)";
	  pstmt=con.prepareStatement(quary);
	  int r=pstmt.executeUpdate();
	   if(r>0)
	   {
	     System.out.println("Records syccessfully inserted");
	     System.out.println("do you want to insert more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 create_student();
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
	     create_student();
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

	 public void display_alls() throws IOException,SQLException,NullPointerException
	 { 
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection();  
	  try
	  { 
	  System.out.println("-----------------ALL STUDENTS DETAILS----------------- ");
	  pstmt=con.prepareStatement("select * from student");
	  ResultSet rs=pstmt.executeQuery();
	  while(rs.next())
	  {
	    int id=rs.getInt(1);
	    String name=rs.getString(2);
	    String course=rs.getString(3);
	    int sem=rs.getInt(4);
	    int cardsUsed=rs.getInt(5);

	    System.out.println("Student Id: "+id);        
	    System.out.println("Student Name: "+name);
	    System.out.println("course: "+course);
	    System.out.println("semister: "+sem);
	    System.out.println("Cards Used: "+cardsUsed);
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

	 public void display_spstudent() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	  try
	  {
	  System.out.println("-------------------STUDENT DETAILS-----------------");
	  System.out.println("Please Enter student ID No.");
	  int id=scr.nextInt();   
	  pstmt=con.prepareStatement("select * from student where Student_Id=?");
	  pstmt.setInt(1,id);
	  int r=pstmt.executeUpdate();
	   if(r>0)
	   {
	    ResultSet rs=pstmt.executeQuery();
	    while(rs.next())
	    {
	    id=rs.getInt(1);
	    String name=rs.getString(2);
	    String course=rs.getString(3);
	    int sem=rs.getInt(4);
	    int cardsUsed=rs.getInt(5);

	    System.out.println("Student id: "+id);
	    System.out.println("Student name: "+name); 
	    System.out.println("course: "+course);
	    System.out.println("semister: "+sem);
	    System.out.println("Cards Used: "+cardsUsed);
	    }
	    System.out.println("do you want to view more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 display_spstudent();
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

	 public void modify_student() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	 try
	 {
	  System.out.println("----------------UPDATE STUDENT RECORD----------------");
	  System.out.println("Enter the student ID No.");    
	  int id=scr.nextInt();
	  System.out.println("Enter The new Name of The Student");
	  String name=scr.next();
	  System.out.println("Enter The Course");
	  String course=scr.next();
	  System.out.println("Enter The semister");
	  int sem=scr.nextInt();
	  System.out.println("Reset cards Used");
	  int cardsUsed=scr.nextInt();
	  String quary="update student set Student_Name='"+name+"',Course_Name='"+course+"',Semister='"+sem+"',Card_Used='"+cardsUsed+"' where Student_Id="+id+"";
	  pstmt=con.prepareStatement(quary);
	  int r=pstmt.executeUpdate();
	  if(r>0)
	   {
	     System.out.println("Records syccessfully updated");
	     System.out.println("do you want to modify more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 modify_student();
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

	 public void delete_student() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	 try
	 {
	  System.out.println("Enter the student ID No. to delete record"); 
	  int id=scr.nextInt();
	  pstmt=con.prepareStatement("delete from student where Student_Id=?");
	  pstmt.setInt(1,id);
	  int r=pstmt.executeUpdate();
	  if(r>0)
	   {
	     System.out.println("Records syccessfully deleted");
	     System.out.println("do you want to delete more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 delete_student();
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