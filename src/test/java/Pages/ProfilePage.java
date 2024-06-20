package Pages;

import ConfigHelper.ConfigHelper;
import io.qameta.allure.Step;
import model.Books;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {
    private final WebDriver driver;
    Books book = new Books();

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openSite() {
        driver.get(ConfigHelper.getURL());
    }

    @Step("Перейти  раздел авторизации https://demoqa.com/login")
    public void openAuthForm() {
        driver.get(ConfigHelper.getURL() + "/login");
    }

    @Step("Ввести логин в поле UserName")
    public void setLogin() {
        driver.findElement(By.cssSelector("#userName")).sendKeys(ConfigHelper.getLogin());
    }

    @Step("Ввести пароль в поле Password")
    public void setPassword() {
        driver.findElement(By.cssSelector("#password")).sendKeys(ConfigHelper.getPassword());
    }

    @Step("Кликнуть кнопку Login")
    public void clickLogin() {
        driver.findElement(By.cssSelector("#login")).click();
    }

    @Step("Авторизация на сайте https://demoqa.com")
    public void authorizeSuccess() {
        setLogin();
        setPassword();
        clickLogin();
    }

    @Step("Открыть раздел Book Store")
    public void openBookStore() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement bookStore = driver.findElement(By.xpath("//span[text()='Book Store']/.."));
        js.executeScript("arguments[0].click();", bookStore);
    }

    @Step("Открыть раздел Profile")
    public void openProfile() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement bookStore = driver.findElement(By.xpath("//span[text()='Profile']/.."));
        js.executeScript("arguments[0].click();", bookStore);
    }

    public void authorizeFail() {
        driver.findElement(By.cssSelector("#userName")).sendKeys("nologin");
        driver.findElement(By.cssSelector("#password")).sendKeys(ConfigHelper.getPassword());
        driver.findElement(By.cssSelector("#login")).click();
    }

    public List<WebElement> getListBooksProfile() {
        return driver.findElements(By.xpath("//div[@class='rt-td'][img]"));
    }

    public void displayTenRowsInBookCollection() {
        WebElement rows = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//span[@class='select-wrap -pageSizeOptions']")));
        rows.click();
        driver.findElement(By.xpath("//option[@value='10']")).click();
    }

    public void deleteBookCollectionUi() {
        JavascriptExecutor scroll = (JavascriptExecutor) driver;
        scroll.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=\"Delete All Books\"]")));
        scroll.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        btn.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.xpath("//*[@id=\"closeSmallModal-ok\"]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert2 = driver.switchTo().alert();
        alert2.accept();
    }


    public String AuthoriseSuccessText() {

        return driver.findElement(By.cssSelector("#userName-value")).getText();
    }

    public String AuthoriseFailText() {

        return driver.findElement(By.cssSelector("#name")).getText();
    }

    public String getWarningText() {
        return driver.findElement(By.cssSelector(".rt-noData")).getText();
    }

    //чудовищный костыль :(
    @Step("Добавить шесть книг в коллекцию")
    public void addBooksBySwagger() throws InterruptedException {
        driver.get(" https://demoqa.com/swagger");
        driver.findElement(By.xpath("//span[text()='Authorize']/.")).click();
        driver.findElement(By.xpath("//input[@required] ")).sendKeys("Tarasapiens");
        driver.findElement(By.xpath("//input[@name='password'] ")).sendKeys("Innopolis&2024!");
        driver.findElement(By.xpath("//button[@type='submit'][1]")).click();
        driver.findElement(By.xpath("//button[@class='btn modal-btn auth btn-done button'][1]")).click();
        driver.findElement(By.xpath("//*[@id=\"operations-BookStore-BookStoreV1BooksPost\"]/div[1]/button[1]/span")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//button[@class='btn try-out__btn']")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//textarea[@class='body-param__text']")).clear();
        driver.findElement(By.xpath("//textarea[@class='body-param__text']")).sendKeys(book.addBookApi);
        driver.findElement(By.xpath("//button[@class='btn execute opblock-control__btn']")).click();
    }
}

