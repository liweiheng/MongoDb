package mongo.service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import mongo.utils.DocumentJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

 import java.util.List;

public class CollectionsService {
    public static void insertMany(MongoCollection<Document> collection, JSONArray array) {

        insertMany(collection, DocumentJson.jsonArrToDocument(array));
    }

    public static void insertMany(MongoCollection<Document> collection, List<Document> list) {
        collection.insertMany(list);
    }

    public static void insertOne(MongoCollection<Document> collection, JSONObject document) {
        insertOne(collection, DocumentJson.jsonObjToDocument(document));
    }

    public static void insertOne(MongoCollection<Document> collection, Document document) {
        collection.insertOne(document);
    }

    public static JSONObject findById(MongoCollection<Document> collection, String id) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
        Document myDoc = collection.find(Filters.eq("_id", _idobj)).first();
        return DocumentJson.DocumentTojsonObj(myDoc);
    }

    public static int deleteById(MongoCollection<Document> coll, String id) {
        int count = 0;
        ObjectId _id = null;
        try {
            _id = new ObjectId(id);
        } catch (Exception e) {
            return 0;
        }
        Bson filter = Filters.eq("_id", _id);
        DeleteResult deleteResult = coll.deleteOne(filter);
        count = (int) deleteResult.getDeletedCount();
        return count;
    }

    /**
     * 删除文件
     *
     * @param coll
     * @param bson
     * @return
     */
    public static long delete(MongoCollection<Document> coll, Bson bson) {
        return coll.deleteMany(bson).getDeletedCount();
    }

    /**
     * 统计数
     */
    public long getCount(MongoCollection<Document> coll) {
        return coll.countDocuments();
    }



    /**
     * 条件查询
     */
    public static JSONArray find(MongoCollection<Document> collection, Bson filter) {

        return getArr(collection.find(filter));
    }

    /**
     * 条件查询
     */
    public static JSONArray find(MongoCollection<Document> collection, Bson filter, String... params) {

        return getArr(collection.find(filter).projection(getParams(params)));
    }

    /**
     * 分页查询
     */
    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);
        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
    }
    /**
     * 分页查询
     * 在 MongoDB 中使用 sort() 方法对数据进行排序，sort() 方法可以通过参数指定排序的字段，并使用 1 和 -1 来指定排序的方式，其中 1 为升序排列，而 -1 是用于降序排列。
     */


    public static JSONArray findAll(MongoCollection<Document> collection) {
        //检索所有文档
        /**
         * 1. 获取迭代器FindIterable<Document>
         * 2. 获取游标MongoCursor<Document>
         * 3. 通过游标遍历检索出的文档集合
         * */
        return getArr(collection.find());
    }

    private static JSONArray getArr(FindIterable<Document> findIterable) {
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        JSONArray array = new JSONArray();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            array.add(DocumentJson.DocumentTojsonObj(document));
        }
        mongoCursor.close();
        return array;
    }

    /**
     * 更新文档 这个明显比较复杂
     * 1需要定制一些东西解析这些
     * 2相关的条件也是
     * where db.col.find({"likes": {$gt:50}, $or: [{"by": "菜鸟教程"},{"title": "MongoDB 教程"}]}).pretty()
     */
    public static long updateMany(MongoCollection<Document> collection, JSONObject object, Bson where) {
        Bson upBson = new Document("$set", DocumentJson.jsonObjToDocument(object));
//        Bson whereBson = Filters.where(where);
        UpdateResult result = collection.updateMany(where, upBson);
        if (result != null && result.getModifiedCount() > 0) {
            //更新成功
            result.getModifiedCount();
        } else {
            //更新失败
        }
        return 0;
        //db.col.find({"likes": {$gt:50}, $or: [{"by": "菜鸟教程"},{"title": "MongoDB 教程"}]}).pretty()

//        Bson update = new Document("scores", new Document("type", "homework").append("score", homework_score_low));
//        collection.updateOne(updateQuery, new Document("$pull", update));
    }

    public static long updateOne(MongoCollection<Document> collection, JSONObject object, Bson where) {
        Bson upBson = new Document("$set", DocumentJson.jsonObjToDocument(object));
//        Bson whereBson = Filters.where(where);
        UpdateResult result = collection.updateOne(where, upBson);
        if (result != null && result.getModifiedCount() > 0) {
            //更新成功
            result.getModifiedCount();
        }
        return 0;
    }

    private static Document getParams(String... paramsStr) {
        Document document = new Document();
        for (String str : paramsStr)
            document.append(str, 1);
        return document;
    }
}
