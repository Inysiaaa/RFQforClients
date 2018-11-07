package RFQforClients;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;


import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class AutoServiceTest {
    public ChromeDriver driver;

    CommonCode CommonCode = new CommonCode();
    private SoftAssertions softAssertions;

    @Before
    public void setUp() {

        driver = CommonCode.InitializeChromeDriver();

        softAssertions = new SoftAssertions();
    }

    @Test
    public void autoServicesForClients(){

        WebDriverRunner.setWebDriver(driver);
        Configuration selenideConfig = new Configuration();
        selenideConfig.timeout = 30000;


        System.out.print("[-] Открываем URL: http://rfq-qa.oltatravel.com");
        open("http://rfq-qa.oltatravel.com");
        CommonCode.WaitForPageToLoad(driver);
        System.out.println(CommonCode.OK);

        //Вводим логин с паролем и кликаем Логин
        System.out.print("[-] Вводим логин с паролем и кликаем Логин");
        $(By.id("username")).setValue(("test2@test.com"));
        $(By.id("password")).setValue(("password"));
        $(By.cssSelector("button[type=\"submit\"]")).click();
        System.out.println(CommonCode.OK);

        //Ждём пока загрузится страница и проподёт "Loading..."
        CommonCode.WaitForPageToLoad(driver);
        CommonCode.WaitForProgruzkaSilent();

        //Закрываем попап с обычеющим видео
        /*System.out.print("[-] Закрываем попап с обучающим видео:");
        $(By.xpath(QuotationListPage.TutorialPopup.closeButtonXP)).click();
        System.out.println(QuotationForClientCommonCode.OK);*/

        //Закрываем попап куки
        System.out.print("[-] Закрываем попап куки:");
        $(By.xpath(NewQuotationPage.TutorialPopupQ.closeButtonQ)).click();
        System.out.println(CommonCode.OK);

        //Создаём новый Quotation
        System.out.print("[-] Создаём новый Quotation, ID = ");
        $(By.cssSelector(NewQuotationPage.newQuotationButton)).click();
        CommonCode.WaitForProgruzkaSilent();

        //Выставляем Nights
        System.out.print("[-] Выставляем 1 ночь: ");
        $(By.xpath(NewQuotationPage.Options.nightsButtonXP)).click();
        $(By.xpath(NewQuotationPage.Options.nightsInputXP)).shouldBe(Condition.visible);
        $(By.xpath(NewQuotationPage.Options.nightsInputXP)).setValue("1");
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

        System.out.print("[-] Выставляем Preset Meal Services - FB: ");
        $(By.cssSelector(NewQuotationPage.Options.presentMealServicesButton)).click();
        $(By.cssSelector(NewQuotationPage.Options.presentMealServicesButton)).click();
        $(By.cssSelector(NewQuotationPage.Options.presentMealServicesSelectors)).shouldBe(Condition.visible);
        $(By.cssSelector(NewQuotationPage.Options.presentMealServiceFullBoard)).click();
        //$(By.cssSelector(NewQuotationPage.Options.presentMealServiceNO)).click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

        //Выбираем Present Menu
        System.out.print("[-] Выставляем Preset Menu - Indian: ");
        $(By.cssSelector(NewQuotationPage.Options.presentMenuButton)).scrollTo().click();
        $(By.cssSelector(NewQuotationPage.Options.presentMenuButton)).hover().click();
        $(By.cssSelector(NewQuotationPage.Options.presentMenuSelectors)).shouldBe(Condition.visible);
        $(By.cssSelector(NewQuotationPage.Options.presentMenuIndian)).hover().click();
        //$(By.cssSelector(NewQuotationPage.Options.presentMealServiceNO)).click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);


        //Заполняем даты
        //Открываем текущий день
        DateTimeFormatter formatForDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                .withLocale(Locale.UK).withZone(ZoneOffset.UTC);
        LocalDate nowDate = LocalDate.now();
        System.out.print("[-] Заполняем дату From: " +formatForDate.format(nowDate)+ " ");
        //Кликаем на поле для ввода даты
        $(By.cssSelector(NewQuotationPage.DatesPeriods.firstIntervalFromInput)).click();

        //Вводим дату в поле
        $(By.cssSelector(NewQuotationPage.DatesPeriods.firstIntervalFromInput)).setValue(formatForDate.format(nowDate)).pressEnter();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

        //Проверяем, что типы питания выставляются корректно и происходит автогенерация сервисов
        System.out.println("[-] Проверяем, что типы питания выставляются корректно и происходит автогенерация сервисов:");
        String[] presentMealServices = {"BB", "HB", "NO", "FB"};
        String errorText;

        for(int i=0; i<presentMealServices.length; i++) {

            //Выбираем Present Meal Service
            System.out.println("[-] Выставляем Preset Meal Services - " + presentMealServices[i] + ": ");
            $(By.cssSelector(NewQuotationPage.Options.presentMealServicesButton)).scrollTo().click();
            $(By.cssSelector(NewQuotationPage.Options.presentMealServicesButton)).click();
            $(By.cssSelector(NewQuotationPage.Options.presentMealServicesSelectors)).shouldBe(Condition.visible);
            $(By.cssSelector(NewQuotationPage.Options.presentMealServicesSelectors + " div[data-value=\"" + presentMealServices[i] + "\"]")).click();

            CommonCode.WaitForProgruzkaSilent();
            //System.out.println(QuotationForClientCommonCode.OK);


            System.out.print("[-] Добавляем город в 1 день - Москва: ");
            $(By.xpath(NewQuotationPage.Itinerary.CityAddButtonXP(1))).scrollTo().click();
            $(By.xpath(NewQuotationPage.Itinerary.CityAddButtonXP(1) + NewQuotationPage.Itinerary.MoscowAddButtonXP)).scrollTo().click();
            CommonCode.WaitForProgruzkaSilent();
            System.out.println(CommonCode.OK);

            //Проверяем автогенерацию arriving at the airport в первом дне
            int arriving = 0;
            arriving = $$(By.xpath(NewQuotationPage.Itinerary.DayCityByNumberXP(1, 2)
                    + "//div[2]//div[@class=\"dayCityServices ui-sortable\"]/div[@class=\"act act-sortable\"]")).size();
            int arrivingMust = 1;
            if ((arriving) == arrivingMust) {
                System.out.println(CommonCode.ANSI_GREEN + "      Автогенерация ARRIVING AT THE AIRPORT прошла успешно +" + CommonCode.ANSI_RESET);
            } else {
                softAssertions.assertThat((arriving))
                        .as("Check that value of arriving services in day 1 is correct")
                        .isEqualTo(arrivingMust);
                System.out.println(CommonCode.ANSI_RED + "     Автогенерация ARRIVING AT THE AIRPORT прошла неуспешно " + CommonCode.ANSI_RESET
                        + (arriving) + " -");
            }
            //Добавляем размещение
            System.out.print("[-] Добавляем размещение: ");
            /*$(By.xpath(NewQuotationPage.Itinerary.DayCityByNumberXP(1, 2)
                    + NewQuotationPage.Itinerary.ServiceAddButtonXP)).hover().click();
            $(By.xpath(NewQuotationPage.Itinerary.DayCityByNumberXP(1, 2)
                    + NewQuotationPage.Itinerary.ServiceAddButtonXP
                    + "//div//div[@id=\"add-service-btn\"]")).shouldBe(Condition.visible);
            $(By.xpath(NewQuotationPage.Itinerary.DayCityByNumberXP(1, 2)
                    + NewQuotationPage.Itinerary.ServiceAddButtonXP
                    + "//span[text()=\"Add Accommodation\"]")).hover().click();
            $(By.xpath("//*[@id=\"checkin-window\"]//button[text()=\"OK\"]")).hover().click();*/
            $(By.xpath("//div[@class=\"dayCityServices ui-sortable\"]//div[@class=\"insert-before-btn\"]")).hover().click();
            $(By.xpath("//div[@class=\"dayCityServices ui-sortable\"]//div[@class=\"insert-before-btn\"]//div//span[text()=\"Add Accommodation\"]")).hover().click();
            $(By.xpath("//*[@id=\"checkin-window\"]//button[text()=\"OK\"]")).hover().click();
            CommonCode.WaitForProgruzkaSilent();
            System.out.println(CommonCode.OK);

            errorText = CommonCode.GetJSErrorText(driver);

            CommonCode.WaitForProgruzkaSilent();

            if (errorText.equals("none")) {
                System.out.println(CommonCode.ANSI_GREEN + "      Базовая генерация прошла + " + CommonCode.ANSI_RESET);

                int servicesCount1 = 0;
                int servicesCount2 = 0;
                servicesCount1 = $$(By.xpath(NewQuotationPage.Itinerary.DayCityByNumberXP(1, 2)
                        + "//div[2]//div[@class=\"dayCityServices ui-sortable\"]/div[@class=\"act act-sortable meal-act\"]")).size();
                //System.out.println(servicesCount1);
                servicesCount2 = $$(By.xpath(NewQuotationPage.Itinerary.DayCityByNumberXP(2, 2)
                        + "//div[2]//div[@class=\"dayCityServices ui-sortable\"]/div[@class=\"act act-sortable meal-act\"]")).size();
                //System.out.println(servicesCount2);

                int servicesCountMust1 = 0;
                int servicesCountMust2 = 0;

                if (presentMealServices[i].equals("NO")) {
                    //Проверить что питание не добавилось
                    servicesCountMust1 = 0;
                    servicesCountMust2 = 0;
                }

                if (presentMealServices[i].equals("BB")) {
                    //Проверить что добавились BREAKFAST + LUNCH
                    servicesCountMust1 = 1;
                    servicesCountMust2 = 2;
                }

                if (presentMealServices[i].equals("HB")) {
                    //Проверить что добавились BREAKFAST + DINNER
                    servicesCountMust1 = 1;
                    servicesCountMust2 = 2;
                }

                if (presentMealServices[i].equals("FB")) {
                    //Проверить что добавились BREAKFAST + LUNCH + DINNER
                    servicesCountMust1 = 2;
                    servicesCountMust2 = 3;
                }

                //Проверяем что добавились нужные автосервисы
                //System.out.println("Из Prices получили:"+priceDBLDS15+" в Totals:"+ result);
                if ((servicesCount1) == servicesCountMust1) {
                    System.out.println(CommonCode.ANSI_GREEN + "      Колличество сервисов питания в первом дне верное + " + CommonCode.ANSI_RESET);
                } else {
                    softAssertions.assertThat((servicesCount1 - 2))
                            .as("Check that value of meal services in day 1 is correct")
                            .isEqualTo(servicesCountMust1);
                    System.out.println(CommonCode.ANSI_RED + "      Колличество сервисов питания в первом дне неверное: " + CommonCode.ANSI_RESET
                            + (servicesCount1) + " -");
                }

                if ((servicesCount2) == servicesCountMust2) {
                    System.out.println(CommonCode.ANSI_GREEN + "      Колличество сервисов питания во втором дне верное + " + CommonCode.ANSI_RESET);
                } else {
                    softAssertions.assertThat((servicesCount2))
                            .as("Check that value of meal services in day 2 is correct")
                            .isEqualTo(servicesCountMust2);
                    System.out.println(CommonCode.ANSI_RED + "      Колличество сервисов питания во втором дне неверное: " + CommonCode.ANSI_RESET
                            + (servicesCount2) + " -");
                }

                //Проверяем автогенерацию departure from the airport в последнем дне
                int departure = 0;
                departure = $$(By.xpath(NewQuotationPage.Itinerary.DayCityByNumberXP(2, 2)
                        + "//div[2]//div[@class=\"dayCityServices ui-sortable\"]//p[text()=\"DEPARTURE FROM THE AIRPORT\"]")).size();
                int departureMust = 1;
                if ((departure) == departureMust) {
                    System.out.println(CommonCode.ANSI_GREEN + "      Автогенерация DEPARTURE FROM THE AIRPORT прошла успешно +" + CommonCode.ANSI_RESET);
                } else {
                    softAssertions.assertThat((departure))
                            .as("Check that value of departure services in day 2 is correct")
                            .isEqualTo(arrivingMust);
                    System.out.println(CommonCode.ANSI_RED + "     Автогенерация DEPARTURE FROM THE AIRPORT прошла неуспешно " + CommonCode.ANSI_RESET
                            + (departure) + " -");
                }

                //Удаляем город из Accomodations
                $(By.xpath(NewQuotationPage.Accommodations.CityByNumberXP(1))).scrollTo().hover();
                $(By.xpath(NewQuotationPage.Accommodations.CityByNumberXP(1) + "//div[@class=\"delete-btn\"]")).hover().click();
                confirm();
                CommonCode.WaitForProgruzkaSilent();

            } else {
                softAssertions.assertThat(errorText)
                        .as("Try to use " + presentMealServices[i] + " for quotation generation: ")
                        .isEqualTo("Ok");
                System.out.println(CommonCode.ANSI_RED + "      Добавление: " + presentMealServices[i] + " упало -" + CommonCode.ANSI_RESET);
                CommonCode.WaitForProgruzkaSilent();
            }


        }

    }

    @After
    public void close() {
        driver.quit();
        softAssertions.assertAll();
    }

}
