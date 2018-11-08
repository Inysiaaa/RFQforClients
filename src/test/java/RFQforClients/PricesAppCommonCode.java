package RFQforClients;


public class PricesAppCommonCode extends CommonCode {
    public static final String citiesSelectors = "div[id=\"title-bar\"] div[id=\"switch-city\"]";
    public static class CitiesSelection{
        public static final String MSKButton = citiesSelectors + " button[data-switch-value=\"MSK\"]";
    }

    public static final String yearSelectorsXP = "//div[@id=\"title-bar\"]//div[@id=\"switch-year\"]";
    public static class YearSelection{
        public static final String year2018XP = yearSelectorsXP + "//button[text()=\"2018\"]";
    }

    public static final String languageSelectors = "div[id=\"title-bar\"] div[id=\"switch-lang\"]";
    public static class LanguageSelection{
        public static final String englishButton = languageSelectors + " button[data-switch-value=\"English\"]";
    }

    public static final String servicesPriceTableXP = "//table[@id=\"service-prices\"]";


    public static final String PRICESAPPLOGIN = "alexkudrya91@gmail.com";
    public static final String PRICESAPPPASSWORD = "password";


}
