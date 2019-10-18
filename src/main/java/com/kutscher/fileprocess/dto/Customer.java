package com.kutscher.fileprocess.dto;


import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
	
	private String CNPJ;
	
	private String name;
	
	private String businessArea;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return Objects.equals(CNPJ, customer.CNPJ);
	}

	@Override
	public int hashCode() {
		return Objects.hash(CNPJ);
	}

}
