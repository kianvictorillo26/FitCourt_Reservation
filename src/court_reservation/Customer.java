
package court_reservation;

import java.util.Scanner;

public class Customer {
    
    public void cTransaction(){
        
        Scanner sc = new Scanner(System.in);
        String response;
        do{
            System.out.println("\n-----------------------------");        
            System.out.println(" CUSTOMER PANEL");
            System.out.println("1. ADD CUSTOMER");
            System.out.println("2. VIEW CUSTOMER");
            System.out.println("3. UPDATE CUSTOMER");
            System.out.println("4. DELETE CUSTOMER");
            System.out.println("5. EXIT");


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
        
        System.out.print("Customer First Name: ");
        String fname = sc.next();
         System.out.print("Customer Lirst Name: ");
        String lname = sc.next();
        System.out.print("Cutomer Address: ");
        String address = sc.next();
        System.out.print("Customer Phone number: ");
        String phone_number = sc.next();
        System.out.print("Customer Status: ");
        String status = sc.next();

        String sql = "INSERT INTO tbl_customers (c_fname, c_lname, c_address, c_phone_number, c_status) VALUES (?, ?, ?, ?, ?)";


        conf.addRecord(sql, fname,lname, address, phone_number, status);


    }
 
  private void viewCustomers() {
        String votersQuery = "SELECT * FROM tbl_customers";
        String[] votersHeaders = {"ID", "fname", "lname","address", "phone_number", "Status"};
        String[] votersColumns = {"c_id", "c_fname","c_lname", "c_address", "c_phone_number", "c_status"};
        config conf = new config();
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
        
    }
  
   private void updateCustomer(){
         Scanner sc = new Scanner(System.in);
         config conf = new config();
         System.out.print("Enter ID to update: ");
         int id = sc.nextInt();
         
         while(conf.getSingleValue("SELECT c_id FROM tbl_customers WHERE c_id = ?",id) == 0){
         System.out.println("Selectd ID doesn't exist");
         System.out.println("Select Customers ID Again: ");
         id = sc.nextInt();
         }
         
         System.out.print("New First Name: ");
         String fname = sc.next();         
         System.out.print("New Last Name: ");
         String lname = sc.next();
         System.out.print("New Address: ");
         String address = sc.next();
         System.out.print("New Phone Number: ");
         int phone_number = sc.nextInt();
         System.out.print("New Status: ");
         String status = sc.next();
         
         String qry = "UPDATE tbl_customers SET c_fname = ?,c_lname =?, c_address = ?, c_phone_number = ?, c_status = ? WHERE C_id = ?";
         
         
         conf.updateRecord(qry, fname,lname, address, phone_number, status, id);
         
     }
   
    
     private void deleteCustomer(){
         Scanner sc = new Scanner(System.in);
          config conf = new config();
         System.out.println("Enter ID to delete: ");
         int id = sc.nextInt();
         
         while(conf.getSingleValue("SELECT c_id FROM tbl_customers WHERE c_id = ?",id) == 0){
         System.out.println("Selectd ID doesn't exist");
         System.out.println("Select Customers ID Again: ");
         id = sc.nextInt();
         }
         
         String qry = "DELETE FROM tbl_customers WHERE c_id = ?";
         conf.deleteRecord(qry, id);
         
     }
}


