package elocindev.protbalancer.math;

import elocindev.protbalancer.ProtBalancer;

public class FormulaParser {
    public static double evaluateFormula(String formula) {
        try {
            return new Object() {
                int pos = -1, ch;
    
                void nextChar() {
                    ch = (++pos < formula.length()) ? formula.charAt(pos) : -1;
                }
    
                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }
    
                double parse() {
                    nextChar();
                    double x = parseExpression();
                    if (pos < formula.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                    return x;
                }
    
                double parseExpression() {
                    double x = parseTerm();
                    for (;;) {
                        if (eat('+')) x += parseTerm();
                        else if (eat('-')) x -= parseTerm();
                        else return x;
                    }
                }
    
                double parseTerm() {
                    double x = parseFactor();
                    for (;;) {
                        if (eat('*')) x *= parseFactor();
                        else if (eat('/')) x /= parseFactor();
                        else return x;
                    }
                }
    
                double parseFactor() {
                    if (eat('+')) return parseFactor();
                    if (eat('-')) return -parseFactor();
    
                    double x;
                    int startPos = pos;
                    if (eat('(')) {
                        x = parseExpression();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(formula.substring(startPos, pos));
                    } else if (ch >= 'a' && ch <= 'z') {
                        while (ch >= 'a' && ch <= 'z') nextChar();
                        String func = formula.substring(startPos, pos);
                        if (func.equals("sqrt")) {
                            x = parseFactor();
                            x = Math.sqrt(x);
                        } else if (func.equals("sin")) {
                            x = parseFactor();
                            x = Math.sin(Math.toRadians(x));
                        } else if (func.equals("cos")) {
                            x = parseFactor();
                            x = Math.cos(Math.toRadians(x));
                        } else if (func.equals("tan")) {
                            x = parseFactor();
                            x = Math.tan(Math.toRadians(x));
                        } else if (func.equals("min")) {
                            eat('(');
                            x = Double.MAX_VALUE;
                            do {
                                x = Math.min(x, parseExpression());
                            } while (eat(','));
                            eat(')');
                        } else if (func.equals("max")) {
                            eat('(');
                            x = Double.MIN_VALUE;
                            do {
                                x = Math.max(x, parseExpression());
                            } while (eat(','));
                            eat(')');
                        } else if (func.equals("clamp")) {
                            eat('(');
                            double value = parseExpression();
                            eat(',');
                            double min = parseExpression();
                            eat(',');
                            double max = parseExpression();
                            x = Math.max(min, Math.min(value, max));
                            eat(')');
                        } else {
                            throw new RuntimeException("Unknown function: " + func);
                        }
                    } else {
                        throw new RuntimeException("Unexpected: " + (char) ch);
                    }
    
                    if (eat('^')) x = Math.pow(x, parseFactor());
    
                    return x;
                }
            }.parse();
        } catch (NumberFormatException e) {
            ProtBalancer.LOGGER.error("Error parsing formula: " + formula);
            return 0;
        }
    }    
}
