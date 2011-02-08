package com.beilers.encryption.diffiehellman.helper;

import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beilers.encryption.exception.EncryptionException;

public class KeyPairHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyPairHelper.class);

    public KeyPairHelper() {
        super();
    }

    public void generateKeyFiles(final KeyPair keyPair, final String userid, final File directory) {
        try {
            FileUtils.writeStringToFile(new File(directory, userid.concat(".public.key")), //
                                        generatePublicKey(keyPair));
            FileUtils.writeStringToFile(new File(directory, userid.concat(".private.key")), //
                                        generatePrivateKey(keyPair));
        }
        catch (final IOException e) {
            LOGGER.error("Unable to create file", e);
            throw new EncryptionException("Unable to create key files");
        }
    }

    public PublicKey loadPublicKeyFile(final File file) {
        try {
            return loadPublicKey(FileUtils.readFileToString(file));
        }
        catch (final IOException e) {
            LOGGER.error("Unable to load public key file", e);
            throw new EncryptionException("Unable to load public key file", e);
        }
    }

    public PrivateKey loadPrivateKeyFile(final File file) {
        try {
            return loadPrivateKey(FileUtils.readFileToString(file));
        }
        catch (final IOException e) {
            LOGGER.error("Unable to load private key file", e);
            throw new EncryptionException("Unable to load private key file", e);
        }
    }

    public PublicKey loadPublicKey(final String publicKey) {

        try {
            final KeyFactory keyFactory = KeyFactory.getInstance("DH");
            final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(new ByteHelper().fromHex(publicKey));
            return keyFactory.generatePublic(x509KeySpec);
        }
        catch (final Exception e) {
            LOGGER.error("Unable to load public key", e);
            throw new EncryptionException("Unable to load public key", e);
        }
    }

    public PrivateKey loadPrivateKey(final String privateKey) {

        try {
            final KeyFactory keyFactory = KeyFactory.getInstance("DH");
            final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(new ByteHelper().fromHex(privateKey));
            return keyFactory.generatePrivate(keySpec);
        }
        catch (final Exception e) {
            LOGGER.error("Unable to load private key", e);
            throw new EncryptionException("Unable to load private key", e);
        }
    }

    public String generatePublicKey(final KeyPair keyPair) {
        final byte[] publicKey = keyPair.getPublic().getEncoded();
        return new ByteHelper().toHex(publicKey);
    }

    public String generatePrivateKey(final KeyPair keyPair) {
        final byte[] privateKey = keyPair.getPrivate().getEncoded();
        return new ByteHelper().toHex(privateKey);
    }

    public KeyPair generate(final DHParameterSpec spec) {
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
            keyPairGenerator.initialize(spec);
            return keyPairGenerator.generateKeyPair();
        }
        catch (final Exception e) {
            LOGGER.error("Unable to generate key pair", e);
            throw new EncryptionException("Unable to generate key pair", e);
        }
    }
}
