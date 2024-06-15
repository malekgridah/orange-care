package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workflow_api_authentification", schema = "alcatel")
public class AuthentificationTable {

	@EmbeddedId
	AuthentificationTableKey authentificationKey;
	
	@Column(name = "login")
	private String login;
	@Column(name = "password")
	private String password;

	
}
