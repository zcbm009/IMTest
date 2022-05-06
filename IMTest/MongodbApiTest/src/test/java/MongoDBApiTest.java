import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class MongoDBApiTest {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    @Before
    public void init() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("test");
        collection = database.getCollection("exam");
    }

    /**
     * 查询
     */
    @Test
    public void findList() {
        collection.find().forEach((Consumer<? super Document>) document -> System.out.println(document.toJson()));
    }

    /**
     * 插入单条数据
     */
    @Test
    public void insertData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "ccs");
        data.put("age", 19.0);
        InsertOneResult insertOneResult = collection.insertOne(new Document(data));
        System.out.println(insertOneResult);
    }

    /**
     * 插入金条数据
     */
    @Test
    public void insertBatchData() {
        ArrayList<Document> documents = new ArrayList<>();
        for (int i = 15; i < 30; i++) {
            int finalI = i;
            documents.add(new Document(new HashMap<String, Object>() {{
                put("id", finalI);
                put("name", (char) finalI + "csv");
                put("age", 2 * finalI);
            }}));
        }
        InsertManyResult insertManyResult = collection.insertMany(documents);
        System.out.println(insertManyResult.getInsertedIds());
    }

    /**
     * 条件查询
     */
    @Test
    public void findByCondition() {
        collection.find().filter(Filters.gte("age", 6)).filter(Filters.gte("id", 7))
                .forEach((Consumer<? super Document>) document -> System.out.println(document.toJson()));
    }

    /**
     * 分页查询
     */
    @Test
    public void findLimitData(){
        collection.find().skip(3).limit(3)
                .forEach((Consumer<? super Document>) document -> System.out.println(document.toJson()));
    }

    @After
    public void after() {
        mongoClient.close();
    }

}
