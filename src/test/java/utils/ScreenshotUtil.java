package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ScreenshotUtil {

    public static void tirarScreenshot(WebDriver driver, String nomeTeste) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File origem = ts.getScreenshotAs(OutputType.FILE);
            String destino = "target/screenshots/" + nomeTeste + "_" + System.currentTimeMillis() + ".png";
            File destinoFinal = new File(destino);
            destinoFinal.getParentFile().mkdirs(); // Cria a pasta se não existir
            Files.copy(origem.toPath(), destinoFinal.toPath());
        } catch (IOException e) {
            System.err.println("Erro ao salvar screenshot: " + e.getMessage());
        }
    }
}