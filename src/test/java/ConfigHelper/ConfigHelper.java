package ConfigHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigHelper {
    private static final String CONFIG_FILE = "src/main/resources/testdata.properties";

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getURL() {
        String URL = properties.getProperty("url");

        return URL;
    }

    public static String getUrlProfile() {
        String urlProfile = properties.getProperty("urlprofile");

        return urlProfile;
    }

    public static String getUrlBooks() {
        String urlBooks = properties.getProperty("urlbooks");

        return urlBooks;
    }

    public static String getUrlBookList() {
        String urlChekBookListByApi = properties.getProperty("urlchekbooklist");
        return urlChekBookListByApi;
    }

    public static String getLogin() {
        String login = properties.getProperty("login");

        return login;
    }

    public static String getPassword() {
        String password = properties.getProperty("password");

        return password;
    }

    public static String getWarningAuthoriseFail() {
        String WarningAuthoriseFail = properties.getProperty("authorisefailtext");
        return WarningAuthoriseFail;
    }

    public static String getNoBooksText() {
        String noBooks = properties.getProperty("warningnobooks");
        return noBooks;
    }

    public static String getUrlAddBookApi() {
        String urlBooksAdd = properties.getProperty("urladdbookapi");
        return urlBooksAdd;
    }

    public static String getUrlAuthoriseByApi() {
        String urlAuthorisebyApi = properties.getProperty("urlauthorise");
        return urlAuthorisebyApi;
    }

    public static String getUserId() {
        String userId = properties.getProperty("useridcookies");
        return userId;
    }

    public static String getIsbnBook1() {
        String IsbnBook1 = properties.getProperty("isbnbook1");
        return IsbnBook1;
    }

    public static String getIsbnBook2() {
        String IsbnBook2 = properties.getProperty("isbnbook2");
        return IsbnBook2;
    }

    public static String getIsbnBook3() {
        String IsbnBook3 = properties.getProperty("isbnbook3");
        return IsbnBook3;
    }

    public static String getIsbnBook4() {
        String IsbnBook4 = properties.getProperty("isbnbook4");
        return IsbnBook4;
    }

    public static String getIsbnBook5() {
        String IsbnBook5 = properties.getProperty("isbnbook5");
        return IsbnBook5;
    }

    public static String getIsbnBook6() {
        String IsbnBook6 = properties.getProperty("isbnbook6");
        return IsbnBook6;
    }
}


