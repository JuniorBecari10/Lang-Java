package com.lang;

public abstract class Expr {
  static class Ident extends Expr {
    final String value;

    public Ident(String value) {
      this.value = value;
    }
  }

  static class LitNum extends Expr {
    final double value;

    public LitNum(double value) {
      this.value = value;
    }
  }

  static class LitStr extends Expr {
    final String value;

    public LitStr(String value) {
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
}
