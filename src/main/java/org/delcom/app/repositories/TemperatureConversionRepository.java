package org.delcom.app.repositories;

import org.delcom.app.entities.TemperatureConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureConversionRepository extends JpaRepository<TemperatureConversion, Long> {
    List<TemperatureConversion> findByInputUnit(String inputUnit);
}
