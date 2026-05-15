import org.example.DriveManager;
import org.example.page.MainPage;
import org.example.service.MainPageService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import static org.example.utils.Constants.MAIN_PAGE_URL;

public class BaseTest {
    protected MainPage mainPage = new MainPage();
    protected MainPageService mainPageService = new MainPageService();

    @BeforeEach
    public void startTests(){

        mainPage.openPage(MAIN_PAGE_URL);

        // Закрываем куки, если они перекрывают элементы
        try {
            mainPage.clickCookie();
        } catch (Exception ignored) {
        }
    }

    @AfterAll
    public static void stopBrowser(){
        DriveManager.quitDriver();
    }
}
