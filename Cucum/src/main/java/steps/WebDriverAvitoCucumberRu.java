package steps;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class WebDriverAvitoCucumberRu {
    private final WebDriverWait webDriverWait;
    private final ChromeDriver driver;

    public WebDriverAvitoCucumberRu() {
        System.setProperty("B:\\SeleniumAndAllure\\chromedriver.exe", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        this.driver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait(driver, 5);
    }


    @Пусть("открыт ресурс авито")
    public void getURL() {
        driver.get("https://avito.ru/");
//        driver.manage().window().maximize();
    }

    @И("в выпадающем списке категорий выбрана <Category>")
    public void Category(Category category) {
        new Select(driver.findElement(By.xpath("//select[@name=\"category_id\"]")))
                .selectByVisibleText(category.getValue());
    }

    @И("в поле поиска введено значение <Search>")
    public void Search(CharSequence search) {
        driver.findElement(By.xpath("//label[@class='input-layout-input-layout-eKhsI input-layout-size-s-UjNk6 " +
                "input-layout-text-align-left-IDAPR width-width-12-2VZLz suggest-input-1dvep js-react-suggest']"))
                .sendKeys(search);
    }

    @Тогда("кликнуть по выпадающему списку региона")
    public void menuCity() {
        driver.findElement(By.xpath("//div[@class=\"main-text-2PaZG\"]")).click();
    }

    @Тогда("в поле регион введено значение <City>")
    public void City(CharSequence city) {
        driver.findElement(By.xpath("//div[@data-marker=\"popup-location/region\"]//input")).sendKeys(city);
        webDriverWait.until(presenceOfElementLocated(By.xpath("//ul[@class=\"suggest-suggests-bMAdj\"]//li[@data-marker=\"suggest(0)\"]" +
                "//*[contains(text(),'" + city + "')]"))).click();
    }

    @И("нажата кнопка показать объявления")
    public void searchClick() {
        driver.findElement(By.xpath("//button[@data-marker=\"popup-location/save-button\"]")).click();
    }

    @Тогда("открылась страница результаты по запросу <Search>")
    public void requestPage(String str) {
        String text = driver.findElement(By.cssSelector("//h1[@class='page-title-text-WxwN3 page-title-inline-2v2CW']")).getText();
        Assert.assertTrue(text.contains(str));
    }

    @И("активирован чекбокс только с фотографией")
    public void checkboxPhoto() {
        WebElement checkbox = driver.findElement(
                By.xpath("//span[contains(text(),'только с фото')]"));
        if (checkbox.isSelected()) {
            checkbox.findElement(By.xpath("./parent::*")).click();
        }
    }

    @И("в выпадающем списке сортировка выбрано занчение <filter>")
    public void filter(FilterSort filterSort) {
        new Select(driver.findElement(By.xpath("//div[@class=\"" +
                "sort-select-3QxXG select-select-box-3LBfK select-size-s-2gvAy\"]//select")))
                .selectByVisibleText(filterSort.getValue());
    }

    @И("в консоль выведено значение названия и цены <num> первых товаров")
    public void nameCost3(int num) {
        List<WebElement> webElements = driver.findElements(
                By.xpath("//div[@data-marker=\"catalog-serp\"]/div[@data-marker=\"item\"]"));
        for (int i = 0; i < num; i++) {
            System.out.println(webElements.get(i).findElement(By.xpath(".//h3[@itemprop=\"name\"]"))
                    .getText());
            System.out.println(webElements.get(i).findElement(By.xpath(".//meta[@itemprop=\"price\"]"))
                    .getAttribute("content") + " RUB");
        }
    }

    @Step
    public void quit() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

    @Attachment
    @Step
    public byte[] makeScreenShot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment
    @Step
    public byte[] makeScreenAShot(WebElement element) {
        Screenshot screenshot = new AShot().shootingStrategy(
                ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver, element);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            ImageIO.write(screenshot.getImage(), "png", buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toByteArray();

    }

    @ParameterType(".*")
    public FilterSort filter(String filterSort) {
        return FilterSort.valueOf(filterSort);
    }

    @ParameterType(".*")
    public Category Category(String category) {
        return Category.valueOf(category);
    }
}