package ru.netology.patient.service.medical;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

public class MedicalServiceImplementationTest {
    public static Stream<Arguments> sourceForTestCheckBloodPresure() {
        return Stream.of(
                Arguments.of("8d8c3589-2d17-4d59-8ad1-bac28f808db9",
                        new BloodPressure(120, 80),
                        new PatientInfo("8d8c3589-2d17-4d59-8ad1-bac28f808db9", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(200, 80)))),
                Arguments.of("8d8c3589-2d17-4d59-8ad1-bac28f808db9",
                        new BloodPressure(120, 80),
                        new PatientInfo("8d8c3589-2d17-4d59-8ad1-bac28f808db9", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))))

        );

    }

    public static Stream<Arguments> sourceForTestCheckTemperature() {
        return Stream.of(
                Arguments.of("8d8c3589-2d17-4d59-8ad1-bac28f808db9",
                        new BigDecimal("1"),
                        new PatientInfo("8d8c3589-2d17-4d59-8ad1-bac28f808db9", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)))),
                Arguments.of("8d8c3589-2d17-4d59-8ad1-bac28f808db9",
                        new BigDecimal("36"),
                        new PatientInfo("8d8c3589-2d17-4d59-8ad1-bac28f808db9", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))))

        );
    }

    @ParameterizedTest
    @MethodSource("sourceForTestCheckBloodPresure")
    public void testCheckBloodPresure(String patientId, BloodPressure bloodPressure, PatientInfo patientInfo) {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        when(patientInfoFileRepository.getById(patientId))
                .thenReturn(patientInfo);
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        when(patientInfoRepository.getById(patientId))
                .thenReturn(patientInfo);
        SendAlertService sendAlertService = new SendAlertServiceImpl();
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure(patientId, bloodPressure);
    }

    @ParameterizedTest
    @MethodSource("sourceForTestCheckTemperature")
    public void testCheckTemperature(String patientId, BigDecimal currentTemperature, PatientInfo patientInfo) {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        when(patientInfoFileRepository.getById(patientId))
                .thenReturn(patientInfo);
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        when(patientInfoRepository.getById(patientId))
                .thenReturn(patientInfo);
        SendAlertService sendAlertService = new SendAlertServiceImpl();
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature(patientId, currentTemperature);
    }
}
