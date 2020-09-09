/**
 *    Copyright 2013-2015 Tom Hombergs (tom.hombergs@gmail.com | http://wickedsource.org)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package io.reflectoring.diffparser.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Diff between two files.
 *
 * @author Tom Hombergs <tom.hombergs@gmail.com>
 */
@SuppressWarnings("UnusedDeclaration")
public class Diff {

    private String fromFileName;

    private String toFileName;

    private List<String> headerLines = new ArrayList<>();

    private List<Hunk> hunks = new ArrayList<>();

    /**
     * The header lines of the diff. These lines are purely informational and are not parsed.
     *
     * @return the list of header lines.
     */
    public List<String> getHeaderLines() {
        return headerLines;
    }

    public void setHeaderLines(List<String> headerLines) {
        this.headerLines = headerLines;
    }

    /**
     * Gets the name of the first file that was compared with this Diff (the file "from" which the changes were made,
     * i.e. the "left" file of the diff).
     *
     * @return the name of the "from"-file.
     */
    public String getFromFileName() {
        return fromFileName;
    }

    /**
     * Gets the name of the second file that was compared with this Diff (the file "to" which the changes were made,
     * i.e. the "right" file of the diff).
     *
     * @return the name of the "to"-file.
     */
    public String getToFileName() {
        return toFileName;
    }

    /**
     * The list if all {@link Hunk}s which contain all changes that are part of this Diff.
     *
     * @return list of all Hunks that are part of this Diff.
     */
    public List<Hunk> getHunks() {
        return hunks;
    }

    public void setFromFileName(String fromFileName) {
        this.fromFileName = fromFileName;
    }

    public void setToFileName(String toFileName) {
        this.toFileName = toFileName;
    }

    public void setHunks(List<Hunk> hunks) {
        this.hunks = hunks;
    }

    /**
     * Gets the last {@link Hunk} of changes that is part of this Diff.
     *
     * @return the last {@link Hunk} that has been added to this Diff.
     */
    public Hunk getLatestHunk() {
        return hunks.get(hunks.size() - 1);
    }

    public void getNextLineNumber(Line line){
        getNextLineNumber(getLatestHunk(), getLatestHunk().getLines().size() - 1, line)
    }

    public void getNextLineNumber(Hunk hunk, int linePosition, Line line){
        if (line.lineType == Line.LineType.FROM || line.lineType == Line.LineType.NEUTRAL){
            getNextFromLineNumber(hunk, linePosition, line)
        }
        if (line.lineType == Line.LineType.TO || line.lineType == Line.LineType.NEUTRAL){
            getNextToLineNumber(hunk, linePosition, line)
        }
    }
    public void getNextFromLineNumber(Hunk hunk, int linePosition, Line line){
        if (linePosition < 0){
            line.fromLineNumber = hunk.toFileRange.lineStart
            return
        }
        if (linePosition >= 0){
            Line tempLine = hunk.getLines().get(linePosition)
            if (tempLine.lineType == Line.LineType.NEUTRAL || tempLine.lineType == Line.LineType.FROM){
                line.fromLineNumber = tempLine.fromLineNumber + 1
            }else {
                getNextFromLineNumber(hunk, linePosition - 1, line)
            }
        }
    }

    public void getNextToLineNumber(Hunk hunk, int linePosition, Line line){
        if (linePosition < 0){
            line.toLineNumber = hunk.toFileRange.lineStart
            return
        }
        if (linePosition >= 0){
            Line tempLine = hunk.getLines().get(linePosition)
            if (tempLine.lineType == Line.LineType.NEUTRAL || tempLine.lineType == Line.LineType.TO){
                line.toLineNumber = tempLine.toLineNumber + 1
            }else {
                getNextToLineNumber(hunk, linePosition - 1, line)
            }
        }
    }


    public int getNextDiffPosition(Line line){
        return getNextDiffPosition(hunks.size() - 1, getLatestHunk().getLines().size() - 1, line)
    }

    public int getNextDiffPosition(int hunkPosition, int linePosition, Line line){
        if (hunkPosition <= 0 && linePosition < 0){
            line.diffPosition = 1
            return line.diffPosition
        }
        if (hunkPosition > 0 && linePosition < 0){
            Hunk preHunk = hunks.get(hunkPosition-1)
            return getNextDiffPosition(hunkPosition - 1, preHunk.getLines().size() - 1, line)
        }
        if (linePosition >= 0){
            Hunk hunk = hunks.get(hunkPosition)
            Line tempLine = hunk.getLines().get(linePosition)
            line.diffPosition = tempLine.diffPosition + 1
            return line.diffPosition
        }
    }
}
