package tests;

import base.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import utils.PropertyReader;

import java.time.Duration;

public class LoginTests extends BaseTest {

    @Test
    public void deveRealizarLoginComSucesso() {
        LoginPage loginPage = new LoginPage(driver);

        String usuario = PropertyReader.get("usuario.valido");
        String senha = PropertyReader.get("senha.valida");

        loginPage.logarComo(usuario, senha);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean redirecionado = wait.until(ExpectedConditions.urlContains("/my_view_page.php"));

        assertTrue(redirecionado, "Login falhou: não foi redirecionado para a página correta!");
    }

    @Test
    public void deveRealizarLoginComUsuarioMaiusculo() {
        LoginPage loginPage = new LoginPage(driver);

        String usuarioMaiusculo = PropertyReader.get("usuario.valido").toUpperCase();
        String senhaValida = PropertyReader.get("senha.valida");

        loginPage.logarComo(usuarioMaiusculo, senhaValida);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean redirecionado = wait.until(ExpectedConditions.urlContains("/my_view_page.php"));

        assertTrue(redirecionado, "Login falhou com usuário em maiúsculo!");
    }

    @Test
    public void naoDeveLogarComCredenciaisInvalidas() {
        LoginPage loginPage = new LoginPage(driver);

        String usuario = PropertyReader.get("usuario.invalido");
        String senha = PropertyReader.get("senha.invalida");

        loginPage.logarComo(usuario, senha);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mensagemErro = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert.alert-danger > p")));

        String mensagemEsperada = "Sua conta pode estar desativada ou bloqueada ou o nome de usuário e a senha que você digitou não estão corretos.";
        String mensagemAtual = mensagemErro.getText();

        assertEquals(mensagemEsperada, mensagemAtual, "Mensagem de erro para login inválido está incorreta!");
    }

    @Test
    public void deveRealizarLogoutComSucesso() {
        LoginPage loginPage = new LoginPage(driver);

        String usuario = PropertyReader.get("usuario.valido");
        String senha = PropertyReader.get("senha.valida");

        loginPage.logarComo(usuario, senha);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement menuUsuario = wait.until(ExpectedConditions.elementToBeClickable(By.className("user-info")));
        menuUsuario.click();

        WebElement linkLogout = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'logout_page.php')]")));
        linkLogout.click();

        boolean voltouParaTelaLogin = wait.until(ExpectedConditions.urlContains("login_page.php"));
        assertTrue(voltouParaTelaLogin, "Usuário não foi redirecionado para a tela de login após logout!");
    }

    @Test
    public void naoDevePermitirLoginComUsuarioEmBranco() {
        LoginPage loginPage = new LoginPage(driver);

        WebElement botaoEntrar = driver.findElement(By.cssSelector("input[type='submit']"));
        botaoEntrar.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mensagemErro = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert.alert-danger > p")));

        String mensagemEsperada = "Sua conta pode estar desativada ou bloqueada ou o nome de usuário e a senha que você digitou não estão corretos.";
        String mensagemAtual = mensagemErro.getText().trim();

        assertEquals(mensagemEsperada, mensagemAtual, "Sua conta pode estar desativada ou bloqueada ou o nome de usuário e a senha que você digitou não estão corretos.");
    }


    @Test
    public void naoDevePermitirLoginComSenhaEmBranco() {
        LoginPage loginPage = new LoginPage(driver);

        String usuarioValido = PropertyReader.get("usuario.valido");
        loginPage.preencherUsuario(usuarioValido);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement botaoProximo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
        botaoProximo.click();

        wait.until(ExpectedConditions.urlContains("login_password_page.php"));

        WebElement botaoEntrar = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
        botaoEntrar.click();

        WebElement mensagemErro = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert.alert-danger > p")));

        String mensagemEsperada = "Sua conta pode estar desativada ou bloqueada ou o nome de usuário e a senha que você digitou não estão corretos.";
        assertEquals("Sua conta pode estar desativada ou bloqueada ou o nome de usuário e a senha que você digitou não estão corretos.", mensagemEsperada, mensagemErro.getText().trim());
    }
}
