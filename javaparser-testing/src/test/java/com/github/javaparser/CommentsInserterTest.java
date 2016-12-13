/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2016 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */

package com.github.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.CommentsCollection;
import com.github.javaparser.ast.expr.Expression;
import org.apache.commons.io.Charsets;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CommentsInserterTest {

    private ParseResult<CompilationUnit> parseSample(String sampleName) throws IOException {
        Provider p = Providers.resourceProvider(
                "com/github/javaparser/issue_samples/" + sampleName + ".java.txt");
        return new JavaParser().parse(ParseStart.COMPILATION_UNIT, p);
    }

    /**
     * Issue: "When there is a String constant "\\" compilationUnit ignores all further comments"
     */
    @Test
    public void issue290() throws IOException {
        ParseResult result = parseSample("Issue290");
        CommentsCollection cc = (CommentsCollection) result.getCommentsCollection().get();
        assertEquals(1, cc.getLineComments().size());
        assertEquals(1, cc.getJavadocComments().size());
    }

    @Test
    public void issue624() {
        ParseResult result = parseSample("Issue624");
        // Should not fail
    }

}
