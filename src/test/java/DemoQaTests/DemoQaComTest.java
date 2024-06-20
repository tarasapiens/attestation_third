package DemoQaTests;

import ConfigHelper.ConfigHelper;
import Pages.ProfilePage;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import model.Books;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoQaComTest {

    Books book = new Books();
    private WebDriver driver;
    private ProfilePage pageProfile;

    @BeforeEach
    public void setDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        pageProfile = new ProfilePage(driver);
    }

    @AfterEach
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Owner("Сергей Тарасов")
    @DisplayName("Успешная авторизация")
    @Description("Проверяем успешную авторизацию по логину и паролю")
    @Tags({@Tag("авторизация"), @Tag("позитивный")})
    public void authoriseSuccess() {
        pageProfile.openAuthForm();
        pageProfile.authorizeSuccess();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        assertEquals(pageProfile.AuthoriseSuccessText(), ConfigHelper.getLogin());
    }

    @Test
    @Owner("Сергей Тарасов")
    @DisplayName("Неудачная авторизация")
    @Description("Проверяем вывод предупреждения при вводе неверного логина или пароля")
    @Tags({@Tag("авторизация"), @Tag("негативный")})
    public void authoriseFail() {
        pageProfile.openAuthForm();
        pageProfile.authorizeFail();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        assertEquals(pageProfile.AuthoriseFailText(), ConfigHelper.getWarningAuthoriseFail());
    }

    @Test
    @Owner("Сергей Тарасов")
    @DisplayName("Сценарий_1: Проверка коллекции книг")
    @Description("Проверяем, что в коллекции пользователя нет книг")
    @Tags({@Tag("таблица"), @Tag("позитивный"), @Tag("книги")})
    public void chekListBookIsEmpty() {
        pageProfile.openAuthForm();
        pageProfile.authorizeSuccess();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        assertEquals(pageProfile.getWarningText(), ConfigHelper.getNoBooksText());
    }

    @Test
    @Owner("Сергей Тарасов")
    @DisplayName("Сценарий_2: Проверка добавления книг в коллекцию пользователя")
    @Description("Проверяем, что аторизованный пользователь может добавить книги в коллекцию")
    @Tags({@Tag("таблица"), @Tag("позитивный"), @Tag("книги")})
    public void addBooks() throws InterruptedException {
        pageProfile.openAuthForm();
        pageProfile.authorizeSuccess();
        Thread.sleep(5000);
        pageProfile.openBookStore();

        System.err.println("БАГ! В UI нет возможности добавить книги");
    }

    @Test
    @Owner("Сергей Тарасов")
    @DisplayName("Сценарий_2: Добавление книги через API")
    @Description("Проверяем, что аторизованный пользователь может добавить книги в коллекцию")
    @Tags({@Tag("таблица"), @Tag("позитивный"), @Tag("книги")})
    public void addBooksByApi() {
        book.authoriseByApi();
        book.addBookByApi();
        book.chekBookCollection();

        System.err.println("Баг. Добавление книг через API не работает");
    }

    @Test
    @Owner("Сергей Тарасов")
    @DisplayName("*Сценарий_2: Добавление шести книг в коллекцию обходным путем")
    @Description("Проверяем, что аторизованный пользователь может добавить книги в коллекцию")
    @Tags({@Tag("таблица"), @Tag("позитивный"), @Tag("книги")})
    public void addBooksApiSwagger() throws InterruptedException {
        pageProfile.addBooksBySwagger();
        //прошу меня извинить за эту дичь с добавлением книг через UI Swagger'a, но иначе
        //с моей стороны не отрабатывалось никак. Пришлось делать ужасные костыли.
        //осознаю чудовищную безобразность способа, но важно было добавить книги, чтобы протестировать UI сайта
        //и это работает

        pageProfile.openAuthForm();
        pageProfile.authorizeSuccess();
        Thread.sleep(10000); //Sleep добавлен из необходимости - на моей стороне долгая прогрузка
        JavascriptExecutor scroll = (JavascriptExecutor) driver;
        scroll.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        pageProfile.displayTenRowsInBookCollection();
        assertEquals(6, pageProfile.getListBooksProfile().size());
        pageProfile.deleteBookCollectionUi();
    }

    @Test
    @Owner("Сергей Тарасов")
    @DisplayName("Сценарий_3: Добавление книг в коллекцию и их удаление")
    @Description("Проверяем, что аторизованный пользователь может добавить книги в коллекцию и удалить их")
    @Tags({@Tag("таблица"), @Tag("позитивный"), @Tag("книги")})
    public void addAndDeleteBooksChek() throws InterruptedException {
        pageProfile.addBooksBySwagger();
        pageProfile.openAuthForm();
        pageProfile.authorizeSuccess();
        Thread.sleep(10000);
        JavascriptExecutor scroll = (JavascriptExecutor) driver;
        scroll.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        pageProfile.displayTenRowsInBookCollection();
        assertEquals(6, pageProfile.getListBooksProfile().size());
        pageProfile.deleteBookCollectionUi();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        assertEquals(pageProfile.getWarningText(), ConfigHelper.getNoBooksText());
    }
}

