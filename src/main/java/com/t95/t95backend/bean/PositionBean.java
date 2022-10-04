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
}
