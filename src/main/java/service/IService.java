package service;


import model.Enseigant;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IService<U> {
    void add(U t) throws SQLException;

    void addEnseignant(Enseigant enseigant) throws SQLException;

    void addEnseignant(User u) throws SQLException;

    void addParent(User u);

    void addEleve(User u);

    void UpdateNom(User t) throws SQLException;

    void updatePhoto(String Photo, String email) throws SQLException;

    boolean Login(String e, String P) throws SQLException;

    void delete(U t);
    void update(U t);
    List<U> readAll();
    U readById(int id);
}
