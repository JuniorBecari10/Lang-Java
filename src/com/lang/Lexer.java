package com.lang;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
  private String input;
  private int cursor;
  private int line;

  private StringBuilder buffer;
  private StringBuilder code;

  public Lexer(String input) {
    this.input = input;
    this.cursor = 0;
    this.line = 1;

    this.buffer = new StringBuilder();
    this.code = new StringBuilder(String.valueOf(this.ch()));
  }

  private void advance() {
    buffer.append(this.ch());
    code.append(this.ch());

    this.cursor++;
  }

  private void advanceNoConsume() {
    this.cursor++;
  }

  private char ch() {
    if (this.cursor >= this.input.length()) return 0;

    return this.input.charAt(this.cursor);
  }

  private char peek() {
    if (this.cursor + 1 >= this.input.length()) return 0;

    return this.input.charAt(this.cursor + 1);
  }

  private Token nextToken() {
    this.buffer = new StringBuilder(String.valueOf(this.ch()));
    Token t = null;

    switch (this.ch()) {
      case 0: t = new Token(TokenType.EOF, buffer.toString(), null, line);
      case '\n': line++; this.code = new StringBuilder(); break;

      case '+': t = newToken(TokenType.Plus); break;
      case '-': t = newToken(TokenType.Minus); break;
      case '*': t = newToken(TokenType.Star); break;
      case '/': t = newToken(TokenType.Slash); break;

      case '(': t = newToken(TokenType.LeftParen); break;
      case ')': t = newToken(TokenType.RightParen); break;
      case '{': t = newToken(TokenType.LeftBrace); break;
      case '}': t = newToken(TokenType.RightBrace); break;

      case ',': t = newToken(TokenType.Comma); break;
      case '.': t = newToken(TokenType.Dot); break;
      case ';': t = newToken(TokenType.Semicolon); break;

      case '!':
        if (this.peek() == '=') { t = newToken(TokenType.BangEqual); break; }

        t = newToken(TokenType.Bang);
        break;

      case '=':
        if (this.peek() == '=') { t = newToken(TokenType.EqualEqual); break; }

        t = newToken(TokenType.Equal);
        break;
      
      case '>':
        if (this.peek() == '=') { t = newToken(TokenType.GreaterEqual); break; }

        t = newToken(TokenType.Greater);
        break;
      
      case '<':
        if (this.peek() == '=') { t = newToken(TokenType.LessEqual); break; }

        t = newToken(TokenType.Less);
        break;
    }

    if (isIdentStart(this.ch())) {
      this.advanceNoConsume();
      while (isIdent(this.ch())) this.advance();

      t = newToken(TokenType.Identifier);
    }

    if (isNumber(this.ch())) {
      this.advanceNoConsume();
      while (isNumber(this.ch())) this.advance();

      Double lit = Double.parseDouble(buffer.toString());
      t = newToken(TokenType.Number, lit);
    }

    if (t == null) {
      Main.reportError("Unknown token.", "This token ('" + this.ch() + "') wasn't recognized, delete it.", code.toString(), this.line, this.cursor);
      t = newToken(TokenType.Error);
    }

    this.advance();
    return t;
  }

  private Token newToken(TokenType type) {
    return new Token(type, buffer.toString(), buffer.toString(), line);
  }

  private Token newToken(TokenType type, Object literal) {
    return new Token(type, buffer.toString(), literal, line);
  }

  private boolean isIdent(char c) {
    return Character.isAlphabetic(c) || Character.isDigit(c) || c == '_';
  }

  private boolean isIdentStart(char c) {
    return Character.isAlphabetic(c) || c == '_';
  }

  private boolean isNumber(char c) {
    return Character.isDigit(c) || c == '.';
  }

  public List<Token> lex() {
    List<Token> tokens = new ArrayList<>();
    Token t = this.nextToken();

    while (t.type() != TokenType.EOF) {
      tokens.add(t);
      t = this.nextToken();
    }

    return tokens;
  }
}
