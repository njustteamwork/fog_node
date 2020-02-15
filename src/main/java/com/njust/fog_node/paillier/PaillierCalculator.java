package com.njust.fog_node.paillier;

import java.math.BigInteger;

public class PaillierCalculator {
    private  BigInteger nSquare;

    public  PaillierCalculator(PaillierPublicKey publicKey){
        nSquare = publicKey.getNSquare();
    }

    public BigInteger add(BigInteger em1, BigInteger em2){
        return em1.multiply(em2).mod(nSquare);
    }

    public BigInteger multiply(BigInteger em1, BigInteger m2){
        return em1.modPow(m2,nSquare);
    }
}
