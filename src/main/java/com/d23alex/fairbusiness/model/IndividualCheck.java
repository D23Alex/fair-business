package com.d23alex.fairbusiness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class IndividualCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne // типа тариф списания
    private AcquiredTariff acquiredTariff;
    private boolean automatedChecksDone = false;
    private boolean manualChecksDone = false;
    @ManyToOne
    private Clerk assignedClerk;
    @ManyToOne
    private Person person;
    @ManyToOne
    private Passport passport;
    @ManyToOne
    private TIN tin;
    @OneToMany
    private List<BankBan> bankBans = new ArrayList<>();
    @ManyToOne
    private DriverLicenseDetails driverLicenseDetails;
    @OneToMany
    private List<DrivingFine> drivingFines = new ArrayList<>();
    @OneToMany
    private List<LeaseDetails> leaseDetails = new ArrayList<>();
}
