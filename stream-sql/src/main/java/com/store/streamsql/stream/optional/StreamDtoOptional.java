package com.store.streamsql.stream.optional;

import com.store.streamsql.dto.ProductDto;
import com.store.streamsql.service.ProductService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class StreamDtoOptional {
    private final ProductService productService;

//    @PostConstruct
    public void start() {
        System.out.println("--------- ");
        List<ProductDto> products = productService.getProducts();
        ProductDto notEmptyProductDto = products.get(0);
        Optional<ProductDto> optionalProduct = Optional.of(notEmptyProductDto);
        Optional<ProductDto> optionalEmptyProductDto = Optional.empty();
        ProductDto productDtoWithoutData = products.get(71);
        Optional<ProductDto> optionalEmptyProductDto2 = Optional.ofNullable(productDtoWithoutData);

        optional1(products);
        optional2optional1ifPresentOrElse(optionalProduct);
//        optional2optional1(optionalEmptyProductDto);

        optional3Or(notEmptyProductDto,optionalEmptyProductDto,optionalProduct);
    }

    private void optional1(List<ProductDto> products) {

        Optional<ProductDto> optionalProduct = Optional.of(products.get(0));

        optionalProduct.isEmpty();
        optionalProduct.isPresent();

        optionalProduct.ifPresent(this::mockSave);

    }

    private void optional2optional1ifPresentOrElse(Optional<ProductDto> optionalProduct) {
        optionalProduct.ifPresentOrElse(this::mockSave,
                () -> System.out.println("--------- This is the first product"));

        optionalProduct.ifPresentOrElse(this::mockSave, () -> {
            System.out.println("--------- This is the first product");
            throw new NoSuchElementException("---------No product found");
        });

        optionalProduct.ifPresentOrElse(this::mockSave, () -> notifyMissingData());

        optionalProduct.ifPresentOrElse(this::mockSave, this::notifyMissingData);
    }

    private void optional3Or(
            ProductDto notEmptyProductDto,
            Optional<ProductDto> dtoOptionalEmpty,
            Optional<ProductDto> dtoOptionalProduct) {

//        public Optional<T> or(Supplier<? extends Optional<? extends T>> supplier)
        Optional<ProductDto> or1 = dtoOptionalEmpty.or(() -> Optional.of(notEmptyProductDto));
        Optional<ProductDto> or2 = dtoOptionalEmpty.or(()-> dtoOptionalProduct);

        ProductDto productDto = dtoOptionalEmpty.orElseGet(() -> notEmptyProductDto);
//        ProductDto productDto1 = dtoOptionalEmpty.orElseThrow();
//        ProductDto productDto2 = dtoOptionalEmpty.orElseThrow(
//                ()->new NoSuchElementException("------- No product found"));
        ProductDto productDto3 = dtoOptionalEmpty.orElse(notEmptyProductDto);
    }


    private void mockSave(ProductDto dto) {
        log.info("savedDto is: " + dto.getName());
    }

    private void notifyMissingData() {
        log.info("no data found");
    }
}
