package com.ecommerce.store.controllers;

import com.ecommerce.store.dtos.ProductDto;
import com.ecommerce.store.entities.Product;
import com.ecommerce.store.mappers.ProductMapper;
import com.ecommerce.store.repositories.CategoryRepository;
import com.ecommerce.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@Getter
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;

    private ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

//    @GetMapping
//    public List<ProductDto> getAllProducts()
//    {
//        return productRepository.findAll()
//                .stream().map(productMapper::toDto).toList();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id)
    {
        var product= productRepository.findById(id).orElse(null);
        if(product==null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(productMapper.toDto(product));
    }

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(name="categoryId",required = false) Byte categoryId)
    {
        List<Product> products;
        if(categoryId!=null)
            products= productRepository.findByCategoryId(categoryId);

        else
            products= productRepository.findAllWithCategory();

        return products.stream().map(productMapper::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, UriComponentsBuilder uriBuilder){
        var category= categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category==null)
            return ResponseEntity.badRequest().build();

        var product= productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());
        var uri= uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){

        var category= categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category==null)
            return ResponseEntity.badRequest().build();
        var product= productRepository.findById(id).orElse(null);
        if(product==null)
            return ResponseEntity.notFound().build();
        
        productMapper.update(productDto,product);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());
        return ResponseEntity.ok(productDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id)
    {
        var product= productRepository.findById(id).orElse(null);
        if(product==null)
            return ResponseEntity.notFound().build();
        productRepository.delete(product);
        return ResponseEntity.noContent().build();

    }
}
