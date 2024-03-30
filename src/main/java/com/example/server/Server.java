package com.example.server;

import com.example.server.HttpHandlers.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.sql.SQLException;

import static spark.Spark.*;

public class Server {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 3600000; // 1 hour

    public static void main(String[] args) {
        TweetMediaHandler tweetMediaHandler;
        UserMediaHandler userMediaHandler;
        LikeHandler likeHandler;
        LoginHandler loginHandler;
        BlockHandler blockHandler;
        PostHandler postHandler;
        FollowHandler followHandler;
        UserHandler userHandler;
        try {
            tweetMediaHandler = new TweetMediaHandler();
            userMediaHandler = new UserMediaHandler();
            likeHandler = new LikeHandler();
            loginHandler = new LoginHandler();
            blockHandler = new BlockHandler();
            postHandler = new PostHandler();
            followHandler = new FollowHandler();
            userHandler = new UserHandler();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Initialize the Spark server
        port(8080); // Set your desired port number


        get("/api/users", userHandler::handleGetUser);
        get("/api/users/:username", userHandler::handleGetUserById);
        post("/api/users", userHandler::handlePostUser);
        delete("/api/users/:username", userHandler::handleDeleteUserById);
        delete("/api/users", userHandler::handleDeleteUser);
        put("/api/users/:username", userHandler::handlePutUser);


        get("/api/bios", userHandler::handleGetBio);
        get("/api/users/:username/bio", userHandler::handleGetBioById);
        post("/api/users/:username/bio", userHandler::handlePostBio);
        put("/api/users/:username/bio", userHandler::handlePutBio);
        delete("/api/users/:username/bio", userHandler::handleDeleteBioById);
        delete("/api/bios", userHandler::handleDeleteBio);


        post("/api/users/:username/follow", followHandler::handlePostFollow);
        post("/api/users/:username/unfollow", followHandler::handlePostUnfollow);
        get("/api/users/:username/followers", followHandler::handleGetFollowers);
        get("/api/users/:username/following", followHandler::handleGetFollowing);
        get("/api/follows", followHandler::handleGetFollows);


        post("/api/users/:username/block", blockHandler::handlePostBlock);
        post("/api/users/:username/unblock", blockHandler::handlePostUnblock);
        get("/api/blocks", blockHandler::handleGetBlocks);
        get("/api/users/:username/blockers", blockHandler::handleGetBlockers);
        get("/api/users/:username/blocking", blockHandler::handleGetBlocking);


        post("/api/tweets", postHandler::handlePostTweet);
        get("/api/tweets", postHandler::handleGetTweets);
        get("/api/tweets/:tweetId", postHandler::handleGetTweetById);
        delete("/api/tweets/:tweetId", postHandler::handleDeleteTweetByTweetId);
        delete("/api/tweets", postHandler::handleDeleteTweet);
        get("/api/users/:username/tweets", postHandler::handleGetTweetsByOwnerId);

        get("/api/timeline", postHandler::handleGetTimeline);

        post("/api/tweets/:tweetId/retweet", postHandler::handlePostRetweet);
        post("/api/tweets/:tweetId/quote", postHandler::handlePostQuoteTweet);
        post("/api/tweets/:tweetId/reply", postHandler::handlePostReplyTweet);
        get("/api/tweets/:tweetId/replies", postHandler::handleGetReplies);
        get("/api/tweets/:tweetId/retweets", postHandler::handleGetRetweets);
        get("/api/tweets/:tweetId/quotes", postHandler::handleGetQuotes);

        post("/api/login", loginHandler::handlePostLogin);

        get("/api/likes", likeHandler::handleGetLike);
        get("/api/users/:username/likes", likeHandler::handleGetLikeByUserId);
        get("/api/tweets/:tweetId/likes", likeHandler::handleGetLikeByTweetId);
        post("/api/tweets/:tweetId/like", likeHandler::handlePostLike);
        post("/api/tweets/:tweetId/unlike", likeHandler::handlePostUnlike);

        get("/api/hashtags/:hashtag", (request, response) -> {
            return "GET /api/hashtags/:hashtag";
        });

        get("/api/search/users", (request, response) -> {
            return "GET /api/search/users";
        });

        get("/api/search/tweets", (request, response) -> {
            return "GET /api/search/tweets";
        });

        get("/api/direct-messages", (request, response) -> {
            return "GET /api/direct-messages";
        });


        get("/api/users/:username/profile-image", userMediaHandler::handleGetMedia);
        get("/api/users/:username/header-image", userMediaHandler::handleGetMedia);
        post("/api/users/:username/profile-image", userMediaHandler::handlePostMedia);
        post("/api/users/:username/header-image", userMediaHandler::handlePostMedia);

        get("/api/tweets/:tweetId/tweet-image", tweetMediaHandler::handleGetMedia);
        post("/api/tweets/:tweetId/tweet-image", tweetMediaHandler::handlePostMedia);

        // Add the notFound route to handle unmatched paths
        notFound((request, response) -> {
            response.status(404);
            return "Not found";
        });
    }
}
