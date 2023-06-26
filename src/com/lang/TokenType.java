package com.lang;

public enum TokenType {
  LeftParen, RightParen,
  LeftBrace, RightBrace,
  Comma, Dot,
  Plus, Minus,
  Star, Slash,
  Semicolon,

  Bang, BangEqual,
  Equal, EqualEqual,
  Greater, GreaterEqual,
  Less, LessEqual,

  And, Or,

  Identifier, String, Number,

  ClassKw, ElseKw, FalseKw, TrueKw, FnKw, LoopKw, IfKw, NilKw,
  PrintKw, PrintlnKw, RetKw, SuperKw, ThisKw, VarKw, WhileKw,

  EOF, Error
}
