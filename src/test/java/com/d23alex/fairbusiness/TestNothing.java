package com.d23alex.fairbusiness;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FairBusinessApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TestNothing {
    @Test
    public void testNothing() {
        assertEquals(1, 1);
    }
}
