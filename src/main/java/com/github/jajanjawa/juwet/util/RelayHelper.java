package com.github.jajanjawa.juwet.util;

import com.github.jajanjawa.juwet.data.Room;
import com.github.jajanjawa.juwet.data.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Perlu setel http klien.
 */
public class RelayHelper {
    public static MediaType JSON = MediaType.parse("application/json");
    private static Gson gson;
    private static Call.Factory callFactory;

    static {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    /**
     * Satu aplikasi kalau bisa gunakan satu http klien.
     * @param factory http klien untuk panggil request
     */
    public static void setDefaultOkHttpCallFactory(Call.Factory factory) {
        RelayHelper.callFactory = factory;
    }

    /**
     * Buat http klien.
     * Lebih baik gunakan {@link #setDefaultOkHttpCallFactory(Call.Factory)}
     */
    public static void initHttpClient() {
        callFactory = new OkHttpClient();
    }

    /**
     * Konversi json jadi objek.
     * Gson disini mempunyai Date format sesuai dari nowdb.
     * @param json data dalam format json
     * @param type tujuan konversi
     * @param <T> class yang sesuai dengan json
     * @return objek dari tipe yang diberikan
     */
    public static <T> T convert(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static List<Room> getRooms(String token, String project) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://io.nowdb.net/relay/room_project").newBuilder();
        urlBuilder.addEncodedPathSegments("token/" + token);
        urlBuilder.addEncodedPathSegments("project/" + project);

        Request request = new Request.Builder().url(urlBuilder.build()).get().build();
        Response response = callFactory.newCall(request).execute();
        String rooms = response.body().string();

        Type type = TypeToken.getParameterized(List.class, Room.class).getType();
        return gson.fromJson(rooms, type);
    }

    public static Room createRoom(String name, String token, String project) throws IOException {
        String url = "http://io.nowdb.net/relay/room_create";
        JsonObject object = new JsonObject();
        object.addProperty("token", token);
        object.addProperty("project", project);
        object.addProperty("room", name);

        String data = object.toString();
        RequestBody body = RequestBody.create(JSON, data);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = callFactory.newCall(request).execute();
        String room = response.body().string();
        return gson.fromJson(room, Room.class);
    }

    public static Status deleteRoom(String roomId) throws IOException {
        String url = "http://io.nowdb.net/relay/room_remove/" + roomId;

        Request request = new Request.Builder().url(url).delete().build();
        Response response = callFactory.newCall(request).execute();
        String status = response.body().string();
        return gson.fromJson(status, Status.class);
    }

}
