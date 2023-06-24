package com.lang;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
  private String input;
  private int cursor;
  private int line;

  public Lexer(String input) {
    this.input = input;
    this.cursor = 0;
    this.line = 1;
  }

  private void advance() {
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
    int start = this.cursor;
    StringBuilder buffer = new StringBuilder(new String(new char[] { this.input.charAt(this.cursor) }));

    switch (this.ch()) {
      case 0: return new Token(TokenType.EOF, buffer.toString(), null, line);
      case '\n': line++; break;

      case '+': return new Token(TokenType.Plus, buffer.toString(), buffer.toString(), line);
      case '-': return new Token(TokenType.Minus, buffer.toString(), buffer.toString(), line);
      case '*': return new Token(TokenType.Star, buffer.toString(), buffer.toString(), line);
      case '/': return new Token(TokenType.Slash, buffer.toString(), buffer.toString(), line);

      case '(': return new Token(TokenType.LeftParen, buffer.toString(), buffer.toString(), line);
      case ')': return new Token(TokenType.RightParen, buffer.toString(), buffer.toString(), line);
      case '{': return new Token(TokenType.LeftBrace, buffer.toString(), buffer.toString(), line);
      case '}': return new Token(TokenType.RightBrace, buffer.toString(), buffer.toString(), line);

      case ',': return new Token(TokenType.Comma, buffer.toString(), buffer.toString(), line);
      case '.': return new Token(TokenType.Dot, buffer.toString(), buffer.toString(), line);
      case ';': return new Token(TokenType.Semicolon, buffer.toString(), buffer.toString(), line);

      case '!':
        if (this.peek() == '=') return new Token(TokenType.BangEqual, buffer.toString(), buffer.toString(), line);

        return new Token(TokenType.Bang, buffer.toString(), buffer.toString(), line);
      
      case '=':
        if (this.peek() == '=') return new Token(TokenType.EqualEqual, buffer.toString(), buffer.toString(), line);

        return new Token(TokenType.Equal, buffer.toString(), buffer.toString(), line);
      
      case '>':
        if (this.peek() == '=') return new Token(TokenType.GreaterEqual, buffer.toString(), buffer.toString(), line);

        return new Token(TokenType.Greater, buffer.toString(), buffer.toString(), line);
      
      case '<':
        if (this.peek() == '=') return new Token(TokenType.LessEqual, buffer.toString(), buffer.toString(), line);

        return new Token(TokenType.Less, buffer.toString(), buffer.toString(), line);
    }

    if (isIdentStart(this.ch())) {
      while (isIdent(this.ch())) this.cursor++;

      return new Token(TokenType.Identifier, buffer.toString(), buffer.toString(), line);
    }
  }

  private boolean isIdent(char c) {
    return Character.isAlphabetic(c) || Character.isDigit(c) || c == '_';
  }

  private boolean isIdentStart(char c) {
    return Character.isAlphabetic(c) || c == '_';
  }

  public List<Token> lex() {
    List<Token> tokens = new ArrayList<>();


  }
}
