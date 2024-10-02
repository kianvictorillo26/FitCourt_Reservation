
package court_reservation;

import java.util.Scanner;

public class IT2EVICTORILLOFCR {
    
    
     public void addCustomer(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("customer First Name: ");
        String fname = sc.next();
        System.out.print("cutomer address: ");
        String address = sc.next();
        System.out.print("customer phone number: ");
        String phone_number = sc.next();
        System.out.print("Student Status: ");
        String status = sc.next();

        String sql = "INSERT INTO tbl_customers (c_fname, c_address, c_phone_number, c_status) VALUES (?, ?, ?, ?)";


        conf.addRecord(sql, fname, address, phone_number, status);


    }
     
     private void viewCustomers() {
        String votersQuery = "SELECT * FROM tbl_customers";
        String[] votersHeaders = {"ID", "fname", "address", "phone_number", "Status"};
        String[] votersColumns = {"c_id", "c_fname", "c_address", "c_phone_number", "c_status"};
        config conf = new config();
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        
        System.out.println("1. ADD");
        System.out.println("2. VIEW");
        System.out.println("3. UPDATE");
        System.out.println("4. DELETE");
        System.out.println("5. EXIT");
        
        
        System.out.println(" Enter action: ");
        int action = sc.nextInt();
         IT2EVICTORILLOFCR test = new  IT2EVICTORILLOFCR();
        switch(action){
            case 1:
                 test.addCustomer();
                 
                 break;
                 
                 
            case 2:
                test.viewCustomers();
                
                
            break;
            
            
        }
    }
    
}
