package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginFormPage extends BasePage {

    public LoginFormPage(WebDriver navegador) {
        super(navegador);
    }

    public LoginFormPage typeLogin(String login){
        navegador.findElement(By.id("signinbox")).findElement(By.name("login")).sendKeys(login);

        return this;
    }

    public LoginFormPage typeSenha(String senha){
        navegador.findElement(By.id("signinbox")).findElement(By.name("password")).sendKeys(senha);

        return this;
    }

    public HomePage clickSignIn(){
        navegador.findElement(By.id("signinbox")).findElement(By.linkText("SIGN IN")).click();

        return new HomePage(navegador);
    }

    /**
     *Abaixo temos o funcional, acredita-se que manter os dois e bom
     * @param login
     * @param senha
     * @return
     */
    public HomePage fazerLogin(String login,String senha){
        typeLogin(login);
        typeSenha(senha);
        clickSignIn();

        return new HomePage(navegador);
    }
}
