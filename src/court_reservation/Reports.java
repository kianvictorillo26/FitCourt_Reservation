package court_reservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Reports {
    Scanner scan = new Scanner(System.in);
    Customer cs = new Customer();
    Reservation rt = new Reservation();

    public void generateReports() {
        int opt;
        do {
            try {
                System.out.println("\n===================== REPORTS PANEL =====================");
                System.out.println("");
                System.out.println("1. RESERVATION REPORT");
                System.out.println("2. RESERVATION RECEIPT");
                System.out.println("3. BACK");

                System.out.print("\nEnter a Selection: ");
                opt = scan.nextInt();
                scan.nextLine();

                switch (opt) {
                    case 1:
                        generateReservationReport();
                        break;

                    case 2:
                        generateReservationReport();
                        generateReceipt();
                        break;

                    case 3:
                        System.out.println("\nGoing back to Main Menu...");
                        System.out.println("\n===================================================");
                        break;

                    default:
                        System.out.println("Invalid Selection. Please select a valid Selection.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();  
                opt = -1;  
            }
        } while (opt != 3); 
    }

   
   public void generateReservationReport() {
    String sql = "SELECT r.r_id AS reservation_id, c.c_fname AS customer_name, s.s_date AS reservation_date, " +
                 "s.s_time AS reservation_time, r.r_status AS reservation_status " +
                 "FROM tbl_reservation r " +
                 "JOIN tbl_customer c ON r.c_id = c.c_id " +
                 "JOIN tbl_schedules s ON r.s_id = s.s_id";  
    try (Connection conn = config.connectDB();  
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        
        if (!rs.next()) {
            System.out.println("No reservations found in the database.");
            return;
        }

        
        System.out.println("\n=====================  FIT COURT RESERVATION REPORTS =====================");
        System.out.println("");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("Reservation ID   | Customer Name       | Reservation Date     | Reservation Time     | Reservation Status");
        System.out.println("-----------------------------------------------------------------------------------------------------");

       
        do {
            int reservationId = rs.getInt("reservation_id");
            String customerName = rs.getString("customer_name");
            String reservationDate = rs.getString("reservation_date");
            String reservationTime = rs.getString("reservation_time");
            String reservationStatus = rs.getString("reservation_status");

            
            System.out.println(String.format("%-15d | %-20s | %-20s | %-20s | %-20s",
                    reservationId, customerName, reservationDate, reservationTime, reservationStatus));

        } while (rs.next());

        System.out.println("-----------------------------------------------------------------------------------------------------");

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error connecting to the database or querying data.");
    }
}

    
public void generateReceipt() {
    Scanner sc = new Scanner(System.in);
    System.out.print("\nEnter Reservation ID to Generate Receipt: ");
    int r_id = sc.nextInt();
    sc.nextLine();

    String sql = "SELECT c.c_fname, s.s_date, s.s_time, s.s_price, r.r_status " +
                 "FROM tbl_reservation r " +
                 "JOIN tbl_customer c ON r.c_id = c.c_id " +
                 "JOIN tbl_schedules s ON r.s_id = s.s_id " +
                 "WHERE r.r_id = ?";

    try (Connection conn = config.connectDB();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, r_id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String customerName = rs.getString("c_fname");
                String reservationDate = rs.getString("s_date");
                String reservationTime = rs.getString("s_time");
                int price = rs.getInt("s_price");
                String reservationStatus = rs.getString("r_status");

                LocalDate currdate = LocalDate.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String date = currdate.format(format);

                System.out.println("\n===================== FIIT COURT RESERVATION RECEIPT =====================");
                System.out.println("");
                System.out.println("=====================================================");
                System.out.println("RESERVATION DETAILS");
                System.out.println("Receipt Generated On: " + date);
                System.out.println("=====================================================");
                System.out.println("Customer Name: " + customerName);
                System.out.println("Reservation Date: " + reservationDate);
                System.out.println("Reservation Time: " + reservationTime);
                System.out.println("Price: ₱" + price);
                System.out.println("Reservation Status: " + reservationStatus);
                System.out.println("=====================================================");
            } else {
                System.out.println("Reservation ID not found.");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error generating receipt: " + e.getMessage());
        }
    }
}