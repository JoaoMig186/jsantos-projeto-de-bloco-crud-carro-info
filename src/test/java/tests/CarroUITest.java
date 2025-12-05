package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CarroFormPage;
import pages.CarroListPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarroUITest {

    private WebDriver driver;

    @BeforeAll
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setupTest() {
        ChromeOptions options = new ChromeOptions();

        String isCI = System.getenv("GITHUB_ACTIONS");
        if ("true".equals(isCI)) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-software-rasterizer");
            options.addArguments("--window-size=1920,1080");
            options.setBinary("/usr/bin/chromium-browser");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void deveCadastrarNovoCarroComSucesso() {
        CarroFormPage formPage = new CarroFormPage(driver);
        formPage.open();
        formPage.fillForm("Toyota", "Corolla", "2022", "150", "1.8");
        formPage.selectCombustivel("diesel");
        formPage.selectCombustivel("gasolina");
        formPage.submit();
        Assertions.assertTrue(driver.getCurrentUrl().contains("/carros"));
    }

    /*@Test
    public void deveEditarCarroComSucesso() {
        CarroFormPage formPage = new CarroFormPage(driver);
        formPage.open();
        formPage.fillForm("Fiat", "Uno", "2005", "60", "1.0");
        formPage.selectCombustivel("GASOLINA");
        formPage.submit();

        CarroListPage listPage = new CarroListPage(driver);
        listPage.open();
        Assertions.assertTrue(listPage.containsCarro("Uno", "Fiat"));

        listPage.clickEditarPrimeiro();

        CarroFormPage formEditar = new CarroFormPage(driver);
        formEditar.clearFields();
        formEditar.fillForm("Fiat", "Palio", "2007", "70", "1.4");
        formEditar.selectCombustivel("ETANOL");
        formEditar.submit();

        listPage.open();
        Assertions.assertTrue(listPage.containsCarro("Palio", "Fiat"));
    }

    @Test
    public void deveExcluirCarroComSucesso() {
        CarroFormPage formPage = new CarroFormPage(driver);
        formPage.open();
        formPage.fillForm("Chevrolet", "Celta", "2010", "80", "1.0");
        formPage.selectCombustivel("GASOLINA");
        formPage.submit();

        CarroListPage listPage = new CarroListPage(driver);
        listPage.open();
        Assertions.assertTrue(listPage.containsCarro("Celta", "Chevrolet"), 
            "Carro deve existir antes da exclusão");

        listPage.clickExcluirPrimeiro();
        
        Assertions.assertFalse(listPage.containsCarro("Celta", "Chevrolet"), 
            "Carro não deve existir após a exclusão");
    }*/
}
