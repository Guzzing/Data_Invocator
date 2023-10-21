package org.guzzing.studay_data_invocator.academy.model.vo.class_info;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@Embeddable
public class Course {

    @Column(name = "curriculm", nullable = true)
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
            final String curriculum,
            final String subject,
            final Integer capacity,
            final String duration,
            final Long totalFee
    ) {
        Assert.isTrue((capacity == null || capacity >= 0), "수강 가능 학생수 정보는 반드시 양수여야 합니다.");
        Assert.isTrue((totalFee == null || totalFee >= 0), "교육비는 반드시 양수여야 합니다.");

        this.curriculum = curriculum;
        this.subject = subject;
        this.capacity = capacity;
        this.duration = duration;
        this.totalFee = totalFee;
    }

    protected Course() {
    }

    public static Course of(
            final String curriculum,
            final String subject,
            final String capacity,
            final String duration,
            final String totalFee
    ) {
        return new Course(
                curriculum,
                subject,
                capacity.isBlank() ? null : Integer.parseInt(capacity),
                duration,
                totalFee.isBlank() ? null : Long.parseLong(totalFee)
        );
    }
}
