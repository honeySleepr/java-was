package webserver.controller;

import java.io.IOException;
import java.util.Optional;

import com.google.common.base.Strings;

import util.HttpRequestUtils;
import webserver.http.Request;
import webserver.http.Response;

public class ArticleFormController implements Controller {
    @Override
    public void process(Request request, Response response) throws IOException {
        Optional.ofNullable(request.getRequestHeader().get("Cookie"))
            .map(cookie -> HttpRequestUtils.parseCookies(cookie).get("sessionId"))
            .filter(sessionId -> !Strings.isNullOrEmpty(sessionId))
            .ifPresentOrElse(
                value -> response.response302Header("/qna/form.html"),
                () -> response.response302Header("/user/login.html")
            );

    }
}
