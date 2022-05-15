package co.casterlabs.youtubeapijava.types;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import co.casterlabs.youtubeapijava.YoutubeApi;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveBroadcastSnippet {
    private String id;

    private String title;

    private String channelId;

    private String description;

    private Instant publishedAt;

    private Instant scheduledStartTime;

    private boolean isDefaultBroadcast;

    private String liveChatId;

    @JsonExclude
    private Map<String, YoutubeThumbnail> thumbnails;

    @JsonDeserializationMethod("thumbnails")
    private void $deserialize_thumbnails(JsonElement e) throws JsonValidationException, JsonParseException {
        Map<String, YoutubeThumbnail> thumbs = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : e.getAsObject().entrySet()) {
            thumbs.put(
                entry.getKey(),
                YoutubeApi.RSON.fromJson(entry.getValue(), YoutubeThumbnail.class)
            );
        }

        this.thumbnails = Collections.unmodifiableMap(thumbs);
    }

    public String getVideoUrl() {
        return String.format("https://youtu.be/%s", this.id);
    }

}
