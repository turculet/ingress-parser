package com.turculet.parser.webtalk.page;

import com.turculet.parser.api.Result;

import java.time.LocalDateTime;
import java.util.List;

public class WebTalkPageParserResult extends Result {

    public Topic topic;

    public WebTalkPageParserResult() {
        super.success = false;
    }

    public WebTalkPageParserResult(Topic topic) {
        this.topic = topic;
        super.success = true;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public record Topic(
            String rawTopicLink,
            List<String> rawTopicPageLinks,
            String forum,
            String title,
            LocalDateTime topicDate,
            List<Post> replies
    ) {
    }

    public record Post(
            String author,
            LocalDateTime postDate,
            String post,
            Quote quote,
            Integer rating,
            Integer page,
            Integer id,
            boolean isTopicPost
            ) {
    }

    public record Quote(
            String authorName,
            String post
    ) {
    }


}

