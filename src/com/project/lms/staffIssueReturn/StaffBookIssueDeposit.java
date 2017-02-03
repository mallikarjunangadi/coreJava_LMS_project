package com.project.lms.staffIssueReturn;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.project.lms.adminMenu.AdminMenu;
import com.project.lms.connection.DBConnection;

public class StaffBookIssueDeposit  
{   
    Scanner scr=new Scanner(System.in);
    Connection con=null;;
    PreparedStatement pstmt1=null; 
    PreparedStatement pstmt2=null;
    PreparedStatement pstmt3=null;
    PreparedStatement pstmt4=null;
    PreparedStatement pstmt5=null;
    PreparedStatement pstmt6=null;
    

  public void stfbook_issue() throws SQLException,IOException,NullPointerException 
  {
	  AdminMenu aobj=new AdminMenu();
	  con=DBConnection.getConnection(); 
	try
	{
	  int cardUsed=0;
   System.out.println("--------------------------BOOK ISSUE-------------------------------");
   System.out.println("Enter The staff ID no.");
   int stfid=scr.nextInt();
   pstmt1=con.prepareStatement("select * from staff where Staff_Id='"+stfid+"'");
   int sfound=pstmt1.executeUpdate();
   ResultSet rs1=pstmt1.executeQuery();
         while(rs1.next())
         {
 	       cardUsed=rs1.getInt(4);
         }
         
   if(cardUsed<=5)
   { 
    
     if(sfound==1)
     {
       System.out.println("Enter The book ID no.");
       int bookid=scr.nextInt();
       pstmt2=con.prepareStatement("select * from book where Book_Id='"+bookid+"'");
       int bfound=pstmt2.executeUpdate();
        
         if(bfound==1)
         {	    
          ResultSet rs2=pstmt2.executeQuery();

           while(rs2.next())
           {
 	        String status=rs2.getString(4);
 	        
            if(status.equals("A"))
            {
               String q="insert into staff_issue_return (Staff_Id,Book_ID,Issue_Date,Return_Date) values("+stfid+","+bookid+",sysdate,sysdate+15)";	 
               pstmt3=con.prepareStatement(q);
               pstmt3.executeUpdate();
               
               pstmt4=con.prepareStatement("update book set Book_Status='N' where Book_Id="+bookid+"");
               pstmt4.executeUpdate();
               
               cardUsed=cardUsed+1;
               pstmt5=con.prepareStatement("update staff set Card_Used='"+cardUsed+"' where Staff_Id="+stfid+"");
               pstmt5.executeUpdate();
               
               System.out.println("Book Issued Successfully");
               System.out.println();
               System.out.println("enter any key to Main menu");
      	       char c=scr.next().charAt(0);
               if(c=='y'||c=='Y')
      	       {
            	   aobj.admin_menu();
      	       }
      	       else
      	       {
      	    	 aobj.admin_menu();
      	       }
            }
            else { System.out.println("Book not available");}
           }
         }
        else{ System.out.println("Book with ID doesn't exist"); }                   
       }
      else{ System.out.println("Student with ID doesn't exist"); }  
     }
     else{ System.out.println("Card limit exceeded"); }    
     }
	 catch(InputMismatchException ime)
	 { 
		 System.out.println("enter valid number");
		 aobj.admin_menu();
	 }
	finally
	{  try
	   {
		System.out.println("finally");
	   	 con.commit();  
	     if(!(pstmt1==null)){pstmt1.close();}
	     if(!(pstmt2==null)){pstmt2.close();}
	     if(!(pstmt3==null)){pstmt3.close();}
	     if(!(pstmt4==null)){pstmt4.close();}
	     if(!(pstmt5==null)){pstmt5.close();}
	     if(!(con==null)){con.close();}
	   }catch(Exception e){e.printStackTrace();}  
	}
   
       return;                            
  }                       


//--------------------------------------------------------------------------------------

  public void stfbook_return() throws IOException,SQLException,NullPointerException
  {
	   AdminMenu aobj=new AdminMenu();
	   con=DBConnection.getConnection();
	  try
	  {
		   int cardUsed=0;
	   System.out.println("--------------------------STAFF BOOK RETURN-------------------------------");
	   System.out.println("Enter The staff ID no.");
	   int stfid=scr.nextInt();
	   pstmt1=con.prepareStatement("select * from staff where Staff_Id=?");
	   pstmt1.setInt(1,stfid);
	   int sfound=pstmt1.executeUpdate();
	   ResultSet rs1=pstmt1.executeQuery();
       while(rs1.next())
       {
	       cardUsed=rs1.getInt(4);
       }
	   
	     if(sfound==1)
	     {
	       System.out.println("Enter The book ID no.");
	       int bookid=scr.nextInt();
	       pstmt2=con.prepareStatement("select * from book where Book_Id=?");
	       pstmt2.setInt(1,bookid);
	       int bfound=pstmt2.executeUpdate();
	        
	        if(bfound==1)
	        {	    
	         ResultSet rs2=pstmt2.executeQuery();

		     while(rs2.next())
		     {
		      String status=rs2.getString(4);
		  
		       if(status.equals("N"))
		       {
	        	pstmt3=con.prepareStatement("select * from staff_issue_return where Book_Id="+bookid+" AND Staff_Id="+stfid+"");
	  	        int found=pstmt3.executeUpdate();
	  	        if(found==1)
	             {
	  	           java.util.Date utildate=new Date();
	 	           java.sql.Date returned_date=new java.sql.Date(utildate.getTime());
	  	        	
	               String q="update staff_issue_return set Returned_Date=sysdate where Book_Id="+bookid+" AND Staff_Id="+stfid+"";	               
	               pstmt4=con.prepareStatement(q);
	               pstmt4.executeUpdate();
	               
	               pstmt5=con.prepareStatement("update book set Book_Status='A' where Book_Id="+bookid+"");
	               pstmt5.executeUpdate();
	               
	               cardUsed=cardUsed-1;
	               pstmt6=con.prepareStatement("update staff set Card_Used='"+cardUsed+"' where Staff_Id="+stfid+"");
	               pstmt6.executeUpdate();
	               System.out.println("Book Returned Successfully");
	               System.out.println();
	               System.out.println("enter any key to Main menu");
	      	       char c=scr.next().charAt(0);
	               if(c=='y'||c=='Y')
	      	       {
	            	 aobj.admin_menu();
	      	       }
	      	       else
	      	       {
	      	    	 aobj.admin_menu();
	      	       }
	               
	             }
	            else { System.out.println("This book is not issued to Staff with this Id");}
		       }
	            else { System.out.println("Book with ID is not issued to anyone");}
		     }
		    }
	        else{ System.out.println("Book with ID doesn't exist"); }                   
	       }
	      else{ System.out.println("Student with ID doesn't exist"); }      
	     }
		 catch(InputMismatchException ime)
		 { 
			 System.out.println("enter valid number");
			 aobj.admin_menu();
		 }
		
	    finally
		 {  try
		   {
             con.commit();
		     if(!(pstmt1==null)){pstmt1.close();}
		     if(!(pstmt2==null)){pstmt2.close();}
		     if(!(pstmt3==null)){pstmt3.close();}
		     if(!(pstmt4==null)){pstmt4.close();}
		     if(!(pstmt5==null)){pstmt5.close();}
		     if(!(pstmt6==null)){pstmt6.close();}
		     if(!(con==null)){con.close();}
		   }catch(Exception e){e.printStackTrace();}  
		 }	
	         return;                            
    }
}