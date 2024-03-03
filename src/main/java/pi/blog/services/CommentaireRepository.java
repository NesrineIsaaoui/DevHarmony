package pi.blog.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pi.blog.interfaces.CrudRepository;
import pi.blog.models.Commentaire;
import pi.blog.models.Publication;
import pi.blog.utils.Connexion_database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentaireRepository implements CrudRepository<Commentaire> {

    private Connection connection;

    // Initialise la connexion à la base de données dans le constructeur
    public CommentaireRepository() {
        try {
            this.connection = Connexion_database.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CommentaireRepository.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    @Override
    public void save(Commentaire commentaire) {
        try {
            String query = "INSERT INTO Commentaire (contenu, date_commentaire, utilisateur_id, publication_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, commentaire.getContenu());
                preparedStatement.setTimestamp(2, new Timestamp(commentaire.getDateCommentaire().getTime()));
                preparedStatement.setInt(3, commentaire.getUtilisateurId());
                preparedStatement.setInt(4, commentaire.getPublicationId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Commentaire findById(int id) {
        try {
            String query = "SELECT * FROM Commentaire WHERE publication_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new Commentaire(
                            resultSet.getInt("id"),
                            resultSet.getString("contenu"),
                            resultSet.getTimestamp("date_commentaire"),
                            resultSet.getInt("utilisateur_id"),
                            resultSet.getInt("publication_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ObservableList<Commentaire> findAllById(int idPublication) {
        ObservableList<Commentaire> commentaires = FXCollections.observableArrayList();

        try {
            String query = "SELECT Commentaire.*, Utilisateur.role, CONCAT(Utilisateur.nom, ' ', Utilisateur.prenom) AS userName " +
                    "FROM Commentaire " +
                    "JOIN Utilisateur ON Commentaire.utilisateur_id = Utilisateur.id " +
                    "WHERE Commentaire.publication_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idPublication);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Commentaire commentaire = new Commentaire(
                            resultSet.getInt("id"),
                            resultSet.getString("contenu"),
                            resultSet.getTimestamp("date_commentaire"),
                            resultSet.getInt("utilisateur_id"),
                            resultSet.getInt("publication_id"),
                            resultSet.getString("userName"),
                            resultSet.getString("role")
                    );
                    commentaires.add(commentaire);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentaires;
    }

    @Override
    public ObservableList<Commentaire> findAll() {
        ObservableList<Commentaire> commentaires =  FXCollections.observableArrayList();

        try {
            String query = "SELECT Commentaire.*, CONCAT(Utilisateur.nom, ' ', Utilisateur.prenom) AS userName , Publication.titre AS titrePublication " +
                    "FROM Commentaire " +
                    "JOIN Utilisateur ON Commentaire.utilisateur_id = Utilisateur.id " +
                    "JOIN Publication ON Commentaire.publication_id = Publication.id";

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Commentaire commentaire = new Commentaire(
                            resultSet.getInt("id"),
                            resultSet.getString("contenu"),
                            resultSet.getTimestamp("date_commentaire"),
                            resultSet.getInt("utilisateur_id"),
                            resultSet.getInt("publication_id"),
                            resultSet.getString("userName"), // Add userName from Utilisateur
                            resultSet.getString("titrePublication") // Add titrePublication from Publication
                    );
                    commentaires.add(commentaire);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentaires;
    }

    @Override
    public void update(Commentaire commentaire) {
        try {
            String query = "UPDATE Commentaire SET contenu = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, commentaire.getContenu());
                preparedStatement.setInt(2, commentaire.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            String query = "DELETE FROM Commentaire WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Commentaire> findAllWithUserDetails() {
        return null;
    }
}
