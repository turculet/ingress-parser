package com.turculet.parser.webtalk.page;

import com.turculet.parser.api.Parser;
import org.jsoup.Jsoup;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

public class WebTalkPageParser implements Parser {

    @Override
    public WebTalkPageParserResult parse(String ihtml) {
        // Parse the HTML string
        var doc = Jsoup.parse(ihtml);

        var pageLink = doc.select("meta[property=og:url]").attr("content");
        var title = doc.select("title").text();
        var forum = doc.select("link[rel=up]").attr("title");

        var pageParam = pageLink.split("p=");
        var page = pageParam.length == 2 ? Integer.parseInt(pageParam[1]) : 1;

        var posts = doc.select("div.post");
        var pageReplies = posts.stream().parallel()
                .map(post -> {
                    var author = post.select("li.pa-author a").text();
                    var timestamp = Long.parseLong(post.attribute("data-posted").getValue());
                    var date = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
                    var postText = post.select("div.post-content>p").text();
                    var rating = post.select("div.post-rating").text();
                    var isTopicPost = post.hasClass("topicpost");
                    var postId = Integer.valueOf(post.attribute("id").getValue().substring(1));

                    return new WebTalkPageParserResult.Post(
                            author,
                            date,
                            postText,
                            new WebTalkPageParserResult.Quote(
                                    post.select("div.quote-box>cite").text().split(" ")[0],
                                    post.select("div.quote-box>blockquote").text()
                            ),
                            Integer.valueOf(rating),
                            page,
                            postId,
                            isTopicPost
                            );
                })
                .collect(Collectors.toList());

        var topic = new WebTalkPageParserResult.Topic(
                pageLink,
                null,
                forum,
                title,
                null,
                pageReplies
        );

        return new WebTalkPageParserResult(topic);
    }


}
