package Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Models.CodePromo;
import Utils.DB;
public class CodePromoService {

    private Connection connection;


    public CodePromoService() {
        this.connection= DB.getInstance().getConnection();

    }


    public void addCodePromo(CodePromo codePromo) {
        try {
            String query = "INSERT INTO codePromo (id_user, id_categorieCodePromo) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, codePromo.getId_user());
                preparedStatement.setInt(2, codePromo.getId_code());
                if( preparedStatement.executeUpdate()>0){
                    System.out.println("Code promo added ");
                }else{
                    System.out.println("Code promo PROBLEM ");

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Read
    public List<CodePromo> getAllCodePromos() {
        List<CodePromo> codePromos = new ArrayList<>();
        String query = "SELECT * FROM CodePromo";
        try {
            Statement st=connection.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                int idPromo = resultSet.getInt("id");
                int idUser = resultSet.getInt("id_user");
                int idCode = resultSet.getInt("id_categorieCodePromo");
                codePromos.add(new CodePromo(idPromo, idUser, idCode));
                return codePromos;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    public void updateCodePromo(CodePromo codePromo) {
        try {
            String query = "UPDATE codePromo SET id_user=?, id_categorieCodePromo=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, codePromo.getId_user());
                preparedStatement.setInt(2, codePromo.getId_code());
                preparedStatement.setInt(3, codePromo.getId_promo());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public void deleteCodePromo(int codePromoId) {
        try {
            String query = "DELETE FROM codePromo WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, codePromoId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
