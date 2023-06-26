package com.lang;

import java.util.Map;
import java.util.HashMap;

public record Token(TokenType type, String lexeme, Object literal, int line) {
  public static final Map<String, TokenType> keywords;

  static {
    keywords = new HashMap<>();

    keywords.put("class", TokenType.ClassKw);
    keywords.put("else", TokenType.ElseKw);
    keywords.put("false", TokenType.FalseKw);
    keywords.put("loop", TokenType.LoopKw);
    keywords.put("fn", TokenType.FnKw);
    keywords.put("if", TokenType.IfKw);
    keywords.put("nil", TokenType.NilKw);
    keywords.put("print", TokenType.PrintKw);
    keywords.put("println", TokenType.PrintlnKw);
    keywords.put("ret", TokenType.RetKw);
    keywords.put("super",  TokenType.SuperKw);
    keywords.put("this", TokenType.ThisKw);
    keywords.put("true", TokenType.TrueKw);
    keywords.put("var", TokenType.VarKw);
    keywords.put("while", TokenType.WhileKw);
  }
}
