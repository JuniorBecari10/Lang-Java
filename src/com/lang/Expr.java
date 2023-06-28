package com.lang;

public abstract class Expr {
  static class Ident extends Expr {
    final String value;

    public Ident(String value) {
      this.value = value;
    }
  }

  static class Literal extends Expr {
    final Object value;

    public Literal(Object value) {
      this.value = value;
    }
  }

  static class Bin extends Expr {
    final Expr left;
    final Token op;
    final Expr right;

    public Bin(Expr left, Token op, Expr right) {
      this.left = left;
      this.op = op;
      this.right = right;
    }
  }

  static class Unary extends Expr {
    final Token op;
    final Expr expr;

    public Unary(Token op, Expr expr) {
      this.op = op;
      this.expr = expr;
    }
  }

  static class Group extends Expr {
    final Expr expr;

    public Group(Expr expr) {
      this.expr = expr;
    }
  }
}
