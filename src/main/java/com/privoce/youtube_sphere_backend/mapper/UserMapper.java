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

import java.util.ArrayList;
import java.util.List;

public class UserMapper implements AutoCloseable{
    private final Driver driver;

    public UserMapper( String uri, String user, String password ) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public List<Record> getFriends(final String userId) {
        try (Session session = driver.session()) {
            List<Record> friendsList = session.writeTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match(me:user{userId:'6035513be4692bb589f139e4'})-[:friend{platform:'youtube'}]->(friend) return friend");
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
                    Result result = transaction.run("MATCH (me {userId: '6035513be4692bb589f139e4')-[:friend{platform:'youtube'}]->(friend)-[:reaction]->(video)" +
                                    "RETURN video");
                    return result.list();
                }
            });
            return friendsList;
        }
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }


}
