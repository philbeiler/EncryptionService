package com.beilers.encryption.diffiehellman;

import javax.crypto.spec.DHParameterSpec;

public interface ParameterInterface {

    DHParameterSpec generate();

}
