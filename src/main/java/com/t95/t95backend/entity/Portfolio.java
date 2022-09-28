package com.t95.t95backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "portfolios")
@Table(name = "portfolios")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Portfolio {
	
	@Id
    @SequenceGenerator(name = "portfolios_id_gen", sequenceName = "portfolios_id_seq", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portfolios_id_gen")

    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "cash")
    private Double cash;

    @Column(name = "user_id", nullable = false)
    private Long userId;

	public Portfolio(Long id, String name, Double cash, Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.cash = cash;
		this.userId = userId;
	}

	public Portfolio(String name, Long userId) {
		super();
		this.name = name;
		this.userId = userId;
	}
    
    

}
