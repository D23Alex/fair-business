package com.d23alex.fairbusiness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Person person;
    private Long series;
    private Long number;
    private LocalDate given;
    private LocalDate expires;
    @ManyToOne
    private Agency givenBy;
    private boolean isDeclaredInvalid = false;


    public boolean isValid() {
        return !isDeclaredInvalid && expires.isAfter(LocalDate.now());
    }
}
