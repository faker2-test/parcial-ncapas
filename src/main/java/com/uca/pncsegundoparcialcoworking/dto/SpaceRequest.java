package com.uca.pncsegundoparcialcoworking.dto;

import com.uca.pncsegundoparcialcoworking.entity.SpaceType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class SpaceRequest {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    private String description;

    @NotNull(message = "El tipo es requerido")
    private SpaceType type;

    @NotNull(message = "La capacidad es requerida")
    @Min(value = 1, message = "La capacidad mínima es 1")
    private Integer capacity;

    @NotNull(message = "El precio por hora es requerido")
    @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser mayor a 0")
    private BigDecimal pricePerHour;

    private Boolean available;

    @NotNull(message = "El piso es requerido")
    @Min(value = 0, message = "El número de piso no puede ser negativo")
    private Integer floor;

    private String amenities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SpaceType getType() {
        return type;
    }

    public void setType(SpaceType type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }
}
