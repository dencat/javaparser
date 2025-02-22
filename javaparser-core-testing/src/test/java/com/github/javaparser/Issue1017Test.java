package com.github.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Issue1017Test {

    @Test()
    void test() {
        String code = "class X{int x(){ a(1+1 -> 10);}}";

        Assertions.assertThrows(ParseProblemException.class, () -> {
            CompilationUnit cu = StaticJavaParser.parse(code);
        });
    }

}
