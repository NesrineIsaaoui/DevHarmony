package Services;

import Models.CategorieCodePromo;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;
import Utils.DB;

public class CategorieCodePromoService {


    private Connection connection;

    public CategorieCodePromoService() {
        this.connection=DB.getInstance().getConnection();


    }

    public void addCategorieCodePromo(CategorieCodePromo categorieCodePromo) {
        try {
            String query = "INSERT INTO CategorieCodePromo(code, value, nb_users) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, categorieCodePromo.getCode());
                preparedStatement.setFloat(2, categorieCodePromo.getValue());
                preparedStatement.setInt(3, categorieCodePromo.getNb_users());
                if( preparedStatement.executeUpdate()>0){
                    System.out.println("Categorie added ");
                }else{
                    System.out.println("Categorie PROBLEM ");

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<CategorieCodePromo> getAllCategories() {
        List<CategorieCodePromo> categories = new ArrayList<>();
        String query = "SELECT * FROM CategorieCodePromo";

        try {
            Statement st=connection.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                float value = resultSet.getFloat("value");
                int nbUsers = resultSet.getInt("nb_users");
                categories.add(new CategorieCodePromo(id, code, value, nbUsers));
            }
            return categories;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    public CategorieCodePromo getCategorieByCode(String promoCode) {
        String query = "SELECT * FROM categoriecodepromo WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, promoCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                float value = resultSet.getFloat("value");
                int nbUsers = resultSet.getInt("nb_users");
                return new CategorieCodePromo(id, code, value, nbUsers);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateCategorieCodePromo(CategorieCodePromo categorieCodePromo) {
        try {
            String query = "UPDATE CategorieCodePromo SET code=?, value=?, nb_users=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, categorieCodePromo.getCode());
                preparedStatement.setFloat(2, categorieCodePromo.getValue());
                preparedStatement.setInt(3, categorieCodePromo.getNb_users());
                preparedStatement.setInt(4, categorieCodePromo.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteCategorieCodePromo(int categoryId) {
        try {
            String query = "DELETE FROM CategorieCodePromo WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, categoryId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int addCategorieCodePromoId(CategorieCodePromo categorieCodePromo) {
        try {
            String query = "INSERT INTO CategorieCodePromo(code, value, nb_users) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, categorieCodePromo.getCode());
                preparedStatement.setFloat(2, categorieCodePromo.getValue());
                preparedStatement.setInt(3, categorieCodePromo.getNb_users());

                if (preparedStatement.executeUpdate() > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        // Return the generated ID
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getIdByCode(String promoCode) {
        try {
            String query = "SELECT id FROM categoriecodepromo WHERE code = ?";
            PreparedStatement pst = DB.getInstance().getConnection().prepareStatement(query);
            pst.setString(1, promoCode);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return -1; // Return -1 if not found or an error occurs
    }


}
