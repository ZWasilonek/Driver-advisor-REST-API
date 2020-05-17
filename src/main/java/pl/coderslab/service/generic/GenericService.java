package pl.coderslab.service.generic;

import java.util.List;

public interface GenericService<D,T> {

    D create(D o);
    D update(D o);
    List<D> findAll();
    List<D> findAll(int pageNo, int limit);
    void remove(D o);
    void removeById(Long id);
    D findById(Long id);
    T convertToEntity(D dto, Class<T> outClass);
    D convertToObjectDTO(T entity, Class<D> outClass);

}