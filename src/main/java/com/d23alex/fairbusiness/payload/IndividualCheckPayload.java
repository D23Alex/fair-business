package com.d23alex.fairbusiness.payload;

import com.d23alex.fairbusiness.model.IndividualCheck;
import com.d23alex.fairbusiness.payload.mappers.PassportMapper;
import com.d23alex.fairbusiness.payload.mappers.PersonMapper;
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
public class IndividualCheckPayload {
    private PersonPayload person;

    private PassportPayload passport;

    private Long TIN; // ИНН
    private String TINRegion;

    private boolean hasValidDriversLicense = false;
    private List<DriverLicensePayload> driverLicenseDetails = new ArrayList<>();

    private boolean isForeignAgent = false;

    private List<BankBanPayload> bankBans = new ArrayList<>();


    public IndividualCheckPayload(IndividualCheck individualCheck) {
        person = PersonMapper.INSTANCE.toPersonPayload(individualCheck.getPerson());
        passport = PassportMapper.INSTANCE.toPassportPayload(individualCheck.getPassport());
        TIN = individualCheck.getTin().getTIN();
        TINRegion = individualCheck.getTin().getGivenBy().getAddress();
    }
}
