package pages;

import org.openqa.selenium.By;

public class VisaoPage {

    // XPaths para cada painel
    public By painelNaoAtribuidos = By.xpath("//a[@class='white' and contains(text(),'Não Atribuídos')]");
    public By painelRelatadosPorMim = By.xpath("//a[@class='white' and contains(text(),'Relatados por Mim')]");
    public By painelResolvidos = By.xpath("//a[@class='white' and contains(text(),'Resolvidos')]");
    public By painelModificadosRecentemente = By.xpath("//a[@class='white' and contains(text(),'Modificados Recentemente (30 Dias)')]");
    public By painelMonitoradosPorMim = By.xpath("//a[@class='white' and contains(text(),'Monitorados por Mim')]");
    public By painelLinhaDoTempo = By.xpath("//h4[@class='widget-title lighter' and contains(normalize-space(),'Linha do tempo')]");

    // XPaths para os itens do menu lateral
    public By menuMinhaVisao = By.xpath("//a[span[contains(@class, 'menu-text') and normalize-space(text())='Minha Visão']]");
    public By menuVerTarefas = By.xpath("//a[span[contains(@class, 'menu-text') and normalize-space(text())='Ver Tarefas']]");
    public By menuCriarTarefa = By.xpath("//a[span[contains(@class, 'menu-text') and normalize-space(text())='Criar Tarefa']]");
    public By menuRegistroDeMudancas = By.xpath("//a[span[contains(@class, 'menu-text') and normalize-space(text())='Registro de Mudanças']]");
    public By menuPlanejamento = By.xpath("//a[span[contains(@class, 'menu-text') and normalize-space(text())='Planejamento']]");

    // Botão para encolher/expandir o menu lateral
    public By botaoEncolherMenu = By.cssSelector("i.fa-angle-double-left, i.fa-angle-double-right");

    // Link "Criar Tarefa" no canto superior
    public By topoCriarTarefa = By.xpath("//div[@id='navbar-container']//a[contains(@href, 'bug_report_page.php')]");

}

