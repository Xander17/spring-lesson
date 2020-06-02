package ru.geekbrains.shopspringboot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.shopspringboot.controllers.utils.PageNumbers;
import ru.geekbrains.shopspringboot.controllers.utils.UrlParamsFilter;
import ru.geekbrains.shopspringboot.entities.Product;
import ru.geekbrains.shopspringboot.services.ProductService;
import ru.geekbrains.shopspringboot.services.filters.ProductFilter;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final String DEFAULT_LINES_ON_PAGE = "5";
    private final int MAX_NEIGHBOR_PAGE_NUMBERS = 4;

    private final ProductService productService;

    @GetMapping
    public String productList(
            Model model,
            Optional<ProductFilter> productFilter,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_LINES_ON_PAGE) Integer pageSize) {
        ProductFilter filter = productFilter.orElse(new ProductFilter());
        Page<Product> products = productService.findAll(filter, PageRequest.of(page, pageSize));
        model.addAttribute("products", products);
        model.addAttribute("productFilter", filter);
        model.addAttribute("filterUrl", "/products?" + UrlParamsFilter.get(filter));
        model.addAttribute("pageNumbers", PageNumbers.get(products.getTotalPages() - 1, products.getNumber(), MAX_NEIGHBOR_PAGE_NUMBERS));
        return "products";
    }

    @GetMapping("add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping
    public String saveProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "product";
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("edit/{id}")
    public String editProduct(Model model, @PathVariable("id") long id) {
        Product product = productService.findById(id).orElse(null);
        if (product == null) return "redirect:/products";
        model.addAttribute("product", product);
        return "product";
    }

    @DeleteMapping("delete/{id}")
    public String deleteProduct(Model model, @PathVariable("id") long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
