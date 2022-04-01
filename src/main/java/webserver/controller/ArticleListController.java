package webserver.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.ArticleDatabase;
import model.Article;
import webserver.http.Request;
import webserver.http.Response;

public class ArticleListController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ArticleListController.class);

    @Override
    public void process(Request request, Response response) throws IOException {
        Collection<Article> articles = ArticleDatabase.findAll();
        byte[] body = Files.readAllBytes(new File("./webapp" + request.getUrl() + ".html").toPath());
        StringBuilder sb = new StringBuilder();

        int i = 1;
        for (Article article : articles) {
            sb.append("<li>")
                .append("<div class=\"wrap\">")
                .append("<div class=\"main\">")
                .append("<strong class=\"subject\">")
                .append("<a href=\"./qna/show.html\">" + article.getTitle() + "</a>")
                .append("</strong>")
                .append("<div class=\"auth-info\">")
                .append("<i class=\"icon-add-comment\"></i>")
                .append("<span class=\"time\">" + article.getLocalDate() + "</span>")
                .append("<a class=\"author\" href=\"./user/profile.html\">" + article.getUserId() + "</a>")
                .append("</div>")
                .append("<div class=\"reply\" title=\"#\">")
                .append("<i class=\"icon-reply\"></i>")
                .append("<span class=\"point\">" + i++ + "</span>")
                .append("</div>")
                .append("</div>")
                .append("</div>")
                .append("</li>");
        }
        String format = new String(body);
        String newBody = String.format(format, sb);

        response.response200Header(newBody.length(), "/index.html");
        response.responseBody(newBody.getBytes(StandardCharsets.UTF_8));
    }
}
