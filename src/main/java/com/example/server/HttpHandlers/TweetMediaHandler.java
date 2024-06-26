package com.example.server.HttpHandlers;

import com.example.server.controllers.TweetMediaController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class TweetMediaHandler {
    private final TweetMediaController tweetMediaController;

    public TweetMediaHandler() throws SQLException {
        tweetMediaController = new TweetMediaController();
    }

    public Object handlePostMedia(Request request, Response response) throws IOException, ServletException {
        String tweetId = request.params(":tweetId");
        String mediaGroup = request.pathInfo().split("/")[4];
        String fileType = request.headers("Content-Type");

        // make unique file name
        String mediaUrl = "assets/" + mediaGroup + "/" + tweetId + "/" + tweetId + "_" + new Date().getTime() + "." + fileType.split("/")[1];

        // get file bytes
        byte[] fileByte = request.bodyAsBytes();
        File file = new File(mediaUrl);

        // create file if it doesn't exist
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        file.createNewFile();

        // write file bytes to file
        java.nio.file.Files.write(file.toPath(), fileByte);

        try {
            tweetMediaController.createMedia(tweetId, mediaGroup, mediaUrl);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }
        response.status(201);
        response.body("Media saved successfully");
        return response.body();
    }

    public Object handleGetMedia(Request request, Response response) throws IOException, ServletException, SQLException {
        String tweetId = request.params(":tweetId");
        String mediaGroup = request.pathInfo().split("/")[4];
        String tweetMedia = tweetMediaController.getMediaByTweetIdAndType(tweetId, mediaGroup);
        String mediaUrl = null;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonArray = mapper.readTree(tweetMedia);
        mediaUrl = jsonArray.get(jsonArray.size() - 1).asText();

        File file = new File(mediaUrl);
        if (!file.exists() || !file.isFile()) {
            response.status(400);
            response.body("Media file not found or is not a file.");
            return response.body();
        }

        writeMediaContentToResponse(file, response);

        response.status(200);
        response.body("Media retrieved successfully");
        return response.body();
    }

    public void writeMediaContentToResponse(File file, Response response) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.raw().getOutputStream().write(buffer, 0, bytesRead);
            }
        }
    }
}