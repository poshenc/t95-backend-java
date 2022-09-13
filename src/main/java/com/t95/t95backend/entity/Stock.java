package com.t95.t95backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "stocks")
@Table(name = "stocks", uniqueConstraints = @UniqueConstraint(name = "symbol_unique", columnNames = "symbol"))

public class Stock {
    @Id
    @SequenceGenerator(name = "stocks_id_gen", sequenceName = "stocks_id_seq", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stocks_id_gen")

    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "movement_points", nullable = false)
    private String movementPoints;

    @Column(name = "movement_percentage", nullable = false)
    private String movementPercentage;

    public Stock() {
    }

    public Stock(Long id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public Stock(String name, String symbol, String price, String movementPoints, String movementPercentage) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.movementPoints = movementPoints;
        this.movementPercentage = movementPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMovementPoints() {
        return movementPoints;
    }

    public void setMovementPoints(String movementPoints) {
        this.movementPoints = movementPoints;
    }

    public String getMovementPercentage() {
        return movementPercentage;
    }

    public void setMovementPercentage(String movementPercentage) {
        this.movementPercentage = movementPercentage;
    }

    @Override
    public String toString() {
        return "Stocks{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", price='" + price + '\'' +
                ", movementPoints='" + movementPoints + '\'' +
                ", movementPercentage='" + movementPercentage + '\'' +
                '}';
    }
}
