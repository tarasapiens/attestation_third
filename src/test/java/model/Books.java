package model;

import ConfigHelper.ConfigHelper;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class Books {

    static String jsonAuth =
            "{\"userName\": \"Tarasapiens\", \n" + "\"password\": \"Innopolis&2024!\"}";

    public String addBookApi =
            "{\"userId\": " + ConfigHelper.getUserId() + ", \n"
                    + "\"collectionOfIsbns\": \n"
                    + "[{\"isbn\":" + ConfigHelper.getIsbnBook1() + "}, \n"
                    + "{\"isbn\":" + ConfigHelper.getIsbnBook2() + "}, \n"
                    + "{\"isbn\":" + ConfigHelper.getIsbnBook3() + "}, \n"
                    + "{\"isbn\":" + ConfigHelper.getIsbnBook4() + "}, \n"
                    + "{\"isbn\":" + ConfigHelper.getIsbnBook5() + "}, \n"
                    + "{\"isbn\":" + ConfigHelper.getIsbnBook6() + "}] \n"
                    + "}";

    @Step("Авторизоваться через API")
    public void authoriseByApi() {
        given()
                .body(jsonAuth)
                .contentType(ContentType.JSON)
                .when().post(ConfigHelper.getUrlAuthoriseByApi())
                .then()
                .statusCode(200)
                .log().all();
    }

    @Step("Добавить шесть книг в коллекцию через API")
    public void addBookByApi() {
        given()
                .log().all()
                .body(addBookApi)
                .contentType(ContentType.JSON)
                .when().post(ConfigHelper.getUrlAddBookApi())
                .then()
                .statusCode(201)
                .log().all();
    }

    @Step("Проверить, что в коллекцию добавились шесть книг")
    public void chekBookCollection() {
        given()
                .when()
                .get(ConfigHelper.getUrlBookList() + "a34ad075-696f-4046-8e37-46dac9380fcc")
                .then()
                .statusCode(200)
                .log().all();

    }
}
