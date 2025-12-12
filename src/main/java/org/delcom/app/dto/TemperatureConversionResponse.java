package org.delcom.app.dto;

import java.time.LocalDateTime;

public class TemperatureConversionResponse {
    private Long id;
    private Double inputTemperature;
    private String inputUnit;
    private Double celsius;
    private Double fahrenheit;
    private Double kelvin;
    private Double reamur;
    private LocalDateTime createdAt;
    
    public TemperatureConversionResponse() {
    }
    
    public TemperatureConversionResponse(Long id, Double inputTemperature, String inputUnit,
                                        Double celsius, Double fahrenheit, Double kelvin,
                                        Double reamur, LocalDateTime createdAt) {
        this.id = id;
        this.inputTemperature = inputTemperature;
        this.inputUnit = inputUnit;
        this.celsius = celsius;
        this.fahrenheit = fahrenheit;
        this.kelvin = kelvin;
        this.reamur = reamur;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Double getInputTemperature() {
        return inputTemperature;
    }
    
    public void setInputTemperature(Double inputTemperature) {
        this.inputTemperature = inputTemperature;
    }
    
    public String getInputUnit() {
        return inputUnit;
    }
    
    public void setInputUnit(String inputUnit) {
        this.inputUnit = inputUnit;
    }
    
    public Double getCelsius() {
        return celsius;
    }
    
    public void setCelsius(Double celsius) {
        this.celsius = celsius;
    }
    
    public Double getFahrenheit() {
        return fahrenheit;
    }
    
    public void setFahrenheit(Double fahrenheit) {
        this.fahrenheit = fahrenheit;
    }
    
    public Double getKelvin() {
        return kelvin;
    }
    
    public void setKelvin(Double kelvin) {
        this.kelvin = kelvin;
    }
    
    public Double getReamur() {
        return reamur;
    }
    
    public void setReamur(Double reamur) {
        this.reamur = reamur;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "TemperatureConversionResponse{" +
                "id=" + id +
                ", inputTemperature=" + inputTemperature +
                ", inputUnit='" + inputUnit + '\'' +
                ", celsius=" + celsius +
                ", fahrenheit=" + fahrenheit +
                ", kelvin=" + kelvin +
                ", reamur=" + reamur +
                ", createdAt=" + createdAt +
                '}';
    }
}
