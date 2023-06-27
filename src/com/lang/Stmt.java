package com.lang;

public abstract class Stmt {
  // var <name> [ = <value> ] | value ommitted = nil
  static class VarDecl extends Stmt {
    final String name;
    final Expr value;

    public VarDecl(String name, Expr value) {
      this.name = name;
      this.value = value;
    }
  }

  // <name> = <value>
  static class VarAssign extends Stmt {
    final String name;
    final Expr value;

    public VarAssign(String name, Expr value) {
      this.name = name;
      this.value = value;
    }
  }

  // print/println <value>
  static class Print extends Stmt {
    final Expr value;
    final boolean breakLine;

    public Print(Expr value, boolean breakLine) {
      this.value = value;
      this.breakLine = breakLine;
    }
  }

  static class Ret extends Stmt {
    final Expr value;

    public Ret(Expr value) {
      this.value = value;
    }
  }

  static class End extends Stmt {}
}
