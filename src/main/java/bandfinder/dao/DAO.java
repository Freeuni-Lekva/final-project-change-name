package bandfinder.dao;

import java.util.List;

public interface DAO<T> {
    T create(T model);
    T update(T model);
    boolean delete(int id);
    T getById(int id);
    List<T> getAll();
}
