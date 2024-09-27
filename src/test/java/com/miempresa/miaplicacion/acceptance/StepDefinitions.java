package com.miempresa.miaplicacion.acceptance;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

    private WebDriver driver;

    @Given("que estoy en la página de creación de productos")
    public void que_estoy_en_la_pagina_de_creacion_de_productos() {
        System.setProperty("webdriver.chrome.driver", "/ruta/al/chromedriver"); // Actualiza esta ruta
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/crear");
    }

    @When("ingreso los datos del producto y envío el formulario")
    public void ingreso_los_datos_del_producto_y_envio_el_formulario() {
        // Implementa la interacción con el formulario usando Selenium
    }

    @Then("el producto debe ser guardado y redirigido a la lista")
    public void el_producto_debe_ser_guardado_y_redirigido_a_la_lista() {
        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/", currentUrl);
        driver.quit();
    }
}
