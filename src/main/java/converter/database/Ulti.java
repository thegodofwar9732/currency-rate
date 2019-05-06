package converter.database;

import converter.downloader.Downloader;
import org.apache.commons.io.FilenameUtils;
import org.bson.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Ulti {
    public static Document makeDocument(String date) throws IOException {
        Document document = new Document("upload", date).append("files", makeFilesDocument());
        return document;
    }

    private static Document makeFilesDocument() throws IOException {
        Document document = new Document();

        try (Stream<Path> paths = Files.walk(Paths.get(Downloader.SAVE_DIRECTORY))) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                try {
                    document.append(FilenameUtils.getBaseName(file.toString()), readFile(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return document;
    }

    private static Document readFile(Path file) throws IOException {
        Document document;
        String contents = new String(Files.readAllBytes(file));

        document = Document.parse(contents);

        return document;
    }
}
