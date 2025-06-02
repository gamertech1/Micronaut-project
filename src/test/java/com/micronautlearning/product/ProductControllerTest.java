package com.micronautlearning.product;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.JsonMapper;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class ProductControllerTest {

    private static final Logger Log = LoggerFactory.getLogger(ProductControllerTest.class);
    @Inject
    @Client("/products")
    private HttpClient httpClient;

    @Inject
    JsonMapper jsonMapper;

    @Test
    void productcount() throws IOException {
        JsonNode result = httpClient.toBlocking().retrieve("/", JsonNode.class);
        Log.debug("Message: {}",logProduct(result));
        assertEquals(10,result.size());
    }

    private String logProduct(JsonNode result) throws IOException {
        return jsonMapper.writeValueAsString(result);
    }
    @Test
    void productbyid(){
        Product result = httpClient.toBlocking().retrieve("/1", Product.class);
        assertEquals(1,result.id());
        assertEquals(Product.Type.COFFEE,result.type());
        assertNotNull(result.name());
    }
    @Test
    void maxQueryParameter() throws IOException {
        JsonNode response = httpClient.toBlocking().retrieve("/filter?max=4",JsonNode.class);
        Log.debug("4 Products: {}",logProduct(response));
        assertEquals(4,response.size());
    }
    @Test
    void maxAndOffsetQueryParameter() throws IOException {
        JsonNode response = httpClient.toBlocking().retrieve("/filter?max=5&offset=3",JsonNode.class);
        Log.debug("5 Products with offset 3: {}",logProduct(response));
        assertEquals(5,response.size());
        assertEquals(4,response.get(0).get("id").getIntValue());
        assertEquals(5,response.get(1).get("id").getIntValue());
    }

}