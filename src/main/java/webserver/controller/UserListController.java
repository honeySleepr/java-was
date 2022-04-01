package webserver.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import db.UserDatabase;
import model.User;
import util.HttpRequestUtils;
import webserver.http.Request;
import webserver.http.Response;

public class UserListController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void process(Request request, Response response) throws IOException {
        Collection<User> users = UserDatabase.findAll();
        byte[] body = Files.readAllBytes(new File("./webapp" + request.getUrl() + ".html").toPath());
        StringBuilder sb = new StringBuilder();

        int i = 1;
        for (User user : users) {
            sb.append("<tr>")
                .append("<th scope=\"row\">" + i++ + "</th>")
                .append("<td>" + user.getUserId() + "</td>")
                .append("<td>" + user.getName() + "</td>")
                .append("<td>" + user.getEmail() + "</td>")
                .append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>")
                .append("</tr>");
        }
        String format = new String(body);
        String newBody = String.format(format, sb);

        Optional.ofNullable(request.getRequestHeader().get("Cookie"))
            .map(cookie -> HttpRequestUtils.parseCookies(cookie).get("sessionId"))
            .filter(sessionId -> !Strings.isNullOrEmpty(sessionId))
            .ifPresentOrElse(
                value -> {
                    response.response200Header(newBody.length(), "/user/list.html");
                    response.responseBody(newBody.getBytes(StandardCharsets.UTF_8));
                },
                () -> response.response302Header("/index.html")
            );
    }
}
