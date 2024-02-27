package services;

import entities.Task;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceTask implements IService<Task> {

    @Override
    public void ajouter(Task task) throws SQLException {
        String req = "INSERT INTO Task (Nomcour, date, etat, Idplan) VALUES (?, ?, ?, ?)";
        try (Connection connection = MyDB.getInstance().getConnection();
             PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, task.getNomcour());
            pre.setDate(2, new java.sql.Date(task.getDate().getTime()));
            pre.setString(3, task.getEtat());
            pre.setInt(4, task.getIdplan());

            pre.executeUpdate();
        }
    }

    @Override
    public void modifier(Task task) throws SQLException {
        String req = "UPDATE Task SET Nomcour=?, date=?, etat=?, Idplan=? WHERE idtask=?";
        try (Connection connection = MyDB.getInstance().getConnection();
             PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, task.getNomcour());
            pre.setDate(2, new java.sql.Date(task.getDate().getTime()));
            pre.setString(3, task.getEtat());
            pre.setInt(4, task.getIdplan());
            pre.setInt(5, task.getIdtask());

            pre.executeUpdate();
        }
    }

    @Override
    public void supprimer(Task task) throws SQLException {
        String req = "DELETE FROM Task WHERE idtask=?";
        try (Connection connection = MyDB.getInstance().getConnection();
             PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, task.getIdtask());

            pre.executeUpdate();
        }
    }

    @Override
    public List<Task> afficher() throws SQLException {
        String req = "SELECT t.*, p.nom AS planName FROM Task t JOIN plan p ON t.idplan = p.id";
        try (Connection connection = MyDB.getInstance().getConnection();
             Statement ste = connection.createStatement();
             ResultSet res = ste.executeQuery(req)) {
            List<Task> list = new ArrayList<>();
            while (res.next()) {
                Task ta = new Task();
                ta.setIdtask(res.getInt("idtask"));
                ta.setNomcour(res.getString("nomcour"));
                ta.setDate(res.getDate("date"));
                ta.setEtat(res.getString("etat"));
                ta.setIdplan(res.getInt("idplan"));
                ta.setPlanName(res.getString("planName")); // Set the plan name

                list.add(ta);
            }
            return list;
        }
    }

    public List<String> getAllPlannerNames() throws SQLException {
        List<String> plannerNames = new ArrayList<>();
        String query = "SELECT nom FROM plan";
        try (Connection connection = MyDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("nom");
                plannerNames.add(name);
            }
        }
        return plannerNames;
    }

    // Get the plan ID by its name
    public int getPlanIdByName(String planName) throws SQLException {
        int planId = -1; // Default value if plan is not found
        String query = "SELECT id FROM plan WHERE nom = ?";
        try (Connection connection = MyDB.getInstance().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, planName);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    planId = resultSet.getInt("id");
                }
            }
        }
        return planId;
    }
}
