package ru.geekbrains.shopspringboot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.shopspringboot.dto.ProductPaging;
import ru.geekbrains.shopspringboot.dto.ProductRequest;
import ru.geekbrains.shopspringboot.dto.ProductResponse;
import ru.geekbrains.shopspringboot.dto.ResponseMessage;
import ru.geekbrains.shopspringboot.entities.Product;
import ru.geekbrains.shopspringboot.exceptions.NotFoundException;
import ru.geekbrains.shopspringboot.services.ProductService;
import ru.geekbrains.shopspringboot.services.filters.ProductFilter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductRestController {

    private final Integer DEFAULT_PAGE_SIZE = 10;

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseMessage> add(@RequestBody Product product) {
        long id = productService.save(product);
        ResponseMessage message = new ResponseMessage();
        message.setId(id);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PostMapping("/page")
    public ProductResponse findAll(@RequestBody ProductRequest request) {
        Pageable pageable;
        if (request.getPage() != null) pageable = PageRequest.of(request.getPage(),
                request.getPageSize() == null ? DEFAULT_PAGE_SIZE : request.getPageSize());
        else pageable = Pageable.unpaged();
        ProductFilter filter = request.getFilter() == null ? new ProductFilter() : request.getFilter();
        Page<Product> page = productService.findAll(filter, pageable);
        return ProductResponse
                .builder()
                .data(page.getContent())
                .paging(ProductPaging
                        .builder()
                        .page(page.getNumber())
                        .totalPages(page.getTotalPages())
                        .pageSize(page.getSize())
                        .totalElements(page.getTotalElements())
                        .build())
                .build();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") long id) {
        return productService.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id=" + id + " not found"));
    }

    @PutMapping
    public void update(@RequestBody Product product) {
        findById(product.getId());
        productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        findById(id);
        productService.delete(id);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseMessage> exceptionHandler(NotFoundException e) {
        ResponseMessage message = new ResponseMessage();
        message.setMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> exceptionHandler(Exception e) {
        ResponseMessage message = new ResponseMessage();
        message.setMessage("Internal server error");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
