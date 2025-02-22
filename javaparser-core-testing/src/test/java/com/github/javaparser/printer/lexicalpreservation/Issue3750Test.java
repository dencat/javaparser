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

package com.github.javaparser.printer.lexicalpreservation;

import com.github.javaparser.ast.body.FieldDeclaration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Issue3750Test extends AbstractLexicalPreservingTest {

	@Test
    void test() {
        considerCode(
                "public class MyClass {\n"
                        + " String s0;\n"
                        + " // Comment\n"
                        + " String s1;\n"
                        + "}");

        List<FieldDeclaration> fields = cu.findAll(FieldDeclaration.class);
        FieldDeclaration field = fields.get(0);
        
        String expected = 
        		"public class MyClass {\n"
                        + " // Comment\n"
                        + " String s1;\n"
                        + "}";

        field.remove();

        assertEquals(expected, LexicalPreservingPrinter.print(cu));
    }
}
