package org.delcom.app.dto;

public class TemperatureConversionRequest {
    private Double temperature;
    private String unit; // CELSIUS, FAHRENHEIT, KELVIN, REAMUR
    
    public TemperatureConversionRequest() {
    }
    
    public TemperatureConversionRequest(Double temperature, String unit) {
        this.temperature = temperature;
        this.unit = unit;
    }
    
    public Double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    @Override
    public String toString() {
        return "TemperatureConversionRequest{" +
                "temperature=" + temperature +
                ", unit='" + unit + '\'' +
                '}';
    }
}
