package com.hackathon.wowlunteer.security.auth.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
