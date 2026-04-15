package ru.adel.tests.mixed.admin.users;

import io.restassured.response.Response;

import org.apache.http.HttpStatus;
import ru.adel.mocks.mixed.FastApiUiMock;
import ru.adel.tests.api.accounts.UserApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.adel.tests.mixed.BaseApiUiTest;
import ru.adel.tests.ui.pages.UsersPage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateDeleteUser extends BaseApiUiTest {
    private String userName;
    private String userId;
    private FastApiUiMock apiUiMock;

    @BeforeEach
    void setupTest() {
        UUID guid = UUID.randomUUID();
        userId = "123";
        userName = "user-name-" + guid;
        apiClient = new UserApiClient(url, endpoint);
        apiUiMock = new FastApiUiMock();
    }

    //с моком
    @Test
    void createUser_checkOnUi_andCheckDeleted_Mocked() {
        //Мокаем api
        apiUiMock.mockApiUsers(userName, userId);
        //Отправка пользователя по api
        Response createResponse = createUser();

        //Проверка статус кода
        checkStatusCode(createResponse);
        getUserId(createResponse);

        //Мокаем ui
        apiUiMock.mockUiData(userName, userId);
        //Проверка пользователя на UI
        UsersPage usersPage = getPageAndCheckUserCreated();

        //мокаем delete
        apiUiMock.mockApiDelete(userId);
        //Удаление пользователя
        deleteUser(userId);

        //мокаем Ui delete
        apiUiMock.mockUiDelete(userId);

        //Проверка удаления на UI
        checkUiUserDeleted(usersPage);
    }

    private void checkStatusCode(Response createResponse) {
        assertEquals(HttpStatus.SC_OK, createResponse.statusCode());
    }

    //без мока
    @Test
    void createUser_checkOnUi_andCheckDeleted() {
        //Отправка пользователя по api
        Response createResponse = createUser();

        //Проверка статус кода
        checkStatusCode(createResponse);
        getUserId(createResponse);

        //Проверка пользователя на UI
        UsersPage usersPage = getPageAndCheckUserCreated();

        //Удаление пользователя
        deleteUser(userId);

        //Проверка удаления на UI
        checkUiUserDeleted(usersPage);
    }

    private void getUserId(Response createResponse) {
        this.userId = createResponse.jsonPath().getString("id");
    }

    private UsersPage getPageAndCheckUserCreated() {
        UsersPage usersPage = getUsersPage();
        assertTrue(usersPage.isUserPresent(userName));
        return usersPage;
    }

    private void checkUiUserDeleted(UsersPage usersPage) {
        driver.navigate().refresh();
        assertFalse(usersPage.isUserPresent(userName));
    }

    private void deleteUser(String userId) {
        apiClient.deleteUser(userId);
    }

    private UsersPage getUsersPage() {
        driver.get(url + endpoint + "/" + userId);
        return new UsersPage(driver);
    }

    private Response createUser() {
        Map<String, Object> user = new HashMap<>();
        user.put("name", userName);
        return apiClient.createUser(user);
    }
}
