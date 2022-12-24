package com.example.ejercicio101112.controller;

import com.example.ejercicio101112.entity.Laptop;
import com.example.ejercicio101112.repository.LaptopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static com.example.ejercicio101112.utils.UtilsTest.getRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    @Autowired
    private LaptopRepository repository;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;


    private       TestRestTemplate restTemplate;
    private final String           url = "/api/laptops";
    @BeforeEach
    void setUp () {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        restTemplate        = new TestRestTemplate(restTemplateBuilder, "admin", "123");
        if (repository.count() == 0){
            Laptop lenovo = new Laptop(null, "Lenovo");
            Laptop     bgh    = new Laptop(null, "BGH");

            repository.save(lenovo);
            repository.save(bgh);
        }
    }

    @Test
    void getAll () {
        ResponseEntity<Laptop[]> res = restTemplate.getForEntity(url, Laptop[].class);

        /* Assert */
        if (res.getBody() == null){
            fail("Response Body was null");
        }

        assertAll(
          () -> assertEquals(OK, res.getStatusCode()),
          () -> assertEquals(2, res.getBody().length)
        );

    }

    @Test
    void getById () {
        ResponseEntity<Laptop> res = restTemplate.getForEntity(url+"/1", Laptop.class);

        /* Assert */
        if (res.getBody() == null){
            fail("Response Body was null");
        }

        assertAll(
          () -> assertEquals(OK, res.getStatusCode()),
          () -> assertEquals(1, res.getBody().getId()),
          () -> assertEquals("Lenovo", res.getBody().getName())
        );
    }

    @Test
    void getByIdNotFound () {
        ResponseEntity<String> res = restTemplate.getForEntity(url+"/50", String.class);

        /* Assert */
        if (res.getBody() == null){
            fail("Response Body was null");
        }

        assertAll(
          () -> assertEquals(NOT_FOUND, res.getStatusCode()),
          () -> assertEquals("Laptop's id not found", res.getBody())
        );
    }

    @Test
    void create () {
        /* Arrange */
        String payload = """
          {
            "name":"HP"
          }
          """;

        HttpEntity<String> request = getRequest(payload);

        /* Act */
        ResponseEntity<Laptop> res = restTemplate.exchange(url, POST, request, Laptop.class);

        Laptop lap = res.getBody();

        /* Assert */
        if (lap == null){
            fail("Response Body was null");
        }

        assertAll(
          () -> assertEquals(CREATED, res.getStatusCode()),
          () -> assertEquals(3L, lap.getId()),
          () -> assertEquals("HP", lap.getName())
        );
    }

    @Test
    void update () {
        /* Arrange */
        String payload = """
          {
            "name":"ReDragon"
          }
          """;

        HttpEntity<String> request = getRequest(payload);

        /* Act */
        ResponseEntity<Laptop> res = restTemplate.exchange(url+"/2", PUT, request, Laptop.class);

        Laptop lap = res.getBody();

        /* Assert */
        if (lap == null){
            fail("Response Body was null");
        }

        assertAll(
          () -> assertEquals(OK, res.getStatusCode()),
          () -> assertEquals(2L, lap.getId()),
          () -> assertEquals("ReDragon", lap.getName())
        );
    }

    @Test
    void delete () {
        ResponseEntity<String> res = restTemplate.exchange(url+"/1", DELETE, null, String.class);

        /* Assert */
        assertAll(
          () -> assertEquals(NO_CONTENT, res.getStatusCode()),
          () -> assertFalse(repository.existsById(1L))
        );
    }

    @Test
    void deleteAll () {
        ResponseEntity<String> res = restTemplate.exchange(url, DELETE, null, String.class);

        /* Assert */
        assertAll(
          () -> assertEquals(NO_CONTENT, res.getStatusCode()),
          () -> assertEquals(0, repository.count())
        );
    }
}