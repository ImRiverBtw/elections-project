package com.election.electionbackend.security;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import com.election.electionbackend.APIConfig;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWToken {
    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS_CLAIM = "ipa";
    private static final String JWT_ISSUER_CLAIM = "iss";
    public static final String JWT_ATTRIBUTE_NAME = "JWTokenInfo";

    private String callName = null;
    private Long accountId = null;
    private String role = null;
    private String ipAddress;



    public JWToken(String callName, Long accountId, String role) {
        this.callName = callName;
        this.accountId = accountId;
        this.role = role;
    }

    public String encode(String issuer, String passphrase, int expiration) {
        Key key = getKey(passphrase);

        return Jwts.builder()
                .subject(this.callName)
                .id(this.accountId.toString())
                .claim(JWT_ROLE_CLAIM, this.role)
                .claim(JWT_IPADDRESS_CLAIM, this.ipAddress != null ? this.ipAddress : "1.1.1.1")
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    public static JWToken decode(String token, String issuer, String passphrase)
            throws ExpiredJwtException, MalformedJwtException {
        // Validate the token string and extract the claims
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parser().
                setSigningKey(key).build()
                .parseClaimsJws(token);
        Claims claims = jws.getBody();

        if (!claims.get(JWT_ISSUER_CLAIM).toString().equals(issuer)) {
            throw new MalformedJwtException("Invalid issuer");
        }
        // build our token from the extracted claims
        JWToken jwToken = new JWToken(
                claims.getSubject(),
                Long.valueOf(claims.getId()),
                claims.get(JWT_ROLE_CLAIM).toString()
        );
        jwToken.setIpAddress((String) claims.get(JWT_IPADDRESS_CLAIM));
        return jwToken;
    }

    public JWToken(String callName, Long accountId, String role, String sourceIpAddress) {
        this(callName, accountId, role);
        this.setIpAddress(sourceIpAddress);
    }

    public long validateImpersonation(long targetAccountId) {
        // checks whether the current account is authorised to access or impersonate the targetAccountId
        if (targetAccountId == 0)
            return this.getAccountId();
        else if (targetAccountId == this.getAccountId() || this.isAdmin())
            return targetAccountId;
        else
            return -1L;
    }





    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role.toLowerCase().contains("admin");
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public static String getIpAddress(HttpServletRequest request) {
        // obtain the source IP-address of the current request
        String ipAddress = null;
        if (APIConfig.IP_FORWARDED_FOR != null) {
            ipAddress = request.getHeader(APIConfig.IP_FORWARDED_FOR);
        }
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

}
