package org.delcom.app.services;

import org.delcom.app.dto.TemperatureConversionRequest;
import org.delcom.app.dto.TemperatureConversionResponse;
import org.delcom.app.entities.TemperatureConversion;
import org.delcom.app.repositories.TemperatureConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemperatureConversionService {
    
    @Autowired
    private TemperatureConversionRepository repository;
    
    /**
     * Konversi suhu dari unit apapun ke semua unit (Celsius, Fahrenheit, Kelvin, Reamur)
     */
    public TemperatureConversionResponse convertTemperature(TemperatureConversionRequest request) {
        // Validasi input
        if (request.getTemperature() == null || request.getUnit() == null) {
            throw new IllegalArgumentException("Temperature dan Unit tidak boleh kosong");
        }
        
        String unit = request.getUnit().toUpperCase();
        Double temperature = request.getTemperature();
        
        // Konversi ke Celsius terlebih dahulu
        Double celsius = convertToCelsius(temperature, unit);
        
        // Konversi dari Celsius ke unit lainnya
        Double fahrenheit = celsiusToFahrenheit(celsius);
        Double kelvin = celsiusToKelvin(celsius);
        Double reamur = celsiusToReamur(celsius);
        
        // Simpan ke database
        TemperatureConversion conversion = new TemperatureConversion(temperature, unit);
        conversion.setCelsius(roundToTwoDecimal(celsius));
        conversion.setFahrenheit(roundToTwoDecimal(fahrenheit));
        conversion.setKelvin(roundToTwoDecimal(kelvin));
        conversion.setReamur(roundToTwoDecimal(reamur));
        
        TemperatureConversion saved = repository.save(conversion);
        
        // Return response
        return mapToResponse(saved);
    }
    
    /**
     * Konversi semua unit ke Celsius
     */
    private Double convertToCelsius(Double temperature, String unit) {
        return switch (unit) {
            case "CELSIUS" -> temperature;
            case "FAHRENHEIT" -> fahrenheitToCelsius(temperature);
            case "KELVIN" -> kelvinToCelsius(temperature);
            case "REAMUR" -> reamurToCelsius(temperature);
            default -> throw new IllegalArgumentException("Unit tidak dikenal: " + unit);
        };
    }
    
    /**
     * Celsius to Fahrenheit: (C × 9/5) + 32
     */
    private Double celsiusToFahrenheit(Double celsius) {
        return (celsius * 9.0 / 5.0) + 32;
    }
    
    /**
     * Celsius to Kelvin: C + 273.15
     */
    private Double celsiusToKelvin(Double celsius) {
        return celsius + 273.15;
    }
    
    /**
     * Celsius to Reamur: C × 4/5
     */
    private Double celsiusToReamur(Double celsius) {
        return celsius * 4.0 / 5.0;
    }
    
    /**
     * Fahrenheit to Celsius: (F - 32) × 5/9
     */
    private Double fahrenheitToCelsius(Double fahrenheit) {
        return (fahrenheit - 32) * 5.0 / 9.0;
    }
    
    /**
     * Kelvin to Celsius: K - 273.15
     */
    private Double kelvinToCelsius(Double kelvin) {
        return kelvin - 273.15;
    }
    
    /**
     * Reamur to Celsius: R × 5/4
     */
    private Double reamurToCelsius(Double reamur) {
        return reamur * 5.0 / 4.0;
    }
    
    /**
     * Dapatkan semua riwayat konversi
     */
    public List<TemperatureConversionResponse> getAllConversions() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan riwayat konversi berdasarkan unit input
     */
    public List<TemperatureConversionResponse> getConversionsByUnit(String unit) {
        return repository.findByInputUnit(unit.toUpperCase()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan riwayat konversi berdasarkan ID
     */
    public TemperatureConversionResponse getConversionById(Long id) {
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Konversi tidak ditemukan dengan ID: " + id));
    }
    
    /**
     * Hapus konversi berdasarkan ID
     */
    public void deleteConversion(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Konversi tidak ditemukan dengan ID: " + id);
        }
        repository.deleteById(id);
    }
    
    /**
     * Map entity ke response
     */
    private TemperatureConversionResponse mapToResponse(TemperatureConversion entity) {
        return new TemperatureConversionResponse(
                entity.getId(),
                entity.getInputTemperature(),
                entity.getInputUnit(),
                entity.getCelsius(),
                entity.getFahrenheit(),
                entity.getKelvin(),
                entity.getReamur(),
                entity.getCreatedAt()
        );
    }
    
    /**
     * Pembulatan ke 2 desimal
     */
    private Double roundToTwoDecimal(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
