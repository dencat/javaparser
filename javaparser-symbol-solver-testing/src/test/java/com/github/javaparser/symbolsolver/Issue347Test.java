/*
 * Copyright (C) 2015-2016 Federico Tomassetti
 * Copyright (C) 2017-2019 The JavaParser Team.
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

package com.github.javaparser.symbolsolver;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.resolution.Navigator;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.AbstractResolutionTest;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.javaparser.StaticJavaParser.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Issue347Test extends AbstractResolutionTest{

    private TypeSolver typeSolver;
    private JavaParserFacade javaParserFacade;

    @BeforeEach
    void setup() {
        typeSolver = new ReflectionTypeSolver();
        javaParserFacade = JavaParserFacade.get(typeSolver);
    }

    @Test
    void resolvingReferenceToEnumDeclarationInSameFile() {
        String code = "package foo.bar;\nenum Foo {\n" +
                "    FOO_A, FOO_B\n" +
                "}\n" +
                "\n" +
                "class UsingFoo {\n" +
                "    Foo myFooField;\n" +
                "}";
        CompilationUnit cu = parse(code);
        FieldDeclaration fieldDeclaration = Navigator.demandNodeOfGivenClass(cu, FieldDeclaration.class);
        ResolvedType fieldType = javaParserFacade.getType(fieldDeclaration);
        assertTrue(fieldType.isReferenceType());
        assertTrue(fieldType.asReferenceType().getTypeDeclaration().get().isEnum());
        assertEquals("foo.bar.Foo", fieldType.asReferenceType().getQualifiedName());
    }
}

