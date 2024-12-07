package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.List;

public class LoxArray extends LoxInstance {
    private ArrayList<Object> elements = new ArrayList<Object>();

    LoxArray(ArrayList<Object> elements) {
        super(new LoxClass("Array", null, null));
        this.elements = elements;
    }

    @Override
    Object get(Token name) {
        if (name.lexeme.equals("length")) {
            return new LoxFunction(null, null, false) {
                @Override
                public Object call(Interpreter interpreter, List<Object> arguments) {
                    return size();
                }

                @Override
                public int arity() {
                    return 0;
                }

                @Override
                public String toString() {
                    return "<fn pop>";
                }
            };
        } else if (name.lexeme.equals("pop")) {
            return new LoxFunction(null, null, false) {
                @Override
                public Object call(Interpreter interpreter, List<Object> arguments) {
                    return pop();
                }

                @Override
                public int arity() {
                    return 0;
                }

                @Override
                public String toString() {
                    return "<fn pop>";
                }
            };
        } else if (name.lexeme.equals("push")) {
            return new LoxFunction(null, null, false) {
                @Override
                public Object call(Interpreter interpreter, List<Object> arguments) {
                    push(arguments.get(0));
                    return null;
                }

                @Override
                public int arity() {
                    return 1;
                }

                @Override
                public String toString() {
                    return "<fn push>";
                }
            };
        }
        throw new RuntimeError(name, "Unknown method or property for Array " + name.lexeme + ".");
    }

    @Override
    void set(Token name, Object value) {
        int index = Integer.parseInt(name.lexeme);
        if (index < 0 || index >= elements.size()) {
            throw new RuntimeError(name, "Index out of bounds.");
        }
        if (name.type != TokenType.NUMBER) {
            throw new RuntimeError(name, "Array index must be a number.");
        }
        elements.set(index, value);
    }

    Object getItem(int index) {
        if (index < 0 || index >= elements.size()) {
            throw new RuntimeError(null, "Index out of bounds.");
        }
        return elements.get(index);
    }

    int size() {
        return elements.size();
    }

    void push(Object value) {
        elements.add(value);
    }

    Object pop() {
        if (elements.size() == 0) {
            return null;
        }
        return elements.remove(elements.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i).toString());
            if (i != elements.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}
