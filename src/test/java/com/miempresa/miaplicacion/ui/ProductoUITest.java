package com.miempresa.miaplicacion.ui;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductoUITest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/ruta/al/chromedriver"); // Actualiza esta ruta
        driver = new ChromeDriver();
    }

    @Test
    public void testTituloPagina() {
        driver.get("http://localhost:8080/");
        String title = driver.getTitle();
        assertEquals("Lista de Productos", title);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
