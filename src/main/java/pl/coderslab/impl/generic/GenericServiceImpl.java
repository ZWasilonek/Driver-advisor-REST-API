package pl.coderslab.impl.generic;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.exception.EntityNotFoundException;
import pl.coderslab.model.Question;
import pl.coderslab.model.generic.GenericEntityID;
import pl.coderslab.service.generic.GenericService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GenericServiceImpl<T,R extends JpaRepository<T, Long>> implements GenericService<T> {

    protected R repository;
//    private Class clazz = this.getClass();
    private final ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();

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
    public void create(T o) {
        repository.save(o);
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
        Optional<T> item = repository.findById(id);
        if (item.get() != null) repository.delete(item.get());
    }

    @Override
    public T findById(Long id) {
        T object = repository.findById(id).get();
        if (object == null) {
            throw new EntityNotFoundException(getGenericTypeClass(T), id.toString());
        }
        return object;
    }
    
}
