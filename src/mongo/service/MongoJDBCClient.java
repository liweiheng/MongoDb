package mongo.service;

import com.mongodb.MongoClient;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoJDBCClient {
    public static MongoJDBCClient client;
    private MongoClient mongoClient;

    public synchronized static MongoJDBCClient getClient() {
        if (client == null) {
            synchronized (MongoJDBCClient.class) {
                client = new MongoJDBCClient();
            }
        }
        return client;
    }

    /*
       连接mongo2
     */
    public MongoJDBCClient() {
        mongoClient = new MongoClient("localhost", 27017);
    }

    /**
     * 得到某个数据库
     *
     * @param dbName
     * @return
     */
    public MongoDatabase getMongoDb(String dbName) {
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");
        if (mongoDatabase == null)
            throw new MongoException("无此数据库");
        return mongoDatabase;
    }

    /**
     * 得到某个集合
     *
     * @param mongoDatabase
     * @param collectionName
     * @return
     */
    public MongoCollection<Document> getCollection(MongoDatabase mongoDatabase, String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }

    public MongoCollection<Document> getCollection(String dbName, String collectionName) {
        return getMongoDb(dbName).getCollection(collectionName);
    }
}
