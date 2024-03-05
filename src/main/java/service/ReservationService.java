package service;

import model.Reservation;
import util.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationService {
    private Connection connection;

    public ReservationService() {

        this.connection= DataSource.getInstance().getConnection();

    }

    public void addEntity(Reservation t) {
        try {
            String rq = "INSERT INTO reservation (id_user, id_cours, resStatus, date_reservation, id_codepromo, prixd, paidStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DataSource.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, t.getId_user());
            pst.setInt(2, t.getId_cours());
            pst.setBoolean(3, t.isResStatus());
            pst.setTimestamp(4, Timestamp.valueOf(t.getDateReservation()));
            pst.setInt(5, t.getId_codepromo());
            pst.setFloat(6, t.getPrixd());
            pst.setBoolean(7, t.isPaidStatus());
            pst.executeUpdate();
            System.out.println("Reservation has been added.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Reservation> displayEntity() {
        List<Reservation> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM reservation";
            Statement st = DataSource.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setId_cours(rs.getInt("id_cours"));
                r.setId_user(rs.getInt("id_user"));
                r.setId_codepromo(rs.getInt("id_codepromo"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                r.setResStatus(rs.getBoolean("resStatus"));
                r.setPrixd(rs.getFloat("prixd"));
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }


    public void updateEntity(Reservation t) {
        try {
            String rq = "UPDATE reservation SET id_user=?, id_cours =? , resStatus = ?, date_reservation = ?, paidStatus = ? WHERE id = ?";
            PreparedStatement pst = DataSource.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, t.getId_user());
            pst.setInt(2, t.getId_cours());
            pst.setBoolean(3, t.isResStatus());
            pst.setTimestamp(4, Timestamp.valueOf(t.getDateReservation()));
            pst.setBoolean(5, t.isPaidStatus());
            pst.setInt(6, t.getId());
            pst.executeUpdate();
            System.out.println("Reservation has been updated.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteEntity(int id) {
        try {
            String rq = "DELETE FROM reservation where id=?";
            PreparedStatement pst = DataSource.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Reservation has been deleted.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Reservation displayById(int id) {
        Reservation r = new Reservation();
        try {
            String rq = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement pst = DataSource.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                r.setId(rs.getInt("id"));
                r.setId_cours(rs.getInt("id_cours"));
                r.setId_user(rs.getInt("id_user"));
                r.setId_codepromo(rs.getInt("id_codepromo"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                r.setResStatus(rs.getBoolean("resStatus"));
                r.setPrixd(rs.getFloat("prixd"));
                r.setPaidStatus(rs.getBoolean("paidStatus"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    public List<Reservation> myReservation(int user_id) {
        List<Reservation> myList = new ArrayList<>();
        try {
            String rq = "SELECT * FROM reservation WHERE id_user = ?";
            PreparedStatement pst = DataSource.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setId_cours(rs.getInt("id_cours"));
                r.setId_user(rs.getInt("id_user"));
                r.setId_codepromo(rs.getInt("id_codepromo"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                r.setResStatus(rs.getBoolean("resStatus"));
                r.setPrixd(rs.getFloat("prixd"));
                r.setPaidStatus(rs.getBoolean("paidStatus"));
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }


    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation";

        try {
            Statement st = DataSource.getInstance().getConnection().createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_user = resultSet.getInt("id_user");
                int id_cours = resultSet.getInt("id_cours");
                boolean resStatus = resultSet.getBoolean("resStatus");
                LocalDateTime dateReservation = resultSet.getTimestamp("date_reservation").toLocalDateTime();
                int id_codepromo = resultSet.getInt("id_codepromo");
                float prixd = resultSet.getFloat("prixd");
                boolean paidStatus = resultSet.getBoolean("paidStatus");

                Reservation reservation = new Reservation();
                reservation.setId(id);
                reservation.setId_user(id_user);
                reservation.setId_cours(id_cours);
                reservation.setResStatus(resStatus);
                reservation.setDateReservation(dateReservation);
                reservation.setId_codepromo(id_codepromo);
                reservation.setPrixd(prixd);
                reservation.setPaidStatus(paidStatus);
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addPaidStatus(int reservationId, boolean paidStatus) {
        try {
            String rq = "UPDATE reservation SET paidStatus = ? WHERE id = ?";
            PreparedStatement pst = DataSource.getInstance().getConnection().prepareStatement(rq);
            pst.setBoolean(1, paidStatus);
            pst.setInt(2, reservationId);
            pst.executeUpdate();
            System.out.println("Paid status has been updated.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Reservation> getReservationsByStatus(boolean resStatus) {
        List<Reservation> filteredReservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation WHERE resStatus = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setBoolean(1, resStatus);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setId_cours(rs.getInt("id_cours"));
                r.setId_user(rs.getInt("id_user"));
                r.setId_codepromo(rs.getInt("id_codepromo"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                r.setResStatus(rs.getBoolean("resStatus"));
                r.setPrixd(rs.getFloat("prixd"));
                r.setPaidStatus(rs.getBoolean("paidStatus"));
                filteredReservations.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return filteredReservations;
    }



        public List<Reservation> getReservationsByStatusAndUser(boolean paidStatus, int userId) {
            List<Reservation> filteredReservations = new ArrayList<>();
            try {
                String query = "SELECT * FROM reservation WHERE paidStatus = ? AND id_user = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setBoolean(1, paidStatus);
                pst.setInt(2, userId);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Reservation r = new Reservation();
                    r.setId(rs.getInt("id"));
                    r.setId_cours(rs.getInt("id_cours"));
                    r.setId_user(rs.getInt("id_user"));
                    r.setId_codepromo(rs.getInt("id_codepromo"));
                    r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                    r.setResStatus(rs.getBoolean("resStatus"));
                    r.setPrixd(rs.getFloat("prixd"));
                    r.setPaidStatus(rs.getBoolean("paidStatus"));
                    filteredReservations.add(r);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return filteredReservations;
        }

    public Map<String, Float> calculateRevenueData(List<Reservation> reservations, String timePeriod) {
        // Implement logic to calculate revenue based on the time period (week, month, or year)
        // You can use LocalDateTime and grouping logic similar to the PieChart
        // This is just a placeholder, you need to replace it with your actual logic

        // Example: Grouping revenue by year
        Map<String, Float> revenueData = reservations.stream()
                .filter(Reservation::isPaidStatus)
                .filter(reservation -> checkTimePeriod(reservation, timePeriod))
                .collect(Collectors.groupingBy(
                        reservation -> String.valueOf(reservation.getDateReservation().getYear()),
                        Collectors.summingDouble(Reservation::getPrixd)
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().floatValue()));

        return revenueData;
    }

    private boolean checkTimePeriod(Reservation reservation, String timePeriod) {
        // Implement logic to check if the reservation falls within the specified time period
        // You may need to compare the reservation date with the current date and timePeriod parameter
        // Adjust this method based on your time period requirements
        // This is just a placeholder, replace it with your actual logic
        LocalDateTime currentDate = LocalDateTime.now();
        switch (timePeriod.toLowerCase()) {
            case "week":
                // Check if the reservation date is within the current week
                return currentDate.minusWeeks(1).isBefore(reservation.getDateReservation());
            case "month":
                // Check if the reservation date is within the current month
                return currentDate.minusMonths(1).isBefore(reservation.getDateReservation());
            case "year":
                // Check if the reservation date is within the current year
                return currentDate.minusYears(1).isBefore(reservation.getDateReservation());
            default:
                return true; // If no valid time period specified, include all reservations
        }
    }

}
