package co.casterlabs.youtubeapijava.types.livechat.snippets;

import lombok.NonNull;

public enum YoutubeLiveChatSnippetType {

    // TODO
    // https://developers.google.com/youtube/v3/live/docs/liveChatMessages#resource

    // @formatter:off
    CHAT_ENDED_EVENT,
    MESSAGE_DELETED_EVENT,
    SPONSOR_ONLY_MODE_ENDED_EVENT,
    SPONSOR_ONLY_MODE_STARTED_EVENT,
    NEW_SPONSOR_EVENT,
    MEMBER_MILESTONE_CHAT_EVENT,
    SUPER_CHAT_EVENT,
    SUPER_STICKER_EVENT,
    TEXT_MESSAGE_EVENT,
    TOMBSTONE,
    USER_BANNED_EVENT,
    MEMBERSHIP_GIFTING_EVENT,
    GIFT_MEMBERSHIP_RECEIVED_EVENT,
    ;
    // @formatter:on

    public static YoutubeLiveChatSnippetType parseType(@NonNull String type) {
        String converted = type
            .replaceAll("([A-Z])", "_$1") // textMessageEvent -> text_Message_Event
            .toUpperCase(); // text_Message_Event -> TEXT_MESSAGE_EVENT

        return YoutubeLiveChatSnippetType.valueOf(converted);
    }

}
