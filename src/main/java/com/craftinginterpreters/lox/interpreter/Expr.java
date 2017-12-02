package com.craftinginterpreters.lox.interpreter;

import com.craftinginterpreters.lox.tokens.Token;

/**
 * Generated by GenerateAst.java class
 */

public abstract class Expr {
    public interface Visitor<R> {
        public R visitBinaryExpr(Binary expr);

        public R visitGroupingExpr(Grouping expr);

        public R visitLiteralExpr(Literal expr);

        public R visitUnaryExpr(Unary expr);

        public R visitVariableExpr(Variable expr);
    }

    /**
     * binary -> expression operator expression
     */
    public static class Binary extends Expr {
        public Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }

        public final Expr left;
        public final Token operator;
        public final Expr right;
    }

    /**
     * grouping -> "(" expression ")"
     */
    public static class Grouping extends Expr {
        public Grouping(Expr expression) {
            this.expression = expression;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }

        public final Expr expression;
    }

    /**
     * literal -> NUMBER | STRING | "true" | "false" | "nil"
     */
    public static class Literal extends Expr {
        public Literal(Object value) {
            this.value = value;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }

        public final Object value;
    }

    /**
     * unary ->  ( "-" | "!" ) expression
     */
    public static class Unary extends Expr {
        public Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }

        public final Token operator;
        public final Expr right;
    }

    /**
     * varDecl → "var" IDENTIFIER ( "=" expression )? ";" ;
     */
    public static class Variable extends Expr {
        Variable(Token name) {
            this.name = name;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariableExpr(this);
        }

        final Token name;
    }

    public abstract <R> R accept(Visitor<R> visitor);
}
