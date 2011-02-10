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

public class GetEncryptedData implements HttpRequestHandler {

    private static final Logger  LOGGER = LoggerFactory.getLogger(GetEncryptedData.class);
    private KeyMakerService      keyMakerService;
    private EncryptedDataService encryptedDataService;

    public void setEncryptedDataService(final EncryptedDataService encryptedDataService) {
        this.encryptedDataService = encryptedDataService;
    }

    public void setKeyMakerService(final KeyMakerService keyMakerService) {
        this.keyMakerService = keyMakerService;
    }

    @Override
    public void handleRequest(final HttpServletRequest request, //
                              final HttpServletResponse response) throws IOException {
        @SuppressWarnings("unchecked")
        final Map<String, String[]> parameterMap = request.getParameterMap();
        final String userId = parameterMap.get("userid")[0];
        final String encryptedKey = parameterMap.get("key")[0];

        LOGGER.info("handle-encrypted-data-request");

        // KeyPairHelper keyPairHelper = new KeyPairHelper();
        final DiffieHellmanEncryption encrypter = new DiffieHellmanEncryption(keyMakerService.getPrivateKey(), //
                keyMakerService.find(userId));

        final String key = encrypter.decrypt(encryptedKey);
        LOGGER.info("Processing request [{}] for user id [{}]", key, userId);

        final String encryptedPayload = encryptedDataService.find(userId, key);
        response.getWriter().print(encryptedPayload);
        response.setContentType("text/html");
    }
}
