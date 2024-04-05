package com.d23alex.fairbusiness.controller;

import com.d23alex.fairbusiness.model.DriverLicenseDetails;
import com.d23alex.fairbusiness.repository.ClerkRepository;
import com.d23alex.fairbusiness.repository.IndividualCheckRepository;
import com.d23alex.fairbusiness.service.IndividualCheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClerkController {
    private final IndividualCheckService individualCheckService;
    private final IndividualCheckRepository individualCheckRepository;
    private final ClerkRepository clerkRepository;


    public ClerkController(IndividualCheckService individualCheckService, IndividualCheckRepository individualCheckRepository, ClerkRepository clerkRepository) {
        this.individualCheckService = individualCheckService;
        this.individualCheckRepository = individualCheckRepository;
        this.clerkRepository = clerkRepository;
    }


    @PostMapping("/api/clerk/assign-new-check")
    public ResponseEntity<?> assignNewCheckToClerk(@RequestParam Long clerkId) {
        var clerk = clerkRepository.findById(clerkId);
        if (clerk.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No clerk with the given id found");
        var check = individualCheckService.checksRequiringManualProcessing.poll();
        if (check == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No check are available at the moment");
        check.setAssignedClerk(clerk.get());
        individualCheckRepository.save(check);
        return ResponseEntity.ok(check);
    }

    @PostMapping("/api/clerk/set-driver-license")
    public ResponseEntity<String> setDriverLicenseDetails(@RequestParam Long checkId,
                                                          @RequestBody DriverLicenseDetails driverLicenseDetails) {
        var check = individualCheckRepository.findById(checkId);
        if (check.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No check with the given id found");

        //TODO: check if this particular clerk is allowed to modify this check in LAB 2

        check.get().setDriverLicenseDetails(driverLicenseDetails);
        individualCheckRepository.save(check.get());

        return ResponseEntity.ok("modified");
    }

    @PostMapping("/api/clerk/consider-done")
    public ResponseEntity<String> considerCheckDone(@RequestParam Long checkId) {
        var check = individualCheckRepository.findById(checkId);
        if (check.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No check with the given id found");

        //TODO: check if this particular clerk is allowed to modify this check in LAB 2

        check.get().setManualChecksDone(true);
        individualCheckRepository.save(check.get());

        return ResponseEntity.ok("done");
    }
}
