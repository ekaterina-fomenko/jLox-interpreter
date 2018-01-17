package com.craftinginterpreters.lox.model;

import com.craftinginterpreters.lox.interpreter.Interpreter;

import java.util.List;

public class LoxClass implements LoxCallable{
    final String name;

    public LoxClass(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int arity() {
        return 0;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        LoxInstance instance = new LoxInstance(this);
        return instance;
    }
}