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

  private boolean isAtEnd() {
    return cursor >= tokens.size();
  }

  private boolean isPeekAtEnd() {
    return cursor + 1 >= tokens.size();
  }

  private boolean peekEqual(TokenType... types) {
    if (isAtEnd() || isPeekAtEnd()) return false;

    for (TokenType t : types) {
      if (peek().type() == t) return true;
    }

    return false;
  }

  private Expr expression() {
    return equality();
  }

  private Expr equality() {
    Expr expr = comparison();

    while (peekEqual(TokenType.BangEqual, TokenType.EqualEqual)) {
      Token op = token();
      advance();

      Expr right = comparison();

      expr = new Expr.Bin(expr, op, right);
    }

    return expr;
  }

  private Expr comparison() {
    Expr expr = term();

    while (peekEqual(TokenType.Greater, TokenType.GreaterEqual, TokenType.Less, TokenType.LessEqual)) {
      Token op = token();
      advance();

      Expr right = term();

      expr = new Expr.Bin(expr, op, right);
    }

    return expr;
  }

  private Expr term() {
    Expr expr = factor();

    while (peekEqual(TokenType.Plus, TokenType.Minus)) {
      Token op = token();
      advance();

      Expr right = factor();

      expr = new Expr.Bin(expr, op, right);
    }

    return expr;
  }

  private Expr factor() {
    Expr expr = unary();

    while (peekEqual(TokenType.Star, TokenType.Slash)) {
      Token op = token();
      advance();

      Expr right = unary();

      expr = new Expr.Bin(expr, op, right);
    }

    return expr;
  }
  private Expr unary() {
    if (peekEqual(TokenType.Bang, TokenType.Minus)) {
      Token op = token();
      advance();

      Expr right = unary();
      return new Expr.Unary(op, right);
    }

    return primary();
  }

  private Expr primary() {
    if (peekEqual(TokenType.FalseKw)) return new Expr.Literal(false);
    if (peekEqual(TokenType.TrueKw)) return new Expr.Literal(true);
    if (peekEqual(TokenType.NilKw)) return new Expr.Literal(null);

    if (peekEqual(TokenType.Number, TokenType.String)) return new Expr.Literal(token().literal());
    if (peekEqual(TokenType.Identifier)) return new Expr.Ident(token().lexeme());

    if (peekEqual(TokenType.LeftParen)) {
      Expr expr = expression();

      if (!peekEqual(TokenType.RightParen)) {
        reportError("Expected ')' after expression.");
      }
      
      advance();
      return new Expr.Group(expr);
    }
  }
}
