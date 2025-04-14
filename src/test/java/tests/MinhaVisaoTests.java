package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BugReportPage;
import pages.LoginPage;
import pages.VisaoPage;
import java.time.Duration;

import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

public class MinhaVisaoTests extends BaseTest {

    @Test
    @DisplayName("Teste 01 - Verificar visibilidade dos painéis na tela Minha Visão após login")
    public void deveExibirTodosOsPaineisNaMinhaVisaoAposLogin() {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido(); // Executa login com sucesso

        // Instancia a página de visão
        VisaoPage visaoPage = new VisaoPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Verificar visibilidade de cada painel individualmente com try-catch
        verificarPainel(wait, visaoPage.painelNaoAtribuidos, "Painel 'Não Atribuídos'");
        verificarPainel(wait, visaoPage.painelRelatadosPorMim, "Painel 'Relatados por Mim'");
        verificarPainel(wait, visaoPage.painelResolvidos, "Painel 'Resolvidos'");
        verificarPainel(wait, visaoPage.painelModificadosRecentemente, "Painel 'Modificados Recentemente (30 Dias)'");
        verificarPainel(wait, visaoPage.painelMonitoradosPorMim, "Painel 'Monitorados por Mim'");
        verificarPainel(wait, visaoPage.painelLinhaDoTempo, "Painel 'Linha do tempo'");
    }

    private void verificarPainel(WebDriverWait wait, By painelLocator, String mensagemErro) {
        try {
            WebElement painel = wait.until(ExpectedConditions.presenceOfElementLocated(painelLocator));
            wait.until(ExpectedConditions.visibilityOf(painel));
            assertTrue(painel.isDisplayed(), mensagemErro + " não está visível.");
            System.out.println(mensagemErro + " foi encontrado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao verificar " + mensagemErro + ": " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste 02 - Verificar se os títulos dos painéis redirecionam corretamente")
    public void deveVerificarTituloDosPaineisNaMinhaVisao() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        verificarCliqueNoTitulo(wait, visaoPage.painelNaoAtribuidos, "Painel 'Não Atribuídos'");
        verificarCliqueNoTitulo(wait, visaoPage.painelRelatadosPorMim, "Painel 'Relatados por Mim'");
        verificarCliqueNoTitulo(wait, visaoPage.painelResolvidos, "Painel 'Resolvidos'");
        verificarCliqueNoTitulo(wait, visaoPage.painelModificadosRecentemente, "Painel 'Modificados Recentemente (30 Dias)'");
        verificarCliqueNoTitulo(wait, visaoPage.painelMonitoradosPorMim, "Painel 'Monitorados por Mim'");
    }

    private void verificarCliqueNoTitulo(WebDriverWait wait, By painelLocator, String nomePainel) {
        try {
            WebElement painel = wait.until(ExpectedConditions.presenceOfElementLocated(painelLocator));
            wait.until(ExpectedConditions.visibilityOf(painel));
            assertTrue(painel.isDisplayed(), nomePainel + " não está visível.");

            WebElement container = painel.findElement(By.xpath("./ancestor::div[contains(@class, 'widget-box')]"));
            WebElement linkTitulo = container.findElement(By.cssSelector(".widget-title a.white"));

            String urlAntes = driver.getCurrentUrl();
            linkTitulo.click();

            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlAntes)));
            String urlDepois = driver.getCurrentUrl();

            assertNotEquals(urlAntes, urlDepois, "Redirecionamento não ocorreu ao clicar no título do " + nomePainel);

            driver.navigate().back();
            wait.until(ExpectedConditions.presenceOfElementLocated(painelLocator));
        } catch (Exception e) {
            fail("❌ Erro ao clicar no título do " + nomePainel + ": " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste 03 - Verificar se os botões 'Ver Tarefas' dos painéis redirecionam corretamente")
    public void deveVerificarBotaoVerTarefasDosPaineisNaMinhaVisao() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        verificarCliqueNoBotaoVerTarefas(wait, visaoPage.painelNaoAtribuidos, "Painel 'Não Atribuídos'");
        verificarCliqueNoBotaoVerTarefas(wait, visaoPage.painelRelatadosPorMim, "Painel 'Relatados por Mim'");
        verificarCliqueNoBotaoVerTarefas(wait, visaoPage.painelResolvidos, "Painel 'Resolvidos'");
        verificarCliqueNoBotaoVerTarefas(wait, visaoPage.painelModificadosRecentemente, "Painel 'Modificados Recentemente (30 Dias)'");
        verificarCliqueNoBotaoVerTarefas(wait, visaoPage.painelMonitoradosPorMim, "Painel 'Monitorados por Mim'");
    }

    private void verificarCliqueNoBotaoVerTarefas(WebDriverWait wait, By painelLocator, String nomePainel) {
        try {
            WebElement painel = wait.until(ExpectedConditions.presenceOfElementLocated(painelLocator));
            wait.until(ExpectedConditions.visibilityOf(painel));
            assertTrue(painel.isDisplayed(), nomePainel + " não está visível.");

            WebElement container = painel.findElement(By.xpath("./ancestor::div[contains(@class, 'widget-box')]"));
            WebElement botao = container.findElement(By.cssSelector(".widget-menu a.btn"));

            String urlAntes = driver.getCurrentUrl();
            botao.click();

            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlAntes)));
            String urlDepois = driver.getCurrentUrl();

