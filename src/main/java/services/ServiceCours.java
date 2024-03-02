package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Cours;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceCours implements IServiceCours {
    private Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouterCours(Cours c)
    {
        String req = "INSERT INTO `cours`(`coursName`, `coursDescription`, `coursImage`, `coursPrix`,`idCategory`) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, c.getCoursName());
            ps.setString(2, c.getCoursDescription());
            ps.setString(3, c.getCoursImage());
            ps.setInt(4, c.getCoursPrix());
            ps.setInt(5, c.getIdCategory());

            ps.executeUpdate();
            System.out.println("Cours ajouté avec succès!");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCours.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public List<Cours> afficherCours() {
        List<Cours> cours = new ArrayList<>();
        String req = "SELECT * FROM cours ";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Cours E = new Cours();
                E.setId(rs.getInt("id"));
                E.setCoursName(rs.getString("coursName"));
                E.setCoursDescription(rs.getString("coursDescription"));
                E.setCoursImage(rs.getString("coursImage"));
                E.setCoursPrix(rs.getInt("coursPrix"));
                E.setIdCategory(rs.getInt("idCategory"));
                cours.add(E);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCours.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cours;
    }

    @Override
    public void modifierCours(Cours c) {
        try {
            String req = "UPDATE `cours` SET `coursName`=?, `coursDescription`=?, `coursImage`=?, `coursPrix`=?, `idCategory`=? WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setString(1, c.getCoursName());
                st.setString(2, c.getCoursDescription());
                st.setString(3, c.getCoursImage());
                st.setInt(4, c.getCoursPrix());
                st.setInt(5, c.getIdCategory());
                st.setInt(6, c.getId());

                int rowsAffected = st.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Cours modifié avec succès");
                } else {
                    System.out.println("Aucune modification effectuée pour le cours avec l'ID " + c.getId());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void supprimerCours(int id) {
        try {
            String req = "DELETE FROM `cours` WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("Cours supprimé avec succès");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void addAvis(int coursId, int stars) {
        String req = "INSERT INTO `avis` (`cours_id`, `etoiles`) VALUES (?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, coursId);
            ps.setInt(2, stars);

            ps.executeUpdate();
            System.out.println("Avis ajouté avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCours.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getNombreAvis(int idCours) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM avis WHERE cours_id = ?")) {
            preparedStatement.setInt(1, idCours);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getNombreAvisByRating(int idCours, int rating) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM avis WHERE cours_id = ? AND etoiles = ?")) {
            preparedStatement.setInt(1, idCours);
            preparedStatement.setInt(2, rating);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
