package net.azisaba.vanilife.core.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    private static final Gson GSON = new Gson();

    @NotNull
    public static <T> List<T> merge(List<T> list, List<T> another) {
        List<T> newList = new ArrayList<>();
        newList.addAll(list);
        newList.addAll(another);
        return newList;
    }

    @NotNull
    public static <T> Stream<T> merge(Stream<T> list, Stream<T> another) {
        return merge(list.collect(Collectors.toList()), another.collect(Collectors.toList())).stream();
    }

    public static void sendDiscordWebhook(@NotNull String url, @Nullable Player player, @NotNull Supplier<@Nullable String> contentSupplier) throws IOException {
        String content = contentSupplier.get();
        if (content == null || content.isEmpty()) return;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        try {
            connection.addRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "VanilifeCore/1.0.0");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStream stream = connection.getOutputStream();
            JsonObject json = new JsonObject();
            JsonArray fields = new JsonArray();
            if (player != null) {
                json.addProperty("username", player.getName());

                JsonObject field = new JsonObject();
                field.addProperty("name", "プレイヤー名");
                field.addProperty("value", player.getName());
                fields.add(field);

                field = new JsonObject();
                field.addProperty("name", "UUID");
                field.addProperty("value", player.getUniqueId().toString());
                fields.add(field);

                field = new JsonObject();
                field.addProperty("name", "座標");
                Location loc = player.getLocation();
                field.addProperty("value", loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
                fields.add(field);
            }
            JsonObject contentField = new JsonObject();
            contentField.addProperty("name", "内容");
            contentField.addProperty("value", content);
            fields.add(contentField);
            JsonArray embeds = new JsonArray();
            JsonObject embed = new JsonObject();
            embed.add("fields", fields);
            embeds.add(embed);
            json.add("embeds", embeds);
            ///System.out.println(GSON.toJson(json));
            stream.write(GSON.toJson(json).getBytes(StandardCharsets.UTF_8));
            stream.flush();
            stream.close();
            connection.connect();
            //System.out.println(readStringThenClose(connection.getInputStream()));
            connection.getInputStream().close();
        } finally {
            connection.disconnect();
        }
    }

    @NotNull
    public static String readStringThenClose(@NotNull InputStream in) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(in);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            StringBuilder builder = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null) builder.append(s);
            return builder.toString();
        }
    }
}
