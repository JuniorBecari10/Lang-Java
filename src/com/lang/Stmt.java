package com.lang;

public abstract class Stmt {
  // var <name> [ = <value> ] | value ommitted = nil
  static class VarDecl extends Stmt {
    final String name;
    final Exp value;

    public VarDecl(String name, Exp value) {
      this.name = name;
      this.value = value;
    }
  }

  // <name> = <value>
  static class VarAssign extends Stmt {
    final String name;
    final Exp value;

    public VarAssign(String name, Exp value) {
      this.name = name;
      this.value = value;
    }
  }

  // print/println <value>
  static class Print extends Stmt {
    final Exp value;
    final boolean breakLine;

    public Print(Exp value, boolean breakLine) {
      this.value = value;
      this.breakLine = breakLine;
    }
  }

  static class Ret extends Stmt {
    final Exp value;

    public Ret(Exp value) {
      this.value = value;
    }
  }

  static class End extends Stmt {}
}
