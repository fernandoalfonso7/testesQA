package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    private By campoEmail = By.name("email");
    private By campoRecuperacaoUsuario = By.name("username");
    private By campoCaptcha = By.id("captcha-field");

    public void preencherUsuario(String usuario) {
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        campo.clear();
        campo.sendKeys(usuario);
    }

    public void clicarEmLogin() {
        WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        botao.click();
    }

    public void preencherSenha(String senha) {
        WebElement senhaCampo = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        senhaCampo.sendKeys(senha);
    }

    public void logarComo(String usuario, String senha) {
        preencherUsuario(usuario);
        clicarEmLogin(); // vai para tela de senha
        preencherSenha(senha);
        clicarEmLogin(); // envia login
    }

    // Ações para recuperação de senha
    public void acessarTelaRecuperacaoSenha(String usuario) {
        preencherUsuario(usuario);
        clicarEmLogin(); // necessário para exibir o link
        WebElement linkEsqueciSenha = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Perdeu a sua senha')]")));
        linkEsqueciSenha.click();
    }

    public void preencherUsuarioRecuperacaoInexistente(String usuario) {
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(campoRecuperacaoUsuario));
        campo.clear();
        campo.sendKeys(usuario);
    }

    public void preencherEmailRecuperacao(String email) {
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(campoEmail));
        campo.clear();
        campo.sendKeys(email);
    }

    public void submeterRecuperacaoSenha() {
        WebElement botaoEnviar = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        botaoEnviar.click();
    }

    public boolean isRedirecionadoParaTelaLoginAposRecuperacao() {
        return wait.until(ExpectedConditions.urlContains("return=lost_pwd.php"));
    }

    private By mensagemErroRecuperacao = By.xpath("//*[contains(text(),'APPLICATION ERROR #1903')]");

    public boolean isMensagemErroRecuperacaoExibida() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemErroRecuperacao)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private String gerarUsuarioDinamico() {
        return "usuario_" + System.currentTimeMillis();
    }

    private String gerarEmailDinamico() {
        return "email_" + System.currentTimeMillis() + "@teste.com";
    }

     public void acessarTelaCriarConta() {
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         WebElement botaoCriarConta = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("criar uma nova conta")));
         botaoCriarConta.click();
     }

    public void preencherDadosNovoUsuario(String usuario, String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement campoUsuario = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(campoEmail));

        campoUsuario.sendKeys(usuario);
        campo.sendKeys(email);
    }

    public void submeterCadastroComCaptcha(String captcha) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement captchaCampo = wait.until(ExpectedConditions.visibilityOfElementLocated(campoCaptcha));
        WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(loginButton));

        captchaCampo.sendKeys(captcha);
        botao.click();
    }



}
