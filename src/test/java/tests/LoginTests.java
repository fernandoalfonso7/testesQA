package tests;

import base.BaseTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import utils.PropertyReader;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTests extends BaseTest {

    @Test
    @DisplayName("Teste 01 - Deve realizar login com sucesso")
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
    @DisplayName("Teste 02 - Deve permitir login com nome de usuário em maiúsculo")
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
    @DisplayName("Teste 03 - Não deve permitir login com credenciais inválidas")
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
    @DisplayName("Teste 04 - Deve realizar logout com sucesso")
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
    @DisplayName("Teste 05 - Não deve permitir login com nome de usuário em branco")
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
    @DisplayName("Teste 06 - Não deve permitir login com senha em branco")
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

    @Test
    @DisplayName("Teste 07 - Deve solicitar recuperação de senha com sucesso")
    public void deveSolicitarRecuperacaoDeSenhaComSucesso() {
        LoginPage loginPage = new LoginPage(driver);

        String usuario = PropertyReader.get("usuario.valido");
        String email = PropertyReader.get("usuario.email");

        loginPage.acessarTelaRecuperacaoSenha(usuario);
        loginPage.preencherEmailRecuperacao(email);
        loginPage.submeterRecuperacaoSenha();

        assertTrue(loginPage.isRedirecionadoParaTelaLoginAposRecuperacao(),
                "A aplicação não redirecionou corretamente após a solicitação de recuperação de senha.");
    }

    @Test
    @DisplayName("Teste 08 - Não deve solicitar recuperação com usuário inexistente")
    public void naoDeveSolicitarRecuperacaoComUsuarioInexistente() {
        LoginPage loginPage = new LoginPage(driver);

        String usuario = PropertyReader.get("usuario.valido");
        String usuarioRecuperacao = PropertyReader.get("usuario.invalido");
        String email = PropertyReader.get("usuario.email");

        loginPage.acessarTelaRecuperacaoSenha(usuario);
        loginPage.preencherUsuarioRecuperacaoInexistente(usuarioRecuperacao);
        loginPage.preencherEmailRecuperacao(email);
        loginPage.submeterRecuperacaoSenha();

        assertTrue(loginPage.isMensagemErroRecuperacaoExibida(),
                "A mensagem de erro ao solicitar recuperação de senha com usuário inexistente não foi exibida.");
    }

    @Test
    @DisplayName("Teste 09 - Não deve permitir criação de usuário com captcha inválido")
    public void naoDeveCriarUsuarioComCaptchaInvalido() {
        LoginPage loginPage = new LoginPage(driver);

        String novoUsuario = "usuario_" + System.currentTimeMillis();
        String email = "email_" + System.currentTimeMillis() + "@teste.com";

        loginPage.acessarTelaCriarConta();
        loginPage.preencherDadosNovoUsuario(novoUsuario, email);
        loginPage.submeterCadastroComCaptcha("v123456");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mensagemErro = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert.alert-danger > p")));

        String mensagemEsperada = "APPLICATION ERROR #1904";
        String mensagemAtual = mensagemErro.getText();

        assertEquals(mensagemEsperada, mensagemAtual, "Mensagem de erro para CAPTCHA inválido está incorreta!");
    }


    @Test
    @DisplayName("Teste 10 - Criar novo usuário (parcial - CAPTCHA manual)")
    public void devePreencherCamposParaCriarNovoUsuario() {
        LoginPage loginPage = new LoginPage(driver);

        String novoUsuario = "usuario_" + System.currentTimeMillis();
        String email = "email_" + System.currentTimeMillis() + "@teste.com";

        loginPage.acessarTelaCriarConta();
        loginPage.preencherDadosNovoUsuario(novoUsuario, email);

        // Inserindo o texto no navegador com uma instrução JavaScript
        String texto = "⚠️ Por favor, preencha o CAPTCHA manualmente e clique em 'Criar Conta'.";

        String script = "var div = document.createElement('div');" +
                "div.textContent = \"" + texto + "\";" +  // Alterado para usar aspas duplas
                "div.style.position = 'fixed';" +
                "div.style.top = '20px';" +
                "div.style.left = '20px';" +
                "div.style.backgroundColor = 'yellow';" +  // Fundo amarelo para destaque
                "div.style.color = 'black';" +  // Cor do texto
                "div.style.padding = '10px';" +
                "div.style.zIndex = '1000';" +  // Garantir que o texto esteja sobre os outros elementos
                "div.style.fontSize = '16px';" +  // Tamanho da fonte
                "document.body.appendChild(div);";

        ((JavascriptExecutor) driver).executeScript(script);

        System.out.println("⚠️ Preencha o CAPTCHA manualmente e clique em 'Criar Conta' na tela do navegador.");

        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        boolean redirecionadoComSucesso = false;
        try {
            WebElement mensagemSucesso = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Registro de conta processado')]")));

            String mensagemEsperada = "Registro de conta processado";
            String mensagemAtual = mensagemSucesso.getText();

            assertTrue(mensagemAtual.contains(mensagemEsperada), "A mensagem de sucesso não foi exibida corretamente.");
            redirecionadoComSucesso = true;
        } catch (TimeoutException e) {
            // Se não encontrou a mensagem de sucesso, falha o teste
            System.out.println("⚠️ Falha no redirecionamento ou CAPTCHA não preenchido dentro do tempo.");
        }

       if (!redirecionadoComSucesso) {
            assertFalse(true, "O redirecionamento não ocorreu após o preenchimento do CAPTCHA.");
        }
    }

    @Test
    @DisplayName("Teste 11 - Criar novo usuário com email inválido")
    public void devePreencherCriarUsuarioEmailErrado() {
        LoginPage loginPage = new LoginPage(driver);

        String novoUsuario = "usuario_" + System.currentTimeMillis();
        String email = "email_" + System.currentTimeMillis(); // Email inválido (sem @)

        loginPage.acessarTelaCriarConta();
        loginPage.preencherDadosNovoUsuario(novoUsuario, email);

        // Exibe aviso no navegador para preenchimento manual do CAPTCHA
        String texto = "⚠️ Por favor, preencha o CAPTCHA manualmente e clique em 'Criar Conta'.";

        String script = "var div = document.createElement('div');" +
                "div.textContent = \"" + texto + "\";" +
                "div.style.position = 'fixed';" +
                "div.style.top = '20px';" +
                "div.style.left = '20px';" +
                "div.style.backgroundColor = 'yellow';" +
                "div.style.color = 'black';" +
                "div.style.padding = '10px';" +
                "div.style.zIndex = '1000';" +
                "div.style.fontSize = '16px';" +
                "document.body.appendChild(div);";

        ((JavascriptExecutor) driver).executeScript(script);
        System.out.println("⚠️ Preencha o CAPTCHA manualmente e clique em 'Criar Conta'.");

        // Aguarda o preenchimento manual
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        String mensagemEsperada = "APPLICATION ERROR #1200";

        try {
            WebElement mensagemErro = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'APPLICATION ERROR #1200')]")));

            String mensagemAtual = mensagemErro.getText();
            assertEquals(mensagemEsperada, mensagemAtual, "A mensagem de erro não corresponde à esperada.");

        } catch (TimeoutException e) {
            // Se a mensagem não apareceu, provavelmente o CAPTCHA não foi preenchido
            fail("⚠️ Verifique se o CAPTCHA foi preenchido corretamente.");
        }
    }

}
