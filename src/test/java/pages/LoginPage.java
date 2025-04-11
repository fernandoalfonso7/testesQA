package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Construtor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Elementos
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("input[type='submit']");

    // Ações
    public void preencherUsuario(String usuario) {
        driver.findElement(usernameField).sendKeys(usuario);
    }

    public void clicarEmLogin() {
        driver.findElement(loginButton).click();
    }

    public void preencherSenha(String senha) {
        WebElement senhaCampo = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        senhaCampo.sendKeys(senha);
    }

    public void logarComo(String usuario, String senha) {
        preencherUsuario(usuario);
        clicarEmLogin();
        preencherSenha(senha);
        clicarEmLogin();
    }
}

