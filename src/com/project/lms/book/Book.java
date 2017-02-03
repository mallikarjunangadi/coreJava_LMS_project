package com.project.lms.book;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.project.lms.adminMenu.AdminMenu;
import com.project.lms.connection.DBConnection;

public class Book 
{
	Scanner scr=new Scanner(System.in);
	Connection con=null;
	PreparedStatement pstmt=null;
	
	 public void create_book() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	  try
	  {
	  System.out.println("----------------NEW BOOK ENTRY------------------");
	  System.out.println("Enter The book Id.");
	  int bookid=scr.nextInt();
      System.out.println("Enter The Name of The Book");
	  String name=scr.next();
	  System.out.println("Enter The Author’s Name");
	  String author=scr.next();
	  pstmt=con.prepareStatement("select * from book where Book_Id="+bookid+"");
	  int i=pstmt.executeUpdate();
	  if(i==0)
	  {
	   String quary="insert into book(Book_ID,Book_Name,Author,Book_Status) values('"+bookid+"','"+name+"','"+author+"','A')";
	   pstmt=con.prepareStatement(quary);
	   int r=pstmt.executeUpdate();
	   if(r>0)
	   {
	     System.out.println("Records successfully inserted");
	     System.out.println("do you want to insert more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 create_book();
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
	     System.out.println("book id already exists"); 
	     create_book();
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

	//------------------------------------------------------

	 public void display_allbooks() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	 try
	 {
	  System.out.println("-----------------ALL BOOK DETAILS----------------- ");
	  pstmt=con.prepareStatement("select * from book");
	  ResultSet rs=pstmt.executeQuery();
	 
	  while(rs.next())
	  {
	    int id=rs.getInt(1);
	    String name=rs.getString(2);
	    String author=rs.getString(3);
	    String status=rs.getString(4);
	    
	    System.out.println("Book Id: "+id);
	    System.out.println("Book Name: "+name);        
	    System.out.println("author: "+author);
	    System.out.println("status: "+status);
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
	//----------------------------------------------------------

	 public void display_spbook() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	 try
	 {
	  System.out.println("-------------------BOOK DETAILS-----------------");
	  System.out.println("Please Enter book No.");
	  int bookid=scr.nextInt();   
	  pstmt=con.prepareStatement("select * from book where Book_Id=?");
	  pstmt.setInt(1,bookid);
	  int r=pstmt.executeUpdate();
	   if(r>0)
	   {
	    ResultSet rs=pstmt.executeQuery();
	    while(rs.next())
	    {
            bookid=rs.getInt(1);
	    String name=rs.getString(2);
	    String author=rs.getString(3);
	    String status=rs.getString(4);
	    
	    
	    System.out.println("Book Id: "+bookid);
	    System.out.println("author: "+name);
	    System.out.println("bookno: "+author);
	    System.out.println("status: "+status);
	    } 
	    System.out.println("do you want to view more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 display_spbook();
	     }
	     else
	     {
	    	 aobj.admin_menu();
	     }
	   }
	   else
	   {
	     System.out.println("book no. not exist");
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

	//-------------------------------------------------------

	 public void modify_book() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	  try
	  {
	  System.out.println("----------------UPDATE BOOK DETAILS----------------");
	  System.out.println("Enter the book No.");    
	  int bookno=scr.nextInt();
	  System.out.println("Enter The Name of The Book");
	  String name=scr.next();
	  System.out.println("Enter The Author’s Name");
	  String author=scr.next();
	  System.out.println("Enter The Book Status(A/N)");
	  String status=scr.next();
	  String quary="update book set Book_Name='"+name+"',Author='"+author+"',Book_Status='"+status+"' where Book_Id="+bookno+"";
	  pstmt=con.prepareStatement(quary);
	  int r=pstmt.executeUpdate();
	  if(r>0)
	   {
	     System.out.println("Records successfully updated");
	     System.out.println("do you want to modify more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 modify_book();
	     }
	     else
	     {
	    	 aobj.admin_menu();
	     }
	   }
	   else
	   {
	     System.out.println("no records updated"); 
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

	//-------------------------------------------------------

	 public void delete_book() throws IOException,SQLException,NullPointerException
	 {
		 AdminMenu aobj=new AdminMenu();
		 con=DBConnection.getConnection(); 
	 try
	 {
	  System.out.println("Enter the book No. to delete record"); 
	  int bookno=scr.nextInt();
	  pstmt=con.prepareStatement("delete from book where Book_Id=?");
	  pstmt.setInt(1,bookno);
	  int r=pstmt.executeUpdate();
	  if(r>0)
	   {
	     System.out.println("Records syccessfully deleted");
	     System.out.println("do you want to delete more records(y/n)");
	     char c=scr.next().charAt(0);
	     if(c=='y'||c=='Y')
	     {
	    	 delete_book();
	     }
	     else
	     {
	    	 aobj.admin_menu();
	     }
	   }
	   else
	   {
	     System.out.println("no records deleted"); 
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
