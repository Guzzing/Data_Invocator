package org.guzzing.studay_data_invocator.holiday;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "holidays")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KoreanHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_name", nullable = false)
    private String dateName;

    @Column(name = "date", nullable = false, unique = true)
    private LocalDate date;

    public KoreanHoliday(String dateName, LocalDate date) {
        this.dateName = dateName;
        this.date = date;
    }
}
