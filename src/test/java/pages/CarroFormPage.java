package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CarroFormPage {

    private WebDriver driver;

    @FindBy(name = "marca")
    private WebElement campoMarca;

    @FindBy(name = "modelo")
    private WebElement campoModelo;

    @FindBy(name = "ano")
    private WebElement campoAno;

    @FindBy(name = "cavalos")
    private WebElement campoCavalos;

    @FindBy(name = "cilindrada")
    private WebElement campoCilindrada;

    @FindBy(css = "form button[type='submit']")
    private WebElement botaoSubmit;

    public CarroFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://localhost:7000/carros/new");
    }

    public void fillForm(String marca, String modelo, String ano, String cavalos, String cilindrada) {
        campoMarca.clear();
        campoMarca.sendKeys(marca);

        campoModelo.clear();
        campoModelo.sendKeys(modelo);

        limparCampo(campoAno);
        campoAno.sendKeys(ano);

        limparCampo(campoCavalos);
        campoCavalos.sendKeys(cavalos);

        limparCampo(campoCilindrada);
        campoCilindrada.sendKeys(cilindrada);
    }

    public void selectCombustivel(String nome) {
        WebElement checkbox = driver.findElement(By.id("combustivel_" + nome.toUpperCase()));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void submit() {
        botaoSubmit.click();
    }

    private void limparCampo(WebElement campo) {
        try {
            campo.clear();
            campo.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = '';", campo);
        }
    }

    public void clearFields() {
        driver.findElement(By.name("marca")).clear();
        driver.findElement(By.name("modelo")).clear();
        driver.findElement(By.name("ano")).clear();
        driver.findElement(By.name("cavalos")).clear();
        driver.findElement(By.name("cilindrada")).clear();
    }
}
