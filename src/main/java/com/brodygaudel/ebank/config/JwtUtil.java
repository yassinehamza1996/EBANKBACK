package com.brodygaudel.ebank.config;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY ="cUZLCi1ywToSRZb5SnR6UuNSZd3hrRricaCiW+IQqQnmgTV2rW/UVh+zEDPEFIEWue1y6SWDGFlhCh7DoCnd/5yL8EzAHuHtbW9ZkHkyuUTwdlqOOOzenkzitDtEYIjF8OUQW/WiHX3zcnK5naoda+Inyu1Pvz3RUWq/OfJyy8IvSwvzSibV5cNAV/l0rU61663yPk6u10APX/D0XmP+FfnBYcmzx2kVA1lrQSlXVxDYFJnFzqoRmyOE86nO/dqlqXu0Zxb5C+9Ctp83VVFpX1YF8v+n68lyCzjXYtSn/edO1wB/5GYu+GMAxA/CnV5YWv0HrofBB//nWPrq2fhE+KDGIbq++mxAOivXs+lpX5g=";

    private static final long EXPIRATION_TIME=86400000;
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();
    }
    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}