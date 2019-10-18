package com.kutscher.fileprocess.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sale {

	private String saleId;
	private List<Item> sales;
	private String salesName;
	private BigDecimal totalOrder;

}
