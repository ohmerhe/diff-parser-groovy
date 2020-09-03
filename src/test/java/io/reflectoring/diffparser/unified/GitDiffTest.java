/*
 * Copyright (c) Crosskey Banking Solutions. All rights reserved.
 */
package io.reflectoring.diffparser.unified;

import io.reflectoring.diffparser.api.DiffParser;
import io.reflectoring.diffparser.api.UnifiedDiffParser;
import io.reflectoring.diffparser.api.model.Diff;
import io.reflectoring.diffparser.api.model.Hunk;
import io.reflectoring.diffparser.api.model.Line;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class GitDiffTest {

    @Test
    public void testParse() {
        // given
        DiffParser parser = new UnifiedDiffParser();
//        InputStream in = getClass().getResourceAsStream("git.diff");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        File file = new File("git.diff");

        // when
        List<Diff> diffs = null;
        try {
            diffs = parser.parse(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // then
        assertNotNull(diffs);
        assertEquals(3, diffs.size());

        Diff diff1 = diffs.get(0);
        assertEquals("a/diffparser/pom.xml", diff1.getFromFileName());
        assertEquals("b/diffparser/pom.xml", diff1.getToFileName());
        assertEquals(2, diff1.getHunks().size());

        List<String> headerLines = diff1.getHeaderLines();
        assertEquals(2, headerLines.size());

        Hunk hunk1 = diff1.getHunks().get(0);
        assertEquals(6, hunk1.getFromFileRange().getLineStart());
        assertEquals(7, hunk1.getFromFileRange().getLineCount());
        assertEquals(6, hunk1.getToFileRange().getLineStart());
        assertEquals(7, hunk1.getToFileRange().getLineCount());

        List<Line> lines = hunk1.getLines();
        assertEquals(8, lines.size());
        assertEquals(Line.LineType.FROM, lines.get(3).getLineType());
        assertEquals(Line.LineType.TO, lines.get(4).getLineType());
    }
}
