package service;

import model.Eleve;
import model.Enseigant;
import model.Parent;
import model.User;
import util.DataSource;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.List;

public class UserService implements IService<User> {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public UserService() {
        conn= DataSource.getInstance().getCnx();
    }

    public User getByEmail(String e) throws SQLException{
        User user = new User();
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, e);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setRole(rs.getString("role"));
            user.setAge(rs.getInt("age"));
            user.setImage(rs.getString("image"));
            user.setEmail(rs.getString("email"));
            user.setNum_tel(rs.getInt("Num_tel"));
            user.setPwd(rs.getString("mdp"));
            user.setStatus(rs.getString("status"));
            user.setResetCode(rs.getInt("resetcode"));
            user.setConfirmCode(rs.getString("confirmcode"));
            user.setStatuscode(rs.getInt("statuscode"));
        }
        return user;
    }

    public User getById(int userId) throws SQLException {
        User user = new User();
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setRole(rs.getString("role"));
            user.setAge(rs.getInt("age"));
            user.setImage(rs.getString("image"));
            user.setEmail(rs.getString("email"));
            user.setNum_tel(rs.getInt("Num_tel"));
            user.setPwd(rs.getString("mdp"));
            user.setStatus(rs.getString("status"));
            user.setResetCode(rs.getInt("resetcode"));
            user.setConfirmCode(rs.getString("confirmcode"));
            user.setStatuscode(rs.getInt("statuscode"));
        }
        return user;
    }

    public boolean checkResetCode(String email, int resetCode) throws SQLException {
        User user = getByEmail(email);
        return user.getResetCode() == resetCode;
    }

    public void updatePassword(String email, String newPassword) throws SQLException {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        String query = "UPDATE user SET mdp = ?, resetcode = 0 WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, hashedPassword);
        ps.setString(2, email);
        ps.executeUpdate();
    }


    public void storeRandomCodeInDatabase(String email, int randomCode) {
        try {
            String query = "UPDATE user SET resetcode = ? WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, randomCode);
            ps.setString(2, email);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void storeCodeConfirmEmailInDatabase(String email, String confirmCode) {
        try {
            String query = "UPDATE user SET confirmcode = ? WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, confirmCode);
            ps.setString(2, email);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyConfirmationCode(String email, String code) throws SQLException {
        User user = getByEmail(email);
        return code.equals(user.getConfirmCode());
    }

    public void updateCodeConfirmEmailStatus(String email) throws SQLException {
        String query = "UPDATE user SET confirmcode = 'verified' WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, email);
        ps.executeUpdate();
    }

    public void storeCodeReactiverAccount(String email, Integer code) {
        try {
            String query = "UPDATE user SET statuscode = ? WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, code);
            ps.setString(2, email);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyReactiverAccountCode(String email, Integer code) throws SQLException {
        User user = getByEmail(email);

        // Add System.out for debugging or logging
        System.out.println("Received code: " + code);
        System.out.println("User's code: " + user.getStatuscode());

        return code.equals(user.getStatuscode());
    }

    public boolean userExist(String e) throws SQLException{
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, e);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if(rs.getInt(1) == 0){
                return false;
            }
        }
        return true;


    }

    @Override
    public void add(User t) throws SQLException {

    }

    @Override
    public void addEnseignant(Enseigant enseigant) throws SQLException {
            String hashedPassword = BCrypt.hashpw(enseigant.getPwd(), BCrypt.gensalt());
            String QueryToUser = "INSERT INTO user (nom,prenom,role,email,mdp,status) VALUES (?,?,?,?,?,?)";
            PreparedStatement psUser = conn.prepareStatement(QueryToUser);
            psUser.setString(1, enseigant.getNom());
            psUser.setString(2, enseigant.getPrenom());
            psUser.setString(3, enseigant.getRole());
            psUser.setString(4, enseigant.getEmail());
            psUser.setString(5, hashedPassword);
            psUser.setString(6, enseigant.getStatus());
            psUser.executeUpdate();

         /*   String QueryToEnseignant = "INSERT INTO Enseignant (idEnseignant ) VALUES (?)";
            int id = getByEmail(enseigant.getEmail()).getId();
            PreparedStatement psEnseignant = conn.prepareStatement(QueryToEnseignant);
            psEnseignant.setInt(1, id);
            psEnseignant.executeUpdate();*/
    }

    @Override
    public void addEnseignant(User u) throws SQLException {

    }

    @Override
    public void addParent(User u) {

    }

    @Override
    public void addEleve(User u) {

    }

    public void addParent(Parent parent) throws SQLException {
        String hashedPassword = BCrypt.hashpw(parent.getPwd(), BCrypt.gensalt());
        String QueryToUser = "INSERT INTO user (nom,prenom,role,email,mdp,status ) VALUES (?,?,?,?,?,?)";
        PreparedStatement psUser = conn.prepareStatement(QueryToUser);
        psUser.setString(1, parent.getNom());
        psUser.setString(2, parent.getPrenom());
        psUser.setString(3, parent.getRole());
        psUser.setString(4, parent.getEmail());
        psUser.setString(5, hashedPassword);
        psUser.setString(6, parent.getStatus());
        psUser.executeUpdate();

     /*   String QueryToParent = "INSERT INTO Parent (idParent ) VALUES (?)";
        int id = getByEmail(parent.getEmail()).getId();
        PreparedStatement psParent = conn.prepareStatement(QueryToParent);
        psParent.setInt(1, id);
        psParent.executeUpdate();*/
    }
    public void addEleve(Eleve eleve) throws SQLException {
        String hashedPassword = BCrypt.hashpw(eleve.getPwd(), BCrypt.gensalt());
        String QueryToUser = "INSERT INTO user (nom,prenom,role,email,mdp,status ) VALUES (?,?,?,?,?,?)";
        PreparedStatement psUser = conn.prepareStatement(QueryToUser);
        psUser.setString(1, eleve.getNom());
        psUser.setString(2, eleve.getPrenom());
        psUser.setString(3, eleve.getRole());
        psUser.setString(4, eleve.getEmail());
        psUser.setString(5, hashedPassword);
        psUser.setString(6, eleve.getStatus());
        psUser.executeUpdate();

     /*   String QueryToEleve = "INSERT INTO Eleve (id_eleve) VALUES (?)";
        int id = getByEmail(eleve.getEmail()).getId();
        PreparedStatement psEleve = conn.prepareStatement(QueryToEleve);
        psEleve.setInt(1, id);
        psEleve.executeUpdate();*/
    }

    public void InvertStatus(String email) throws SQLException {
        String query = "UPDATE user SET Status = ?  WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        if (getByEmail(email).getStatus().equals("Active")) {
            ps.setString(1, "Desactive");
        } else {
            ps.setString(1, "Active");
        }
        ps.setString(2,email);
        //System.out.println("done image uploaded");
        ps.executeUpdate();
    }

    public void update(User t , String email) throws SQLException {
        String hashedPassword = BCrypt.hashpw(t.getPwd(), BCrypt.gensalt());

        String query = "UPDATE user SET age = ?, nom = ?  , email = ? , mdp = ? , num_tel = ?, image = ?, resetcode = ?, confirmcode = ? WHERE email = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, t.getAge());
        ps.setString(2, t.getNom());
        ps.setString(3, t.getEmail());
        ps.setString(4, hashedPassword);
        ps.setInt(5, t.getNum_tel());
        ps.setString(6, t.getImage());
        ps.setInt(7,t.getResetCode());
        ps.setString(8,t.getConfirmCode());
        ps.setString(9, email);
        ps.executeUpdate();
    }


    @Override
    public void UpdateNom(User t) throws SQLException{

        String query = "UPDATE User SET nom = ?  WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, t.getNom());
        ps.setString(2, t.getEmail());
        ps.executeUpdate();
    }

    @Override
    public void updatePhoto(String Photo, String email) throws SQLException {
        String query = "UPDATE user SET Image = ?  WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, Photo);
        ps.setString(2,email);
        System.out.println("done image uploaded");
        ps.executeUpdate();
    }
    @Override
    public boolean Login(String e, String P) throws SQLException {
        User U1 = getByEmail(e);
        return U1 != null && BCrypt.checkpw(P, U1.getPwd());
    }




    @Override
    public void delete(User t) {

    }

    @Override
    public void update(User t) {

    }


    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public User readById(int id) {
        return null;
    }
}
