package org.guzzing.studay_data_invocator.academy.model;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.util.Assert;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academies_id")
    private Academy academy;

    @Column(name = "subject", nullable = true)
    private String subject;

    @Column(name = "curriculum", nullable = true)
    private String curriculum;

    @Column(name = "capacity", nullable = true)
    private Integer capacity;

    @Column(name = "duration", nullable = true)
    private String duration;

    @Column(name = "total_fee", nullable = true)
    private Long totalFee;

    protected Lesson(
            final Academy academy,
            final String subject,
            final String curriculum,
            final Integer capacity,
            final String duration,
            final Long totalFee
    ) {
        Assert.notNull(academy, "학원 정보는 반드시 주어져야 합니다.");
        Assert.isTrue((capacity == null || capacity >= 0), "수강 가능 학생수 정보는 반드시 양수여야 합니다.");
        Assert.isTrue((totalFee == null || totalFee >= 0), "교육비는 반드시 양수여야 합니다.");

        this.academy = academy;
        this.subject = subject;
        this.curriculum = curriculum;
        this.capacity = capacity;
        this.duration = duration;
        this.totalFee = totalFee;
    }

    protected Lesson() {
    }

    public static Lesson of(
            final Academy academy,
            final String subject,
            final String curriculum,
            final String capacity,
            final String duration,
            final String totalFee
    ) {
        return new Lesson(
                academy,
                subject,
                curriculum,
                capacity.isBlank() ? null : Integer.parseInt(capacity),
                duration,
                totalFee.isBlank() ? null : Long.parseLong(totalFee)
        );
    }
}
