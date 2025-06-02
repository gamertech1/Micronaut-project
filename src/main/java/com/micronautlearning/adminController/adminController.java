package com.micronautlearning.adminController;

import com.micronautlearning.product.InMemoryStorage;
import com.micronautlearning.product.Product;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpException;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;

@Controller("/admin/products")
public class adminController {

    @Inject
    private final InMemoryStorage storage;

    public adminController(InMemoryStorage storage) {
        this.storage = storage;
    }
    @Status(HttpStatus.CREATED)
    @Post(consumes = MediaType.APPLICATION_JSON,produces = MediaType.APPLICATION_JSON)
    public Product createProduct(@Body Product product){
        if (storage.getProductMap().containsKey(product.id())){
            throw new HttpStatusException(HttpStatus.CONFLICT,"Product already exist " + product.id());
        }
        return storage.addproduct(product);
    }

    @Put("/{id}")
    public Product updatedProduct(@PathVariable Integer id ,@Body UpdateProduct product){
      var update = new Product(id,product.name(),product.type());
      return storage.addproduct(update);
    }

    @Delete("/{id}")
    public Product deleteProduct(@PathVariable Integer id){

        return storage.deteleProduct(id);
    }

}
