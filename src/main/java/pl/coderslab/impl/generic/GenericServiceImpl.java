package pl.coderslab.impl.generic;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.generic.GenericEntityID;
import pl.coderslab.service.generic.GenericService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericServiceImpl<D,T,R extends JpaRepository<T, Long>> implements GenericService<D,T> {

    protected R repository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public GenericServiceImpl(R repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    private Class<D> getGenericDTOTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<D>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericEntityTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }

    @Override
    public D create(D dto) {
        T entity = repository.save(convertToEntity(dto, getGenericEntityTypeClass()));
        return convertToObjectDTO(entity, getGenericDTOTypeClass());
    }

    @Override
    public D update(D dto) {
        T e = convertToEntity(dto, getGenericEntityTypeClass());
        T model = repository.findById(((GenericEntityID)e).getId()).orElseThrow(
                () -> new EntityNotFoundException(getGenericDTOTypeClass(), "id", ((GenericEntityID)e).getId().toString()));
        if (model != null) {
            ((GenericEntityID)e).setId(((GenericEntityID) model).getId());
            repository.save(e);
            return convertToObjectDTO(model, getGenericDTOTypeClass());
        }
        throw new ServiceException("Cannot find item to update");
    }

    @Override
    public List<D> findAll() {
        return repository.findAll().stream()
                .map(entity -> convertToObjectDTO(entity, getGenericDTOTypeClass()))
                .collect(Collectors.toList());
    }

    @Override
    public List<D> findAll(int pageNo, int limit) {
        Pageable pageableRequest = PageRequest.of(pageNo, limit);
        return repository.findAll(pageableRequest).stream()
                .map(entity -> convertToObjectDTO(entity, getGenericDTOTypeClass()))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(D dto) {
        T model = convertToEntity(dto, getGenericEntityTypeClass());
        repository.findById(((GenericEntityID) model).getId()).ifPresent(item -> repository.delete(model));
    }

    @Override
    public void removeById(Long id) throws EntityNotFoundException {
//        repository.findById(id).ifPresent(object -> repository.delete(object));
        repository.delete(repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(getGenericDTOTypeClass(), "id", id.toString())));
    }

    @Override
    public D findById(Long id) throws EntityNotFoundException {
        T entity = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(getGenericDTOTypeClass(), "id", id.toString()));
        return convertToObjectDTO(entity, getGenericDTOTypeClass());
    }

    @Override
    public D convertToObjectDTO(T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    @Override
    public T convertToEntity(D dto, Class<T> outClass) {
        return modelMapper.map(dto, outClass);
    }

    @Override
    public URL getURLForFile(Long fileId, HttpServletRequest request) {
        String pattern = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String lastPathItem = pattern.substring(pattern.lastIndexOf('/') + 1);
        String requestMappingPath = pattern.replace(lastPathItem, "showFile/");
        URL urlToFile = null;
        try {
            urlToFile = new URL(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(requestMappingPath)
                    .path(fileId.toString())
                    .toUriString());
        } catch (MalformedURLException e) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (urlToFile == null) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return urlToFile;
    }

}
