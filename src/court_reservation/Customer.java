
package court_reservation;

import java.util.Scanner;

public class Customer {
    
    public void cTransaction(){
        
        Scanner sc = new Scanner(System.in);
        String response;
        do{
            System.out.println("\n===================== CUSTOMERS PANEL =====================");
            System.out.println("");
            System.out.println("1. ADD CUSTOMER");
            System.out.println("2. VIEW CUSTOMER");
            System.out.println("3. EDIT CUSTOMER");
            System.out.println("4. REMOVE CUSTOMER");
            System.out.println("5. BACK TO MAIN MENU");


            System.out.print("Enter a selection: ");
            int act = sc.nextInt();

            Customer cs = new Customer();
        switch(act){

            case 1:
                cs.addCustomer();
                cs.viewCustomers();
                break;
            case 2:
                cs.viewCustomers();
                break;
            case 3:
                cs.viewCustomers();
                cs.updateCustomer();
                cs.viewCustomers();
                break;
            case 4:
                cs.viewCustomers();
                cs.deleteCustomer();
                cs.viewCustomers();
                break;
            case 5:
                break;
           
            }
            System.out.println("Do you want to continue? (yes/no): ");
            response = sc.next();
        }while(response.equalsIgnoreCase("yes"));
    }
    
    
 public void addCustomer(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Customer Full Name: ");
        String fname = sc.next();
        System.out.print("Cutomer Address: ");
        String address = sc.next();
        System.out.print("Customer Phone number: ");
        String phone_number = sc.next();
        System.out.print("Customer Status: ");
        String status = sc.next();

        String sql = "INSERT INTO tbl_customer (c_fname, c_address, c_phone_number, c_status) VALUES (?, ?, ?, ?)";


        conf.addRecord(sql, fname, address, phone_number, status);


    }
 
  public void viewCustomers() {
        String votersQuery = "SELECT * FROM tbl_customer";
        String[] votersHeaders = {"ID", "fname","address", "phone_number", "Status"};
        String[] votersColumns = {"c_id", "c_fname","c_address", "c_phone_number", "c_status"};
        config conf = new config();
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
        
    }
  
   private void updateCustomer(){
         Scanner sc = new Scanner(System.in);
         config conf = new config();
         System.out.print("Enter ID to update: ");
         int id = sc.nextInt();
         
         while(conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?",id) == 0){
         System.out.println("Selectd ID doesn't exist");
         System.out.println("Select Customers ID Again: ");
         id = sc.nextInt();
         }
         
         System.out.print("New First Name: ");
         String fname = sc.next();         
         System.out.print("New Address: ");
         String address = sc.next();
         System.out.print("New Phone Number: ");
         int phone_number = sc.nextInt();
         System.out.print("New Status: ");
         String status = sc.next();
         
         String qry = "UPDATE tbl_customer SET c_fname = ?, c_address = ?, c_phone_number = ?, c_status = ? WHERE c_id = ?";
         
         
         conf.updateRecord(qry, fname, address, phone_number, status, id);
         
     }
   
    
     private void deleteCustomer(){
         Scanner sc = new Scanner(System.in);
          config conf = new config();
         System.out.println("Enter ID to delete: ");
         int id = sc.nextInt();
         
         while(conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?",id) == 0){
         System.out.println("Selectd ID doesn't exist");
         System.out.print("Select Customers ID Again: ");
         id = sc.nextInt();
         }
         
         String qry = "DELETE FROM tbl_customer WHERE c_id = ?";
         conf.deleteRecord(qry, id);
         
     }
}


