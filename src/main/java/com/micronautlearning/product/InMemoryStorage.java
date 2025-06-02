package com.micronautlearning.product;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Singleton
public class InMemoryStorage {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryStorage.class);
    private final Map<Integer,Product> productMap = new HashMap<>();
    private final Faker faker = new Faker();

    @PostConstruct
    public void intialise(){
        IntStream.range(1,11).forEach(
                this::addproduct
        );
    }
    public void addproduct(int id){
        var product = new Product(id,faker.coffee().blendName(), Product.Type.COFFEE);
        productMap.put(id,product);
        LOG.trace("Product: {}",product);
    }
    public Product addproduct(Product product){
        productMap.put(product.id(),product);
        return productMap.get(product.id());
    }

    public Map<Integer, Product> getProductMap() {
        return productMap;
    }

    public Product deteleProduct(Integer id) {
        return productMap.remove(id);
    }
}
