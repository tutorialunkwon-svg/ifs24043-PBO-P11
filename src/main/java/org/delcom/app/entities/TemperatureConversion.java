package org.delcom.app.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "temperature_conversions")
public class TemperatureConversion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Double inputTemperature;
    
    @Column(nullable = false, length = 20)
    private String inputUnit; // CELSIUS, FAHRENHEIT, KELVIN, REAMUR
    
    @Column(nullable = false)
    private Double celsius;
    
    @Column(nullable = false)
    private Double fahrenheit;
    
    @Column(nullable = false)
    private Double kelvin;
    
    @Column(nullable = false)
    private Double reamur;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    // Constructor
    public TemperatureConversion() {
        this.createdAt = LocalDateTime.now();
    }
    
    public TemperatureConversion(Double inputTemperature, String inputUnit) {
        this.inputTemperature = inputTemperature;
        this.inputUnit = inputUnit;
        this.createdAt = LocalDateTime.now();
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
        return "TemperatureConversion{" +
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
