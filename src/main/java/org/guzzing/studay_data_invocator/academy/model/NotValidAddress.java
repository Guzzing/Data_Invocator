package org.guzzing.studay_data_invocator.academy.model;

import jakarta.persistence.*;
import org.guzzing.studay_data_invocator.academy.model.vo.AcademyInfo;

@Entity
@Table(name = "not_valid_addresses")
public class NotValidAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String academy_name;

    protected NotValidAddress () {
    }

    protected NotValidAddress(String address, String academy_name) {
        this.address = address;
        this.academy_name = academy_name;
    }

    public static NotValidAddress of(String address, String academy_name) {
        return new NotValidAddress(address, academy_name);
    }

}
