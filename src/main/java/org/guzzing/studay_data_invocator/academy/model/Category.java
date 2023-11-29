package org.guzzing.studay_data_invocator.academy.model;

import jakarta.persistence.*;
import org.guzzing.studay_data_invocator.academy.model.vo.CategoryName;

@Table(name = "categories")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "category_name")
    @Enumerated(value = EnumType.STRING)
    CategoryName categoryName;

}
