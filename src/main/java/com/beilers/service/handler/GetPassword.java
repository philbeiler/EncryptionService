package com.beilers.service.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

import com.beilers.encryption.diffiehellman.DiffieHellmanEncryption;
import com.beilers.service.EncryptedDataService;
import com.beilers.service.KeyMakerService;

public class GetPassword implements HttpRequestHandler {

    private static final Logger  LOGGER = LoggerFactory.getLogger(GetPassword.class);
    private KeyMakerService      keyMakerService;
    private EncryptedDataService passwordService;

    public void setPasswordService(final EncryptedDataService passwordService) {
        this.passwordService = passwordService;
    }

    public void setKeyMakerService(final KeyMakerService keyMakerService) {
        this.keyMakerService = keyMakerService;
    }

    @Override
    public void handleRequest(final HttpServletRequest request, //
                              final HttpServletResponse response) throws IOException {
        LOGGER.info("handle-password");
        final Map<String, String[]> parameterMap = request.getParameterMap();
        final String userId = parameterMap.get("userid")[0];
        final String encryptedKey = parameterMap.get("key")[0];

        // KeyPairHelper keyPairHelper = new KeyPairHelper();
        final DiffieHellmanEncryption encrypter = new DiffieHellmanEncryption(keyMakerService.getPrivateKey(), //
                keyMakerService.find(userId));

        final String key = encrypter.decrypt(encryptedKey);
        LOGGER.info("Processing request [{}] for user id [{}]", key, userId);

        final String password = passwordService.find(userId, key);
        response.getWriter().print(password);
        response.setContentType("text/html");
    }
}
