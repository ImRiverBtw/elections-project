package com.election.electionbackend.security;

import com.election.electionbackend.models.forum.UserRole;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWToken {

    public static final String JWT_ATTRIBUTE_NAME = "jwt";
    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS_CLAIM = "ipa";

    private static final String JWT_ISSUER_CLAIM = "iss";
    private static final String JWT_USERNAME_CLAIM = "sub";
    private static final String JWT_ACCOUNTID_CLAIM = "id";

    private String username;
    private String userId;
    private UserRole role;
    private String ipAddress;

    private void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public JWToken(String username, Long userId, UserRole role) {
        this.username = username;
        this.userId = userId.toString();
        this.role = role;
    }

    //encodes the JWToken
    public String encode(String issuer, String passphrase, int expiration) {
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

    //generate a key to sign the  JWToken with.
    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    //decode the JWToken.
    public static JWToken decode(String token, String issuer, String passphrase)
            throws ExpiredJwtException, MalformedJwtException {
        //validate the tokens and extract the claims
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
        Claims claims = jws.getBody();
        //check the issuer claim
        if (!claims.getIssuer().equals(issuer)) {
            throw new MalformedJwtException("Invalid Issuer");
        }
        String roleString = claims.get(JWT_ROLE_CLAIM).toString();
        UserRole role = UserRole.valueOf(roleString);
        //build a token from the extracted claims
        JWToken jwToken = new JWToken(
                claims.getSubject(),
                Long.valueOf(claims.getId()),
                role
        );
        jwToken.setIpAddress(claims.get(JWT_IPADDRESS_CLAIM).toString());
        return jwToken;
    }



}
