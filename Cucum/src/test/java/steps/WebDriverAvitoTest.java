package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebDriverAvitoTest {
    private WebDriverAvitoCucumberRu step;
    private WebDriver driver;

    private final String category = "Оргтехника и расходники";
    private final String search = "принтер";
    private final String city = "Владивосток";
    private final String sort = "Дороже";


    @DataProvider
    public static Object[][] provider() {
        return new Object[][]{
                {"Оргтехника и расходники", "принтер", "Владивосток", "Дороже", 3},
                {"Настольные компьютеры", "игровой пк", "Москва", "Дешевле", 5},
                {"Настольные компьютеры", "игровой пк", "Москва", "Дороже", 10}
        };
    }

    @BeforeMethod
    public void setUp() {
        step = new WebDriverAvitoCucumberRu();
        driver = step.getDriver();
    }

    @Test
    public void category() {
        step.getURL();
        step.makeScreenShot();
        step.Category(category);
        step.makeScreenAShot(driver.findElement(By.xpath("//div[@data-marker=\"search-form\"]")));
        step.quit();
    }

    @Test
    public void search() {
        step.getURL();
        step.Category(category);
        step.makeScreenShot();
        step.Search(search);
        step.makeScreenAShot(driver.findElement(By.xpath("//div[@data-marker=\"search-form\"]")));
        step.quit();
    }

    @Test
    public void searchCity() {
        step.getURL();
        step.Category(category);
        step.Search(search);
        step.makeScreenShot();
        step.City(city);
        step.makeScreenAShot(driver.findElement(By.xpath("//div[@data-marker=\"fieldset/d\"]")));
        step.quit();
    }

    @Test
    public void filter() {
        step.getURL();
        step.Category(category);
        step.Search(search);
        step.City(city);
        step.makeScreenShot();
        step.filter(sort);
        step.makeScreenAShot(driver.findElement(By.xpath("//div[@class='index-topPanel-1F0TP']")));
        step.quit();
    }

    @Test(dataProvider = "provider")
    public void nC3(String category, String search, String city, String sort, int num) {
        step.getURL();
        step.Category(category);
        step.Search(search);
        step.City(city);
        step.filter(sort);
        step.makeScreenShot();
        step.nameCost3(num);
        step.makeScreenAShot(driver.findElement(By.xpath("//div[@data-marker=\"catalog-serp\"]")));
        step.quit();
    }
}
