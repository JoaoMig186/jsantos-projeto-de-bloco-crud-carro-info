package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CarroListPage {

    private WebDriver driver;
    private final String URL = "http://localhost:7000/carros";

    public CarroListPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(URL);
    }

    public boolean containsCarro(String modelo, String marca) {
        return driver.getPageSource().contains(modelo) && driver.getPageSource().contains(marca);
    }

    public void clickEditarPrimeiro() {
        WebElement botaoEditar = driver.findElement(By.cssSelector("a.edit-btn"));
        botaoEditar.click();
    }

    public void clickExcluirPrimeiro() {
        WebElement botaoExcluir = driver.findElement(By.cssSelector("form[action^='/carros/delete/'] button"));
        botaoExcluir.click();
    }

    public List<WebElement> listarLinhas() {
        return driver.findElements(By.cssSelector("table tr"));
    }
}
