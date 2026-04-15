package ru.adel.dataproviders;

import org.apache.http.HttpStatus;
import org.junit.jupiter.params.provider.Arguments;

import java.util.UUID;
import java.util.stream.Stream;

public class UserTestData {
    public static Stream<Arguments> userData() {
        return Stream.of(
                Arguments.of("valid-user-" + UUID.randomUUID(), "tets@test.ru", HttpStatus.SC_OK),
                Arguments.of("invalid-email-user", "not_email_test", HttpStatus.SC_BAD_REQUEST),
                Arguments.of("duplicate-user", "test@test.ru", HttpStatus.SC_BAD_REQUEST)
        );
    }
}
