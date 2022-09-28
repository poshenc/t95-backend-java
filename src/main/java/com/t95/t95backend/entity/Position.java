package com.t95.t95backend.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "positions")
@Table(name = "positions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Position {
	
	@Id
    @SequenceGenerator(name = "positions_id_gen", sequenceName = "positions_id_seq", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "positions_id_gen")

    @Column(name = "id", updatable = false)
    private Long id;
	
    @Column(name = "quantity")
    private Long quantity;
    
    @Column(name = "cost_basis")
    private Double costBasis;
    
    @Column(name = "open_date")
    private Date openDate;
    
    @Column(name = "close_date")
    private Date closeDate;
    
    @Column(name = "is_opened", nullable = false)
    private Boolean isOpened;
    
    @Column(name = "portfolio_id", nullable = false)
    private Long portfolioId;

    @Column(name = "stock_id", nullable = false)
    private Long stockId;

	public Position(Long quantity, Double costBasis, Date openDate, Date closeDate, Boolean isOpened, Long portfolioId,
			Long stockId) {
		super();
		this.quantity = quantity;
		this.costBasis = costBasis;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.isOpened = isOpened;
		this.portfolioId = portfolioId;
		this.stockId = stockId;
	}
    
    

}
