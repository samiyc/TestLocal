import java.util.*;

public class CountChar {

    public static void main(String[] args) {
        String input = "the to and of i a in was that it he his as for you not had with her my be is have me at which but she him this on all by so from or would no were they one very will them an are what been now if when your said more could their who there some we than any out do into then such up much other upon man little did should before am time must only great never like though may own good well two these about being made know might most over see first can after again say down too our think where yet indeed those himself its without thought ever long us make came shall last go way how many day here every come myself found nothing same young has still old men saw away soon having life seemed nor while house world lady answered take place went ship head three off another eyes heard hand give both perhaps began part poor just even once sure almost always till whole love through whom better back room seen night heart father cannot therefore enough mind knew took herself left tell however told against rather sea least looked things between says called boat side look put believe moment far something present done kind let cries gentleman morning often woman under face gave get thing manner few cried find person word going matter got brought hands hope less because people name next near round friend home years certain each set since half door towards felt leave water whether whose together anything right end among asked hear sister dear family full account reason best either given reader turned words lay means thus immediately short wife gone honour quite want received voice taken wish stood mother company answer business fire truth nature within captain light rest master evening sort speak really thoughts letter sat return bed power certainly daughter small work alone country passed squire days others sometimes true above air hour large shore brother resolved case girl opinion occasion order white yourself keep call does known times pleasure possible husband strange new fortune why returned child open also suppose already several use making neither themselves able eye happened read used fell high itself length pleased sight feet mean character coming doubt human live looking sooner everything bear course kept aunt ladies replied appeared continued care seems fear happy money feel hard saying seeing proper behind dead second afterwards point none subject fine four afraid five lost town along entirely gentlemen longer help table turn wild added conversation former mentioned sent women married island mine obliged sense pretty soul hold body bring death behaviour presently ground remember dark especially taking hours year story different nay purpose run thousand creature desire idea promise cold entered happiness hardly ought ran ready affection black wanted whatever carried object arms besides minutes book laid sun observed wind living friends spoke else strong further general wonder blood close past distance arm ask likewise stand uncle low acquainted easy forward lived passion question stay ill mistress ten circumstances consider met rose seem become beyond hundred impossible common glad danger during fair violent arrived fact surprised talk mention deal deep forth history visit land regard natural cause degree necessary particular cousin deck surprise greatly marriage eat expected instant son stranger usual ago convinced devil perfectly understand condition cut acquaintance except feeling followed mouth unless probably scene standing boy considered earth line marry wished concluded secret beauty latter news walk walked children contrary opened struck boats crew discovered fast late need spirits tears sleep killed scarce pass silent virtue board expect held road sound suddenly farther favour real silence feelings foot imagine matters servant afternoon became age interest satisfied bad broken directly generally possibly situation state utmost knows please turning attention easily piece view chair concerning meet play satisfaction consequence doing duty greater persons plain single sufficient appear dinner miles show believed giving hair mere sorry sudden act handsome servants sit vast worse carry fit form hopes intended lips trouble opportunity twenty desired knowledge pale proceeded broke declared offered party running assure comfort concern engaged following girls goodness leaving sisters smile send spent fall heaven justice learned placed somewhat appearance clear drew plainly spirit conduct sake temper wood bread fish instantly art fixed otherwise agreeable equal forget future sitting step strength assured horse settled absolutely advantage effect instead landlord ordered presence advice arrival die joy lord noble offer remained six supposed chapter speaking around circumstance lie meant months wall express likely notice particularly thinking truly window attended consent pride respect comes force fresh greatest honest laugh pardon repeated sensible filled school shot caught law loss match service below change fancy frequently liked pity raised third cast escape minute perceived receive resolution started wait affair creatures dreadful expressed instance liberty lower pieces pleasant society accident dare early flesh grew loved perfect promised quiet scarcely stopped delight determined disposition follow imagined reached week difficulty excellent forced hearing journey nephew savage heartily highest seldom suffer yes confess highly iron reality whence beneath blue delivered design dress front glass grave kill looks parts pay try chance extraordinary kitchen success write wrong agreed capable ships shook trees green leg middle miserable top warm books bottom getting horses talking angry broad exactly mate orders rain safe tree avoid calm companion consideration curious ears number rising worth youth guilty hill prevented rich showed suffered sweet tender vain across alive assistance bright curiosity maid moved talked beginning expression heavy understood wicked attempt begged discourse picture pray ruin watch wise draw figure hall meeting pounds suspicion tried understanding beg cabin judgment led peculiar season countenance drawn removed slowly taste beautiful clothes estate food knowing possession reading serious weather departed distress free glance health informed soft ashamed cry difference dog gun misery proceed quality stop according eight endeavour fifty formed prevailed wide worthy altogether attend command daughters discovery noise quit applied breath considerable drink drive equally finding garden gold higher inn lives moon nose ourselves reach retired";
        String binput = 
        """
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
                    System.out.println("out:" + out);
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
                    
        """;
        String[] sa = input.split(" ");
        //System.err.println(Arrays.asList(sa));

        Map<Character, Integer> firstMap = new HashMap<>();
        Map<Character, Integer> allMap = new HashMap<>();
        Map<String, Integer> twoMap = new HashMap<>();
        Map<String, Integer> thirdMap = new HashMap<>();
        Map<String, Integer> fourMap = new HashMap<>();

        for(String s : sa) {
            s = s.trim();
            if (!s.isEmpty()) {
                //System.err.println(s+", ");
                for (int i=0; i<s.length(); i++) {
                    char car = s.charAt(i);
                    if (car == '\n') car = '§';

                    allMap.putIfAbsent(car, Integer.valueOf(0));
                    allMap.computeIfPresent(car, (k, v) -> v + 1);
                    if (i==0) {
                        firstMap.putIfAbsent(car, Integer.valueOf(0));
                        firstMap.computeIfPresent(car, (k, v) -> v + 1);
                    }
                    if (i+1 < s.length()) {
                        String key = s.substring(i, i+2);
                        twoMap.putIfAbsent(key, Integer.valueOf(0));
                        twoMap.computeIfPresent(key, (k,v) -> v + 1);
                    }
                    if (i+2 < s.length()) {
                        String key = s.substring(i, i+3);
                        thirdMap.putIfAbsent(key, Integer.valueOf(0));
                        thirdMap.computeIfPresent(key, (k,v) -> v + 1);
                    }
                    if (i+3 < s.length()) {
                        String key = s.substring(i, i+4);
                        fourMap.putIfAbsent(key, Integer.valueOf(0));
                        fourMap.computeIfPresent(key, (k,v) -> v + 1);
                    }
                }
            }
        }

        // Convert to list of entries
        List<Map.Entry<Character, Integer>> firstEnt = new ArrayList<>(firstMap.entrySet());
        firstEnt.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        
        List<Map.Entry<Character, Integer>> allEnt = new ArrayList<>(allMap.entrySet());
        allEnt.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<Map.Entry<String, Integer>> twoEnt = new ArrayList<>(twoMap.entrySet());
        twoEnt.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        
        List<Map.Entry<String, Integer>> thirdEnt = new ArrayList<>(thirdMap.entrySet());
        thirdEnt.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<Map.Entry<String, Integer>> fourEnt = new ArrayList<>(fourMap.entrySet());
        fourEnt.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        
        System.err.println();
        System.err.print("## first letter: ");
        System.err.println(firstEnt.toString().substring(1));
        System.err.print("##  all letters: ");
        System.err.println(allEnt.toString().substring(1));
        System.err.print("## 2 letter seq: ");
        int count = 0; for (Map.Entry<String, Integer> entry : twoEnt) {
            System.out.print(entry+" ");
            if (count++ >= 25) break;
        }
        System.err.println();
        System.err.print("## 3 letter seq: ");
        count = 0; for (Map.Entry<String, Integer> entry : thirdEnt) {
            System.out.print(entry+" ");
            if (count++ >= 20) break;
        }
        System.err.println();
        System.err.print("## 4 letter seq: ");
        count = 0; for (Map.Entry<String, Integer> entry : fourEnt) {
            System.out.print(entry+" ");
            if (count++ >= 20) break;
        }
        System.err.println();
        System.err.println();
    }  
}
