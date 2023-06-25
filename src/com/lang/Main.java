package com.lang;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static String file = "REPL";
  public static boolean hadError = false;

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
    System.out.println("Prism REPL");
    System.out.println("Type 'exit!' to exit.\n");

    Scanner s = new Scanner(System.in);

    for (;;) {
      System.out.print("> ");
      String input = s.nextLine();

      if (input.isBlank()) continue;

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

    if (hadError) return;
  }

  public static void reportError(String message, String help, String code, int line, int col) {
    hadError = true;


    System.out.println("Error: " + file + " | " + line + ":" + (col + 1));
    System.out.println(message + "\n");

    System.out.println(" " + line + " | " + code);

    System.out.print("    ");
    for (int i = 0; i < String.valueOf(line).length(); i++) {
      System.out.print(" ");
    }
    
    for (int i = 0; i < col; i++) {
      System.out.print(" ");
    }
    System.out.println("^ " + help + "\n");
  }
}