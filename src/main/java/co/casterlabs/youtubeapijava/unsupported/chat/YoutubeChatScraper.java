package co.casterlabs.youtubeapijava.unsupported.chat;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.util.Scanner;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.youtubeapijava.YoutubeApi;
import lombok.NonNull;

public class YoutubeChatScraper {
    private static final boolean isRunningOnWindows = System.getProperty("os.name", "").contains("Windows");
    private static File targetLocation = null;

    public static void listen(@NonNull String liveChatId, @NonNull YoutubeScrapeChatListener listener) throws IOException {
        assert targetLocation != null : "You must call setupEnvironment() before calling listen().";

        Process process = execute(true, "node", "wrapper.mjs", liveChatId);

        Thread readThread = new Thread(() -> {
            try (Scanner in = new Scanner(process.getInputStream())) {
                while (in.hasNext()) {
                    JsonObject packet = Rson.DEFAULT.fromJson(in.nextLine(), JsonObject.class);
                    String type = packet.getString("type");

                    switch (type) {
                        case "start": {
                            listener.onStart();
                            break;
                        }

                        case "end": {
                            JsonObject data = packet.getObject("data");
                            String reason = data.getString("reason");

                            listener.onEnd(reason);
                            break;
                        }

                        case "chat": {
                            JsonObject data = packet.getObject("data");
                            YoutubeScrapeChatItem item = YoutubeApi.RSON.fromJson(data, YoutubeScrapeChatItem.class);

                            listener.onChat(item);
                            break;
                        }

                        case "error": {
                            JsonObject data = packet.getObject("data");
                            String error = data.getString("error");

                            listener.onError(error);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        readThread.setName("ScrapeChat Read Thread: " + liveChatId);
        readThread.start();
    }

    public static void setupEnvironment(@NonNull File targetLocation) throws IOException, InterruptedException {
        YoutubeChatScraper.targetLocation = targetLocation;
        targetLocation.mkdirs();

        final File wrapperFile = new File(targetLocation, "wrapper.mjs");
        wrapperFile.delete();

        execute(false, "npm", "i", "youtube-chat").waitFor();

        Files.copy(
            YoutubeChatScraper.class.getClassLoader().getResourceAsStream("wrapper.mjs"),
            wrapperFile.toPath()
        );

    }

    private static Process execute(boolean keepOutput, String... cmd) throws IOException {
        String[] commandLine;

        if (isRunningOnWindows) {
            commandLine = new String[] {
                    "cmd",
                    "/c",
                    String.join(" ", cmd)
            };
        } else {
            commandLine = new String[] {
                    "bash",
                    "-c",
                    String.join(" ", cmd)
            };
        }

        return new ProcessBuilder(commandLine)
            .directory(targetLocation)
            .redirectError(Redirect.INHERIT)
            .redirectInput(Redirect.PIPE)
            .redirectOutput(keepOutput ? Redirect.PIPE : Redirect.DISCARD)
            .start();
    }

}
