package RFQforClients;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class NewQuotationPage {
    public final static String newQuotationButton = "#content div[class=\"buttons-row\"] button[class=\"add-quotation-btn\"]";

    public final static String tutorialPopupXP = "//div[@id=\"modal-tutorial-video\"]";

    public class TutorialPopup{

        public final static String closeButtonXP = tutorialPopupXP + "//div/div/button";
    }
    public final static String tutorialPopupQ = "//div[@role=\"dialog\"]";

    public class TutorialPopupQ{

        public final static String closeButtonQ = tutorialPopupQ + "//a[@class=\"cc-btn cc-dismiss\"]";
    }
    public final static String quotationId = "#content span[class=\"grey-title\"] span";



    public final static String optionsArea = "#options div[class=\"options-row\"]";
    public final static String optionsAreaXP = "//div[@id=\"options\"]//div[@class=\"options-row\"]";
    public static class Options{

        public final static String currencyButton = optionsArea+ " div[class=\"options-col have-list\"]";
        public final static String currencySelectors = optionsArea+ " div[class=\"check-list\"]";
        public final static String currencySelectorsXP = optionsAreaXP+ "//div[@class=\"check-list\"]";
        public final static String currencyEURXP = currencySelectorsXP+ "//div[@data-value=\"EUR\"]";
        public final static String nightsButtonXP = optionsAreaXP+ "[2]//div[@class=\"options-col have-string value-null\"]";
        public final static String nightsInputXP = optionsAreaXP+ "[2]//div//input[@data-optionkey=\"number_of_nights\"]";

        public final static String presentMealServicesButton = optionsArea+ ":nth-child(4) div";
        public final static String presentMealServicesSelectors = presentMealServicesButton+ " div[class=\"check-list\"]";
        public final static String presentMealServiceFullBoard = presentMealServicesSelectors+ " div[data-value=\"FB\"]";

        public final static String presentMenuButton = optionsArea+ ":nth-child(4) div:nth-child(2)";
        public final static String presentMenuSelectors = presentMenuButton+ " div[class=\"check-list country-list\"]";
        public final static String presentMenuIndian = presentMenuSelectors+ " div[data-value=\"Indian\"]";

    }

    public final static String datesArea = "#datesBlock";
    public static class DatesPeriods{

        public final static String firstIntervalFromInput = datesArea+ " div[style=\"cursor: pointer;\"] div[class=\"info-col\"] input[type=\"text\"]";

    }
    public final static String accommodationsAreaXP = "//div[@id=\"accommodationsBlock\"]";
    public static class Accommodations{

        public static String CityByNumberXP(int cityNumber){
            return String.valueOf(accommodationsAreaXP+"//div[@class=\"info-row\"]["+cityNumber+"]");
        }

    }

    public static class Itinerary {

        public static String ProgramSectionXP() {

            String result = $(By.cssSelector(NewQuotationPage.quotationId)).getText();
            result = result.substring(1, result.length());
            result = "//div[@id=\"quotationdays" + result + "\"]";

            return result;
        }

        public static String DayByNumberXP(int dayCounter) {

            return ProgramSectionXP() + "//div[@class=\"day-info\"][" + String.valueOf(dayCounter) + "]";
        }

        public static String GetDataIdOfDayByNumber(int dayCounter) {

            return $(By.xpath(DayByNumberXP(dayCounter))).getAttribute("data-id");
        }

        public static String DayCityByNumberXP(int dayCounter, int cityCounter) {

            return "//div[@id=\"quotationdaycities" + GetDataIdOfDayByNumber(dayCounter) + "\"]/div[" + String.valueOf(cityCounter) + "]";

        }


        public static String CityAddButtonXP(int dayCounter) {
            return ProgramSectionXP() + "/div[" + String.valueOf(dayCounter) + "]" + "//div[@class=\"program-empty-add-city\"]";
        }

        public static final String MoscowAddButtonXP = "//div[@group-value=\"Two Capitals\"]//span[text()=\"Moscow\"]";
    }

    public final static String resultsAreaXP = "//div[@id=\"resultsBlock\"]";
    public static class Results{

        public static final String calculateButton = resultsAreaXP + "/button[@id=\"execute\"]";
        public static final String singleTotalsTable = resultsAreaXP + "//div[@id=\"result\"]//table[@id=\"table-result-totals\"]/tbody";


        public static String PeriodByNumber(int number){
            return String.valueOf("//tr["+ (number+1) +"]");
        }


        public static String GroupByNumber(int number){
            return String.valueOf("//td["+ (number+1) +"]");
        }


    }
}
