/*
 * Copyright (C) 2015-2016 Federico Tomassetti
 * Copyright (C) 2017-2020 The JavaParser Team.
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

package com.github.javaparser.symbolsolver.resolution.typeinference;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;

import java.util.List;

/**
 * @author Federico Tomassetti
 */
public class ExpressionHelper {

    public static boolean isExplicitlyTyped(LambdaExpr lambdaExpr) {
        return lambdaExpr.getParameters().stream().allMatch(p -> !(p.getType() instanceof UnknownType));
    }

    public static List<Expression> getResultExpressions(BlockStmt blockStmt) {
        throw new UnsupportedOperationException();
    }

    public static boolean isCompatibleInAssignmentContext(Expression expression, ResolvedType type, TypeSolver typeSolver) {
        return type.isAssignableBy(JavaParserFacade.get(typeSolver).getType(expression, false));
    }
}
