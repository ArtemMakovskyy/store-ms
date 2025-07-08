package com.store.streamsql.stream.optional;

import com.store.streamsql.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StreamDtoOptional {
    private final ProductService productService;
}
