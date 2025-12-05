package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.javalin.Javalin;
import org.jsantostp1.Main;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebTest {

    private static Javalin app;
    private WebDriver driver;

    @BeforeAll
    void startServer() throws IOException, IOException {
        app = Main.startServer();
    }

    @AfterAll
    void stopServer() {
        if (app != null) app.stop();
    }

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void testarPaginaHome() {
        driver.get("http://localhost:7000/carros");

        String titulo = driver.getTitle();

        assertEquals("Lista de Carros", titulo);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
