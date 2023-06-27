package com.lang;

import java.util.List;

public class Parser {
  private final List<Token> tokens;
  private int cursor = 0;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
  }

  private void advance() {
    this.cursor++;
  }

  private Token token() {
    return tokens.get(cursor);
  }

  private Token peek() {
    return tokens.get(cursor + 1);
  }

  private Expr expression() {
    return equality();
  }

  private Expr equality() {
    Expr expr = comparison();

    if (peek().type() == TokenType.BangEqual || peek().type() == TokenType.EqualEqual) {
      advance();
      Token op = token();
      Expr right = comparison();

      expr = new Expr.Bin(expr, op, right);
    }

    return expr;
  }
}
