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
    /**
     * format document to be upload to database
     *
     * @param date the date of upload
     * @return formatted document
     * @throws IOException if file is not found
     */
    public static Document makeDocument(String date) throws IOException {
        Document document = new Document("upload", date).append("files", makeFilesDocument());
        return document;
    }

    /**
     * format file field in document
     *
     * @return formatted document
     * @throws IOException if file not found
     */
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

    /**
     * read the content of file and parse it as Document
     *
     * @param file the path file
     * @return parsed document
     * @throws IOException if file not found
     */
    private static Document readFile(Path file) throws IOException {
        Document document;
        String contents = new String(Files.readAllBytes(file));

        document = Document.parse(contents);

        return document;
    }
}
