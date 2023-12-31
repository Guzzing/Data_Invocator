package org.guzzing.studay_data_invocator.academy.model;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Table(name = "academy_categories")
@Entity
public class AcademyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academy_id")
    Academy academy;

    @Column(name = "category_id")
    Long categoryId;

    protected AcademyCategory() {
    }

    private AcademyCategory(
            final Academy academy,
            final Long categoryId
    ){
        this.academy = academy;
        this.categoryId = categoryId;
    }

    public static AcademyCategory of(
            final Academy academy,
            final Long categoryId
    ){
        return new AcademyCategory(
                academy,
                categoryId
        );
    }


}
