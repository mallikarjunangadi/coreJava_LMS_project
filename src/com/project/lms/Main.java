package com.project.lms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.project.lms.adminMenu.AdminMenu;
import com.project.lms.staffIssueReturn.StaffBookIssueDeposit;
import com.project.lms.studentIssueReturn.StudentBookIssueDeposit;

public class Main 
{	
	 
  public static void main(String arg[])
  {
	  AdminMenu aobj=new AdminMenu();
	  StudentBookIssueDeposit hobj=new StudentBookIssueDeposit();
	  StaffBookIssueDeposit sobj=new StaffBookIssueDeposit();
	  Scanner scr=new Scanner(System.in);
	try
    {
	System.out.println("MAIN MENU");
	System.out.println("1.STUDENT BOOK ISSUE");
	System.out.println("2.STUDENT BOOK RETURN");
	System.out.println("3.STAFF BOOK ISSUE");
	System.out.println("4.STAFF BOOK RETURN");
	System.out.println("5.ADMINISTRATOR MENU");
	System.out.println("Please Select Your Option (1-5)");
	int ch1=scr.nextInt();
	 
	switch(ch1)
	 {
	 case 1: 
	       hobj.sbook_issue();
	       break;
	 case 2: 
	       hobj.sbook_return();
	       break;
	 case 3: 
	       sobj.stfbook_issue();
	       break;
	 case 4: 
	      sobj.stfbook_return();
	       break;      
	 case 5:
	       aobj.admin_menu();
	       break;
	 case '6':
	     //  exit(0);
	 default:
	       System.out.println("please select valid option");
	       main(null);
	 }
   }
   catch(SQLException sqle)
   {
	   sqle.printStackTrace();
   }
   catch(IOException ioe)
   {
	   ioe.printStackTrace();
   }
   catch(NullPointerException npe)
   {
	   npe.printStackTrace();
   }
 
  }
}

