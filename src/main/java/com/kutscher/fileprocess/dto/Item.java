package com.kutscher.fileprocess.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

	private int itemId;
	private int quantity;
	private BigDecimal value;

}
