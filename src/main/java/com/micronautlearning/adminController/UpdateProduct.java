package com.micronautlearning.adminController;

import com.micronautlearning.product.Product;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UpdateProduct (
        String name,
        Product.Type type
){}
