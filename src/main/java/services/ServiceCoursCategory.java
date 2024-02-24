package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.CoursCategory;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ServiceCoursCategory implements IServiceCategory {
    Connection cnx = DataSource.getInstance().getConnection();
    ObservableList<CoursCategory> obList = FXCollections.observableArrayList();

    @Override
    public String ajouterCoursCategory(CoursCategory c) {
        try {
            String req = "INSERT INTO `CoursCategory`( `categoryName`) VALUES ('" + c.getCategoryName() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Category Added successfully!");
        } catch (SQLException ex) {
            System.out.println("failed!");
        }
        return c.getCategoryName();
    }


    @Override
    public List<CoursCategory> afficherCoursCategory() {
        List<CoursCategory> categorie = new ArrayList<>();
        //1
        String req = "SELECT * FROM CoursCategory";
        try {
            //2
            Statement st = cnx.createStatement();
            //3
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                CoursCategory E = new CoursCategory();
                E.setId(rs.getInt("Id"));
                E.setCategoryName(rs.getString("categoryName"));
                categorie.add(E);
            }


        } catch (SQLException ex) {
            Logger.getLogger(ServiceCoursCategory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categorie;
    }


    @Override
    public void modifierCoursCategory(CoursCategory c) {
        try {
            String req = "UPDATE `CoursCategory` SET `categoryName`=?  WHERE id=?";
            PreparedStatement st = cnx.prepareStatement(req);

            st.setString(1, c.getCategoryName());

            st.setInt(2, c.getId());
            st.executeUpdate();
            System.out.println("Categorie Modifié avec succès");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void supprimerCoursCategory(int id) {

        try {
            String req = "DELETE FROM `CoursCategory` WHERE id=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("categorie supprimer avec succès");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }


    public ObservableList<CoursCategory> afficherCategory2() {
        String sql = "SELECT * FROM CoursCategory";
        try {
            Statement statement = cnx.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                String categoryName = result.getString(2);
                CoursCategory c = new CoursCategory(id, categoryName);
                obList.add(c);
            }
            result.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return obList;
    }

    public int getIdCategorieByName(String categoryName) {
        int id = -1;
        String sql = "SELECT id FROM CoursCategory WHERE categoryName = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, categoryName);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    id = result.getInt("id");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return id;
    }
}