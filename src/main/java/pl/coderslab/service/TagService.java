package pl.coderslab.service;

import pl.coderslab.dto.TagDto;
import pl.coderslab.model.Tag;

public interface TagService {

    TagDto createTag(TagDto tagDto);
    TagDto findTagById(Long tagId);
    TagDto updateTag(TagDto tagDto);
    boolean removeTagById(Long tagId);

    TagDto convertToObjectDTO(Tag tag);
    Tag convertToEntity(TagDto tagDto);

}
