/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2021 The JavaParser Team.
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
package com.github.javaparser.printer.concretesyntaxmodel;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.observer.ObservableProperty;
import com.github.javaparser.printer.SourcePrinter;

public class CsmTextBlock implements CsmElement {

    private final ObservableProperty property;

    public CsmTextBlock(ObservableProperty property) {
        this.property = property;
    }

    public ObservableProperty getProperty() {
        return property;
    }

    @Override
    public void prettyPrint(Node node, SourcePrinter printer) {
        // Note that values within TextBlocks ALWAYS have the \n line ending, per https://openjdk.java.net/jeps/378#1--Line-terminators
        printer.print("\"\"\"\n");
        // TODO: Confirm if we need to force this to use {@code \n} separators
        printer.print(property.getValueAsStringAttribute(node));
        printer.print("\"\"\"");
    }

    @Override
    public String toString() {
        return String.format("%s(property:%s)", this.getClass().getSimpleName(), getProperty());
    }
}
