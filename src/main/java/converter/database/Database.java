package converter.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;

public class Database {
    private final String HOST = "mongodb+srv://admin:admin@cluster0-zoo5t.mongodb.net/?retryWrites=true";
    private final String DATABASE = "currency";
    private final String COLLECTION = "data";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public Database() {
        mongoClient = MongoClients.create(HOST);
        database = mongoClient.getDatabase(DATABASE);
        collection = database.getCollection(COLLECTION);
    }

    // put all json files in Downloader.SAVE_DIRECTORY into a specified collection
    public void addToCollection(String date) throws IOException {
        collection.insertOne(Ulti.makeDocument(date));
    }

    public JsonObject getCurrency(String fileName) {
        String currencyJsonString = database.getCollection(getLatestCollection()).find(eq("file", fileName)).first().toJson();
        JsonParser parser = new JsonParser();
        JsonObject currencyJsonObject = parser.parse(currencyJsonString).getAsJsonObject();
        return currencyJsonObject;
    }

    public String getLatestCollection() {
        return database.listCollectionNames().first();
    }
}
