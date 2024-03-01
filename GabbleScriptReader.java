import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class GabbleScriptReader {

    public static void main(String W[]) {
        GabbleScriptReader ys = new GabbleScriptReader();
        String filePath = "E:\\Autre\\Dev\\Algo code in game\\testLocal\\GabbleScript.gs";
        String startOfCode = "import java.util.*;class Solution{public static void main(String W[]){Scanner Z=new Scanner(System.in);\n";
        String endOfCode = "}}";

        String out = ys.translate(filePath, startOfCode, endOfCode);
        System.out.println("out:\n-------------------------\n" + out + "\n-------------------------\n");
    }

    String translate(String filePath, String startOfCode, String endOfCode) {
        Data data = new Data();
        data.code = readCode(filePath).toCharArray();
        String out = "";

        out += startOfCode;
        for (int i = 0; i < data.code.length; i++) {
            final char c = data.code[i];
            CharSet ct = CharSet.getCT(c);

            if (c == ' ') {
                System.out.print("");
            }
            if (ct == CharSet.EMPTY) {
                out += data.operatorDetected(c);
                out += c;
            } else if (ct == CharSet.OPERATOR) {
                out += data.operatorDetected(c);
            } else {
                // Exclude OPERATOR and EMPTY from cache
                data.addToCache(c);
            }
        }
        out += endOfCode;
        return out;
    }

    // ----------------------------------------------------------------------

    class Data {

        char[] code;
        String cache = "";
        Set<CharSet> seen = new HashSet<>();
        List<Object> todoStack = new ArrayList<>();
        HashMap<String, Object> hashVars = new HashMap<>();
        FunctionSet activeFunction = null;
        char lastOperator = ' ';

        public void addToCache(char c) {
            CharSet ct = CharSet.getCT(c);
            seen.add(ct);
            this.cache += c;
        }

        public String operatorDetected(char c) {
            String out = "";
            this.lastOperator = c;
            if (cacheNotEmpty()) {
                if (seen.contains(CharSet.AZ_CHAR)) {
                    out += checkIfCurrentIsFonction();
                    out += evalFunctionReady();
                    out += checkIfCurrentIsExistingVar();
                    out += newVar();
                    
                } else {
                    out += newNumber();
                }
            }
            seen.clear();
            return out;
        }

        public String checkIfCurrentIsFonction() {
            if (cacheNotEmpty()) {
                FunctionSet fs = FunctionSet.getFun(this.cache);
                if (fs != null) {
                    this.cache = "";
                    this.activeFunction = fs;
                    todoStack.add(fs);
                    return " f";
                }
            }
            return "";
        }

        private String evalFunctionReady() {
            int vb_needed = this.activeFunction.varBeforeRequired ? 1 : 0;
            int np_needed = this.activeFunction.nbParams;

            if (np < 0 && this.lastOperator != '>') return "";
            for (int i=this.todoStack.size() - 1 ; i >= 0; i--) {
                todoStack
            }

            return "";
        }

        public String checkIfCurrentIsExistingVar() {
            if (cacheNotEmpty()) {
                Object ob = hashVars.get(this.cache);
                if (ob != null) {
                    this.cache = "";
                    todoStack.add(ob);
                    return " v";
                }
            }
            return "";
        }

        public String newVar() {
            String out = "";
            if (cacheNotEmpty()) {
                hashVars.put(this.cache, "");
                out += "var " + this.cache + ";";
                this.cache = "";
            }
            return out;
        }

        public String newNumber() {
            int n = Integer.valueOf(this.cache);
            todoStack.add(n);
            this.cache = "";
            return " " + n;
        }

        boolean cacheNotEmpty() {
            return (this.cache.length() > 0);
        }
    }

    // --------------------------------------------------------------------

    public enum CharSet {
        NUMBER("0123456789"),
        AZ_CHAR("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_"),
        OPERATOR("<,>."),
        EMPTY(" \t\n");

        final String value;

        CharSet(String value) {
            this.value = value;
        }
        public static CharSet getCT(char c) {
            for (CharSet charSet : CharSet.values())
                if (charSet.value.indexOf(c) != -1)
                    return charSet;
            return null;
        }
    }

    public enum FunctionSet {
        //Input
        NEXT_INT("nextInt", false, 0, false),
        NEXT_LINE("nextLine", false, 0, false),
        
        //String manipulation
        UPPER("toUpper", true, 0, false),
        LOWER("toUpper", true, 0, false),
        CHAR_ARRAY("toCharArray", true, 0, false),

        //Var manipulation
        SET("set", true, 1, false),
        SET_ARRAY("setArray", true, -1, false),
        GET("get", true, 1, false),
        LEN("len", true, 0, false),
        
        ADD("add", true, 1, false),
        MINUS("mns", true, 1, false),
        MULTIPLY("mul", true, 1, false),
        DIVIDE("div", true, 1, false),
        INCREMENT("inc", true, 0, false),
        DECREMENT("dec", true, 0, false),
        
        //Condition
        IF("if", false, 2, true),
        ELSE("else", true, 2, true),
        TERNARY("ternary", false, 3, false),

        //Comparaison
        INFERIOR("inf", true, 1, false),
        SUPERIOR("sup", true, 1, false),
        EQUAL("eq", true, 1, false),
        DIFFERENT("dif", true, 1, false),
        AND("and", true, 1, false),
        OR("or", true, 1, false),

        //Loops
        TIMES("times", true, 2, true),
        EACH("each", true, 2, true),
        FOR("for", true, 3, true),
        
        //Output
        PRINT("print", false, -1, false),
        PRINTLN("printLn", false, -1, false),
        ;

        final String value;
        final boolean varBeforeRequired;
        final int nbParams;
        final boolean lastParamIsCode;

        FunctionSet(String name, boolean varBeforeRequired, int nbParams, boolean lastParamIsCode) {
            this.value = name;
            this.varBeforeRequired = varBeforeRequired;
            this.nbParams = nbParams;
            this.lastParamIsCode = lastParamIsCode;
        }
        public static FunctionSet getFun(String s) {
            for (FunctionSet charSet : FunctionSet.values())
                if (charSet.value.toLowerCase().equals(s.toLowerCase()))
                    return charSet;
            return null;
        }
    }

    String readCode(String filePath) {
        String code = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null)
                code += line + "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
}
