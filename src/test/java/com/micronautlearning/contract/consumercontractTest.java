package com.micronautlearning.contract;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "MyCustomProviderContract", pactVersion = PactSpecVersion.V3)
public class consumercontractTest {

    @Pact(consumer = "MyCustomConsumerContract")
    public RequestResponsePact createpact(PactDslWithProvider builder){
       return builder
               .given("My micronaut application")
               .uponReceiving("Provide the data")
               .path("/products/1")
               .method("GET")
               .willRespondWith()
               .status(200)
               .body("""
                       {
                           "id": 1,
                           "name": "Blacktop Level",
                           "type": "COFFEE"
                       }""")
               .toPact();
    }
    @Test
    @PactTestFor(pactMethod = "createpact")
    void testStatusCodeForApplication(MockServer mockServer) throws IOException {
        URL url = new URL(mockServer.getUrl() + "/products/1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        assertThat(connection.getResponseCode()).isEqualTo(200);
    }

    @Pact(consumer = "MyCustomConsumerContract")
    public RequestResponsePact createPostPact(PactDslWithProvider builder) {
        return builder
                .given("Product creation is allowed")
                .uponReceiving("Create a new product")
                .path("/admin/products")
                .method("POST")
                .headers(
                        "Content-Type", "application/json; charset=UTF-8",
                        "Accept", "*/*"
                )
                .body(new PactDslJsonBody()
                        .numberType("id", 89)
                        .stringType("name", "ESPRESSO")
                        .stringType("type", "COFFEE"))
                .willRespondWith()
                .status(201)
                .headers(Map.of("Content-Type", "application/json"))
                .body(new PactDslJsonBody()
                        .stringType("message", "Product created successfully"))
                .toPact();
    }


    @Test
    @PactTestFor(pactMethod = "createPostPact")
    void testCreateProduct(MockServer mockServer) throws IOException {
        URL url = new URL(mockServer.getUrl() + "/admin/products");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "*/*");  // <-- Add this line

        String jsonInputString = """
    {
        "id": 89,
        "name": "ESPRESSO",
        "type": "COFFEE"
    }
    """;

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        assertThat(connection.getResponseCode()).isEqualTo(201);
    }


}
