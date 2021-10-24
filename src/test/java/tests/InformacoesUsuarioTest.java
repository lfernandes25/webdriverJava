package tests;

import static org.junit.Assert.*;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTestData.csv")
public class InformacoesUsuarioTest {
    private static WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){
        navegador = Web.createChrome();
    }

    @Test
    public void testValidarLogin(){

        realizarLogin("leanstorm","QvmfPcfmass8yBU");

        WebElement me = navegador.findElement(By.className("me"));
        String textoNoElementoMe = me.getText();
        assertEquals("Hi, Leandro Fernandes",textoNoElementoMe);

    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name="tipo")String tipo,
                                                             @Param(name="contato")String contato,
                                                             @Param(name="mensagem")String mensagemEsperada){

        realizarLogin("leanstorm","QvmfPcfmass8yBU");
        acessarMoreDataAboutYou();

        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));
        //Trabalhando com o campo select
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();

        assertEquals(mensagemEsperada,mensagem);

    }

    @Test
    public void testRemoverUmContatoDeUmUsuario(){
        realizarLogin("leanstorm","QvmfPcfmass8yBU");
        acessarMoreDataAboutYou();

        // Clicar no elemento pelo seu xpath: //span[text()="+551133334444"]/following-sibling::a
        navegador.findElement(By.xpath("//span[text()=\"+551133334444\"]/following-sibling::a")).click();

        // Confirmar a janela javascriot
        navegador.switchTo().alert().accept();

        //validar que a mensagem apresentada foi Rest in peace, dear phone!
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!",mensagem);

        String screenshot = "C:\\Users\\leans\\test-report\\taskit\\" + Generator.dataHoraParaArquivo() + test.getMethodName() +".png";
        Screenshot.tirar(navegador,screenshot);

        //Aguardar até 10 segundos para que a janela desapareça
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        //Clicar no link com texto "logout"
        navegador.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown(){
        navegador.close();
    }

    private void realizarLogin(String usuario, String senha){
        WebElement linkSignIn = navegador.findElement(By.linkText("Sign in"));
        linkSignIn.click();

        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));
        formularioSignInBox.findElement(By.name("login")).sendKeys(usuario);
        formularioSignInBox.findElement(By.name("password")).sendKeys(senha);
        formularioSignInBox.findElement(By.linkText("SIGN IN")).click();
    }

    private void acessarMoreDataAboutYou() {
        navegador.findElement(By.className("me")).click();
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }
}
