package com.billcom.authentication.jose;

import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.UUID;

public final class Jwks {

	private Jwks() {
	}

	public static RSAKey generateRsa() {
		KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		return new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
	}

	public static OctetSequenceKey generateSecret() {
		SecretKey secretKey = KeyGeneratorUtils.generateSecretKey();
		// @formatter:off
		return new OctetSequenceKey.Builder(secretKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		// @formatter:on
	}

	static KeyPair generateEcKey() {
		EllipticCurve ellipticCurve = new EllipticCurve(
				new ECFieldFp(
						new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951")),
				new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853948"),
				new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291"));
		ECPoint ecPoint = new ECPoint(
				new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286"),
				new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109"));
		ECParameterSpec ecParameterSpec = new ECParameterSpec(
				ellipticCurve,
				ecPoint,
				new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369"),
				1);

		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
			keyPairGenerator.initialize(ecParameterSpec);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}
}
