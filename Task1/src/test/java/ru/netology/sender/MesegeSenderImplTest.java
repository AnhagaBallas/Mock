package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MesegeSenderImplTest {
    public static Stream<Arguments> sourceFortestMesegeSenderImpl() {
        return Stream.of(
                Arguments.of("172.0", "Добро пожаловать", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.", "Welcome", new Location("New York", Country.USA, null, 0))
        );

    }


    @ParameterizedTest
    @MethodSource("sourceFortestMesegeSenderImpl")
    public void testMesegeSenderImpl(String ip, String expected, Location location) {
        Map<String, String> headers = new HashMap<>();
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);
        Mockito.when(localizationService.locale(geoService.byIp(ip).getCountry()))
                .thenReturn(expected);
        headers.put(messageSender.IP_ADDRESS_HEADER, ip);

        String result = messageSender.send(headers);
        Assertions.assertEquals(result, expected);


    }
}
