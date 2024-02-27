package Services;

import Models.CategorieCodePromo;
import Models.Reservation;
import Utils.DB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private Connection connection;

    public ReservationService() {

        this.connection=DB.getInstance().getConnection();


    }
    public void addEntity(Reservation t) {
        try {
            String rq = "INSERT INTO reservation (id_user, id_cours, resStatus, date_reservation, id_codepromo, prixd) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DB.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, t.getId_user());
            pst.setInt(2, t.getId_cours());
            pst.setBoolean(3, t.isResStatus());
            pst.setTimestamp(4, Timestamp.valueOf(t.getDateReservation()));
            pst.setInt(5, t.getId_codepromo());
            pst.setFloat(6, t.getPrixd());
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
            Statement st = DB.getInstance().getConnection().createStatement();
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
            String rq = "UPDATE reservation SET id_user=?, id_cours =? , resStatus = ?, date_reservation = ? WHERE id = ?";
            PreparedStatement pst = DB.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, t.getId_user());
            pst.setInt(2, t.getId_cours());
            pst.setBoolean(3, t.isResStatus());
            pst.setTimestamp(4, Timestamp.valueOf(t.getDateReservation()));
            pst.setInt(5, t.getId());
            pst.executeUpdate();
            System.out.println("Reservation has been updated.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteEntity(int id) {
        try {
            String rq = "DELETE FROM reservation where id=?";
            PreparedStatement pst = DB.getInstance().getConnection().prepareStatement(rq);
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
            PreparedStatement pst = DB.getInstance().getConnection().prepareStatement(rq);
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
            PreparedStatement pst = DB.getInstance().getConnection().prepareStatement(rq);
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
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<Reservation> cours_reservations(int cours_id) {
        List<Reservation> myList = new ArrayList<>();
        try {
            String rq = "SELECT * FROM reservation WHERE id_cours = ?";
            PreparedStatement pst = DB.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, cours_id);
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
            Statement st = DB.getInstance().getConnection().createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_user = resultSet.getInt("id_user");
                int id_cours = resultSet.getInt("id_cours");
                boolean resStatus = resultSet.getBoolean("resStatus");
                LocalDateTime dateReservation = resultSet.getTimestamp("date_reservation").toLocalDateTime();
                int id_codepromo = resultSet.getInt("id_codepromo");
                float prixd = resultSet.getFloat("prixd");

                Reservation reservation = new Reservation();
                reservation.setId(id);
                reservation.setId_user(id_user);
                reservation.setId_cours(id_cours);
                reservation.setResStatus(resStatus);
                reservation.setDateReservation(dateReservation);
                reservation.setId_codepromo(id_codepromo);
                reservation.setPrixd(prixd);

                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Reservation> cours_reservations_by_year(int cours_id, int year) {
        List<Reservation> myList = new ArrayList<>();
        try {
            String rq = "SELECT * FROM reservation WHERE id_cours = ? AND YEAR(date_reservation) = ?";
            PreparedStatement pst = DB.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, cours_id);
            pst.setInt(2, year);
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
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<Reservation> cours_reservations_by_month(int cours_id, int year, int month) {
        List<Reservation> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM reservation WHERE id_cours = ? AND YEAR(date_reservation) = ? AND MONTH(date_reservation) = ?";
            PreparedStatement pst = DB.getInstance().getConnection().prepareStatement(rq);
            pst.setInt(1, cours_id);
            pst.setInt(2, year);
            pst.setInt(3, month);
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
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
}
