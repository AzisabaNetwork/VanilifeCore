package net.azisaba.vanilife.core.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
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

  public static void sendDiscordWebhook(@Nullable String url, @Nullable Player player, @Nullable String content) throws IOException {
    if (url == null) return;
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
      if (content != null && !content.isEmpty()) {
        JsonObject contentField = new JsonObject();
        contentField.addProperty("name", "内容");
        contentField.addProperty("value", content);
        fields.add(contentField);
      }
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

  public static String encodeBase64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  public static byte[] decodeBase64(String encodedString) {
    return Base64.getDecoder().decode(encodedString);
  }

  public static void serialize(FileConfiguration config, String prefix, Inventory from) {
    for (int i = 0; i < from.getSize(); i++) {
      String encoded = null;
      ItemStack item = from.getItem(i);
      if (item != null) {
        encoded = encodeBase64(item.serializeAsBytes());
      }
      config.set(prefix + ".inventory.slot" + i, encoded);
    }
  }

  public static void deserialize(FileConfiguration config, String prefix, Inventory to) {
    for (int i = 0; i < to.getSize(); i++) {
      String encoded = config.getString(prefix + ".inventory.slot" + i);
      if (encoded == null) {
        to.setItem(i, null);
      } else {
        to.setItem(i, ItemStack.deserializeBytes(decodeBase64(encoded)));
      }
    }
  }

  @NotNull
  public static <K, V> Map.Entry<K, V> entry(K k, V v) {
    return new AbstractMap.SimpleImmutableEntry<>(k, v);
  }

  @SafeVarargs
  public static <K, V> @NotNull Map<K, V> mapOf(Map.Entry<K, V>... entries) {
    Map<K, V> map = new HashMap<>();
    for (Map.Entry<K, V> entry : entries) {
      map.put(entry.getKey(), entry.getValue());
    }
    return map;
  }

  @NotNull
  public static String replaceByMap(@NotNull String s, @NotNull Map<String, ?> map) {
    AtomicReference<String> string = new AtomicReference<>(s);
    map.forEach((k, v) -> {
      string.set(string.get().replace(k, String.valueOf(v)));
    });
    return string.get();
  }
}
