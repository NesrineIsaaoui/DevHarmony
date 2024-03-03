package pi.blog.interfaces;

import javafx.collections.ObservableList;
import pi.blog.models.Publication;

import java.util.List;

public interface CrudRepository<T> {

    // Create
    void save(T entity);

    // Read
    T findById(int id);
    List<T> findAll();

    // Update
    void update(T entity);

    // Delete
    void deleteById(int id);
    public ObservableList<T> findAllWithUserDetails();
}
