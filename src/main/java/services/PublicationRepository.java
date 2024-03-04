package services;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
 import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import interfaces.CrudRepository;
import models.Publication;
import utils.Connexion_database;

 import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PublicationRepository implements CrudRepository<Publication> {

    private Connection connection;

    // Initialise la connexion à la base de données dans le constructeur
    public PublicationRepository() {
        try {
            this.connection = Connexion_database.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PublicationRepository.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    @Override
    public void save(Publication publication) {
        try {
            String query = "INSERT INTO Publication (titre, contenu, utilisateur_id) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, publication.getTitre());
                preparedStatement.setString(2, publication.getContenu());
                preparedStatement.setInt(3, publication.getUtilisateurID());
                preparedStatement.executeUpdate();
// Check user role
//                if (user.getRole().equals("Professeur")) {
//                    // Get users with role "Eleve" from the database
//                    List<User> eleveUsers = getUsersWithRole("Eleve");
//
//                    // Iterate through the list of "Eleve" users and send emails
//                    for (User eleveUser : eleveUsers) {
//                        String to = eleveUser.getEmail(); // Get user email from the User object
//                        String subject = "Nouvelle Publication ";
//                        String body = "Bonjour,\n\nUne nouvelle Publication est ajoutee de depart" + ": " +
//                                publication.getUserName() + " " + ".\n\nCordialement,\nL'equipe de support";
//                        sendEmail(to, subject, body); // Call function to send email
//                    }
//                }
                String to = "mistiskaziz@gmail.com"; // Get user email from database
               String subject = "Nouvelle Publication ";
                String body = "Bonjour,\n\nUne nouvelle Publication est ajoutee de la part de"+": "+publication.getUserName()+" "+" .\n\nCordialement,\nL'equipe de support";
                sendEmail(to, subject, body); // Call function to send email*/   // Send email to user
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void sendEmail(String to, String subject, String body) {
        String username = "mistiskaziz@gmail.com";
        String password = "ejjtljhcbnelwfcw";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Change this to your SMTP server host(yahoo...)
        props.put("mail.smtp.port", "587"); // Change this to your SMTP server port
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

         Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        try {
            // Create a MimeMessage object

            // Create a new message
            MimeMessage message = new MimeMessage(session);
            // Set the From, To, Subject, and Text fields of the message
            message.setFrom(new InternetAddress(username));
            message.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            // Send the message using Transport.send
            Transport.send(message);

            System.out.println("Email sent successfully");
        } catch (MessagingException ex) {
            System.err.println("Failed to send email: " + ex.getMessage());
        }

    }
    // Method to retrieve users with a specific role from the database
  /*  private List<User> getUsersWithRole(String role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateur WHERE role = ?";

        try  {

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, role);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getLong("id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setEmail(resultSet.getString("email"));
                        user.setRole(resultSet.getString("role"));

                        users.add(user);
                    }
                }
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., log the error)
            e.printStackTrace();
        }

        return users;
    }
        return null; // Replace this with your actual implementation
    }*/
    @Override
    public Publication findById(int id) {
        try {
            String query = "SELECT * FROM Publication WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new Publication(
                            resultSet.getInt("id"),
                            resultSet.getString("titre"),
                            resultSet.getString("contenu"),
                            resultSet.getDate("datePublication"),

                            resultSet.getInt("utilisateur_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Publication> findAll() {
        ObservableList<Publication> publications = FXCollections.observableArrayList();


        try {
            String query = "SELECT Publication.*,  CONCAT(Utilisateur.nom, ' ', Utilisateur.prenom) AS userName " +
                    "FROM Publication " +
                    "JOIN Utilisateur ON Publication.utilisateur_id = Utilisateur.id";

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Publication publication = new Publication(
                            resultSet.getInt("id"),
                            resultSet.getString("titre"),
                            resultSet.getString("contenu"),
                            resultSet.getDate("date_publication"),
                            resultSet.getInt("utilisateur_id"),
                            resultSet.getString("userName") // Adding the userName from the join
                    );
                    publications.add(publication);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return publications;
    }

    public List<Publication> findAllWithUserDetails() {
        List<Publication> publications = new ArrayList<>();

        try {
            String query = "SELECT Publication.*, CONCAT(Utilisateur.nom, ' ', Utilisateur.prenom) AS userName, Utilisateur.role " +
                    "FROM Publication " +
                    "JOIN Utilisateur ON Publication.utilisateur_id = Utilisateur.id";

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Publication publication = new Publication(
                            resultSet.getInt("id"),
                            resultSet.getString("titre"),
                            resultSet.getString("contenu"),
                            resultSet.getDate("date_publication"),
                            resultSet.getInt("utilisateur_id"),
                            resultSet.getString("userName"),
                            resultSet.getString("role")
                    );
                    publications.add(publication);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return publications;
    }

    @Override
    public void update(Publication publication) {
        try {
            String query = "UPDATE Publication SET titre = ?, contenu = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, publication.getTitre());
                preparedStatement.setString(2, publication.getContenu());
                preparedStatement.setInt(3, publication.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            String query = "DELETE FROM Publication WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
