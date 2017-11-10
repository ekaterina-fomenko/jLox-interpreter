package com.craftinginterpreters.lox.parser;

import com.craftinginterpreters.lox.Expr;
import com.craftinginterpreters.lox.Lox;
import com.craftinginterpreters.lox.tokens.Token;
import com.craftinginterpreters.lox.tokens.TokenType;

import java.util.List;

import static com.craftinginterpreters.lox.tokens.TokenType.*;

/**
 * Parse tokens and construct ast tree
 */

public class Parser {
    private static class ParseError extends RuntimeException {
    }

    private final List<Token> tokens;
    private int current;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
        current = 0;
    }

    /**
     * Expression rule : expression → equality
     *
     * @return
     */
    private Expr expression() {
        return equality();
    }

    /**
     * Equality rule : equality → comparison ( ( "!=" | "==" ) comparison )* ;
     *
     * @return
     */
    private Expr equality() {
        Expr expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType tokenType) {
        if (isAtEnd()) return false;
        return peek().type == tokenType;
    }

    /**
     * Increase current index token
     *
     * @return
     */
    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    /**
     * Comparison rule : comparison → addition ( ( ">" | ">=" | "<" | "<=" ) addition )* ;
     *
     * @return
     */
    private Expr comparison() {
        Expr expr = addition();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = addition();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    /**
     * Addition rule : addition → multiplication ( ( "-" | "+" ) multiplication )* ;
     *
     * @return
     */
    private Expr addition() {
        Expr expr = multiplication();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = multiplication();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    /**
     * Multiplication rule : multiplication → unary ( ( "/" | "*" ) unary )* ;
     *
     * @return
     */
    private Expr multiplication() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    /**
     * Unary rule : unary → ( "!" | "-" ) unary | primary ;
     *
     * @return
     */
    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }

        return primary();
    }

    /**
     * Primary rule : primary → NUMBER | STRING | "false" | "true" | "nil" | "(" expression ")" ;
     */
    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }
        throw error(peek(), "Expect expression.");
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }

    private ParseError error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }
}