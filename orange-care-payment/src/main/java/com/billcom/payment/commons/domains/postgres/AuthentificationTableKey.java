package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class AuthentificationTableKey implements Serializable 
{
	@Column(name = "canal")
	private String canal;

	@Column(name = "operation_type")
	private String operationType;

	public AuthentificationTableKey() {
	}



	public AuthentificationTableKey(String canal, String operationType)
	{
		super();
		this.canal = canal;
		this.operationType = operationType;
	}
}
