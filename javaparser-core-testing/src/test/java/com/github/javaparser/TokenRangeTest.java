/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2019 The JavaParser Team.
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
import com.github.javaparser.ast.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.github.javaparser.StaticJavaParser.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenRangeTest {
    @Test
    void toStringWorks() {
        CompilationUnit cu = parse("class X {\n\tX(){\n// hello\n}\n}");
        assertEquals("X(){\n// hello\n}", cu.getClassByName("X").get().getDefaultConstructor().get().getTokenRange().get().toString());
    }

    @Test
    void renumberRangesWorks() {
        CompilationUnit cu = parse("class X {\n\tX(){\n// hello\n}\n}");

        assertEquals("1,1-5/6,1-1/7,1-1/8,1-1/9,1-1/10,1-1/1,2-1/2,2-1/3,2-1/4,2-1/5,2-1/6,2-1/1,3-8/9,3-1/1,4-1/2,4-1/1,5-1/1,5-1/", makeRangesString(cu));

        TokenRange tokenRange = cu.getTokenRange().get();
        tokenRange.getBegin().insertAfter(new JavaToken(1, "feif"));
        tokenRange.getBegin().getNextToken().get().getNextToken().get().insert(new JavaToken(JavaToken.Kind.WINDOWS_EOL.getKind(), "\r\n"));
        cu.recalculatePositions();

        assertEquals("1,1-5/6,1-4/10,1-2/1,2-1/2,2-1/3,2-1/4,2-1/5,2-1/1,3-1/2,3-1/3,3-1/4,3-1/5,3-1/6,3-1/1,4-8/9,4-1/1,5-1/2,5-1/1,6-1/2,6-1/", makeRangesString(cu));
    }

    /**
     * Make a compact String for comparing token range positions.
     */
    private String makeRangesString(Node node) {
        Optional<JavaToken> token = node.getTokenRange().map(TokenRange::getBegin);
        StringBuilder result = new StringBuilder();
        while (token.isPresent()) {
            token = token.flatMap(t -> {
                result.append(t.getRange().map(r ->
                        r.begin.column + "," + r.begin.line + "-" +
                                (r.end.column - r.begin.column + 1) + "/"

                ).orElse("?"));
                return t.getNextToken();
            });
        }
        return result.toString();
    }
}
