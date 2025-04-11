package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo de propriedades: " + e.getMessage());
        }
    }

    public static String get(String chave) {
        return properties.getProperty(chave);
    }
}