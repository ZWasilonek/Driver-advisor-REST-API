package pl.coderslab.service.generic;

import java.util.List;

public interface GenericService<T> {

    void create(T o);
    T update(T t);
    List<T> findAll();
    List<T> findAll(int pageNo, int limit);
    void remove(T o);
    void removeById(Long id);
    T findById(Long id);

}