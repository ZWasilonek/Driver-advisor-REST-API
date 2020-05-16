package pl.coderslab.impl.generic;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.generic.GenericEntityID;
import pl.coderslab.service.generic.GenericService;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericServiceImpl<T,R extends JpaRepository<T, Long>> implements GenericService<T>, Serializable {

    protected R repository;

    @Autowired
    public GenericServiceImpl(R repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }

    @Override
    public T create(T o) {
        repository.save(o);
        return o;
    }

    @Override
    public T update(T t) {
        T model = repository.findById(((GenericEntityID)t).getId()).orElse(null);
        if (model != null) {
            ((GenericEntityID)t).setId(((GenericEntityID) model).getId());
            return repository.save(t);
        }
        throw new ServiceException("Cannot find item to update");
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(int pageNo, int limit) {
        Pageable pageableRequest = PageRequest.of(pageNo, limit);
        return repository.findAll(pageableRequest).stream().collect(Collectors.toList());
    }

    @Override
    public void remove(T o) {
        repository.findById(((GenericEntityID) o).getId()).ifPresent(item -> repository.delete(o));
    }

    @Override
    public void removeById(Long id) {
        repository.findById(id).ifPresent(object -> repository.delete(object));
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        T object = repository.findById(id).orElse(null);
        if (object == null) {
            throw new EntityNotFoundException(getGenericTypeClass(), "id", id.toString());
        }
        return object;
    }
    
}
