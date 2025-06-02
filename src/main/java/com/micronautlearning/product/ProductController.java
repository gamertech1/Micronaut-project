package com.micronautlearning.product;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller("/products")
public class ProductController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Inject
    private final InMemoryStorage inMemoryStorage;

    public ProductController(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    @Get
    public List<Product>getproducts(){
        return new ArrayList<>(inMemoryStorage.getProductMap().values());
    }
    @Get("{id}")
    public Product getproductbyid(@PathVariable Integer id){
        return inMemoryStorage.getProductMap().get(id);
    }

    @Get("/filter{?max,offset}")
    public List<Product>getlistbyQuery(@QueryValue Optional<Integer>max , @QueryValue Optional<Integer> offset){
        return inMemoryStorage.getProductMap().values().stream().skip(offset.orElse(0)).limit(max.orElse(0)).toList();
    }
    @Get("/type/{type}")
    public List<Product> getproductbyType(@PathVariable String type){
        var ProductType = Product.Type.valueOf(type);
        return inMemoryStorage.getProductMap().values().stream().filter(p -> ProductType.equals(p.type())).toList();
    }
    @Error
    public HttpResponse<JsonError>productNotFound(HttpRequest<?> request, IllegalArgumentException e){
      LOG.debug("Local Error Handler");
      var error = new JsonError("Invalid Product Type " + e.getMessage() + "Supported Types are " + Arrays.toString(Product.Type.values())).link(Link.SELF,Link.of(request.getUri()));
      return HttpResponse.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