            assertNotEquals(urlAntes, urlDepois, "Redirecionamento não ocorreu ao clicar no botão 'Ver Tarefas' do " + nomePainel);

            driver.navigate().back();
            wait.until(ExpectedConditions.presenceOfElementLocated(painelLocator));
        } catch (Exception e) {
            fail("❌ Erro ao clicar no botão 'Ver Tarefas' do " + nomePainel + ": " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste 04 - Verificar visibilidade dos itens do menu lateral na tela Minha Visão após login")
    public void deveExibirTodosOsItensDoMenuLateralAposLogin() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        verificarMenuLateral(wait, visaoPage.menuMinhaVisao, "Menu 'Minha Visão'");
        verificarMenuLateral(wait, visaoPage.menuVerTarefas, "Menu 'Ver Tarefas'");
        verificarMenuLateral(wait, visaoPage.menuCriarTarefa, "Menu 'Criar Tarefa'");
        verificarMenuLateral(wait, visaoPage.menuRegistroDeMudancas, "Menu 'Registro de Mudanças'");
        verificarMenuLateral(wait, visaoPage.menuPlanejamento, "Menu 'Planejamento'");
    }

    private void verificarMenuLateral(WebDriverWait wait, By elementoLocator, String nomeElemento) {
        try {
            WebElement elemento = wait.until(ExpectedConditions.presenceOfElementLocated(elementoLocator));
            wait.until(ExpectedConditions.visibilityOf(elemento));
            System.out.println(nomeElemento + " está visível.");
        } catch (TimeoutException e) {
            System.out.println(nomeElemento + " não está visível.");
            throw e;
        }
    }

    @Test
    @DisplayName("Teste 05 - Verificar funcionalidade dos links do menu lateral na tela Minha Visão após login")
    public void deveRedirecionarCorretamenteParaAsPaginasDoMenuLateral() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        testarLinkMenuLateral(visaoPage.menuMinhaVisao, "Minha Visão", "my_view_page.php");
        testarLinkMenuLateral(visaoPage.menuVerTarefas, "Ver Tarefas", "view_all_bug_page.php");
        testarLinkMenuLateral(visaoPage.menuCriarTarefa, "Criar Tarefa", "bug_report_page.php");
        testarLinkMenuLateral(visaoPage.menuRegistroDeMudancas, "Registro de Mudanças", "changelog_page.php");
        testarLinkMenuLateral(visaoPage.menuPlanejamento, "Planejamento", "roadmap_page.php");
    }


    private void testarLinkMenuLateral(By elementoLocator, String nomeElemento, String paginaEsperada) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement elemento = wait.until(ExpectedConditions.presenceOfElementLocated(elementoLocator));
            wait.until(ExpectedConditions.visibilityOf(elemento));

            elemento.click();
            wait.until(ExpectedConditions.urlContains(paginaEsperada));

            System.out.println(nomeElemento + " redireciona corretamente para " + paginaEsperada);
        } catch (TimeoutException e) {
            System.out.println(nomeElemento + " não redirecionou corretamente para " + paginaEsperada);
            throw e;
        }
    }

    @Test
    @DisplayName("Teste 06 - Verificar se os links do menu lateral funcionam com o menu encolhido")
    public void deveRedirecionarCorretamenteComMenuEncolhido() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement botaoEncolherMenu = wait.until(ExpectedConditions.elementToBeClickable(visaoPage.botaoEncolherMenu));
        botaoEncolherMenu.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        testarLinkMenuLateral(wait, visaoPage.menuMinhaVisao, "Minha Visão", "my_view_page.php");
        testarLinkMenuLateral(wait, visaoPage.menuVerTarefas, "Ver Tarefas", "view_all_bug_page.php");
        testarLinkMenuLateral(wait, visaoPage.menuCriarTarefa, "Criar Tarefa", "bug_report_page.php");
        testarLinkMenuLateral(wait, visaoPage.menuRegistroDeMudancas, "Registro de Mudanças", "changelog_page.php");
        testarLinkMenuLateral(wait, visaoPage.menuPlanejamento, "Planejamento", "roadmap_page.php");
    }

    private void testarLinkMenuLateral(WebDriverWait wait, By elementoLocator, String nomeElemento, String paginaEsperada) {
        try {
            WebElement elemento = wait.until(ExpectedConditions.presenceOfElementLocated(elementoLocator));
            wait.until(ExpectedConditions.visibilityOf(elemento));

            elemento.click();
            wait.until(ExpectedConditions.urlContains(paginaEsperada));

            System.out.println("✅ " + nomeElemento + " redireciona corretamente para " + paginaEsperada);
        } catch (TimeoutException e) {
            System.out.println("❌ " + nomeElemento + " não redirecionou corretamente para " + paginaEsperada);
            throw e;
        } catch (Exception e) {
            fail("❌ Erro inesperado ao testar link '" + nomeElemento + "': " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste 06 - Verificar funcionalidade do link de criar tarefa no menu lateral com campos obrigatórios")
    public void deveCriarTarefaComCamposObrigatoriosMenuLateral() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        BugReportPage bugReportPage = new BugReportPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement menuCriarTarefa = wait.until(ExpectedConditions.elementToBeClickable(visaoPage.menuCriarTarefa));
        menuCriarTarefa.click();
        bugReportPage.preencherCamposObrigatorios(wait);
        bugReportPage.criarTarefa(wait);

        WebElement mensagemSucesso = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
        assertTrue(mensagemSucesso.isDisplayed(), "A mensagem de sucesso não foi exibida.");
    }


    @Test
    @DisplayName("Teste 07 - Verificar funcionalidade do link de criar tarefa no canto superior com campos obrigatórios")
    public void deveCriarTarefaComCamposObrigatoriosCantoSuperior() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        BugReportPage bugReportPage = new BugReportPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement topoCriarTarefa = wait.until(ExpectedConditions.elementToBeClickable(visaoPage.topoCriarTarefa));
        topoCriarTarefa.click();
        bugReportPage.preencherCamposObrigatorios(wait);
        bugReportPage.criarTarefa(wait);

        WebElement mensagemSucesso = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
        assertTrue(mensagemSucesso.isDisplayed(), "A mensagem de sucesso não foi exibida.");
    }

    @Test
    @DisplayName("Teste 08 - Verificar erro ao criar tarefa sem preencher Categoria")
    public void deveExibirErroAoCriarTarefaSemCategoria() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        BugReportPage bugReportPage = new BugReportPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement menuCriarTarefa = wait.until(ExpectedConditions.elementToBeClickable(visaoPage.menuCriarTarefa));
        menuCriarTarefa.click();

        bugReportPage.preencherResumoEDescricaoSomente(wait);
        bugReportPage.criarTarefa(wait);

        assertTrue(driver.getPageSource().contains("APPLICATION ERROR #11"), "Erro esperado não foi exibido ao deixar Categoria em branco.");
    }

    @Test
    @DisplayName("Teste 09 - Verificar impedimento ao criar tarefa sem preencher Resumo")
    public void deveImpedirCriacaoDeTarefaSemResumo() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        BugReportPage bugReportPage = new BugReportPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement topoCriarTarefa = wait.until(ExpectedConditions.elementToBeClickable(visaoPage.topoCriarTarefa));
        topoCriarTarefa.click();

        bugReportPage.preencherCategoriaEDescricaoSomente(wait);
        bugReportPage.criarTarefa(wait);

        WebElement resumoCampo = wait.until(ExpectedConditions.presenceOfElementLocated(bugReportPage.campoResumo));
        assertTrue(resumoCampo.isDisplayed(), "O campo Resumo deveria estar visível e impedir envio do formulário.");
    }

    @Test
    @DisplayName("Teste 10 - Verificar impedimento ao criar tarefa sem preencher Descrição")
    public void deveImpedirCriacaoDeTarefaSemDescricao() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLoginValido();

        VisaoPage visaoPage = new VisaoPage();
        BugReportPage bugReportPage = new BugReportPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement topoCriarTarefa = wait.until(ExpectedConditions.elementToBeClickable(visaoPage.topoCriarTarefa));
        topoCriarTarefa.click();

        bugReportPage.preencherCategoriaEResumoSomente(wait);
        bugReportPage.criarTarefa(wait);

        WebElement descricaoCampo = wait.until(ExpectedConditions.presenceOfElementLocated(bugReportPage.campoDescricao));
        assertTrue(descricaoCampo.isDisplayed(), "O campo Descrição deveria estar visível e impedir envio do formulário.");
    }
}

