package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BugReportPage {


    public By comboCategoria = By.id("category_id");
    public By campoResumo = By.id("summary");
    public By campoDescricao = By.id("description");


    public By botaoCriarTarefa = By.xpath("//input[@value='Criar Nova Tarefa']");

    // Método para preencher apenas os campos obrigatórios
    public void preencherCamposObrigatorios(WebDriverWait wait) {

        WebElement selectCategoria = wait.until(ExpectedConditions.elementToBeClickable(comboCategoria));
        Select dropdown = new Select(selectCategoria);
        dropdown.selectByIndex(1); // Seleciona a primeira opção válida
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoResumo)).sendKeys("Teste via Selenium");
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoDescricao)).sendKeys("Descrição gerada automaticamente.");
    }

    public void criarTarefa(WebDriverWait wait) {
        WebElement botaoCriar = wait.until(ExpectedConditions.elementToBeClickable(botaoCriarTarefa));
        botaoCriar.click();
    }

    // Preenche apenas Resumo e Descrição (sem Categoria)
    public void preencherResumoEDescricaoSomente(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoResumo)).sendKeys("Tarefa sem categoria");
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoDescricao)).sendKeys("Descrição de teste sem categoria");
    }

    // Preenche apenas Categoria e Descrição (sem Resumo)
    public void preencherCategoriaEDescricaoSomente(WebDriverWait wait) {
        WebElement selectCategoria = wait.until(ExpectedConditions.elementToBeClickable(comboCategoria));
        new Select(selectCategoria).selectByIndex(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoDescricao)).sendKeys("Descrição sem resumo");
    }

    // Preenche apenas Categoria e Resumo (sem Descrição)
    public void preencherCategoriaEResumoSomente(WebDriverWait wait) {
        WebElement selectCategoria = wait.until(ExpectedConditions.elementToBeClickable(comboCategoria));
        new Select(selectCategoria).selectByIndex(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoResumo)).sendKeys("Resumo sem descrição");
    }

}


