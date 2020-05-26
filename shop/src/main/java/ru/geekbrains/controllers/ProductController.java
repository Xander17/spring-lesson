package ru.geekbrains.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.entities.Product;
import ru.geekbrains.services.ProductService;

import java.math.BigDecimal;

@Controller
@RequestMapping("products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final int LINES_ON_PAGE = 5;

    private final ProductService productService;

    @GetMapping
    public String productList(
            Model model,
            @RequestParam(name = "min", required = false) BigDecimal min,
            @RequestParam(name = "max", required = false) BigDecimal max,
            @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Page<Product> products = productService.findAll(min, max, PageRequest.of(page, LINES_ON_PAGE));
        model.addAttribute("products", products);
//        String linkParam = "";
//        if (min != null) linkParam += "&min=" + min;
//        if (max != null) linkParam += "&max=" + max;
//        linkParam = linkParam.replaceFirst("&", "?");
//        model.addAttribute("linkParam", linkParam);
        return "products";
    }

    @GetMapping("add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping("add")
    public String addProduct(Product product) {
        productService.add(product);
        return "redirect:/products";
    }
}
