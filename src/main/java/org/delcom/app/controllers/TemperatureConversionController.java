package org.delcom.app.controllers;

import org.delcom.app.configs.ApiResponse;
import org.delcom.app.dto.TemperatureConversionRequest;
import org.delcom.app.dto.TemperatureConversionResponse;
import org.delcom.app.services.TemperatureConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temperature")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TemperatureConversionController {
    
    @Autowired
    private TemperatureConversionService temperatureConversionService;
    
    /**
     * POST /api/temperature/convert
     * Melakukan konversi suhu dari satu unit ke semua unit lainnya
     */
    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<TemperatureConversionResponse>> convertTemperature(
            @RequestBody TemperatureConversionRequest request) {
        try {
            TemperatureConversionResponse response = temperatureConversionService.convertTemperature(request);
            return ResponseEntity.ok(new ApiResponse<>(
                    "200",
                    "Konversi suhu berhasil",
                    response
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(
                    "400",
                    "Error: " + e.getMessage(),
                    null
            ));
        }
    }
    
    /**
     * GET /api/temperature/all
     * Mendapatkan semua riwayat konversi suhu
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TemperatureConversionResponse>>> getAllConversions() {
        List<TemperatureConversionResponse> conversions = temperatureConversionService.getAllConversions();
        return ResponseEntity.ok(new ApiResponse<>(
                "200",
                "Data konversi berhasil diambil",
                conversions
        ));
    }
    
    /**
     * GET /api/temperature/by-unit/{unit}
     * Mendapatkan riwayat konversi berdasarkan unit input
     */
    @GetMapping("/by-unit/{unit}")
    public ResponseEntity<ApiResponse<List<TemperatureConversionResponse>>> getConversionsByUnit(
            @PathVariable String unit) {
        try {
            List<TemperatureConversionResponse> conversions = temperatureConversionService.getConversionsByUnit(unit);
            return ResponseEntity.ok(new ApiResponse<>(
                    "200",
                    "Data konversi untuk unit " + unit + " berhasil diambil",
                    conversions
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(
                    "400",
                    "Error: " + e.getMessage(),
                    null
            ));
        }
    }
    
    /**
     * GET /api/temperature/{id}
     * Mendapatkan detail konversi berdasarkan ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TemperatureConversionResponse>> getConversionById(
            @PathVariable Long id) {
        try {
            TemperatureConversionResponse conversion = temperatureConversionService.getConversionById(id);
            return ResponseEntity.ok(new ApiResponse<>(
                    "200",
                    "Detail konversi berhasil diambil",
                    conversion
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "404",
                    "Error: " + e.getMessage(),
                    null
            ));
        }
    }
    
    /**
     * DELETE /api/temperature/{id}
     * Menghapus konversi berdasarkan ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteConversion(@PathVariable Long id) {
        try {
            temperatureConversionService.deleteConversion(id);
            return ResponseEntity.ok(new ApiResponse<>(
                    "200",
                    "Konversi berhasil dihapus",
                    null
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "404",
                    "Error: " + e.getMessage(),
                    null
            ));
        }
    }
}
