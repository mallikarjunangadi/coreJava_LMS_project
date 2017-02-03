package com.project.lms.adminMenu;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import com.project.lms.Main;
import com.project.lms.book.Book;
import com.project.lms.staff.Staff;
import com.project.lms.student.Student;

public class AdminMenu 
{
       Scanner scr=new Scanner(System.in);
	 
	   public void admin_menu() throws IOException, SQLException,NullPointerException
	   {
		   Student stobj=new Student();
		   Book bkobj=new Book();
		   Staff stfobj=new Staff();
		
	    try
	    {
		 System.out.println("----------ADMINISTRATOR MENU------------");
		 System.out.println("1.CREATE STUDENT RECORD");
		 System.out.println("2.DISPLAY ALL STUDENTS RECORD");
		 System.out.println("3.DISPLAY SPECIFIC STUDENT RECORD");
		 System.out.println("4.MODIFY STUDENT RECORD");
		 System.out.println("5.DELETE STUDENT RECORD");
		 System.out.println("6.CREATE STAFF");
		 System.out.println("7.DISPLAY ALL STAFF");
		 System.out.println("8.DISPLAY SPECIFIC STAFF");
		 System.out.println("9.MODIFY STAFF");
		 System.out.println("10.DELETE STAFF");
		 System.out.println("11.Add New Books");
		 System.out.println("12.Display All Books");
		 System.out.println("13.Display Specific Book");
		 System.out.println("14.Modify Book Details");
		 System.out.println("15.Delete Book");
		 System.out.println("16.BACK TO MAIN MENU");
		 System.out.println("Please Enter Your Choice (1-16)");
		 int i=scr.nextInt();
		
		 switch(i)
		 {
		 case 1: 
		       stobj.create_student();
		       break;
		 case 2:
		       stobj.display_alls();
		       break;
		 case 3:
			 stobj.display_spstudent();
		       break;
		 case 4: 
			 stobj.modify_student();
		       break;
		 case 5: 
			 stobj.delete_student();
		       break;
		 case 6: 
		       stfobj.create_staff();
		       break;
		 case 7: 
			 stfobj.display_allstaff();
		       break;
		 case 8:
			 stfobj.display_spstaff();
		       break;
		 case 9: 
			 stfobj.modify_staff();
		       break;
		 case 10: 
			 stfobj.delete_staff();
		       break;
		 case 11: 
		     bkobj.create_book();
		     break;
		 case 12: 
			 bkobj.display_allbooks();
		     break;
		 case 13:
			 bkobj.display_spbook();
		     break;
		 case 14: 
			 bkobj.modify_book();
		     break;
		 case 15: 
			 bkobj.delete_book();
		     break;      
		 case 16: 
		       Main.main(null);
		 default:
		       {
		       System.out.println("please select valid option");
		       admin_menu();
		       }
		  }
		 
	     }
		catch(InputMismatchException ime)
		 { 
			 System.out.println("invalid input");
			 Main.main(null);
		 }
	  }
}
