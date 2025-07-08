package com.store.streamsql.service;

import com.store.streamsql.dto.ProductDto;
import com.store.streamsql.dto.ProductRequestDto;
import com.store.streamsql.dto.ProductResponseDto;
import com.store.streamsql.mapper.ProductMapper;
import com.store.streamsql.mapper.TagMapper;
import com.store.streamsql.model.Product;
import com.store.streamsql.model.Tag;
import com.store.streamsql.repository.ProductRepository;
import com.store.streamsql.repository.TagRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private final ProductMapper productMapper;
    private final TagMapper tagMapper;

    public void saveNewProductsIfDbIsEmpty() {
        if (productRepository.count() == 0) {
            log.info("Product table is empty. Saving new products to the database...");
            getProducts().stream()
                    .map(productMapper::toRequestDto)
                    .forEach(this::createProduct);
        } else {
            log.info("Product table is not empty. Skipping insert.");
        }
    }

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        List<Tag> tagsFromDb = getOrCreateTagsFromDb(requestDto);

        Product product = productMapper.toEntity(requestDto);
        product.setTags(tagsFromDb);

        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public ProductRequestDto convertToRequestDto(ProductDto dto) {
        return productMapper.toRequestDto(dto);
    }

    private List<Tag> getOrCreateTagsFromDb(ProductRequestDto requestDto) {
        return requestDto.getTags().stream()
                .map(tagDto -> tagRepository.findByName(tagDto.getName())
                        .orElseGet(
                                () -> {
                                    Tag newTag = tagMapper.toEntity(tagDto);
                                    return tagRepository.save(newTag);
                                }
                        )
                )
                .toList();
    }

    public List<ProductDto> getProducts() {
        return List.of(
                new ProductDto("Red Wine", "Beverage", 19.99, true, List.of("alcohol", "europe", "red"), LocalDate.of(2024, 5, 10)),
                new ProductDto("White Wine", "Beverage", 14.99, false, List.of("alcohol", "white", "europe"), LocalDate.of(2024, 6, 18)),
                new ProductDto("Champagne", "Beverage", 49.99, true, List.of("alcohol", "sparkling", "france"), LocalDate.of(2024, 12, 31)),
                new ProductDto("Whiskey", "Beverage", 89.99, true, List.of("alcohol", "aged", "scotland"), LocalDate.of(2023, 11, 20)),
                new ProductDto("Gin", "Beverage", 29.99, true, List.of("alcohol", "herbal", "england"), LocalDate.of(2024, 1, 5)),
                new ProductDto("Vodka", "Beverage", 15.49, false, List.of("alcohol", "clear", "russia"), LocalDate.of(2023, 8, 12)),
                new ProductDto("Coffee Beans", "Grocery", 11.50, true, List.of("organic", "arabica", "ethiopia"), LocalDate.of(2024, 10, 1)),
                new ProductDto("Green Tea", "Grocery", 8.30, true, List.of("natural", "loose leaf", "japan"), LocalDate.of(2025, 1, 12)),
                new ProductDto("Olive Oil", "Grocery", 9.49, true, List.of("organic", "cold-pressed"), LocalDate.of(2023, 11, 3)),
                new ProductDto("Tomato Sauce", "Grocery", 2.99, true, List.of("vegetarian", "italy"), LocalDate.of(2023, 5, 7)),
                new ProductDto("Instant Noodles", "Grocery", 1.50, false, List.of("asian", "fast"), LocalDate.of(2025, 2, 4)),
                new ProductDto("Chocolate Bar", "Grocery", 2.25, true, List.of("sweet", "dessert", "cocoa"), LocalDate.of(2024, 3, 2)),
                new ProductDto("Granola", "Grocery", 4.99, true, List.of("healthy", "breakfast"), LocalDate.of(2024, 7, 17)),
                new ProductDto("Protein Powder", "Grocery", 24.99, true, List.of("fitness", "supplement"), LocalDate.of(2024, 6, 1)),
                new ProductDto("Notebook A5", "Stationery", 3.20, true, List.of("paper", "recycled"), LocalDate.of(2025, 2, 11)),
                new ProductDto("Sketchbook", "Stationery", 6.80, false, List.of("art", "paper", "spiral"), LocalDate.of(2023, 9, 22)),
                new ProductDto("Erasable Pen", "Stationery", 1.90, true, List.of("writing", "ink"), LocalDate.of(2024, 5, 25)),
                new ProductDto("Mechanical Pencil", "Stationery", 2.50, true, List.of("lead", "drawing"), LocalDate.of(2024, 12, 15)),
                new ProductDto("Smartphone X", "Electronics", 699.00, true, List.of("android", "5g", "oled"), LocalDate.of(2025, 3, 15)),
                new ProductDto("Bluetooth Speaker", "Electronics", 59.49, true, List.of("wireless", "portable", "usb-c"), LocalDate.of(2024, 1, 20)),
                new ProductDto("Mechanical Keyboard", "Electronics", 88.99, true, List.of("rgb", "gaming", "wired"), LocalDate.of(2024, 12, 5)),
                new ProductDto("4K Monitor", "Electronics", 299.99, false, List.of("display", "ultraHD"), LocalDate.of(2023, 7, 10)),
                new ProductDto("Wireless Mouse", "Electronics", 24.50, true, List.of("usb", "silent"), LocalDate.of(2024, 8, 8)),
                new ProductDto("USB-C Cable", "Electronics", 5.00, true, List.of("accessory", "charging"), LocalDate.of(2025, 1, 9)),
                new ProductDto("Desk Lamp", "Electronics", 14.75, true, List.of("led", "adjustable"), LocalDate.of(2024, 9, 28)),
                new ProductDto("LED Strip", "Electronics", 12.00, false, List.of("light", "decoration"), LocalDate.of(2024, 6, 21)),
                new ProductDto("Power Bank", "Electronics", 34.99, true, List.of("charging", "portable"), LocalDate.of(2025, 4, 2)),
                new ProductDto("Smartwatch", "Electronics", 149.99, false, List.of("fitness", "wearable"), LocalDate.of(2023, 12, 9)),
                new ProductDto("Gaming Chair", "Furniture", 219.00, true, List.of("ergonomic", "adjustable"), LocalDate.of(2024, 2, 11)),
                new ProductDto("Desk Organizer", "Furniture", 18.99, true, List.of("storage", "minimalist"), LocalDate.of(2024, 8, 3)),
                new ProductDto("Wooden Table", "Furniture", 299.99, false, List.of("oak", "handmade"), LocalDate.of(2023, 11, 30)),
                new ProductDto("Floor Lamp", "Furniture", 89.99, true, List.of("modern", "led"), LocalDate.of(2025, 1, 15)),
                new ProductDto("Sofa", "Furniture", 549.00, true, List.of("leather", "modern"), LocalDate.of(2024, 5, 22)),
                new ProductDto("Curtains", "Furniture", 39.99, true, List.of("linen", "blue"), LocalDate.of(2023, 6, 14)),
                new ProductDto("Rug", "Furniture", 79.99, false, List.of("wool", "soft"), LocalDate.of(2024, 4, 17)),
                new ProductDto("Yoga Mat", "Sports", 24.99, true, List.of("fitness", "eco"), LocalDate.of(2025, 2, 5)),
                new ProductDto("Dumbbells", "Sports", 49.99, true, List.of("workout", "adjustable"), LocalDate.of(2024, 9, 9)),
                new ProductDto("Bicycle Helmet", "Sports", 59.99, true, List.of("safety", "lightweight"), LocalDate.of(2024, 7, 19)),
                new ProductDto("Running Shoes", "Sports", 89.95, false, List.of("lightweight", "breathable"), LocalDate.of(2024, 3, 26)),
                new ProductDto("Water Bottle", "Sports", 14.50, true, List.of("bpa-free", "durable"), LocalDate.of(2023, 10, 8)),
                new ProductDto("Tent", "Sports", 129.00, false, List.of("camping", "4-person"), LocalDate.of(2023, 5, 1)),
                new ProductDto("Hiking Backpack", "Sports", 89.90, true, List.of("waterproof", "lightweight"), LocalDate.of(2024, 6, 11)),
                new ProductDto("Sunscreen", "Cosmetics", 7.99, true, List.of("spf50", "face"), LocalDate.of(2024, 8, 21)),
                new ProductDto("Lip Balm", "Cosmetics", 3.49, true, List.of("moisturizing", "natural"), LocalDate.of(2023, 9, 13)),
                new ProductDto("Perfume", "Cosmetics", 69.00, true, List.of("floral", "france"), LocalDate.of(2023, 12, 2)),
                new ProductDto("Shampoo", "Cosmetics", 6.75, false, List.of("herbal", "sulfate-free"), LocalDate.of(2023, 11, 7)),
                new ProductDto("Conditioner", "Cosmetics", 6.75, true, List.of("moisturizing", "natural"), LocalDate.of(2024, 1, 25)),
                new ProductDto("Face Cream", "Cosmetics", 22.99, true, List.of("hydrating", "daily"), LocalDate.of(2025, 3, 20)),
                new ProductDto("Deodorant", "Cosmetics", 4.50, true, List.of("roll-on", "fresh"), LocalDate.of(2024, 2, 2)),
                new ProductDto("Nail Polish", "Cosmetics", 5.25, true, List.of("color", "glossy"), LocalDate.of(2024, 5, 5)),
                new ProductDto("Toothpaste", "Cosmetics", 3.20, true, List.of("whitening", "mint"), LocalDate.of(2023, 8, 18)),
                new ProductDto("Toothbrush", "Cosmetics", 2.99, true, List.of("soft", "manual"), LocalDate.of(2025, 1, 1)),
                new ProductDto("Mouthwash", "Cosmetics", 5.80, false, List.of("antibacterial", "minty"), LocalDate.of(2024, 10, 7)),
                new ProductDto("Hair Dryer", "Electronics", 39.99, true, List.of("ionic", "travel"), LocalDate.of(2023, 7, 22)),
                new ProductDto("E-Reader", "Electronics", 129.99, true, List.of("e-ink", "wifi"), LocalDate.of(2024, 11, 11)),
                new ProductDto("Kindle Cover", "Electronics", 19.99, true, List.of("leather", "protective"), LocalDate.of(2024, 6, 13)),
                new ProductDto("Book Light", "Electronics", 12.75, true, List.of("usb", "clip-on"), LocalDate.of(2023, 4, 4)),
                new ProductDto("Notebook Sleeve", "Accessories", 16.00, true, List.of("neoprene", "13-inch"), LocalDate.of(2023, 6, 10)),
                new ProductDto("Backpack", "Accessories", 54.99, true, List.of("padded", "college"), LocalDate.of(2024, 9, 2)),
                new ProductDto("Sunglasses", "Accessories", 25.00, true, List.of("uv", "polarized"), LocalDate.of(2025, 2, 14)),
                new ProductDto("Leather Belt", "Accessories", 29.95, true, List.of("brown", "classic"), LocalDate.of(2023, 11, 6)),
                new ProductDto("Wristwatch", "Accessories", 139.00, true, List.of("analog", "minimal"), LocalDate.of(2024, 3, 30)),
                new ProductDto("Wallet", "Accessories", 44.50, true, List.of("slim", "rfid"), LocalDate.of(2025, 1, 28)),
                new ProductDto("Key Organizer", "Accessories", 9.99, true, List.of("metal", "compact"), LocalDate.of(2023, 10, 21)),
                new ProductDto("Scarf", "Accessories", 15.00, false, List.of("wool", "warm"), LocalDate.of(2024, 11, 9)),
                new ProductDto("Gloves", "Accessories", 12.00, true, List.of("touchscreen", "winter"), LocalDate.of(2024, 12, 16)),
                new ProductDto("Beanie", "Accessories", 10.99, true, List.of("knit", "black"), LocalDate.of(2024, 1, 7)),
                new ProductDto("Umbrella", "Accessories", 17.89, true, List.of("folding", "windproof"), LocalDate.of(2024, 8, 25)),
                new ProductDto("Luggage", "Travel", 119.00, true, List.of("carry-on", "hard shell"), LocalDate.of(2023, 12, 28)),
                new ProductDto("Travel Pillow", "Travel", 22.50, true, List.of("memory foam", "compact"), LocalDate.of(2024, 7, 1)),
                new ProductDto("Passport Holder", "Travel", 9.49, true, List.of("leather", "rfid"), LocalDate.of(2025, 1, 1))
        );
    }
}
