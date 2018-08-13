package mongo.utils;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DocumentJson {
    public static Document jsonObjToDocument(String jsonStr) {
        return jsonObjToDocument(JSONObject.fromObject(jsonStr));
    }

    public static Document jsonObjToDocument(JSONObject jsonObject) {
        Iterator iter = jsonObject.keys();
        Document document = new Document();
        while (iter != null && iter.hasNext()) {
            String key = (String) iter.next();
            document.append(key, jsonObject.opt(key));
        }
        return document;
    }


    public static List<Document> jsonArrToDocument(String jsonStr) {
        return jsonArrToDocument(JSONArray.fromObject(jsonStr));
    }

    public static List<Document> jsonArrToDocument(JSONArray jsonArray) {
        List<Document> documents = new ArrayList<>();
        if (jsonArray != null && jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                documents.add(jsonObjToDocument(jsonArray.getJSONObject(i)));
            }
        }
        return documents;
    }


    public static JSONObject DocumentTojsonObj(Document document) {
        JSONObject object = new JSONObject();
        for (HashMap.Entry<String, Object> entry : document.entrySet()) {
            object.put(entry.getKey(), entry.getValue());
        }
        return object;


    }

}
