package court_reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Reservation {

    public void rTransaction() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n===================== RESERVATION PANEL=====================");
            System.out.println("");
            System.out.println("1. ADD RESERVATION");
            System.out.println("2. VIEW RESERVATION");
            System.out.println("3. EDIT RESERVATION");
            System.out.println("4. DELETE RESERVATION");
            System.out.println("5. BACK");

            System.out.print("Enter a selection: ");
            int act = sc.nextInt();

           
            Reservation rs = new Reservation();
            switch (act) {
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
                    rs.viewReservation();
                    rs.deleteReservation();
                    rs.viewReservation();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid selection! Please try again.");
            }
            System.out.println("Do you want to continue? (yes/no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
    }

   private boolean isScheduleBooked(int sid) {
    config conf = new config();
    String query = "SELECT COUNT(*) FROM tbl_reservation WHERE s_id = ? AND r_status <> 'Cancelled'";
    return conf.getSingleValue(query, sid) > 0;
}

private void addReservation() {
    Scanner sc = new Scanner(System.in);

    config conf = new config();
    Customer cs = new Customer();
    cs.viewCustomers();

    System.out.print("Enter the ID of the Customer: ");
    int cid = sc.nextInt();

    String csql = "SELECT c_id FROM tbl_customer WHERE c_id = ?";
    while (conf.getSingleValue(csql, cid) == 0) {
        System.out.print("Customer doesn't exist, Select again: ");
        cid = sc.nextInt();
    }

    Schedule ss = new Schedule();
    ss.viewSchedules();

    System.out.print("Enter the ID of the Schedule: ");
    int sid = sc.nextInt();

    String ssql = "SELECT s_id FROM tbl_schedules WHERE s_id = ?";
    while (conf.getSingleValue(ssql, sid) == 0) {
        System.out.print("Schedule doesn't exist, Select again: ");
        sid = sc.nextInt();
    }

    // Check if the schedule is already booked
    if (isScheduleBooked(sid)) {
        System.out.println("This schedule is already booked. Please select a different schedule.");
        return; // Exit the method if the schedule is booked
    }

    String spriceqry = "SELECT s_price FROM tbl_schedules WHERE s_id = ?";
    double sprice = conf.getSingleValue(spriceqry, sid);

    System.out.println("-------------------------------------");
    System.out.println("Total Due:  " + sprice);
    System.out.println("-------------------------------------");

    System.out.print("Enter the received cash: ");
    double rcash = sc.nextDouble();

    while (rcash < sprice) {
        System.out.print("Invalid Amount, Please Try Again: ");
        rcash = sc.nextDouble();
    }

    // Calculate change
    double change = rcash - sprice;

    LocalDate currdate = LocalDate.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String date = currdate.format(format);

    String paymentStatus = (rcash >= sprice) ? "Paid" : "Not Paid";
    String status = "Pending"; 

    String reservationqry = "INSERT INTO tbl_reservation (c_id, s_id, r_status, payment_status) "
            + "VALUES(?,?,?,?)";

    conf.addRecord(reservationqry, cid, sid, status, paymentStatus);

    System.out.println("Reservation added successfully.");
    System.out.println("Payment Status: " + paymentStatus);
    System.out.println("Change to be returned: ₱" + change); // Display change amount
}
    
    public void viewReservation() {
        String votersQuery = "SELECT r.r_id, c.c_fname, s.s_price, s.s_date, s.s_time, r.r_status, r.payment_status "
                + "FROM tbl_reservation r "
                + "JOIN tbl_customer c ON c.c_id = r.c_id "
                + "JOIN tbl_schedules s ON s.s_id = r.s_id";

        String[] votersHeaders = {"Reservation ID", "Customer Name", "Schedule Price", "Schedule Date", "Schedule Time", "Reservation Status", "Payment Status"};
        String[] votersColumns = {"r_id", "c_fname", "s_price", "s_date", "s_time", "r_status", "payment_status"};

        config conf = new config();
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
    }

    
    public void updateReservation() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter Reservation ID to update: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT r_id FROM tbl_reservation WHERE r_id = ?", id) == 0) {
            System.out.println("Selected Reservation ID doesn't exist");
            System.out.print("Select Reservation ID Again: ");
            id = sc.nextInt();
        }

        System.out.print("New Reservation Status: ");
        String rstatus = sc.next();

        
        System.out.print("New Payment Status (Paid/Not Paid): ");
        String paymentStatus = sc.next();

        String qry = "UPDATE tbl_reservation SET r_status = ?, payment_status = ? WHERE r_id = ?";

        conf.updateRecord(qry, rstatus, paymentStatus, id);
        
        System.out.println("Reservation updated successfully!");
    }

    
    private void deleteReservation() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.println("Enter Reservation ID to delete: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT r_id FROM tbl_reservation WHERE r_id = ?", id) == 0) {
            System.out.println("Selected Reservation ID doesn't exist");
            System.out.print("Select Reservation ID Again: ");
            id = sc.nextInt();
        }

        String qry = "DELETE FROM tbl_reservation WHERE r_id = ?";
        conf.deleteRecord(qry, id);
         System.out.println("Reservation deleted successfully!");
    }
}
