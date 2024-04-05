package com.d23alex.fairbusiness;

import com.d23alex.fairbusiness.model.*;
import com.d23alex.fairbusiness.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;

import static com.d23alex.fairbusiness.Util.runCurlScript;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FairBusinessApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class SomeTest {

    @LocalServerPort
    private int port;

    @Autowired
    IndividualCheckRepository individualCheckRepository;
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ClerkRepository clerkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TINRepository tinRepository;
    @Autowired
    private BankBanRepository bankBanRepository;
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private TariffTypeRepository tariffTypeRepository;
    @Autowired
    private AcquiredTariffRepository acquiredTariffRepository;


    private Agency govAgency1 = Agency.builder()
            .name("govAgency1Name")
            .address("govAgency1Address").build();
    private Agency govAgency2 = Agency.builder()
            .name("govAgency2Name")
            .address("govAgency2Address").build();
    private Agency govAgency3 = Agency.builder()
            .name("govAgency3Name")
            .address("govAgency3Address").build();
    private Agency bank1 = Agency.builder()
            .name("bank1Name")
            .address("bank1Address").build();
    private Agency bank2 = Agency.builder()
            .name("bank2Name")
            .address("bank2Address").build();
    private Agency bank3 = Agency.builder()
            .name("bank3Name")
            .address("bank3Address").build();

    private Person person1 = Person.builder()
            .firstName("firstname1")
            .lastName("lastname1")
            .middleName("middlename1").build();
    private Person person2 = Person.builder()
            .firstName("firstname2")
            .lastName("lastname2")
            .middleName("middlename2").build();
    private Person person3 = Person.builder()
            .firstName("firstname3")
            .lastName("lastname3")
            .middleName("middlename3").build();

    private Passport person1Passport = Passport.builder()
            .person(person1)
            .number(1L)
            .series(1L)
            .given(LocalDate.of(2000, 1, 1))
            .expires(LocalDate.of(2025, 1, 1))
            .givenBy(govAgency1)
            .build();
    private Passport person2Passport = Passport.builder()
            .person(person2)
            .number(2L)
            .series(2L)
            .given(LocalDate.of(2000, 1, 2))
            .expires(LocalDate.of(2025, 1, 2))
            .givenBy(govAgency1)
            .build();
    private Passport person3Passport = Passport.builder()
            .person(person3)
            .number(3L)
            .series(3L)
            .given(LocalDate.of(2000, 1, 3))
            .expires(LocalDate.of(2025, 1, 3))
            .givenBy(govAgency1)
            .build();

    private TIN person1TIN = TIN.builder()
            .person(person1)
            .TIN(1L)
            .givenBy(govAgency2).build();
    private TIN person2TIN = TIN.builder()
            .person(person2)
            .TIN(1L)
            .givenBy(govAgency2).build();
    private TIN person3TIN = TIN.builder()
            .person(person3)
            .TIN(1L)
            .givenBy(govAgency2).build();

    private User user1 = User.builder()
            .username("user1").build();

    private User user2 = User.builder()
            .username("user1").build();

    private User user3 = User.builder()
            .username("user1").build();

    private TariffType infinite = TariffType.builder()
            .name("infinite")
            .description("nearly infinite checks, lasts for a lot of time")
            .durationInSeconds(99999999999L)
            .IndividualChecks(Long.MAX_VALUE).build();

    private AcquiredTariff user1Tariff = AcquiredTariff.builder()
            .user(user1)
            .tariffType(infinite)
            .acquiredTimestamp(new Timestamp(1)).build();

    private AcquiredTariff user2Tariff = AcquiredTariff.builder()
            .user(user2)
            .tariffType(infinite)
            .acquiredTimestamp(new Timestamp(1)).build();

    private AcquiredTariff user3Tariff = AcquiredTariff.builder()
            .user(user3)
            .tariffType(infinite)
            .acquiredTimestamp(new Timestamp(1)).build();


    private void saveDefaultData() {
        agencyRepository.save(govAgency1);
        agencyRepository.save(govAgency2);
        agencyRepository.save(govAgency3);
        agencyRepository.save(bank1);
        agencyRepository.save(bank2);
        agencyRepository.save(bank3);

        personRepository.save(person1);
        personRepository.save(person2);
        personRepository.save(person3);

        passportRepository.save(person1Passport);
        passportRepository.save(person2Passport);
        passportRepository.save(person3Passport);

        tinRepository.save(person1TIN);
        tinRepository.save(person2TIN);
        tinRepository.save(person3TIN);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        tariffTypeRepository.save(infinite);
        acquiredTariffRepository.save(user1Tariff);
        acquiredTariffRepository.save(user2Tariff);
        acquiredTariffRepository.save(user3Tariff);
    }


    @Test
    @DirtiesContext
    public void test1() throws IOException, InterruptedException {
        saveDefaultData();

        assertEquals(0, individualCheckRepository.count());

        String response = runCurlScript(new File(System.getProperty("user.dir")
                + "/src/test/resources/curl/check-person-1-by-user-1.sh"), port);

        assertEquals(1, individualCheckRepository.count());

    }
 }
