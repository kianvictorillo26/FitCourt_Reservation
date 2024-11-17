
package court_reservation;

import java.util.Scanner;

public class Schedule {
    
    public void sTransaction(){
         Scanner sc = new Scanner(System.in);
        String response;
        do{
            System.out.println("\n===================== SCHEDULE PANEL =====================");        
            System.out.println("");
            System.out.println("1. ADD SCHEDULE");
            System.out.println("2. VIEW SCHEDULES");
            System.out.println("3. EDIT SCHEDULES");
            System.out.println("4. DELETE SCHEDULE");
            System.out.println("5. BACK TO MAIN MENU");


            System.out.print("Enter a selection: ");
            int act = sc.nextInt();

            Schedule ss = new Schedule();
        switch(act){

            case 1:
                ss.addSchedules();
               
                break;
            case 2:
                ss.viewSchedules();
                
                break;
            case 3:
                ss.viewSchedules();
                ss.updateSchedules();
                ss.viewSchedules();
             
                break;
            case 4:
                ss.viewSchedules();
                ss.deleteSchedules();
                ss.viewSchedules();
                break;
            case 5:
                break;
           
            }
            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
        }while(response.equalsIgnoreCase("yes"));
    }
    
   public void addSchedules(){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Schedule Date: ");
        String sdate = sc.nextLine();
        
        System.out.print("Schedule Time: ");
        String stime = sc.next();
        
        System.out.print("Schedule Price: ");
        double sprice = sc.nextDouble();
        
        String qry = "INSERT INTO tbl_schedules ( s_date, s_time, s_price) VALUES (?,?,?)";
        config conf = new config();
        conf.addRecord(qry,sdate, stime, sprice);
   
   }
   
  public void viewSchedules(){
     String qry = "SELECT * FROM tbl_schedules";
        String[] hrds = {"ID", "Schedule_Date","Schedule_Time", "Schedule_Price"};
        String[] clms = {"s_id","s_date", "s_time", "s_price"};
        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
        
  }
  
  private void updateSchedules(){
         Scanner sc = new Scanner(System.in);
         config conf = new config();
         System.out.print("Enter Schedule ID to update: ");
         int id = sc.nextInt();
         
         while(conf.getSingleValue("SELECT s_id FROM tbl_schedules WHERE s_id = ?",id) == 0){
         System.out.println("Selected Schedule ID doesn't exist");
         System.out.println("Select Schedule ID Again: ");
         id = sc.nextInt();
         }
        
         System.out.print("New Schedule Date: ");
         String sdate = sc.next();     
         
         System.out.print("New Schedule Time: ");
         String stime = sc.next();
         
         System.out.print("New Schedule Price: ");
         String sprice = sc.next();
         
         String qry = "UPDATE tbl_schedules SET s_date = ?,s_time = ?, s_price = ? WHERE s_id = ?";
         
         conf.updateRecord(qry, sdate, stime, sprice, id);
         
     
  }
  
  private void deleteSchedules(){
      
         Scanner sc = new Scanner(System.in);
          config conf = new config();
         System.out.println("Enter Schedule ID to Delete: ");
         int id = sc.nextInt();
         
         while(conf.getSingleValue("SELECT s_id FROM tbl_schedules WHERE s_id = ?",id) == 0){
         System.out.println("Selected Schedule ID doesn't exist");
         System.out.print("Select Schedule ID Again: ");
         id = sc.nextInt();
         }
         
         String qry = "DELETE FROM tbl_schedule WHERE s_id = ?";
         conf.deleteRecord(qry, id);
         
  
  }
  
  
   
}