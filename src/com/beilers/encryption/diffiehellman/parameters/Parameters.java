package com.beilers.encryption.diffiehellman.parameters;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;

import javax.crypto.spec.DHParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beilers.encryption.diffiehellman.ParameterInterface;
import com.beilers.encryption.exception.EncryptionException;

public class Parameters implements ParameterInterface {

    private static final int    PARAMETER_SIZE = 1024;
    private static final Logger LOGGER         = LoggerFactory.getLogger(Parameters.class);

    @Override
    public DHParameterSpec generate() {

        try {
            LOGGER.info("Creating Diffie-Hellman parameters (takes VERY long) ...");
            final AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
            paramGen.init(PARAMETER_SIZE);
            final AlgorithmParameters params = paramGen.generateParameters();
            return params.getParameterSpec(DHParameterSpec.class);
        }
        catch (final Exception e) {
            LOGGER.error("Problem creating parameters", e);
            throw new EncryptionException("Problem creating parameters", e);
        }
    }

}
