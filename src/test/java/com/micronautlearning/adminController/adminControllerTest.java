package com.micronautlearning.adminController;

import com.micronautlearning.product.InMemoryStorage;
import com.micronautlearning.product.Product;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class adminControllerTest {

    @Inject
    @Client("/admin/products")
    HttpClient client;

    @Inject
    InMemoryStorage storage;

    @Test
    void addproducttest(){
        var addProduct = new Product(123,"Black Tea", Product.Type.TEA);
        storage.getProductMap().remove(addProduct.id());
        assertNull(storage.getProductMap().get(addProduct.id()));
        var response = client.toBlocking().exchange(HttpRequest.POST("/",addProduct), Product.class);
        assertEquals(HttpStatus.CREATED,response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(addProduct.id(),response.getBody().get().id());
        assertEquals(addProduct.name(),response.getBody().get().name());
        assertEquals(addProduct.type(),response.getBody().get().type());
    }
    @Test
    void addproductconficttest(){
        var addProduct = new Product(123,"Green Tea", Product.Type.TEA);
        storage.getProductMap().remove(addProduct.id());
        assertNull(storage.getProductMap().get(addProduct.id()));
        var response = client.toBlocking().exchange(HttpRequest.POST("/",addProduct), Product.class);
        assertEquals(HttpStatus.CREATED,response.getStatus());
        var conflict = assertThrows(HttpClientResponseException.class,
                ()-> client.toBlocking().exchange(HttpRequest.POST("/",addProduct)));
        assertEquals(HttpStatus.CONFLICT,conflict.getStatus());
    }

    @Test
    void testupdate(){
        var update = new Product(1,"Latte", Product.Type.COFFEE);
        storage.getProductMap().put(update.id(),update);
        assertEquals(update,storage.getProductMap().get(update.id()));
        var updateproduct = new UpdateProduct("Black Coffee", Product.Type.COFFEE);
        var response = client.toBlocking().exchange(HttpRequest.PUT("/" + update.id(),updateproduct),Product.class);
        assertEquals(HttpStatus.OK,response.status());
        var result = storage.getProductMap().get(update.id());
        assertEquals(updateproduct.name(),result.name());
        assertEquals(updateproduct.type(),result.type());
    }

    @Test
    void createproductwithPut(){
        var productid = 123;
        storage.getProductMap().remove(productid);
        assertNull(storage.getProductMap().get(productid));
        var productupdate = new UpdateProduct("Green Tea", Product.Type.TEA);
        var response = client.toBlocking().exchange(HttpRequest.PUT("/" + productid,productupdate),Product.class);
        var result = storage.getProductMap().get(productid);
        assertEquals(HttpStatus.OK,response.status());
        assertEquals(productid,result.id());
        assertEquals(productupdate.name(),result.name());
        assertEquals(productupdate.type(),result.type());
    }

    @Test
    void deleteProduct(){
        var createProduct = new Product(1,"Black Coffee", Product.Type.COFFEE);
        var createProductId = createProduct.id();
        assertNotNull(storage.getProductMap().get(createProduct.id()));
        final HttpResponse<Product> errorResponse = client.toBlocking().
                exchange(HttpRequest.DELETE("/" + createProduct.id()), Product.class);
       // var response = client.toBlocking().exchange(HttpRequest.DELETE("/" + createProduct.id()), Product.class);
//        var errorResponse = assertThrows(HttpClientResponseException.class,
//                ()-> client.toBlocking().exchange(HttpRequest.GET("/products/" + createProductId)));
        //assertEquals(HttpStatus.OK,response.status());
//        assertEquals(HttpStatus.NOT_FOUND,errorResponse.getStatus());
//        var deletingEmptyProduct = assertThrows(HttpClientResponseException.class,
//                ()-> client.toBlocking().exchange(HttpRequest.DELETE("/" + createProduct.id()), Argument.of(Product.class)));
//        assertEquals(HttpStatus.NOT_FOUND,deletingEmptyProduct.getStatus());
        assertEquals(HttpStatus.OK,errorResponse.status());
        assertTrue(errorResponse.getBody().isPresent());
        var deletingEmptyProduct = assertThrows(HttpClientResponseException.class,
                ()-> client.toBlocking().exchange(HttpRequest.DELETE("/" + createProduct.id()), Argument.of(Product.class)));
        assertEquals(HttpStatus.NOT_FOUND,deletingEmptyProduct.getStatus());
    }

}