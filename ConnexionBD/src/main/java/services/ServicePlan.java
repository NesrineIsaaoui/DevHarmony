package services;

import entities.Plan;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePlan implements IService<Plan> {
    Connection connection;

    @Override
    public void ajouter(Plan plan) throws SQLException {
        String req = "INSERT INTO Plan (nom) VALUES (?)";
        connection = MyDB.getInstance().getConnection();
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, plan.getNom());

        pre.executeUpdate();
    }

    @Override
    public void modifier(Plan plan) throws SQLException {
        String req = "UPDATE Plan SET nom=? WHERE idplan=?";
        connection = MyDB.getInstance().getConnection();
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, plan.getNom());
        pre.setInt(2, plan.getIdplan());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(Plan plan) throws SQLException {
        String req = "DELETE FROM Plan WHERE idplan=?";
        connection = MyDB.getInstance().getConnection();
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, plan.getIdplan());

        pre.executeUpdate();
    }

    @Override
    public List<Plan> afficher() throws SQLException {
        String req = "SELECT * FROM Plan";
        connection = MyDB.getInstance().getConnection();
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<Plan> list = new ArrayList<>();
        while (res.next()) {
            Plan p = new Plan();
            p.setIdplan(res.getInt("idplan"));
            p.setNom(res.getString("nom"));

            list.add(p);
        }
        return list;
    }
}
