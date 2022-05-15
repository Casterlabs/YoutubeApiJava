package co.casterlabs.youtubeapijava.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.youtubeapijava.types.livechat.YoutubeLiveChatEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class YoutubeLiveChatMessagesList {
    private String nextPageToken;

    private long pollingIntervalMillis;

    private List<YoutubeLiveChatEvent> events;

    @JsonDeserializationMethod("items")
    private void $deserialize_items(JsonElement itemsElement) {
        JsonArray items = itemsElement.getAsArray();

        List<YoutubeLiveChatEvent> events = new ArrayList<>(items.size());

        // TODO
        System.out.println(items);

        this.events = Collections.unmodifiableList(events);
    }

    public boolean isEmpty() {
        return this.events.isEmpty();
    }

}
