package org.guzzing.studay_data_invocator.academy.repository;

import org.guzzing.studay_data_invocator.academy.model.InvalidAcademy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidAddressRepository extends JpaRepository<InvalidAcademy, Long> {
}