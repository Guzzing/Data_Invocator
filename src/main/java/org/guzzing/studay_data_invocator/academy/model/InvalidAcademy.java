package org.guzzing.studay_data_invocator.academy.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "invalid_addresses")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidAcademy that = (InvalidAcademy) o;
        return Objects.equals(id, that.id) && Objects.equals(address, that.address) && Objects.equals(academy_name, that.academy_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, academy_name);
    }

}
