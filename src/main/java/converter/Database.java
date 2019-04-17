package converter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import converter.downloader.Downloader;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;

public class Database {
    private final String HOST = "mongodb+srv://admin:admin@cluster0-zoo5t.mongodb.net/?retryWrites=true";
    private final String DATABASE = "currency";

    private MongoClient mongoClient;
    private MongoDatabase database;

    public Database() {
        mongoClient = MongoClients.create(HOST);
        database = mongoClient.getDatabase(DATABASE);
    }

    private Document readFile(File name) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(name));

        String json;
        Document document = new Document();
        while ((json = reader.readLine()) != null) {
            document = Document.parse(json);
        }
        reader.close();
        return document;
    }

    private void addCurrency(String coll, File fileName) throws IOException {
        MongoCollection<Document> collection = database.getCollection(coll);
        Document document = new Document("file", fileName.getName()).append("currency", readFile(fileName));
        collection.insertOne(document);
    }

    // put all json files in Downloader.SAVE_DIRECTORY into a specified collection
    public void addToCollection(String collection) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(Downloader.SAVE_DIRECTORY))) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            addCurrency(collection, file.toFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    public JsonObject getCurrency(String collection, String fileName) {
        String currencyJsonString = database.getCollection(collection).find(eq("file", fileName)).first().toJson();
        JsonParser parser = new JsonParser();
        JsonObject currencyJsonObject = parser.parse(currencyJsonString).getAsJsonObject();
        return currencyJsonObject;
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
