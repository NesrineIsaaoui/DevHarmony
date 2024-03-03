package services;

import models.Cours;
import models.CoursCategory;
import utils.DataSource;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceCours implements IServiceCours {
    private Connection cnx = DataSource.getInstance().getConnection();
    private List<CoursCategory> coursCategories;

    public CoursCategory getCoursCategoryById(int id) {
        for (CoursCategory category : coursCategories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    @Override
    public void ajouterCours(Cours c) {
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
public  double getEtoiles(int idCours)
{
    try {
        PreparedStatement preparedStatement = cnx.prepareStatement("SELECT etoiles FROM avis WHERE cours_id = ?");
        preparedStatement.setInt(1, idCours);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getDouble(1);
    } catch (SQLException e) {
        e.printStackTrace();
        return 0;
    }
}
    public double getMoyenneEtoiles(int idCours) {
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT AVG(etoiles) FROM avis WHERE cours_id = ?");
            preparedStatement.setInt(1, idCours);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }



    public String getCourseFeedback(double averageStars) {
        if (averageStars > 4.0) {
            return "Excellent";
        } else if (averageStars <= 4.0 && averageStars > 2.0) {
            return "Bon";
        } else {
            return "Faible";
        }
    }

    public Cours getCourseById(int courseId) {
        Cours course = null;
        String req = "SELECT * FROM cours WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    course = new Cours();
                    course.setId(rs.getInt("id"));
                    course.setCoursName(rs.getString("coursName"));
                    course.setCoursDescription(rs.getString("coursDescription"));
                    course.setCoursImage(rs.getString("coursImage"));
                    course.setCoursPrix(rs.getInt("coursPrix"));
                    course.setIdCategory(rs.getInt("idCategory"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCours.class.getName()).log(Level.SEVERE, null, ex);
        }
        return course;
    }


    public Map<Integer, Integer> getAvisStats(int idCours) {
        Map<Integer, Integer> avisStats = new HashMap<>();
        String req = "SELECT etoiles, COUNT(*) FROM avis WHERE cours_id = ? GROUP BY etoiles";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, idCours);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    avisStats.put(rs.getInt(1), rs.getInt(2));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCours.class.getName()).log(Level.SEVERE, null, ex);
        }
        return avisStats;
    }

    public List<Cours> searchCourses(String searchText) {
        List<Cours> courses = new ArrayList<>();
        String req = "SELECT * FROM cours WHERE coursName LIKE ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, "%" + searchText + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cours cours = new Cours();
                    cours.setId(rs.getInt("id"));
                    cours.setCoursName(rs.getString("coursName"));
                    cours.setCoursDescription(rs.getString("coursDescription"));
                    cours.setCoursImage(rs.getString("coursImage"));
                    cours.setCoursPrix(rs.getInt("coursPrix"));
                    cours.setIdCategory(rs.getInt("idCategory"));
                    courses.add(cours);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCours.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }
}
