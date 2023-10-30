package org.guzzing.studay_data_invocator.academy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "not_valid_addresses")
public class InvalidAcademy implements Institute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String academy_name;

    protected InvalidAcademy() {
    }

    protected InvalidAcademy(String address, String academy_name) {
        this.address = address;
        this.academy_name = academy_name;
    }

    public static InvalidAcademy of(String address, String academy_name) {
        return new InvalidAcademy(address, academy_name);
    }

}
