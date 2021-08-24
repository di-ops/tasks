package com.tsystems.javaschool.tasks.calculator;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {

        String result = statement;


        try {
            if (!validateExpr(statement)){
                return null;
            } if (statement.contains("(")){
                while (result.contains("(")){
                    String fromLastParenthesis = result.substring(result.lastIndexOf("("));
                    fromLastParenthesis = fromLastParenthesis.substring(0, fromLastParenthesis.indexOf(")")+1);
                    String value = this.evaluate(fromLastParenthesis.substring(1, fromLastParenthesis.indexOf(")")));
                    result = result.replace(fromLastParenthesis,value);
                }
            }

            String [] operators = result.replaceAll("[0-9,.]+","").split("");
            String []operands = result.replaceAll("[+*/()-]"," ").split(" ");
            if (operands.length>2 && !result.contains("/-") && !result.contains("*-")){
                StringBuilder strWithPriority= new StringBuilder();

                for (int i = 0; i < operators.length; i++) {
                    if (operators[i].equals("*") || operators[i].equals("/"))
                    {
                        strWithPriority.append("(");
                        strWithPriority.append(operands[i]);
                        strWithPriority.append(operators[i]);
                        i++;
                        strWithPriority.append(operands[i]);
                        strWithPriority.append(")");
                        if(i!= operators.length){
                            strWithPriority.append(operators[i]);
                            if(i==operators.length-1){
                                strWithPriority.append(operands[i+1]);
                            }
                        }
                    } else {
                        strWithPriority.append(operands[i]);
                        strWithPriority.append(operators[i]);
                        if (i==operators.length-1){
                                strWithPriority.append(operands[i+1]);
                            }
                        }
                }
                if (strWithPriority.toString().contains("(")){
                    result= this.evaluate(strWithPriority.toString());
                }
                operators = result.replaceAll("[0-9]+","").split("");
                operands = result.replaceAll("[+*/()-]"," ").split(" ");
            }

            double total = Double.parseDouble(operands[0]);

            for (int i=0; i< operators.length; i++) {
                switch (operators[i]) {
                    case "+":
                        total += Double.parseDouble(operands[i + 1]);
                        break;
                    case "-":
                        if ((result.contains("/-") && operators[i-1].equals("/"))
                            || (result.contains("*-") && operators[i-1].equals("*"))){
                            total*=-1;
                            break;
                        }
                        total -= Double.parseDouble(operands[i + 1]);
                        break;
                    case "*":
                        if (operands[i+1].equals("")){
                            total*=Double.parseDouble(operands[i + 2]);
                        }
                        total *= Double.parseDouble(operands[i + 1]);
                        break;
                    case "/":
                        if (operands[i+1].equals("")){
                            total/=Double.parseDouble(operands[i + 2]);
                            break;
                        }
                        if (operands[i+1].equals("0")){
                            return null;
                        }
                        total /= Double.parseDouble(operands[i + 1]);
                        break;
                }
            }
            result = String.valueOf(total);
            if (result.endsWith(".0"))
                result = result.substring(0, result.length()-2);
        }
        catch (Exception e){
            return null;
        }
        return result;
    }


    static Pattern simpleLang = Pattern.compile("\\s*-?\\d+(\\s*[-.+*%/]{1}\\s*-?\\d+)*\\s*");

    static Pattern innerParen = Pattern.compile("[(]([^()]*)[)]");
    public static boolean validateExpr(String expr) {

        while (expr.contains(")") || expr.contains("(")) {
            Matcher m = innerParen.matcher(expr);
            if (m.find()) {
                if (!simpleLang.matcher(m.group(1)).matches()) {
                    return false;
                }
                expr = expr.substring(0,m.start()) + " 1 " + expr.substring(m.end());
            } else {
                return false;
            }
        }
        return simpleLang.matcher(expr).matches();
    }


}
