package taskmanager.junit5;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.apache.commons.io.output.WriterOutputStream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.io.*;

public class RestAssuredDiskLogRule implements TestWatcher {

    private PrintStream printStream;
    private LogConfig originalLogConfig;
    private String logFolder ="/tmp/rest-assured-logs";

    public RestAssuredDiskLogRule() {
        File dir = new File(logFolder);
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
        if (logFolder.endsWith("/")) {
            this.logFolder = logFolder.substring(0, logFolder.length() - 1);
        } else {
            this.logFolder = logFolder;
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        originalLogConfig = RestAssured.config().getLogConfig();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(logFolder + "/" + context.getDisplayName() + ".log");
            printStream = new PrintStream(new WriterOutputStream(fileWriter), true);
            RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream).enablePrettyPrinting(false));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {

        }
    }



}