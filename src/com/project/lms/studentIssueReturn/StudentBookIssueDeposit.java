package com.project.lms.studentIssueReturn;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;

import com.project.lms.adminMenu.AdminMenu;
import com.project.lms.connection.DBConnection;
public class StudentBookIssueDeposit  
{   
    Scanner scr=new Scanner(System.in);
    Connection con=null;
    PreparedStatement pstmt1=null; 
    PreparedStatement pstmt2=null;
    PreparedStatement pstmt3=null;
    PreparedStatement pstmt4=null;
    PreparedStatement pstmt5=null;
    PreparedStatement pstmt6=null;
    String status=null;

  public void sbook_issue() throws SQLException,IOException,NullPointerException 
  {
	  AdminMenu aobj=new AdminMenu();
	  con=DBConnection.getConnection();
	try
	{
	  int cardUsed=0;
   System.out.println("--------------------------BOOK ISSUE-------------------------------");
   System.out.println("Enter The student ID no.");
   int stdid=scr.nextInt();
   pstmt1=con.prepareStatement("select * from student where Student_Id=?");
   pstmt1.setInt(1,stdid);
   int sfound=pstmt1.executeUpdate();
   ResultSet rs1=pstmt1.executeQuery();
         while(rs1.next())
         {
 	       cardUsed=rs1.getInt(5);
         }
         
   if(cardUsed<2)
   {
    
     if(sfound==1)
     {
       System.out.println("Enter The book ID no.");
       int bookid=scr.nextInt();
       pstmt3=con.prepareStatement("select * from book where Book_Id="+bookid+"");
       //pstmt3.setInt(1,bookid);
       int bfound=pstmt3.executeUpdate();
        
         if(bfound==1)
         {	    
          ResultSet rs3=pstmt3.executeQuery();

           while(rs3.next())
           {
    	    String status=rs3.getString(4);
            if(status.equals("A"))
            {
               String q="insert into student_issue_return (Student_Id,Book_ID,Issue_Date,Return_Date) values("+stdid+","+bookid+",sysdate,sysdate+15)";	 
               pstmt4=con.prepareStatement(q);
               pstmt4.executeUpdate();
               
               pstmt5=con.prepareStatement("update book set Book_Status='N' where Book_Id="+bookid+"");
               pstmt5.executeUpdate();
               
               cardUsed=cardUsed+1;
               pstmt2=con.prepareStatement("update student set Card_Used='"+cardUsed+"' where Student_Id="+stdid+"");
               pstmt2.executeUpdate();

               System.out.println("Book Issued Successfully... Return within 15 days to avoid fine");
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
        else{ System.out.println("book with ID doesn't exist"); }                   
       }
      else{ System.out.println("student with ID doesn't exist"); }  
     }
     else{ System.out.println("card limit exceeded"); }    
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
	     if(!(con==null)){con.close();}
	   }catch(Exception e){e.printStackTrace();}  
	}
         return;                            
  }                       

//-------------------------------------------------------------------------

   public void sbook_return() throws IOException,SQLException,NullPointerException
   {
	   AdminMenu aobj=new AdminMenu();
	   con=DBConnection.getConnection();
	  try
	  {
	   int cardUsed=0;
	   System.out.println("--------------------------STUDENT BOOK RETURN-------------------------------");
	   System.out.println("Enter The student ID no.");
	   int stdid=scr.nextInt();
	   pstmt1=con.prepareStatement("select * from student where Student_Id=?");
	   pstmt1.setInt(1,stdid);
	   int sfound=pstmt1.executeUpdate();
	   ResultSet rs1=pstmt1.executeQuery();
       while(rs1.next())
       {
	       cardUsed=rs1.getInt(5);
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
	             Date return_date = null;
	             pstmt3=con.prepareStatement("select * from student_issue_return where Student_Id="+stdid+" AND Book_Id="+bookid+" AND Fine_Paid_Status='NULL'");	 	  	   
	 	  	     int found=pstmt3.executeUpdate();
       	 	  	  
	               if(found>0)
	 	           {
	            	   ResultSet rs3=pstmt3.executeQuery();
	            	   while(rs3.next())
	                   {
	              	   return_date=rs3.getDate(4);
	                   }
	            	   
	        	 	   java.util.Date utildate=new Date();
	 	               java.sql.Date returned_date=new java.sql.Date(utildate.getTime());
	 	 	    
	               long diff=returned_date.getTime()-return_date.getTime();
	               long fineAmount=1*(diff/(1000*60*60*24));
	              
	               String q=null;
	               String paid="paid";
	               if(fineAmount>0)
	               {
	               q="update student_issue_return set Returned_Date=sysdate,Fine_Amount=?,Fine_Paid_Status='"+paid+"' where Student_Id="+stdid+" AND Book_Id="+bookid+"";	 
	               pstmt4=con.prepareStatement(q);
	               pstmt4.executeUpdate();
	               System.out.println("you have to pay Fine Rs."+fineAmount);
	 	           }
	               else
	               {   
	            	   q="update student_issue_return set Returned_Date=sysdate,Fine_Amount=0,Fine_Paid_Status='"+paid+"' where Student_Id="+stdid+" AND Book_Id="+bookid+"";	 
		               pstmt4=con.prepareStatement(q);
		               pstmt4.executeUpdate();
		               System.out.println("You Returned within 15 days... Rs.0 Fine ");
	               }   
	               pstmt5=con.prepareStatement("update book set Book_Status='A' where Book_Id="+bookid+"");
	               pstmt5.executeUpdate();
	               
	               cardUsed=cardUsed-1;
	               pstmt6=con.prepareStatement("update student set Card_Used='"+cardUsed+"' where Student_Id="+stdid+"");
	               pstmt6.executeUpdate();

	               System.out.println("Thank You");
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
	             else { System.out.println("this book is not issued to Student with Id");}
	            }
	            else { System.out.println("Book is not issued to anyone");}
	           }
	         }
	        else{ System.out.println("book with ID doesn't exist"); }                   
	       }
	      else{ System.out.println("student with ID doesn't exist"); }      
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
		     if(!(con==null)){con.close();}
		   }catch(Exception e){e.printStackTrace();}  
		 }	
	         return;                            
  
   }
   
}  