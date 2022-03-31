package webserver.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.Request;
import webserver.http.Response;

public class StaticFileController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(StaticFileController.class);

    @Override
    public void process(Request request, Response response) throws IOException {
        log.debug(request.getUrl());
        byte[] body = Files.readAllBytes(new File("./webapp" + request.getUrl()).toPath());
        response.response200Header(body.length, request.getUrl());
        response.responseBody(body);
    }
}
