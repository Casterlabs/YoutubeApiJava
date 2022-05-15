package co.casterlabs.youtubeapijava.types.livechat;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.youtubeapijava.types.livechat.snippets.YoutubeLiveChatSnippet;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveChatEvent {
    private String id;
    private YoutubeLiveChatAuthor authorDetails;
    private @JsonExclude YoutubeLiveChatSnippet snippet;

    @JsonDeserializationMethod("snippet")
    private void $deserialize_snippet(JsonElement snippetElement) {

    }

}
