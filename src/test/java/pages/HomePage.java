package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver navegador) {
        super(navegador);
    }

    public MePage clickMe(){
        navegador.findElement(By.className("me")).click();

        return new MePage(navegador);
    }
}
