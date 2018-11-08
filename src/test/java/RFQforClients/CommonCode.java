package RFQforClients;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.url;

public class CommonCode {

    public static final String Login = "//*[@id=\"username\"]";
    public static final String Password = "//*[@id=\"password\"]";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String OK = ANSI_GREEN + " - Готово" + ANSI_RESET;

    public static final String overallLoadingIndicatorREG = "//span[contains(text(),'Loading')]";


    public static void WaitForProgruzkaSilent() {$(By.xpath(overallLoadingIndicatorREG)).shouldNot(exist); }

    public void WaitForPageToLoad(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        if(js.executeScript("return document.readyState").toString().equals("compleate")) {
            System.out.println("[-] страница загружена - URL: " + url());
            return;
        }

        int totalTime = 0;
        int numberOfIterations = 0;

        for (int i=0; i < 120; i++) {
            try {
                Thread.sleep(250);
                totalTime = totalTime + 1;
                numberOfIterations = numberOfIterations + 1;

            } catch (InterruptedException e) {
            }
            if (js.executeScript("return document.readyState").toString().equals("complete")) break;
        }
        System.out.println("[-] ждали открытия страницы - URL: " + url() + " - " + totalTime + " сек., кол-во повторений: " + numberOfIterations);
    }

    public String GetJSErrorText(WebDriver driver) {
        String result;

        try {
            Alert alert = (new WebDriverWait(driver, 4))
                    .until(ExpectedConditions.alertIsPresent());
            result = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
        } catch (TimeoutException e) {
            result = "none";
            //System.err.println("Проверка не отработала");
        }

        return result;
    }

    public ChromeDriver InitializeChromeDriver(){
        ChromeDriver driver;
        boolean isWindows=false;
        if(System.getProperty("os.name").toLowerCase().indexOf("win")>=0){isWindows=true;}

        if(isWindows){
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();}
        else{
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
            driver = new ChromeDriver();
            driver.manage().window().setSize(new Dimension(1280, 720));
        }
        return driver;
    }


}
