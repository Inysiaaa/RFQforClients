package RFQforClients;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;


import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class BaseScenarioTest {
    public ChromeDriver driver;

    CommonCode CommonCode = new CommonCode();
    private SoftAssertions softAssertions;

    @Before
    public void setUp() {

        driver = CommonCode.InitializeChromeDriver();

        softAssertions = new SoftAssertions();
    }



    @Test
    public void baseScenarioTest(){
    class PeriodsCollection{
        public LocalDate dateFrom;
        public LocalDate dateTo;
        public int priceSGL;
        public int priceDBL;
        public int priceSGLWE;
        public int priceDBLWE;

        PeriodsCollection(String ... data) {
            if(data.length>=1)
                dateFrom = LocalDate.of(Integer.valueOf(data[0].substring(6)), Integer.valueOf(data[0].substring(3,5)), Integer.valueOf(data[0].substring(0,2)));
            if(data.length>=2)
                dateTo = LocalDate.of(Integer.valueOf(data[1].substring(6)), Integer.valueOf(data[1].substring(3,5)), Integer.valueOf(data[1].substring(0,2)));
            if(data.length>=3)
                priceSGL = Integer.valueOf(data[2]);
            if(data.length>=4)
                priceDBL = Integer.valueOf(data[3]);
            if(data.length>=5)
                priceSGLWE = Integer.valueOf(data[4]);
            if(data.length>=6)
                priceDBLWE = Integer.valueOf(data[5]);
        }
    }

        WebDriverRunner.setWebDriver(driver);
        Configuration selenideConfig = new Configuration();
        selenideConfig.timeout = 30000;


        System.out.print("[-] Открываем URL: http://rfq-qa.oltatravel.com");
        open("http://rfq-qa.oltatravel.com");
        CommonCode.WaitForPageToLoad(driver);
        System.out.println(CommonCode.OK);


    //Вводим логин с паролем и кликаем Логин
        System.out.print("[-] Вводим логин с паролем и кликаем Логин");
    $(By.id("username")).setValue(("test"));
    $(By.id("password")).setValue(("password"));
    $(By.cssSelector("button[type=\"submit\"]")).click();
        System.out.println(CommonCode.OK);

    //Ждём пока загрузится страница и проподёт "Loading..."
        CommonCode.WaitForPageToLoad(driver);
        CommonCode.WaitForProgruzkaSilent();

    //Открываем Prices приложение
        System.out.print("[-] Открываем приложение Prices");
    open("http://rfq-qa.oltatravel.com/application/olta.prices");
    //Ждём пока загрузится страница и проподёт "Loading..."
        CommonCode.WaitForPageToLoad(driver);
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);


    //Открываем групповые цены на отели
        System.out.print("[-] Открываем групповые цены");
    $(By.id("group")).click();
    //Открываем текущий день
    DateTimeFormatter formatForDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            .withLocale(Locale.UK).withZone(ZoneOffset.UTC);
    LocalDate nowDate = LocalDate.now();
        System.out.println(CommonCode.OK);

    //Открываем Москву
        System.out.print("[-] Открываем Москву");
    $(By.cssSelector(PricesAppCommonCode.CitiesSelection.MSKButton)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Открываем текущий год
        System.out.print("[-] Открываем текущий год");
    $(By.xpath(PricesAppCommonCode.YearSelection.year2018XP)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Выставляем тип отеля - Hotel 4* central
        System.out.print("[-] Выставляем тип отеля - Hotel 4* central");
    //$(By.xpath("//select[@id=\"hotel-type-filter\"]")).selectOptionContainingText("Hotel 4* central");
    $(By.xpath("//div[@id=\"switch-hotel-type\"]//button[contains(text(),'Hotel 4* central')]")).click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Открываем попап с ценами
        System.out.print("[-] Открываем попап с ценами");
    $(By.xpath("//div[@id=\"content\"]//div[@id=\"hotel-calendar\"]//div[@data-year=\"2018\"]" +
            "//div//table//tbody//tr" +
            "//td[@data-date=\"2018-08-01\"]")).click();

        System.out.println(CommonCode.OK);

    //Сохраняем цены
        System.out.print("[-] Сохраняем цены");
    ArrayList<PeriodsCollection> prices = new ArrayList<>();

    $(By.xpath("//div[@class=\"modal-dialog\"]//div[@class=\"modal-content\"]")).shouldBe(Condition.visible);
    //Сохраняем значения из попапа
    String dateFrom = $(By.xpath("//div[@class=\"modal-dialog\"]//div[@class=\"modal-content\"]//form[@id=\"form-update-group-hotel-price\"]//input[@id=\"u-dateFrom\"]")).getValue();
    String dateTo = $(By.xpath("//div[@class=\"modal-dialog\"]//div[@class=\"modal-content\"]//form[@id=\"form-update-group-hotel-price\"]//input[@id=\"u-dateTo\"]")).getValue();
    String priceSGL = $(By.xpath("//div[@class=\"modal-dialog\"]//div[@class=\"modal-content\"]//form[@id=\"form-update-group-hotel-price\"]//input[@id=\"u-priceSgl\"]")).getValue();
    String priceDBL = $(By.xpath("//div[@class=\"modal-dialog\"]//div[@class=\"modal-content\"]//form[@id=\"form-update-group-hotel-price\"]//input[@id=\"u-priceDbl\"]")).getValue();
    String priceSGLWE = $(By.xpath("//div[@class=\"modal-dialog\"]//div[@class=\"modal-content\"]//form[@id=\"form-update-group-hotel-price\"]//input[@id=\"u-priceSglWe\"]")).getValue();
    String priceDBLWE = $(By.xpath("//div[@class=\"modal-dialog\"]//div[@class=\"modal-content\"]//form[@id=\"form-update-group-hotel-price\"]//input[@id=\"u-priceDblWe\"]")).getValue();

    //Сохраняем значения в новый элемент списка
        prices.add(new PeriodsCollection(dateFrom, dateTo, priceSGL, priceDBL, priceSGLWE, priceDBLWE));


    //System.out.println("from: "+dateFrom+" to: "+dateTo+" SGL: "+priceSGL+" DBL: "+priceDBL+" SGLWE:"+priceSGLWE+" DBLWE"+priceDBLWE);
    //Закрываем попап
    $(By.xpath("//div[@class=\"modal-dialog\"]//div[@class=\"modal-content\"]//div[@class=\"modal-footer\"]//button[3]")).click();
    $(By.xpath("//div[@class=\"modal-dialog\"]//div[@class=\"modal-content\"]")).shouldNotBe(Condition.visible);
        System.out.println(CommonCode.OK);

    //Открываем Экскурсии
        System.out.print("[-] Открываем Экскурсии");
    $(By.id("excursions")).click();
        System.out.println(CommonCode.OK);

    //Открываем Москву
        System.out.print("[-] Открываем Москву");
    $(By.cssSelector(PricesAppCommonCode.CitiesSelection.MSKButton)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Открываем текущий год
        System.out.print("[-] Открываем текущий год");
    $(By.xpath(PricesAppCommonCode.YearSelection.year2018XP)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Сохраняем цену за экскурсию - Бункер 42
        System.out.print("[-] Сохраняем цену за экскурсию - Бункер 42");
    String priceForBunker42 = $(By.xpath(PricesAppCommonCode.servicesPriceTableXP+"//tbody//tr[@data-excursion-id=\"3\"]" +
            "//td[@class=\"editable editable-service-price price\"]")).getText();
        System.out.println(CommonCode.OK);

    //Сохраняем цену за Гида
        System.out.print("[-] Открываем Цены для Гида");
    $(By.id("guides")).click();
        System.out.println(CommonCode.OK);

    //Открываем Москву
        System.out.print("[-] Открываем Москву");
    $(By.cssSelector(PricesAppCommonCode.CitiesSelection.MSKButton)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Открываем текущий год
        System.out.print("[-] Открываем текущий год");
    $(By.xpath(PricesAppCommonCode.YearSelection.year2018XP)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

        System.out.print("[-] Открываем язык Английский:");
    $(By.cssSelector(PricesAppCommonCode.LanguageSelection.englishButton)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

        System.out.print("[-] Сохраняем сумму за - 1/2 DAY (4 HOURS):");
    double guidePriceforHalfDay = Double.valueOf($(By.xpath(PricesAppCommonCode.servicesPriceTableXP
            +"//tbody//tr//td//a[contains(text(),'1/2 DAY (4 HOURS)')]/../../td[5]")).getText());
        System.out.println(CommonCode.OK);

    //Сохраняем цену за Транспорт
        System.out.print("[-] Открываем цены на транспорт:");
    $(By.id("transport")).click();
        System.out.println(CommonCode.OK);

    //Открываем Москву
        System.out.print("[-] Открываем Москву");
    $(By.cssSelector(PricesAppCommonCode.CitiesSelection.MSKButton)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Открываем текущий год
        System.out.print("[-] Открываем текущий год");
    $(By.xpath(PricesAppCommonCode.YearSelection.year2018XP)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

        System.out.print("[-] Сохраняем сумму за 1 час для автобуса до 18 + ДТР человек:");
    double transportPriceHourly18 = Double.valueOf($(By.xpath(PricesAppCommonCode.servicesPriceTableXP
            + "//tbody//tr[@data-transport-type=\"Mini-bus\"][@data-provider=\"ДТР\"][2]//td[2]")).getText());
        System.out.println(CommonCode.OK);

        System.out.print("[-] Сохраняем сумму за 1 час для автобуса до 45 + ДТР человек:");
    double transportPriceHourly44 = Double.valueOf($(By.xpath(PricesAppCommonCode.servicesPriceTableXP
            + "//tbody//tr[@data-transport-type=\"Bus\"][@data-provider=\"ДТР\"]//td[3][contains(text(),'46')]/.."
            + "//td[5]")).getText());
        System.out.println(CommonCode.OK);

    //Получаем цену за завтрак Hotel 4* central
        System.out.println("[-] Получаем цену за завтрак Hotel 4* central:");
    $(By.id("meal")).click();
        System.out.println(CommonCode.OK);

    //Открываем Москву
        System.out.print("    Открываем Москву");
    $(By.cssSelector(PricesAppCommonCode.CitiesSelection.MSKButton)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Открываем текущий год
        System.out.print("    Открываем текущий год");
    $(By.xpath(PricesAppCommonCode.YearSelection.year2018XP)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

        System.out.print("    Сохраняем цену");
    String breakfast4Central = $(By.xpath(PricesAppCommonCode.servicesPriceTableXP
            + "//tbody//tr[2]//td[@class=\"restaurantType\"][contains(text(),'Hotel 4*')]"
            + "/../td[@class=\"editable editable-service-price price\"]")).getText();

    String dinner4Central = $(By.xpath(PricesAppCommonCode.servicesPriceTableXP
            + "//tbody//tr[16]//td[@class=\"restaurantType\"][contains(text(),'Hotel 4*')]"
            + "/../td[@class=\"editable editable-service-price price\"]")).scrollTo().getText();

    String lunch4Central = $(By.xpath(PricesAppCommonCode.servicesPriceTableXP
            + "//tbody//tr[38]//td[@class=\"restaurantType\"][contains(text(),'Hotel 4*')]"
            + "/../td[@class=\"editable editable-service-price price\"]")).scrollTo().getText();
        System.out.println(CommonCode.OK);


    //Выходим из Prices
        System.out.print("[-] Выходим из Prices");
    $(By.xpath("//div[@id=\"profile\"]")).click();
    $(By.xpath("//button[@id=\"btn-logout\"]")).shouldBe(Condition.visible).click();
        System.out.println(CommonCode.OK);

    //Открываем клиентский RFQ
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

    //Закрываем попап с обучающим видео
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

        Selenide.clearBrowserLocalStorage();

    //Выбираем Currency
        System.out.print("[-] Выставляем валюту - EUR: ");
    $(By.cssSelector(NewQuotationPage.Options.currencyButton)).click();
    $(By.cssSelector(NewQuotationPage.Options.currencySelectors)).shouldBe(Condition.visible).click();
        CommonCode.WaitForProgruzkaSilent();
    $(By.xpath(NewQuotationPage.Options.currencyEURXP)).shouldBe(Condition.visible).hover().click();
        CommonCode.WaitForProgruzkaSilent();
    $(By.xpath(NewQuotationPage.Options.currencyEURXP)).shouldNotBe(Condition.visible);
        System.out.println(CommonCode.OK);

    //Выставляем Nights
        System.out.print("[-] Выставляем 1 ночь: ");
    $(By.xpath(NewQuotationPage.Options.nightsButtonXP)).scrollTo().click();
    $(By.xpath(NewQuotationPage.Options.nightsInputXP)).shouldBe(Condition.visible);
    $(By.xpath(NewQuotationPage.Options.nightsInputXP)).setValue("1");
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Выбираем Present Meal Service
        System.out.print("[-] Выставляем Preset Meal Services - FB: ");
    $(By.cssSelector(NewQuotationPage.Options.presentMealServicesButton)).scrollTo().click();
    $(By.cssSelector(NewQuotationPage.Options.presentMealServicesButton)).hover().click();
        CommonCode.WaitForProgruzkaSilent();
    $(By.cssSelector(NewQuotationPage.Options.presentMealServicesSelectors)).shouldBe(Condition.visible);
    $(By.cssSelector(NewQuotationPage.Options.presentMealServiceFullBoard)).hover().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Выбираем Present Menu
        System.out.print("[-] Выставляем Preset Menu - Indian: ");
    $(By.cssSelector(NewQuotationPage.Options.presentMenuButton)).scrollTo().click();
    $(By.cssSelector(NewQuotationPage.Options.presentMenuButton)).hover().click();
        CommonCode.WaitForProgruzkaSilent();
    $(By.cssSelector(NewQuotationPage.Options.presentMenuSelectors)).shouldBe(Condition.visible);
    $(By.cssSelector(NewQuotationPage.Options.presentMenuIndian)).hover().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Заполняем даты
        System.out.print("[-] Заполняем дату From: " +formatForDate.format(nowDate)+ " ");
    //Кликаем на поле для ввода даты
    $(By.cssSelector(NewQuotationPage.DatesPeriods.firstIntervalFromInput)).click();

    //Вводим дату в поле
    $(By.cssSelector(NewQuotationPage.DatesPeriods.firstIntervalFromInput)).setValue(formatForDate.format(nowDate)).pressEnter();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Выбираем Москву
        System.out.print("[-] Добавляем город в 1 день - Москва: ");
    $(By.xpath(NewQuotationPage.Itinerary.CityAddButtonXP(1))).scrollTo().click();
    $(By.xpath(NewQuotationPage.Itinerary.CityAddButtonXP(1) + NewQuotationPage.Itinerary.MoscowAddButtonXP)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //Добавляем размещение
        System.out.print("[-] Добавляем размещение: ");
        /*$(By.xpath(Itinerary.DayCityByNumberXP(1,2)
                + Itinerary.ServiceAddButtonXP)).hover().click();

        $(By.xpath(Itinerary.DayCityByNumberXP(1,2)
                + Itinerary.ServiceAddButtonXP
                + "//div//div[@id=\"add-service\"]")).shouldBe(Condition.visible);
        $(By.xpath(Itinerary.DayCityByNumberXP(1,2)
                + Itinerary.ServiceAddButtonXP
                + "//span[text()=\"Add Accommodation\"]")).hover().click(*/

    $(By.xpath("//div[@class=\"dayCityServices ui-sortable\"]//div[@class=\"insert-before-btn\"]")).hover().click();
    $(By.xpath("//div[@class=\"dayCityServices ui-sortable\"]//div[@class=\"insert-before-btn\"]//div//span[text()=\"Add Accommodation\"]")).hover().click();
    $(By.xpath("//*[@id=\"checkin-window\"]//button[text()=\"OK\"]")).hover().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

    //В первый день добавить экскурсию Бункер-42
        System.out.print("[-] В первый день добавляем экскурсию - Бункер-42: ");
        /*$(By.xpath(Itinerary.DayCityByNumberXP(1,2)
                + Itinerary.ServiceAddButtonXP)).hover().click();
        QuotationForClientCommonCode.WaitForProgruzkaSilent();

        $(By.xpath(Itinerary.DayCityByNumberXP(1,2)
                + Itinerary.ServiceAddButtonXP
                + "//div//div[@id=\"add-service\"]")).shouldBe(Condition.visible)*/

    /*$(By.xpath(Itinerary.DayCityByNumberXP(1,2)
            + Itinerary.ServiceAddButtonXP
            + "//div//div[@id=\"add-service\"]//div[@data-service-type-id=\"3\"]")).click();
    QuotationForClientCommonCode.WaitForProgruzkaSilent();

    $(By.xpath(Itinerary.DayCityByNumberXP(1,2)
            + Itinerary.ServiceByNumberXP(5)
            + "//div[@class=\"click-service-area\"]")).hover().click();
    QuotationForClientCommonCode.WaitForProgruzkaSilent();

    $(By.xpath(Itinerary.DayCityByNumberXP(1,2)
            + Itinerary.ServiceByNumberXP(5)
            + "//div[@class=\"service-names-list check-wrapper\"]//div[@class=\"check-list-top-row\"]/form/input")).sendKeys("BUNKER-42");

    $(By.xpath(Itinerary.DayCityByNumberXP(1,2)
            + Itinerary.ServiceByNumberXP(5)
            + "//div[@class=\"service-names-list check-wrapper\"]"
            + "//div[@class=\"check-list scroll-pane\"]/div[@class=\"jspContainer\"]/div[@class=\"jspPane\"]"
            + "//div[@group-value=\"B\"]//div[@data-value=\"BUNKER-42 (ZKP, 85 MIN.)\"]/span")).hover().click();*/
    $(By.xpath("//div[@class=\"dayCityServices ui-sortable\"]//div[@class=\"insert-before-btn\"]")).hover().click();
    $(By.xpath("//div[@class=\"dayCityServices ui-sortable\"]//div[@class=\"insert-before-btn\"]//div//span[text()=\"Excursion\"]")).hover().click();
    $(By.xpath("//span[text()=\"BUNKER-42 (ZKP, 85 MIN.)\"]")).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);

        System.out.println("[-] Проверяем, что Export to Word отключена: ");
    String result = $(By.xpath("//div[@class=\"footer-olta-rfq\"]//a[text()='Export to Word']")).getAttribute("class");

        if (result.equals("disabled")){
        System.out.println(CommonCode.ANSI_GREEN+"      Ошибки нет, кнопка отключена + " + CommonCode.ANSI_RESET);
    } else {
        softAssertions.assertThat(result)
                .as("Check that Export to Word button is not disabled")
                .isEqualTo("");
        System.out.println(CommonCode.ANSI_RED +"      Кнопка включена: " + CommonCode.ANSI_RESET
                + result + " -");
    }
        refresh();
        CommonCode.WaitForProgruzkaSilent();
     //Запускаем расчёт
        System.out.print("[-] Запускаем расчёт: ");
        $(By.xpath(NewQuotationPage.Results.calculateButton)).scrollTo().click();
        CommonCode.WaitForProgruzkaSilent();
        System.out.println(CommonCode.OK);


    double currencyRate = 76.0;
    Double hotelsWE;
    Double hotelsWESS;

    System.out.println("[-] Проверяем, что цены в Totals верные:");

        if(nowDate.getDayOfWeek().getValue() >= 1 & nowDate.getDayOfWeek().getValue()<=4){
        hotelsWE = Double.valueOf((new BigDecimal(Double.valueOf(prices.get(0).priceDBL)/2.0).setScale(0, RoundingMode.DOWN).floatValue()));
        hotelsWESS = Double.valueOf(prices.get(0).priceSGL) - (Double.valueOf(prices.get(0).priceDBL)/2.0);

    }
        else{
        hotelsWE = Double.valueOf((new BigDecimal(Double.valueOf(prices.get(0).priceDBLWE)/2.0).setScale(1, RoundingMode.DOWN).floatValue()));
        hotelsWESS = Double.valueOf(prices.get(0).priceSGLWE) - (Double.valueOf(prices.get(0).priceDBLWE)/2.0);
    }

    double hotelsWE15 = hotelsWE + Double.valueOf(priceForBunker42)
            + Double.valueOf((new BigDecimal(guidePriceforHalfDay/15.0).setScale(0, RoundingMode.DOWN).floatValue()))
            + Double.valueOf((new BigDecimal(transportPriceHourly18/15.0*5.0).setScale(0, RoundingMode.DOWN).floatValue()))
            + Double.valueOf(breakfast4Central)
            + Double.valueOf(lunch4Central)*2.0
            + Double.valueOf(dinner4Central)*2.0;

    double hotelsWE20 = hotelsWE + Double.valueOf(priceForBunker42)
            + Double.valueOf((new BigDecimal(guidePriceforHalfDay/20.0).setScale(0, RoundingMode.DOWN).floatValue()))
            + Double.valueOf((new BigDecimal(transportPriceHourly44/20.0*6.0).setScale(0, RoundingMode.DOWN).floatValue()))
            + Double.valueOf(breakfast4Central)
            + Double.valueOf(lunch4Central)*2.0
            + Double.valueOf(dinner4Central)*2.0;

    double hotelsWE25 = hotelsWE + Double.valueOf(priceForBunker42)
            + Double.valueOf((new BigDecimal(guidePriceforHalfDay/25.0).setScale(0, RoundingMode.DOWN).floatValue()))
            + Double.valueOf((new BigDecimal(transportPriceHourly44/25.0*6.0).setScale(0, RoundingMode.DOWN).floatValue()))
            + Double.valueOf(breakfast4Central)
            + Double.valueOf(lunch4Central)*2.0
            + Double.valueOf(dinner4Central)*2.0;

    hotelsWE15 = hotelsWE15 / 0.9 / currencyRate;
    hotelsWE20 = hotelsWE20 / 0.9 / currencyRate;
    hotelsWE25 = hotelsWE25 / 0.9 / currencyRate;
    hotelsWESS = hotelsWESS / 0.9 / currencyRate;
    String priceDBLDS15 = String.valueOf((int) new BigDecimal(hotelsWE15).setScale(0, RoundingMode.HALF_UP).floatValue());
    String priceDBLDS20 = String.valueOf((int) new BigDecimal(hotelsWE20).setScale(0, RoundingMode.HALF_UP).floatValue());
    String priceDBLDS25 = String.valueOf((int) new BigDecimal(hotelsWE25).setScale(0, RoundingMode.HALF_UP).floatValue());
    String priceDBLDSSS = String.valueOf((int) new BigDecimal(hotelsWESS).setScale(0, RoundingMode.HALF_UP).floatValue());

    //Проверяем для группы 15 человек
    result = $(By.xpath(NewQuotationPage.Results.singleTotalsTable
            + NewQuotationPage.Results.PeriodByNumber(1)
                + NewQuotationPage.Results.GroupByNumber(1))).getText();
    result = result.substring(0, result.indexOf('€'));
        if (result.equals(priceDBLDS15)){
        System.out.println(CommonCode.ANSI_GREEN+"      Ошибки нет, значение для группы 15 корректное + " + CommonCode.ANSI_RESET);
    } else {
        softAssertions.assertThat(result)
                .as("Check that value in Hotels for 15, is correct")
                .isEqualTo(priceDBLDS15);
        System.out.println(CommonCode.ANSI_RED +"      Значение для группы 15 не некорректное: " + CommonCode.ANSI_RESET
                + result + " -");
    }

    //Проверяем для группы 20 человек
    result = $(By.xpath(NewQuotationPage.Results.singleTotalsTable
            + NewQuotationPage.Results.PeriodByNumber(1)
                + NewQuotationPage.Results.GroupByNumber(2))).getText();
    result = result.substring(0, result.indexOf('€'));
        if (result.equals(priceDBLDS20)){
        System.out.println(CommonCode.ANSI_GREEN+"      Ошибки нет, значение для группы 20 корректное + " + CommonCode.ANSI_RESET);
    } else {
        softAssertions.assertThat(result)
                .as("Check that value in Hotels for 20, is correct")
                .isEqualTo(priceDBLDS20);
        System.out.println(CommonCode.ANSI_RED +"      Значение для группы 20 не некорректное: " + CommonCode.ANSI_RESET
                + result + " -");
    }

    //Проверяем для группы 25 человек
    result = $(By.xpath(NewQuotationPage.Results.singleTotalsTable
            + NewQuotationPage.Results.PeriodByNumber(1)
                + NewQuotationPage.Results.GroupByNumber(3))).getText();
    result = result.substring(0, result.indexOf('€'));
        if (result.equals(priceDBLDS25)){
        System.out.println(CommonCode.ANSI_GREEN+"      Ошибки нет, значение для группы 25 корректное + "+CommonCode.ANSI_RESET);
    } else {
        softAssertions.assertThat(result)
                .as("Check that value in Hotels for 25, is correct")
                .isEqualTo(priceDBLDS25);
        System.out.println(CommonCode.ANSI_RED +"      Значение для группы 25 не некорректное: " + CommonCode.ANSI_RESET
                + result + " -");
    }

    //Проверяем для SS
    result = $(By.xpath(NewQuotationPage.Results.singleTotalsTable
            + NewQuotationPage.Results.PeriodByNumber(1)
                + NewQuotationPage.Results.GroupByNumber(4))).getText();
    result = result.substring(0, result.indexOf('€'));
        if (result.equals(priceDBLDSSS)){
        System.out.println(CommonCode.ANSI_GREEN+"      Ошибки нет, значение SS корректное + " + CommonCode.ANSI_RESET);
    } else {
        softAssertions.assertThat(result)
                .as("Check that value in Hotels for SS, is correct")
                .isEqualTo(priceDBLDSSS);
        System.out.println(CommonCode.ANSI_RED +"      Значение SS не некорректное: " + CommonCode.ANSI_RESET
                + result + " -");
    }

        System.out.println("[-] Проверяем, Export to Word: ");
    result = $(By.xpath("//div[@class=\"footer-olta-rfq\"]//div[text()='Export to Word']")).getAttribute("class");
    //System.out.println("Из Prices получили:"+priceDBLDS15+" в Totals:"+ result);
        if (!result.equals("none")){
        System.out.println(CommonCode.ANSI_GREEN+"      - Ошибки нет, кнопка включена + " + CommonCode.ANSI_RESET);

        //Проверяем, что можно скачать сформированную программу
        System.out.println("[-] Проверяем, что можно скачать сформированную программу: ");
        //Кликаем по линке
        boolean fileIsDownloaded=true;
        try {
            $(By.xpath("//div[@class=\"footer-olta-rfq\"]//div[text()='Export to Word']")).scrollTo().click();
            $(By.xpath("//div[@class=\"footer-olta-rfq\"]//div[text()='Export to Word']//a[1]")).download();
        } catch (FileNotFoundException e) {
            fileIsDownloaded=false;
            softAssertions.assertThat("Yes")
                    .as("Try to download exported to Word program")
                    .isNotEqualTo("No");
            System.out.println(CommonCode.ANSI_RED +"      Не смог скачать программу: " + CommonCode.ANSI_RESET
                    + result + " -");
        }
        if(fileIsDownloaded){System.out.println(CommonCode.ANSI_GREEN+"      - Програма скачана + " + CommonCode.ANSI_RESET);}
    } else {
        softAssertions.assertThat(result)
                .as("Check that Export to Word button is enabled")
                .isNotEqualTo("none");
        System.out.println(CommonCode.ANSI_RED +"      Кнопка не включена: " + CommonCode.ANSI_RESET
                + result + " -");
    }


    //Кликаем Book This Program
        System.out.println("[-] Кликаем Book This Program: ");
    $(By.xpath("//div[@class=\"footer-olta-rfq\"]//a[text()='Book This Program']")).click();
    confirm();
    result = CommonCode.GetJSErrorText(driver);
    /*Quotation has been submitted for approval. Your manager will contact you soon.*/
        if (result.equals("Thank you! We received the Quotation and will contact you soon.")){
        System.out.println(CommonCode.ANSI_GREEN+"      - Попап появился + " + CommonCode.ANSI_RESET);
    } else {
        softAssertions.assertThat(result)
                .as("Check that popup with text \"Thank you! We received the Quotation and will contact you soon.\" appears.")
                .isEqualTo(priceDBLDSSS);
        System.out.println(CommonCode.ANSI_RED +"      - Появлся не тот попап: " + CommonCode.ANSI_RESET
                + result + " -");
    }
    //Проверяем что у квотации появился статус
        System.out.println("[-] Проверяем что у квотации появился статус submitted: ");
    result=$(By.xpath("//div[@id=\"content\"]//div[@class=\"submited-info\"]")).scrollTo().getText();
        if (result.equals("Waiting for approval")){
        System.out.println(CommonCode.ANSI_GREEN+"      - Статус появился + "+CommonCode.ANSI_RESET);
    } else {
        softAssertions.assertThat(result)
                .as("Check submitted status appears.")
                .isEqualTo(priceDBLDSSS);
        System.out.println(CommonCode.ANSI_RED +"      - Статус не появился: " + CommonCode.ANSI_RESET
                + result + " -");
    }
}


    @After
    public void close() {
        driver.quit();
        softAssertions.assertAll();
    }

}
