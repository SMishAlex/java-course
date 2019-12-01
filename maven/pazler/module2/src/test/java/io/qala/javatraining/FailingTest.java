package io.qala.javatraining;


import org.testng.annotations.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class FailingTest {

    @Test
    @SuppressWarnings("all")
    public void strangeFailure() throws Exception {
        try (InputStream file = ClassLoader.getSystemResourceAsStream("myresource.txt")) {
            assertEquals(file.read(), 49);
        };
    }
}
