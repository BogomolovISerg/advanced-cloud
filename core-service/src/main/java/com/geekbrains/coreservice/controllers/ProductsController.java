package com.geekbrains.coreservice.controllers;

import com.geekbrains.coreservice.converters.ProductConverter;
import com.geekbrains.coreservice.dto.ProductDto;
import com.geekbrains.coreservice.entities.Product;
import com.geekbrains.coreservice.exceptions.ResourceNotFoundException;
import com.geekbrains.coreservice.services.ProductsService;
import com.geekbrains.coreservice.validators.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name="ProductsController", description="ProductsController")
public class ProductsController {
    @Schema(description = "productsService")
    private final ProductsService productsService;
    @Schema(description = "productConverter")
    private final ProductConverter productConverter;
    @Schema(description = "productValidator")
    private final ProductValidator productValidator;

    @GetMapping
    @Operation(summary = "getAllProducts")
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return productsService.findAll(minPrice, maxPrice, titlePart, page).map(
                p -> productConverter.entityToDto(p)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "getProductById")
    public ProductDto getProductById(@PathVariable Long id) {
        Product product = productsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    @PostMapping
    @Operation(summary = "saveNewProduct")
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product = productsService.save(product);
        return productConverter.entityToDto(product);
    }

    @PutMapping
    @Operation(summary = "updateProduct")
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productsService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "deleteById")
    public void deleteById(@PathVariable Long id) {
        productsService.deleteById(id);
    }
}
