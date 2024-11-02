
package court_reservation;

import java.util.Scanner;

public class Reservation {
    
    public void rTransaction(){
         Scanner sc = new Scanner(System.in);
        String response;
        do{
            System.out.println("\n-----------------------------");        
            System.out.println(" RESERVATION PANEL");
            System.out.println("1. ADD RESERVATION");
            System.out.println("2. VIEW RESERVATION");
            System.out.println("3. UPDATE RESERVATION");
            System.out.println("4. DELETE RESERVATION");
            System.out.println("5. EXIT");


            System.out.print("Enter a selection: ");
            int act = sc.nextInt();

            Reservation rs = new Reservation();
        switch(act){

            case 1:
                rs.addReservation();
               
                break;
            case 2:
                rs.viewReservation();
                
                break;
            case 3:
                rs.viewReservation();
                rs.updateReservation();
                rs.viewReservation();
             
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
    
   public void addReservation(){
        Scanner sc = new Scanner(System.in);
     
        System.out.print("Reservation Date: ");
        String rdate = sc.nextLine();
        
        System.out.print("Reservation Time: ");
        String rtime = sc.next();
        
        System.out.print("Reservation Price: ");
        double rprice = sc.nextDouble();
        
        System.out.print("Reservation Status: ");
        String rstatus = sc.next();
        
        String qry = "INSERT INTO tbl_reservation ( r_date, r_time, r_price, r_status) VALUES (?,?,?,?)";
        config conf = new config();
        conf.addRecord(qry, rdate, rtime, rprice, rstatus );
   
   }
   
  public void viewReservation(){
     String qry = "SELECT * FROM tbl_reservation";
        String[] hrds = {"ID", "Date","Time", "Price", "Status"};
        String[] clms = {"r_id", "r_date", "r_time", "r_price", "r_status"};
        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
        
  }
  
  private void updateReservation(){
         Scanner sc = new Scanner(System.in);
         config conf = new config();
         System.out.print("Enter ID to update: ");
         int id = sc.nextInt();
         
         while(conf.getSingleValue("SELECT r_id FROM tbl_reservation WHERE r_id = ?",id) == 0){
         System.out.println("Selectd ID doesn't exist");
         System.out.println("Select Reservation ID Again: ");
         id = sc.nextInt();
         }
         
         System.out.print("New Date: ");
         String rdate = sc.nextLine();         
         System.out.print("New Time: ");
         String rtime = sc.next();
         System.out.print("New Price: ");
         String rprice = sc.next();
         System.out.print("New Status: ");
         String rstatus = sc.next();
         
         String qry = "UPDATE tbl_reservation SET r_date = ?,r_time =?, r_price = ?, r_status = ? WHERE r_id = ?";
         
         conf.updateRecord(qry, rdate, rtime, rprice, rstatus, id);
         
     }
   
}