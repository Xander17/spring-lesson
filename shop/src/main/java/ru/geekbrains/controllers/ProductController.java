package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.entities.Product;
import ru.geekbrains.repositories.ProductRepository;

@Controller
@RequestMapping("products")
public class ProductController {

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepo) {
        this.productRepository = productRepo;
    }

    @GetMapping
    public String productList(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping("add")
    public String addProduct(Product product) {
        productRepository.add(product);
        return "redirect:/products";
    }
}
