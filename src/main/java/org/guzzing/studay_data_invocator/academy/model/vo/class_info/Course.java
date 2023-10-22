package org.guzzing.studay_data_invocator.academy.model.vo.class_info;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.springframework.util.Assert;

@Entity
@Table(name = "coures")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academies_id")
    private Academy academy;

    @Column(name = "curriculum", nullable = true)
    private String curriculum;

    @Column(name = "subject", nullable = true)
    private String subject;

    @Column(name = "capacity", nullable = true)
    private Integer capacity;

    @Column(name = "duration", nullable = true)
    private String duration;

    @Column(name = "total_fee", nullable = true)
    private Long totalFee;

    protected Course(
            final Academy academy,
            final String curriculum,
            final String subject,
            final Integer capacity,
            final String duration,
            final Long totalFee
    ) {
        Assert.notNull(academy, "학원 정보는 반드시 주어져야 합니다.");
        Assert.isTrue((capacity == null || capacity >= 0), "수강 가능 학생수 정보는 반드시 양수여야 합니다.");
        Assert.isTrue((totalFee == null || totalFee >= 0), "교육비는 반드시 양수여야 합니다.");

        this.academy = academy;
        this.curriculum = curriculum;
        this.subject = subject;
        this.capacity = capacity;
        this.duration = duration;
        this.totalFee = totalFee;
    }

    protected Course() {
    }

    public static Course of(
            final Academy academy,
            final String curriculum,
            final String subject,
            final String capacity,
            final String duration,
            final String totalFee
    ) {
        return new Course(
                academy,
                curriculum,
                subject,
                capacity.isBlank() ? null : Integer.parseInt(capacity),
                duration,
                totalFee.isBlank() ? null : Long.parseLong(totalFee)
        );
    }
}
