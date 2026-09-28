package com.baitap.jwt_nimbus_demo.service;

import com.baitap.jwt_nimbus_demo.entity.User;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long expiration;

    // 1. Sinh JWT chứa đầy đủ thông tin từ đối tượng User
    public String generateToken(User user) throws Exception {

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .claim("uid", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + expiration))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claims
        );

        MACSigner signer = new MACSigner(secretKey.getBytes());

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    // 2. Lấy username từ JWT
    public String extractUsername(String token)
            throws ParseException {

        SignedJWT jwt = SignedJWT.parse(token);

        return jwt.getJWTClaimsSet().getSubject();
    }

    // 3. Lấy custom claim (uid, email, role,...) từ JWT
    public Object extractClaim(String token, String claimName)
            throws ParseException {

        SignedJWT jwt = SignedJWT.parse(token);

        return jwt.getJWTClaimsSet().getClaim(claimName);
    }

    // 4. Kiểm tra token hết hạn chưa
    public boolean isTokenExpired(String token)
            throws ParseException {

        SignedJWT jwt = SignedJWT.parse(token);

        Date expirationDate = jwt.getJWTClaimsSet().getExpirationTime();

        return expirationDate.before(new Date());
    }

    // 5. Kiểm tra chữ ký JWT
    public boolean verifyToken(String token)
            throws Exception {

        SignedJWT jwt = SignedJWT.parse(token);

        MACVerifier verifier = new MACVerifier(secretKey.getBytes());

        return jwt.verify(verifier);
    }

    // 6. Kiểm tra token hợp lệ
    public boolean isValidToken(String token)
            throws Exception {

        return verifyToken(token)
                && !isTokenExpired(token);
    }

    // 7. Tách token thuần từ Authorization Header (Bearer <token>)
    public String extractTokenFromHeader(String header) {

        if (header == null) {
            return null;
        }

        if (!header.startsWith("Bearer ")) {
            return null;
        }

        return header.substring(7);
    }

}