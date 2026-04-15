package bsh;

import java.io.InputStream;
import java.io.InputStreamReader;

/** Reduced UTF-8 reader wrapper for CN1 runtime use. */
final public class FileReader extends InputStreamReader {
    public FileReader(String path) {
        this(openUnsupported(path));
    }

    public FileReader(InputStream in) {
        super(in);
    }

    private static InputStream openUnsupported(String path) {
        throw new UnsupportedOperationException("File access is disabled in the reduced CN1 runtime: " + path);
    }
}
