package com.d23alex.fairbusiness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcquiredTariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private TariffType tariffType;
    private Timestamp acquiredTimestamp;


    public boolean isTariffActive() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        long elapsedTime = currentTimestamp.getTime() - acquiredTimestamp.getTime();
        long durationInMillis = tariffType.getDurationInSeconds() * 1000;

        return elapsedTime < durationInMillis;
    }

    public Timestamp expires() {
        return new Timestamp(acquiredTimestamp.getTime() + tariffType.getDurationInSeconds() * 1000);
    }
}
