package com.lang;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static String file;

  public static void main(String[] args) {
    if (args.length > 1) {
      System.out.println("Usage: lang <script | file>");
      System.exit(1);
    }
    else if (args.length == 1) {
      try {
        runFile(args[0]);
      } catch (IOException e) {
        System.out.println("Cannot read file " + args[0] + ".");
      }
    }
    else {
      repl();
    }
  }

  public static void runFile(String file) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(file));

    Main.file = file;
    run(new String(bytes, Charset.defaultCharset()));
  }

  public static void repl() {
    Scanner s = new Scanner(System.in);

    for (;;) {
      System.out.print("> ");
      String input = s.nextLine();

      if (input.equals("exit!")) {
        s.close();
        System.exit(0);
      }

      run(input);
    }
  }

  public static void run(String code) {
    Lexer l = new Lexer(code);
    List<Token> tokens = l.lex();

    for (Token t : tokens) {
      System.out.println(t);
    }
  }
}