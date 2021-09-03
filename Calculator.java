package edu.ics211.h07;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Represents a Calculator.
 * 
 * @author Cathy Kim
 *
 */
public class Calculator implements ICalculator {

  private static Calculator instance;

  private Stack<Number> operands;

  /**
   * Returns the singleton instance.
   * 
   * @return the singleton instance.
   * 
   */
  public static Calculator getInstance() {
    if (instance == null) {
      instance = new Calculator();
    }
    return instance;
  }


  private Calculator() {
    // initialize the member variables
    operands = new Stack<>();
  }


  @Override
  public void clear() {
    // return the empty stack
    operands = new Stack<>();
  }


  @Override
  public Number postFixCalculate(String expression) throws InvalidExpressionException {

    // split the expression into tokens
    String[] tokens = expression.split("\\s+");

    // loop
    for (int i = 0; i < tokens.length; i++) {
      // if token is a number
      if (isNumber(tokens[i])) {
        // push the converted number on the operands stack
        Number numberTokens = converToNumber(tokens[i]);
        operands.push(numberTokens);
        // else if token is a operator
      } else if (isOperator(tokens[i])) {
        try {
          // pop the rhs from operands
          Number rhs = operands.pop();
          // pop the lhs from operands
          Number lhs = operands.pop();
          // do the math and push number into operands stack
          operands.push(doTheMath(lhs, rhs, tokens[i]));
          // catch EmptyStackException
        } catch (EmptyStackException e) {
          // if empty stack, throw new InvalidExpressionException
          throw new InvalidExpressionException();
        }
        // else throw new InvalidExpressionExeption
      } else {
        throw new InvalidExpressionException();
      }
    }

    // the answer is operands.pop
    Number answer = operands.pop();

    // if operands is not empty throw InvalidExpressionExcpetion
    if (!operands.isEmpty()) {
      throw new InvalidExpressionException();
    }

    // return the answer
    return answer;
  }


  @Override
  public Number preFixCalculate(String expression) throws InvalidExpressionException {

    // reverse the string
    String reversedExpression = reverse(expression);
    // split the reversed expression into tokens
    String[] tokens = reversedExpression.split("\\s+");

    // loop
    for (int i = 0; i < tokens.length; i++) {
      // if token is a number
      if (isNumber(tokens[i])) {
        // push the converted number on the operands stack
        Number numberTokens = converToNumber(tokens[i]);
        operands.push(numberTokens);
        // else if token is a operator
      } else if (isOperator(tokens[i])) {
        try {
          // switch rhs and lhs because it is preFix
          // pop the rhs from operands
          Number lhs = operands.pop();
          // pop the lhs from operands
          Number rhs = operands.pop();
          // do the math and push number into operands stack
          operands.push(doTheMath(lhs, rhs, tokens[i]));
          // catch EmptyStackException
        } catch (EmptyStackException e) {
          // if empty stack, throw new InvalidExpressionException
          throw new InvalidExpressionException();
        }
        // else throw new InvalidExpressionExeption
      } else {
        throw new InvalidExpressionException();
      }
    }
    // the answer is operands.pop
    Number answer = operands.pop();

    // if operands is not empty throw InvalidExpressionExcpetion
    if (!operands.isEmpty()) {
      throw new InvalidExpressionException();
    }
    // return the answer
    return answer;
  }


  private boolean isNumber(String s) {
    try {
      // if it is a integer return true
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException e) {
      try {
        // if it is a double return double
        Double.parseDouble(s);
        return true;
      } catch (NumberFormatException ee) {
        // return false if it is not a number
        return false;
      }
    }
  }


  private Number converToNumber(String s) {
    try {
      // return if number can convert to integer
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      try {
        // return if number can convert to double
        return Double.parseDouble(s);
      } catch (NumberFormatException ee) {
        // throw exception when it is not a number
        throw new NumberFormatException();
      }
    }
  }


  private boolean isOperator(String s) {
    // switch on s
    switch (s) {
      // return true depending cases
      case "+":
        return true;
      case "-":
        return true;
      case "*":
        return true;
      case "/":
        return true;
      // default return false
      default:
        return false;
    }
  }


  private Number doTheMath(Number lhs, Number rhs, String operator) {
    
    Number answer = 0;
    
    // check if both lhs and rhs are Integers then do int math "instanceof"
    if (lhs instanceof Integer && rhs instanceof Integer) {
      // cast lhs and rhs to Integer, do the math
      int intlhs = (int) lhs;
      int intrhs = (int) rhs;
      // switch on operator
      switch (operator) {
        case "+":
          answer = intlhs + intrhs;
          break;
        case "-":
          answer = intlhs - intrhs;
          break;
        case "*":
          answer = intlhs * intrhs;
          break;
        case "/":
          answer = intlhs / intrhs;
          break;
        default:
          break;
      }
      // else do double math
    } else {
      // cast lhs and rhs to Double, do the math
      Double doublelhs = lhs.doubleValue();
      Double doublerhs = rhs.doubleValue();
      // switch on operator
      switch (operator) {
        case "+":
          answer = doublelhs + doublerhs;
          break;
        case "-":
          answer = doublelhs - doublerhs;
          break;
        case "*":
          answer = doublelhs * doublerhs;
          break;
        case "/":
          answer = doublelhs / doublerhs;
          break;
        default:
          break;
      }
    }
    // return the answer
    return answer;
  }


  // Got this reverse code from https://www.geeksforgeeks.org/reverse-words-given-string-java/
  private String reverse(String s) {

    // Specifying the pattern to be searched
    Pattern pattern = Pattern.compile("\\s");

    // splitting String str with a pattern
    // (i.e )splitting the string whenever their
    // is whitespace and store in temp array.
    String[] temp = pattern.split(s);
    String result = "";

    // Iterate over the temp array and store
    // the string in reverse order.
    for (int i = 0; i < temp.length; i++) {
      if (i == temp.length - 1) {
        result = temp[i] + result;
      } else {
        result = " " + temp[i] + result;
      }
    }
    return result;
  }

}