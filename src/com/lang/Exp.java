package com.lang;

public abstract class Exp {
  static class Ident extends Exp {
    final String value;

    public Ident(String value) {
      this.value = value;
    }
  }

  static class LitNum extends Exp {
    final double value;

    public LitNum(double value) {
      this.value = value;
    }
  }

  static class LitStr extends Exp {
    final String value;

    public LitStr(String value) {
      this.value = value;
    }
  }

  static class Bin extends Exp {
    final Exp left;
    final Token op;
    final Exp right;

    public Bin(Exp left, Token op, Exp right) {
      this.left = left;
      this.op = op;
      this.right = right;
    }
  }
}
