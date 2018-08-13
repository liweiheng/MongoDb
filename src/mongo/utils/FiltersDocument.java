package mongo.utils;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiltersDocument {
    private Document document = null;
    private List<Document> orList = null;

    public FiltersDocument() {
        document = new Document();
    }

    public FiltersDocument and(String name, Object value) {
        document.append(name, value);
        return this;
    }

    /**
     * 有操作符的
     *
     * @param name
     * @param operator =,>,>=,<,<=,!=
     * @param value
     * @return
     */
    public FiltersDocument and(String name, String operator, Object value) {
        switch (operator) {
            case "=":
                document.append(name, value);
                break;
            case ">":
                document.append(name, new Document("$gt", value));
                break;
            case ">=":
                document.append(name, new Document("$gte", value));
                break;
            case "<":
                document.append(name, new Document("$lt", value));
                break;
            case "<=":
                document.append(name, new Document("$lte", value));
                break;
            case "!=":
                document.append(name, new Document("$ne", value));
                break;
        }
        return this;
    }

    public FiltersDocument or(String name, String operator, Object value) {
        //  Bson bson = new Document().append("$or",Arrays.asList(new Document("name","段誉"),new Document("age",new Document("$gt",21))));
        if (orList == null)
            orList = new ArrayList<>();
        switch (operator) {
            case "=":
                orList.add(new Document(name, value));
                break;
            case ">":
                orList.add(new Document(name, new Document("$gt", value)));
                break;
            case ">=":
                orList.add(new Document(name, new Document("$gte", value)));
                break;
            case "<":
                orList.add(new Document(name, new Document("$lt", value)));
                break;
            case "<=":
                orList.add(new Document(name, new Document("$lte", value)));
                break;
            case "!=":
                orList.add(new Document(name, new Document("$ne", value)));
                 break;
        }
        document.append("$or", orList);
        return this;
    }

    public FiltersDocument or(String name, Object value) {
        document.append("$or", Arrays.asList(new Document(name, value)));
        return this;
    }

    public Document get() {
        return document;
    }
}
