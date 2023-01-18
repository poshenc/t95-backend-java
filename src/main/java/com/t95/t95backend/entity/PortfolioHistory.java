package com.t95.t95backend.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "portfolioHistory")
@Table(name = "portfolioHistory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PortfolioHistory {
	@Id
    @SequenceGenerator(name = "portfolioHistory_id_gen", sequenceName = "portfolioHistory_id_seq", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portfolioHistory_id_gen")

    @Column(name = "id", updatable = false)
    private Long id;
	
	@Column (name = "date")
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    
    @Column(name = "value")
    private Double value;
    
    @Column(name = "portfolio_id", nullable = false)
    private Long portfolio_id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

	public PortfolioHistory(LocalDate date, Double value, Long portfolio_id, Long userId) {
		this.date = date;
		this.value = value;
		this.portfolio_id = portfolio_id;
		this.userId = userId;
	}
}

