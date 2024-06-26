package com.example.server.HttpHandlers;


import com.example.server.controllers.FollowController;
import com.example.server.utils.JWTController;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class FollowHandler {
    private final FollowController followController;

    public FollowHandler() throws SQLException {
        followController = new FollowController();
    }

    public Object handleGetFollows(Request request, Response response) {
        try {
            return followController.getFollows();
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }


    public Object handleGetFollowers(Request request, Response response) {
        try {
            return followController.getFollowers(request.params(":username"));
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleGetFollowing(Request request, Response response) {
        try {
            return followController.getFollowings(request.params(":username"));
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handlePostFollow(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

        // todo: check jwt

//        if (!JWTController.validateJwtToken(token)) {
//            response.status(401);
//            return "Unauthorized";
//        }

        String followerId = JWTController.getSubjectFromJwt(token);
        String followedId = request.params(":username");
        try {
            followController.saveFollow(followerId, followedId);
            response.status(201);
            return "Follow created successfully!";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handlePostUnfollow(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

//        if (!JWTController.validateJwtToken(token)) {
//            response.status(401);
//            return "Unauthorized";
//        }

        String followerId = JWTController.getSubjectFromJwt(token);
        String followedId = request.params(":username");
        try {
            followController.deleteFollow(followerId, followedId);
            response.status(201);
            return "Follow created successfully!";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }
}
