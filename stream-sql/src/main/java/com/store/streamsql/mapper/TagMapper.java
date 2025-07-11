package com.store.streamsql.mapper;

import com.store.streamsql.config.MapperConfig;
import com.store.streamsql.dto.TagRequestDto;
import com.store.streamsql.dto.TagResponseDto;
import com.store.streamsql.model.product.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    TagResponseDto toDto(Tag entity);
    Tag toEntity(TagRequestDto dto);
}
