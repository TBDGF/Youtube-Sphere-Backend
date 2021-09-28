package com.privoce.youtube_sphere_backend.mapper;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.Record;

import static org.neo4j.driver.Values.parameters;

import java.util.List;

public class NeoMapper implements AutoCloseable{
    private final Driver driver;

    public NeoMapper(String uri, String user, String password ) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public List<Record> getFriends(final String userId) {
        try (Session session = driver.session()) {
            List<Record> friendsList = session.writeTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match(me:user{userId:$userId})-[:friend{platform:'youtube'}]-(friend) return distinct friend.userId"
                    , parameters( "userId", userId));
                    return result.list();
                }
            });
            return friendsList;
        }
    }

    public List<Record> getFriendsReaction(final String userId) {
        try (Session session = driver.session()) {
            List<Record> friendsList = session.writeTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("MATCH (me:user{userId: $userId})-[:friend{platform:'youtube'}]-(friend)-[l:like]->(video) " +
                            "RETURN friend.userId,video.videoId,l.datetime,l.reactiontime",
                            parameters("userId", userId));
                    return result.list();
                }
            });
            return friendsList;
        }
    }

    public Record createReaction(final String userId,final String videoId,final String datetime,final int reactionTime) {
        try (Session session = driver.session()) {
            Record result = session.writeTransaction(new TransactionWork<Record>() {
                @Override
                public Record execute(Transaction transaction) {
                    Result result = transaction.run( "MATCH (u:user{userId: $uid})\n" +
                                    "MERGE (v:video{videoId: $vid})\n" +
                                    "MERGE (u)-[r:like{datetime: $datetime, reactiontime: $reactionTime}]->(v)\n" +
                                    "RETURN r",
                            parameters( "uid", userId,"vid",videoId,"datetime",datetime,"reactionTime",reactionTime) );
                    return result.single();
                }
            });
            return result;
        }
    }

    public Record createRelation(final String meId,final String friendId,final String platform) {
        try (Session session = driver.session()) {
            Record result = session.writeTransaction(new TransactionWork<Record>() {
                @Override
                public Record execute(Transaction transaction) {
                    Result result = transaction.run( "match (me:user{userId:$meId})\n" +
                                    "merge (friend:user{userId:$friendId})\n" +
                                    "merge (me)-[r:friend{platform:$platform}]-(friend)\n" +
                                    "return r",
                            parameters( "meId", meId,"friendId",friendId,"platform",platform) );
                    return result.single();
                }
            });
            return result;
        }
    }

    public Record createUser(final String userId) {
        try (Session session = driver.session()) {
            Record result = session.writeTransaction(new TransactionWork<Record>() {
                @Override
                public Record execute(Transaction transaction) {
                    Result result = transaction.run( "merge (u:user{userId:$userId}) return u",
                            parameters( "userId", userId) );
                    return result.single();
                }
            });
            return result;
        }
    }

    public Boolean checkVideo(final String videoId) {
        try (Session session = driver.session()) {
            List<Record> resultList = session.writeTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("MATCH (v:video{videoId: $vid}) RETURN v",
                            parameters("vid", videoId));
                    return result.list();
                }
            });
            return resultList.isEmpty();
        }
    }

    public Record createVideo(final String videoId) {
        try (Session session = driver.session()) {
            Record result = session.writeTransaction(new TransactionWork<Record>() {
                @Override
                public Record execute(Transaction transaction) {
                    Result result = transaction.run( "merge (u:video{videoId:$videoId}) return u",
                            parameters( "videoId", videoId) );
                    return result.single();
                }
            });
            return result;
        }
    }


    @Override
    public void close() throws Exception {
        driver.close();
    }
}
