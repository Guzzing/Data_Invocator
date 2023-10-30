package org.guzzing.studay_data_invocator.academy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "not_valid_addresses")
public class NotFullAddressAcademy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String academy_name;

    protected NotFullAddressAcademy() {
    }

    protected NotFullAddressAcademy(String address, String academy_name) {
        this.address = address;
        this.academy_name = academy_name;
    }

    public static NotFullAddressAcademy of(String address, String academy_name) {
        return new NotFullAddressAcademy(address, academy_name);
    }

}
