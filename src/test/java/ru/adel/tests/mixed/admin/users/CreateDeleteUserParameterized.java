package ru.adel.tests.mixed.admin.users;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.adel.mocks.mixed.ApiUiMock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateDeleteUserParameterized extends CreateDeleteUser{
    @ParameterizedTest
    @MethodSource("ru.adel.dataproviders.UserTestData#userData")
    void createUser_checkOnUi_andCheckDeleted_Mocked_Parameterized(
            String userName,
            String email,
            int expectedStatus
    ) {
        //mock
        apiUiMock.mockApiUsersWithEmail(userName, email);
        apiUiMock.mockTriggered("duplicate-user");
        apiUiMock.mockTriggered("invalid-email-user");
        Response response = createUserWithEmail(userName, email);
        assertEquals(expectedStatus, response.statusCode());
    }

    private Response createUserWithEmail(String userName, String email) {
        Map<String, Object> userWithMail = new HashMap<>();
        userWithMail.put(userName, email);
        return apiClient.createUser(userWithMail);
    }
}
