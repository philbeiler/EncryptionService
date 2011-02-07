package com.beilers.encryption.diffiehellman.parameters;

import java.math.BigInteger;

import javax.crypto.spec.DHParameterSpec;

public class ParameterTransformer {

    public String convert(final DHParameterSpec spec) {
        return "" + spec.getP() + "," + spec.getG() + "," + spec.getL();

    }

    public DHParameterSpec generate(final String parameters) {
        final String[] values = parameters.split(",");
        final BigInteger p = new BigInteger(values[0]);
        final BigInteger g = new BigInteger(values[1]);
        final int l = Integer.parseInt(values[2]);

        return new DHParameterSpec(p, g, l);
    }
}
