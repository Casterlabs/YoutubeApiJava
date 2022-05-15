package co.casterlabs.youtubeapijava;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.NonNull;

public class YoutubeApi {

    public static final Rson RSON = new Rson.Builder()
        .registerTypeResolver(new TypeResolver<Instant>() {
            @Override
            public @Nullable Instant resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
                if (value.isJsonString()) {
                    return Instant.parse(value.getAsString());
                } else {
                    return null;
                }
            }

            @Override
            public @Nullable JsonElement writeOut(@NonNull Instant value, @NonNull Class<?> type) {
                return new JsonString(value.toString());
            }
        }, Instant.class)
        .build();

}
