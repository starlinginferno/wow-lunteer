package com.hackathon.wowlunteer.security.auth.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}