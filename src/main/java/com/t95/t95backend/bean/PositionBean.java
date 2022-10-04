package com.t95.t95backend.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PositionBean {
    Long quantity;
    Double costBasis;
    Date openDate;
    Date closeDate;
    Long positionId;
    Long stockId;
    
    //for new position
	public PositionBean(Long quantity, Double costBasis, Date openDate, Long stockId) {
		super();
		this.quantity = quantity;
		this.costBasis = costBasis;
		this.openDate = openDate;
		this.stockId = stockId;
	}
	
	//for edit position
	public PositionBean(Long positionId, Long quantity, Double costBasis, Date openDate) {
		super();
		this.quantity = quantity;
		this.costBasis = costBasis;
		this.openDate = openDate;
		this.positionId = positionId;
	}


	//for close position
	public PositionBean(Long positionId, Date closeDate) {
		this.closeDate = closeDate;
		this.positionId = positionId;
	}
    
    
}
