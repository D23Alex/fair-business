package com.d23alex.fairbusiness.service;

import com.d23alex.fairbusiness.model.AcquiredTariff;
import com.d23alex.fairbusiness.model.User;
import com.d23alex.fairbusiness.repository.AcquiredTariffRepository;
import com.d23alex.fairbusiness.repository.IndividualCheckRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TariffService {
    private final AcquiredTariffRepository acquiredTariffRepository;
    private final IndividualCheckRepository individualCheckRepository;

    public TariffService(AcquiredTariffRepository acquiredTariffRepository, IndividualCheckRepository individualCheckRepository) {
        this.acquiredTariffRepository = acquiredTariffRepository;
        this.individualCheckRepository = individualCheckRepository;
    }

    public static class TariffException extends RuntimeException {}

    public Optional<AcquiredTariff> soonestExpiringTariffWithIndividualChecksLeft(User user) {
        return allActiveTariffsByUser(user).stream()
                .filter(t -> individualCheckRepository.findAllByAcquiredTariff(t).size() < t.getTariffType().getIndividualChecks())
                .min(Comparator.comparing(AcquiredTariff::expires));
    }

    private List<AcquiredTariff> allActiveTariffsByUser(User user) {
        return acquiredTariffRepository.findAllByUser(user).stream().filter(AcquiredTariff::isTariffActive).toList();
    }
}
