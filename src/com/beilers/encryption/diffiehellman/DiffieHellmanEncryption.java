package com.beilers.encryption.diffiehellman;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beilers.encryption.diffiehellman.helper.ByteHelper;
import com.beilers.encryption.diffiehellman.helper.KeyPairHelper;
import com.beilers.encryption.exception.EncryptionException;

public class DiffieHellmanEncryption {

    private static final Logger LOGGER        = LoggerFactory.getLogger(KeyPairHelper.class);

    private final PrivateKey    privateKey;
    private final PublicKey     publicKey;
    private final KeyPairHelper keyPairHelper = new KeyPairHelper();

    public DiffieHellmanEncryption(final File privateKey, final File publicKey) {
        this.privateKey = keyPairHelper.loadPrivateKeyFile(privateKey);
        this.publicKey = keyPairHelper.loadPublicKeyFile(publicKey);
    }

    public DiffieHellmanEncryption(final File privateKey, final String publicKey) {
        this.privateKey = keyPairHelper.loadPrivateKeyFile(privateKey);
        this.publicKey = keyPairHelper.loadPublicKey(publicKey);
    }

    public DiffieHellmanEncryption(final String privateKey, final String publicKey) {
        this.privateKey = keyPairHelper.loadPrivateKey(privateKey);
        this.publicKey = keyPairHelper.loadPublicKey(publicKey);
    }

    public String getSharedSecret() {
        try {
            final KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(publicKey, true);
            return new ByteHelper().toHex(keyAgreement.generateSecret());
        }
        catch (final Exception e) {
            LOGGER.error("Unable to generate secret key", e);
            throw new EncryptionException("Unable to generate secret key");
        }
    }

    public String encrypt(final String value) {
        try {
            final KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(publicKey, true);
            final Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyAgreement.generateSecret("DES"));

            return new ByteHelper().toHex(cipher.doFinal(value.getBytes()));
        }
        catch (final Exception e) {
            LOGGER.error("Unable to encrypt value", e);
            throw new EncryptionException("Unable to encrypt value", e);
        }
    }

    public String decrypt(final String value) {
        try {
            final KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(publicKey, true);
            final Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keyAgreement.generateSecret("DES"));

            return new String(cipher.doFinal(new ByteHelper().fromHex(value)));
        }
        catch (final Exception e) {
            LOGGER.error("Unable to decrypt value", e);
            throw new EncryptionException("Unable to decrypt value", e);
        }
    }

}
