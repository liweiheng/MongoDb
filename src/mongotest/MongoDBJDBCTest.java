package mongotest;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongo.service.CollectionsService;
import mongo.service.MongoJDBCClient;
import mongo.utils.FiltersDocument;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MongoDBJDBCTest {
    public static void main(String args[]) {
        MongoCollection<Document> collection = MongoJDBCClient.getClient().getMongoDb("testdb").getCollection("testcollection");
        JSONObject object = new JSONObject();
        object.put("name", "段誉");
        object.put("_id", "6666663");
        object.put("age", 20);
        object.put("address", "大理。。。。");
        object.put("mobilenum", "1560601233");
//        CollectionsService.insertOne(collection, object);
//        System.out.println(CollectionsService.findAll(collection));
        //>db.col.find({"likes": {$gt:50}, $or: [{"by": "菜鸟教程"},{"title": "MongoDB 教程"}]}).pretty()
        String where = "{'age':{$gt:12}}";
//        Bson bson = new Document().append("$or",Arrays.asList(new Document("name","段誉"),new Document("age",new Document("$gt",21))));
//        System.out.println(bson);
 //        System.out.println(CollectionsService.delete(collection, bson));
//        System.out.println(Filters.where(where));
        FiltersDocument filtersDocument=new FiltersDocument();
        filtersDocument//.and("name","段誉").and("age","<",21)
                .or("age",">",21).or("mobilenum","=","1560601233").and("name","段誉");
        Bson bson=filtersDocument.get();
        System.out.println(((Document) bson).toJson());
        System.out.println(CollectionsService.find(collection, bson,"name","age"));

    }

//    /**
//     * 需要密码方式连接
//     */
//    public void connection() {
//        try {
//            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
//            //ServerAddress()两个参数分别为 服务器地址 和 端口
//            ServerAddress serverAddress = new ServerAddress("localhost", 27017);
//            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
//            addrs.add(serverAddress);
//
//            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
//            MongoCredential credential = MongoCredential.createScramSha1Credential("username", "databaseName", "password".toCharArray());
//            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
//            credentials.add(credential);
//
//            //通过连接认证获取MongoDB连接
//            MongoClient mongoClient = new MongoClient(addrs, credentials);
//
//            //连接到数据库
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("databaseName");
//            System.out.println("Connect to database successfully");
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//        }
//    }
}
