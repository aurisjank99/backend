package com.example.backend.controller;

import com.example.backend.dao.Product;
import com.example.backend.dto.ProductInfo;
import com.example.backend.dto.Request;
import com.example.backend.dto.Response;
import com.example.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    ProductService productService;


    @GetMapping(value = "/products")
    @ResponseBody
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(value = "/product/{id}")
    @ResponseBody
    public ProductInfo getProductPrice(@PathVariable double id) {
        return productService.getProductPrices(id);
    }

    @PostMapping(value = "/calculate")
    @ResponseBody
    public ResponseEntity<Response> calculatePrice(@RequestParam double productId,
                                                   @RequestParam Long commitment,
                                                   @RequestParam(required = false) Optional<String> returnMonths) {
        Request request;
        if (returnMonths.isPresent() && returnMonths.get().equals("null")) {
            request = new Request(productId, commitment, null);
        } else {
            request = new Request(productId, commitment, returnMonths.get());
        }
        return productService.calculatePrice(request);
    }

}
