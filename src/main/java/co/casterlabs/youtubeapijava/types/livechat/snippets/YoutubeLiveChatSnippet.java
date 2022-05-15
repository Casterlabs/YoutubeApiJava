package co.casterlabs.youtubeapijava.types.livechat.snippets;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public abstract class YoutubeLiveChatSnippet {
    private YoutubeLiveChatSnippetType type;
    private String liveChatId;
    private String authorChannelId;
    private Instant publishedAt;
    private boolean hasDisplayContent;
    private String displayMessage;

}
