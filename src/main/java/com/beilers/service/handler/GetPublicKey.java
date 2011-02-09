package com.beilers.service.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

import com.beilers.service.KeyMakerService;

public class GetPublicKey implements HttpRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPublicKey.class);

    private KeyMakerService     keyMakerService;

    public void setKeyMakerService(final KeyMakerService keyMakerService) {
        this.keyMakerService = keyMakerService;
    }

    @Override
    public void handleRequest(final HttpServletRequest request, //
                              final HttpServletResponse response) throws IOException {
        LOGGER.info("handle");
        response.getWriter().print(keyMakerService.find("SYSTEM.PUBLIC"));
        response.setContentType("text/html");
    }
}
