
package court_reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Schedules {
    
    public void sTransaction(){
    
        Scanner sc = new Scanner(System.in);
        String response;
        do{
            System.out.println("\n----------------------------------------------");        
            System.out.println(" SCHEDULES PANEL");
            System.out.println("1. ADD SCHEDULE");
            System.out.println("2. VIEW SCHEDULE");
            System.out.println("3. UPDATE SCHEDULE");
            System.out.println("4. DELETE SCHEDULE ");
            System.out.println("5. EXIT");


            System.out.print("Enter a selection: ");
            int act = sc.nextInt();

            Schedules ss = new Schedules();
        switch(act){

            case 1:
               ss.addSchedules();
                break;
            case 2:
                ss.viewSchedules();
                
                break;
            case 3:
                
                break;
            case 4:
               
                break;
            case 5:
                break;
           
            }
            System.out.println("Do you want to continue? (yes/no): ");
            response = sc.next();
        }while(response.equalsIgnoreCase("yes"));
    
    
    
    }
   
    
 private void addSchedules(){
     Scanner sc = new Scanner(System.in);
     
     config conf = new config();
     Customer cs = new Customer();
     cs.viewCustomers();
 
     System.out.print("Enter the ID of the Customer: ");
     int cid = sc.nextInt();
  
     String csql = "SELECT c_id FROM tbl_customer WHERE c_id = ?";
     while(conf.getSingleValue(csql, cid)== 0){
     
         System.out.print("Customer doesn't exist, Select again: ");
         cid = sc.nextInt();
     }
 
     Reservation rs = new Reservation();
     rs.viewReservation();
     
    System.out.print("Enter the ID of the Reservation: ");
    int rid = sc.nextInt();

    String rsql = "SELECT r_id FROM tbl_reservation WHERE r_id = ? ";
    while(conf.getSingleValue(rsql, rid)== 0){

        System.out.print("Reservation doesn't exist, Select again: ");
        rid = sc.nextInt();
 
 
     }
        System.out.print("Enter Quantity: ");
        double quantity = sc.nextDouble();
        
        String rpriceqry = "SELECT r_price FROM tbl_reservation WHERE r_id = ?";
        double rprice = conf.getSingleValue(rpriceqry, rid);
        double due = rprice * quantity;
        
        System.out.println("-------------------------------------");
        System.out.println("Total Due:  "+ due);
        System.out.println("-------------------------------------");

    
        System.out.println("");
        
        
        System.out.print("Enter the received cash: ");
        double rcash = sc.nextDouble();
    
        while(rcash < due){
            System.out.print("Invalid Amount, Please Try Again: ");
            rcash = sc.nextDouble();
        }    
            
        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = currdate.format(format);
        
        String status = "Pending";
        
        String schedulesqry = "INSERT INTO tbl_schedule (c_id, r_id, s_quantity, s_due, s_rcash, s_date, s_status) "
                + "VALUES(?,?,?,?,?,?,?)";
        
        conf.addRecord(schedulesqry, cid, rid, quantity, due, rcash, date, status);
        
           
        
    }
    
  public void viewSchedules() {
        String votersQuery = "SELECT s_id, c_fname, r_name, s_due, s_date, s_status FROM tbl_schedule "
                + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_schedule.c_id "
                + "LEFT JOIN tbl_reservation ON tbl_reservation.r_id = tbl_schedule.r_id";
        
        String[] votersHeaders = {"SID", "Name", "Reservation","Due", "Date", "Status"};
        String[] votersColumns = {"s_id", "c_fname","r_name", "s_due", "s_date", "s_status"};
        config conf = new config();
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
        
    }
}