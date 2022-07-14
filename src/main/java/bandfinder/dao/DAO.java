package bandfinder.dao;

import java.util.List;

public interface DAO<T> {
    T create(T model);
    T update(T model);
    // Return true if deletion was successful;
    boolean delete(int id);
    T getById(int id);
    List<T> getAll();
}
