package com.d23alex.fairbusiness.service;

import com.d23alex.fairbusiness.model.*;
import com.d23alex.fairbusiness.payload.CheckRequest;
import com.d23alex.fairbusiness.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class IndividualCheckService {
    public static class CheckRequestException extends RuntimeException {
        public CheckRequestException(String s) {
            super(s);
        }
    }


    public final ConcurrentLinkedQueue<IndividualCheck> checksRequiringManualProcessing = new ConcurrentLinkedQueue<>();


    private final PersonRepository personRepository;
    private final PassportRepository passportRepository;
    private final TINRepository tinRepository;
    private final IndividualCheckRepository individualCheckRepository;
    private final BankBanRepository bankBanRepository;


    public IndividualCheckService(PersonRepository personRepository, PassportRepository passportRepository, TINRepository tinRepository, IndividualCheckRepository individualCheckRepository, BankBanRepository bankBanRepository) {
        this.personRepository = personRepository;
        this.passportRepository = passportRepository;
        this.tinRepository = tinRepository;
        this.individualCheckRepository = individualCheckRepository;
        this.bankBanRepository = bankBanRepository;
    }


    public IndividualCheck createCheck(Person personBeingChecked, AcquiredTariff acquiredTariff) {
        return individualCheckRepository.save(
                IndividualCheck.builder()
                        .person(personBeingChecked)
                        .acquiredTariff(acquiredTariff).build()
        );
    }

    public void runAutomatedChecks(IndividualCheck individualCheck) {
        Person person = individualCheck.getPerson();
        individualCheck.setBankBans(bankBanRepository.findAllByPerson(person));
        //TODO: bla

        individualCheck.setAutomatedChecksDone(true);
        individualCheckRepository.save(individualCheck);
        checksRequiringManualProcessing.add(individualCheck);
    }

    public Person findPerson(CheckRequest checkRequest) {
        Optional<Passport> passport = Optional.empty();
        Optional<TIN> tin = Optional.empty();
        if (checkRequest.getPassportNumber() != null && checkRequest.getPassportSeries() != null)
            passport = passportRepository.findByNumberAndSeries(checkRequest.getPassportNumber(), checkRequest.getPassportSeries());
        if (checkRequest.getTIN() != null)
            tin = tinRepository.findByTIN(checkRequest.getTIN());

        if (checkRequest.getPassportNumber() == null
                && checkRequest.getPassportSeries() == null
                && checkRequest.getTIN() == null)
            throw new CheckRequestException("Passport data and TIN both not specified");

        if (passport.isEmpty() && tin.isEmpty())
            throw new CheckRequestException("No person found with that passport data or TIN");

        if (passport.isPresent() && !passport.get().isValid())
            throw new CheckRequestException("The passport has expired or has been declared invalid");

        if (passport.isPresent() && tin.isPresent() && !passport.get().getPerson().equals(tin.get().getPerson()))
            throw new CheckRequestException("Passport data and TIN don't match");

        Person person = passport.isPresent() ? passport.get().getPerson() : tin.get().getPerson();
        if (!person.getFirstName().equals(checkRequest.getFirstName())
                || !person.getLastName().equals(checkRequest.getLastName())
                || !person.getMiddleName().equals(checkRequest.getMiddleName()))
            throw new CheckRequestException("Passport (or TIN) to name mismatch");

        return person;
    }
}
