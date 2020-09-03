package io.reflectoring.diffparser.api

/**
 * Interface to a parser that parses a textual diff between two text files. See the javadoc of the implementation you want to use to see
 * what diff format it is expecting as input.
 *
 * @author Tom Hombergs <tom.hombergs@gmail.com>
 */
interface DiffParser {
    /**
     * Constructs a list of Diffs from a textual InputStream.
     *
     * @param in the input stream to parse
     * @return list of Diff objects parsed from the InputStream.
     */
    public abstract java.util.List<io.reflectoring.diffparser.api.model.Diff> parse(java.io.InputStream inputStream);

    /**
     * Constructs a list of Diffs from a textual byte array.
     *
     * @param bytes the byte array to parse
     * @return list of Diff objects parsed from the byte array.
     */
    public abstract java.util.List<io.reflectoring.diffparser.api.model.Diff> parse(java.lang.Byte[] bytes);

    /**
     * Constructs a list of Diffs from a textual File
     *
     * @param file the file to parse
     * @return list of Diff objects parsed from the File.
     */
    public abstract java.util.List<io.reflectoring.diffparser.api.model.Diff> parse(java.io.File file) throws java.io.IOException;
}
