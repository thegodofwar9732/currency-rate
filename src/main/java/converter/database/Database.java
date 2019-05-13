package converter.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.io.IOException;
import java.util.Optional;

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
        collection.createIndex(Indexes.descending("upload"), new IndexOptions().unique(true));
    }

    /**
     * insert a formatted Document to database
     *
     * @param uploadDate date of upload
     * @throws IOException if the read permission is denied on json files
     */
    public void addToCollection(String uploadDate) throws IOException {
        collection.insertOne(Ulti.makeDocument(uploadDate));
    }

    /**
     * find the latest currency data in the database and pase it to JsonObject
     *
     * @param currency - name of currency
     * @param num      - position of Document in database
     * @return parsed json object
     */
    public JsonObject getCurrencyData(String currency, int num) {
        String currencyJsonString = collection.find(eq("upload", getUploadDate(num))).first().toJson();
        JsonParser parser = new JsonParser();
        JsonObject currencyJsonObject = parser.parse(currencyJsonString).getAsJsonObject().getAsJsonObject("files").getAsJsonObject(currency);

        return currencyJsonObject;
    }

    /**
     * get the latest upload date
     *
     * @return the latest upload date
     */
    public String getUploadDate() {
        return getUploadDate(0);
    }

    /**
     * get the upload date by num position
     *
     * @param num the position of Document in database
     * @return the upload date or "No Result" if null
     */
    public String getUploadDate(int num) {
        FindIterable<Document> documents = collection.find().sort(new Document("upload", -1));
        String uploadDate = null;
        for (MongoCursor<Document> iterator = documents.iterator(); num >= 0 && iterator.hasNext(); num--) {
            Document temp = iterator.next();
            uploadDate = temp.getString("upload");
        }

        return Optional.ofNullable(uploadDate).orElse("No Result");
    }
    
    /**
     * get the collection
     * to use for testing
     * @return the collection
     */
    public MongoCollection<Document> getTheCollection() {
    	return collection;
    }
}
