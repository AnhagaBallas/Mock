package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {
    public static Stream<Arguments> sourceFortestLocalizationServiceImplmpl() {
        return Stream.of(
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.RUSSIA, "Добро пожаловать")
        );
    }

    @ParameterizedTest
    @MethodSource("sourceFortestLocalizationServiceImplmpl")
    public void testLocalizationServiceImplmpl(Country country, String expected) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String result = localizationService.locale(country);
        Assertions.assertEquals(result, expected);

    }
}
