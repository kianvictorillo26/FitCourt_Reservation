
package court_reservation;

import java.util.Scanner;

public class IT2EVICTORILLOFCR {
    
    public static void main (String[]args){
        
    
    Scanner sc = new Scanner(System.in);
    
    boolean exit = true;
    do{
        System.out.println("\n-------------------------------------------------------------------------------------------------");        

        System.out.println("WELCOME TO FIT COURT RESERVATION");
        System.out.println("");
        System.out.println("1. CUSTOMER");   
        System.out.println("2. RESERVATION");   
        System.out.println("3. SCHEDULES");   
        System.out.println("4. REPORTS");   
        System.out.println("5. EXIT");   

        System.out.print("Enter Action: ");   
        int action = sc.nextInt();

        switch(action){

            case 1:
                Customer cs = new Customer();
                cs.cTransaction();
                break;
            case 2:
                Reservation rs = new Reservation();
                rs.rTransaction();
                break;
            case 3:
                Schedules ss = new Schedules();
                ss.sTransaction();
                break;
            case 4:
                break;
            case 5:
                System.out.print("Exit Selected... type 'yes' to continue: ");
                String resp = sc.next();
                if(resp.equalsIgnoreCase("yes")){
                exit = false;
                }
                break;

        }
    }while(exit);    
    }  
}
