package com.d23alex.fairbusiness.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckRequest {
    // по факту проверка идёт по серии + номеру или по ИНН, но надо будет сверять данные, полученные по разным запросам,
    // а также проверять соответствие имени.
    private String firstName;
    private String lastName;
    private String middleName;
    private Long passportSeries;
    private Long passportNumber;
    private Long TIN; // ИНН
}
