package com.kutscher.fileprocess.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Salesman {

	private String name;
	private BigDecimal salary;
	private String cpf;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Salesman salesman = (Salesman) o;
		return Objects.equals(cpf, salesman.cpf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

}
