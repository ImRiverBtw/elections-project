package com.election.electionbackend.security;

import com.election.electionbackend.models.forum.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWToken {

    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS_CLAIM = "ipa";

    private String username;
    private String userId;
    private UserRole role;
    private String ipAddress;

    public JWToken(String username, Long userId, UserRole role){
        this.username = username;
        this.userId = userId.toString();
        this.role = role;
    }

    public String encode(String issuer, String passphrase, int expiration){
        Key key = getKey(passphrase);
        return Jwts.builder()
                .subject(this.username)
                .id(this.userId)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .claim(JWT_ROLE_CLAIM, this.role.name())
                .claim(JWT_IPADDRESS_CLAIM, this.ipAddress != null ? this.ipAddress : "1.1.1.1")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private static Key getKey(String passphrase){
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());

}


}
