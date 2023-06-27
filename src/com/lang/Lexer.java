package com.lang;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
  private String input;
  private int cursor;
  private int line;

  private StringBuilder buffer;
  private String[] lines;

  public Lexer(String input) {
    this.input = input;
    this.cursor = 0;
    this.line = 1;

    this.buffer = new StringBuilder();
    this.lines = this.input.split("\n");
  }

  private void advance() {
    buffer.append(this.ch());

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

  private boolean isAtEnd() {
    return this.cursor >= input.length();
  }

  private Token nextToken() {
    while (this.ch() == ' ') this.advanceNoConsume();
    while (this.ch() == '\n') { this.line++; this.advanceNoConsume(); }
    
    if (this.ch() == '#') {
      while (this.peek() != '\n' && !isAtEnd()) this.advanceNoConsume();
    }

    this.buffer = new StringBuilder(String.valueOf(this.ch()));
    Token t = newToken(TokenType.Error);

    int start = this.cursor;

    if (isAtEnd()) return new Token(TokenType.EOF, "", null, line);

    switch (this.ch()) {
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

      case '&': t = newToken(TokenType.And); break;
      case '|': t = newToken(TokenType.Or); break;

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
      
      case '<': // TODO fix this
        if (this.peek() == '=') { t = newToken(TokenType.LessEqual); break; }

        t = newToken(TokenType.Less);
        break;
      
      case '"':
        this.advanceNoConsume();
        while (this.peek() != '"' && !isAtEnd()) {
          if (this.peek() == '\n') line++;
          this.advance();
        }

        if (isAtEnd()) {
          Main.reportError("Unterminated string.", "This string doesn't have a closing '\"'.", this.lines[this.line - 1], this.line, start);
          break;
        }

        advance();
        advance();

        t = newToken(TokenType.String);
        break;
      
      default:
          if (isIdentStart(this.ch())) {
            this.advanceNoConsume();
            while (isIdent(this.ch())) this.advance();

            TokenType type = Token.keywords.get(buffer.toString());
            if (type == null) type = TokenType.Identifier;

            t = newToken(type);
          }

          else if (isNumber(this.ch())) {
            this.advanceNoConsume();
            while (isNumber(this.ch())) this.advance();

            Double lit = null;

            try {
              lit = Double.parseDouble(buffer.toString());
            } catch (NumberFormatException e) {
              Main.reportError("Couldn't parse number.", "Note: you may have put 2 or more points in there. Consider deleting one.", this.lines[this.line - 1], this.line, start);
            }

            t = newToken(TokenType.Number, lit);
          }
    }

    if (t.type() == TokenType.Error && !Main.hadError) {
      Main.reportError("Unknown token.", "This token ('" + this.ch() + "') wasn't recognized, delete it.", this.lines[this.line - 1], this.line, start);
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

    tokens.add(t);
    return tokens;
  }
}
