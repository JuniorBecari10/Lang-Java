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

  Identifier, String, Number,

  AndKw, ClassKw, ElseKw, FalseKw, TrueKw, FnKw, LoopKw, IfKw, NilKw, OrKw,
  PrintKw, PrintlnKw, RetKw, SuperKw, ThisKw, VarKw, WhileKw,

  EOF, Error
}
