package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.TagDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.Tag;
import pl.coderslab.repository.TagRepository;
import pl.coderslab.service.TagService;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        return convertToObjectDTO(tagRepository.save(convertToEntity(tagDto)));
    }

    @Override
    public TagDto findTagById(Long tagId) throws EntityNotFoundException {
        return convertToObjectDTO(getTagById(tagId));
    }

    @Override
    public TagDto updateTag(TagDto tagDto) throws EntityNotFoundException {
        getTagById(tagDto.getId());
        Tag updated = tagRepository.save(convertToEntity(tagDto));
        return convertToObjectDTO(updated);
    }

    @Override
    public boolean removeTagById(Long tagId) throws EntityNotFoundException {
        Tag founded = getTagById(tagId);
        if (founded != null) {
            tagRepository.delete(founded);
            return true;
        }
        return false;
    }

    @Override
    public TagDto convertToObjectDTO(Tag tag) {
        return new ModelMapper().map(tag, TagDto.class);
    }

    @Override
    public Tag convertToEntity(TagDto tagDto) {
        return new ModelMapper().map(tagDto, Tag.class);
    }

    private Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(
                () -> new EntityNotFoundException(Tag.class, "id", tagId.toString()));
    }

}