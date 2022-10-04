package com.t95.t95backend.returnBean;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReturnPosition {

	private Long positionId;
	private Long quantity;
	private Double costBasis;
	private String openDate;
	private String name;
	private String symbol;
	private String price;
	private String movement_points;
	private String movement_percentage ;	
	
	public ReturnPosition(Long positionId, Long quantity, Double costBasis, String openDate, String name, String symbol, String price,
			String movement_points, String movement_percentage) {
		this.positionId = positionId;
		this.quantity = quantity;
		this.costBasis = costBasis;
		this.openDate = openDate;
		this.name = name;
		this.symbol = symbol;
		this.price = price;
		this.movement_points = movement_points;
		this.movement_percentage = movement_percentage;
	}
	
	
}

