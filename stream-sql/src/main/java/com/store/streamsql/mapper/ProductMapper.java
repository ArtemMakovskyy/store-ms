package com.store.streamsql.mapper;

import com.store.streamsql.config.MapperConfig;
import com.store.streamsql.dto.ProductDto;
import com.store.streamsql.dto.ProductRequestDto;
import com.store.streamsql.dto.ProductResponseDto;
import com.store.streamsql.dto.TagRequestDto;
import com.store.streamsql.model.product.Product;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = TagMapper.class)
public interface ProductMapper {

    ProductResponseDto toDto(Product product);

    Product toEntity(ProductRequestDto dto);

    ProductRequestDto toRequestDto(ProductDto dto);

    default List<TagRequestDto> map(List<String> tagNames) {
        if (tagNames == null){
            return null;
        }
        return tagNames.stream()
                .map(name -> {
                    TagRequestDto dto = new TagRequestDto();
                    dto.setName(name);
                    return dto;
                })
                .toList();
    }
}
