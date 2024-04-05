package com.d23alex.fairbusiness.controller;

import com.d23alex.fairbusiness.model.Person;
import com.d23alex.fairbusiness.payload.CheckRequest;
import com.d23alex.fairbusiness.payload.IndividualCheckPayload;
import com.d23alex.fairbusiness.repository.IndividualCheckRepository;
import com.d23alex.fairbusiness.repository.UserRepository;
import com.d23alex.fairbusiness.service.IndividualCheckService;
import com.d23alex.fairbusiness.service.IndividualCheckService.CheckRequestException;
import com.d23alex.fairbusiness.service.TariffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
public class CheckController {
    private final TariffService tariffService;
    private final UserRepository userRepository;
    private final IndividualCheckService individualCheckService;
    private final IndividualCheckRepository individualCheckRepository;


    public CheckController(TariffService tariffService,
                           UserRepository userRepository,
                           IndividualCheckService individualCheckService, IndividualCheckRepository individualCheckRepository) {
        this.tariffService = tariffService;
        this.userRepository = userRepository;
        this.individualCheckService = individualCheckService;
        this.individualCheckRepository = individualCheckRepository;
    }

    @PostMapping("/api/check")
    public ResponseEntity<String> makeIndividualCheck(@RequestParam Long userId, @RequestBody CheckRequest checkRequest) {
        var user = userRepository.findById(userId);
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user with the given id was found");

        Person personBeingChecked;
        try {
            personBeingChecked = individualCheckService.findPerson(checkRequest);
        } catch (CheckRequestException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        var acquiredTariffBeingUsed = tariffService.soonestExpiringTariffWithIndividualChecksLeft(user.get());
        if (acquiredTariffBeingUsed.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Деньги мне плати");
        }

        CompletableFuture.runAsync(() ->
                individualCheckService.runAutomatedChecks(
                        individualCheckService.createCheck(personBeingChecked, acquiredTariffBeingUsed.get())
                ));

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/api/check/{checkId}")
    public ResponseEntity<?> getIndividualCheck(@PathVariable Long checkId) {
        var check = individualCheckRepository.findById(checkId);
        if (check.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No check with the given id found");

        return ResponseEntity.ok(new IndividualCheckPayload(check.get()));
    }
}
