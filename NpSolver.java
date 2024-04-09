import java.util.*;

//NpSolver
public class NpSolver {
    private static final int MAX_ID = 30;
    private static final int NB_INPUT = 3;
    public static void main(String W[]) {

        List<InOut> map = new ArrayList<>();
        
        Map<Integer, Node> nodes = new HashMap<>();
        for(int i=0; i<NB_INPUT; i++) nodes.put(i, new Node("INPUT", i));
        for(int i=NB_INPUT; i<MAX_ID; i++) nodes.put(i, new Node(nodes, i));
        //System.out.println("\n### NODES - nbNodes:" + nodes.size()); System.out.println(nodes);

        //Verify eval system
        int eval, oldI=0;
        for (int i=-100; i<=100; i+=Math.abs(i/10)+1) {
            eval=Node.baseEval(i, i, i-oldI, i-oldI);
            if (eval != 100) throw new RuntimeException("WARNING EVAL KO !!! - i:"+i+" ev:"+eval);
            //System.out.println("i:"+i+" dt:"+(i-oldI)+" ev:"+eval);
            oldI = i;
        }
        
        double min, max=0; int count=0;
        while (max < 99 && count++ < 10000) {

            map.clear();
            for(int j=0; j<10000; j++) map.add(new InOut(NB_INPUT));
            //System.out.println("\n### THE MAP - nbTest:" + map.size()); System.out.println(map);
            
            for(InOut io : map) nodes.forEach((k, v) -> v.compute(k, nodes, io));
            //System.out.println("\n### SIM > OUTS"); System.out.println(nodes);
            
            nodes.forEach((k, v) -> v.evaluate(map));
            //System.out.println("\n### EVALUATE > EVALS"); System.out.println(nodes);

            nodes.forEach((k, v) -> v.backProp(nodes));
            System.out.println("\n### AVG EVALS & BACK PROPAGATION"); System.out.println(nodes);

            double min2=getMin(nodes); min = min2; max=getMax(nodes);
            nodes.forEach((k, v) -> nodes.put(k, v.cleanUp(k, nodes, min2)));
            System.out.println("\n### CLEAN UP - max:" + max + " min:" + min);// System.out.println(nodes);
        }
        System.out.println();
    }

    // ***************************************************************************************************************************************************
    // ***************************************************************************************************************************************************

    static class InOut {
        List<Value> in;
        Value out;
        public InOut(int nbInupt){
            in = new ArrayList<>();
            Random random = new Random();
            for(int i=nbInupt; i>0; i--) in.add(new Value(random.nextInt(100)-50));
            
            //InputCode
            int a=in.get(0).number, b=in.get(1).number, c=in.get(2).number;
            if (a > b) out = new Value(a);
            else out = new Value(b + c);
        }
        @Override
        public String toString() {
            return in + " => " + out;
        }
    }//End of InOut

    // ***************************************************************************************************************************************************
    // ***************************************************************************************************************************************************

    static class Value {
        Integer number = 0;
        Boolean bool = false;
        ValueType type;

        public Value() {
            type = ValueType.EMPTY;
        }
        public Value(Integer val) {
            type = ValueType.INT;
            number = val;
        }
        public Value(Boolean val) {
            type = ValueType.BOOL;
            bool = val;
        }

        public Value add(Value other) {
            if (isEmpty()) return other; if (other.isEmpty()) return this;
            if (bothInt(other)) return new Value(number + other.number);
            if (bothBool(other)) return new Value(bool || other.bool);
            return new Value();
        }
        public Value minus(Value other) {
            if (other.isEmpty()) return this;
            if (bothInt(other)) return new Value(number - other.number);
            if (bothBool(other)) return new Value(bool != other.bool);
            return new Value();
        }
        public Value mult(Value other) {
            if (isEmpty() || other.isEmpty()) return new Value();
            if (bothInt(other)) return new Value(number - other.number);
            if (bothBool(other)) return new Value(bool != other.bool);
            if (isInt() && other.isBool() && other.bool) return new Value(number);
            if (isInt() && other.isBool() && other.bool) return new Value(number);

            return new Value();
        }
        public Value inf(Value other) {
            if (bothInt(other)) return new Value(number < other.number);
            if (bothBool(other)) return new Value(!bool && other.bool); // False < True
            return new Value();
        }

        public Value alternative(Value other) {
            if (isBool() && !bool) return other;
            return new Value();
        }

        private boolean bothInt(Value other) {
            return isInt() && other.isInt();
        }
        private boolean bothBool(Value other) {
            return isBool() && other.isBool();
        }

        @Override
        public String toString() {
            if (isInt())  return number.toString();
            if (isBool()) return bool.toString();
            return "EMPTY";
        }

        public boolean isInt() { return type == ValueType.INT; }
        boolean isBool() { return type == ValueType.BOOL; }
        boolean isEmpty() { return type == ValueType.BOOL; }
    }//End of InOut

    enum ValueType {
        INT, BOOL, STRING, EMPTY
    }

    // ***************************************************************************************************************************************************
    // ***************************************************************************************************************************************************

    static class Node {
        private static final int MAX_OP = 5;
        int ida, idb, op;
        double avgEval=0.0;
        List<Value> outs;
        List<Integer> evals;

        //New Input nodes
        public Node(String s, int curId) {
            op = MAX_OP;
            outs = new ArrayList<>();
            evals = new ArrayList<>();
        }

        //New Compute node
        public Node(Map<Integer, Node> nodes, int curId) {
            Random random = new Random();
            outs = new ArrayList<>();
            evals = new ArrayList<>();

            //Randomly choose ida, idb and operator
            int count=0;
            do {
                int maxId = curId < 20 ? curId : 20;
                int minId = curId > 20 ? curId - 20 : 0;
                ida = random.nextInt(maxId) + minId;
                idb = random.nextInt(maxId) + minId;
                if (ida==idb) idb++;
                if (ida > idb) { //ida < idb only. Simplify duplicate checking
                    int tmp=idb; idb=ida; ida=tmp;
                }
                op = random.nextInt(MAX_OP);
            }
            while (
                //Duplicate ? retry 10x
                nodes.values().stream().anyMatch(p->p.ida==ida && p.idb==idb && p.op==op)
                && ++count < 10
            ); if (count > 9) throw new RuntimeException("WARNING CONFLIC i:"+curId+" count:"+count);
        }

        public void compute(int key, Map<Integer, Node> nodes, InOut io) {
            if (op == MAX_OP){
                outs.add(io.in.get(key));
            }
            else {
                Value a = nodes.get(ida).lastOut(), b = nodes.get(idb).lastOut();
                if (op == 0) outs.add(a.add(b));   //A+B
                if (op == 1) outs.add(a.inf(b));   //Supp & Inf
                if (op == 2) outs.add(b.alternative(a));  //Replace 0 by
                if (op == 3) outs.add(a.alternative(b));
                if (op == 4) outs.add(a.mult(b));
                //if (op == 5) outs.add(a.minus(b));
            }
        }
        
        public void evaluate(List<InOut> map) {
            if (isCompute()) {
                Value lout=new Value(), lexp=new Value();
                for (int i=0; i<outs.size(); i++) {
                    Value out=outs.get(i);
                    Value exp=map.get(i).out;
                    Value outDif=out.minus(lout), expDif=exp.minus(lexp);

                    evals.add(baseEval(out, exp, outDif, expDif));

                    lout=out; lexp=exp;
                }
                avgEval = calcAverage(evals);
            }
        }

        public static int baseEval(int valOut, int valExp, int valOutDif, int valExpDif) {
            return baseEval(new Value(valOut), new Value(valExp), new Value(valOutDif), new Value(valExpDif));
        }
        public static int baseEval(Value valOut, Value valExp, Value valOutDif, Value valExpDif) {

            int eval = 0;
            if (valOut.isBool() && valExp.isBool()) {
                if (valOut.bool == valExp.bool) eval = 100;
            }
            else {
                int out = valOut.number, exp = valExp.number, outDif=valOutDif.number, expDif=valExpDif.number;
                if (outDif == expDif) eval += 10;
                if (outDif > 0 && expDif > 0 || outDif < 0 && expDif < 0) eval += 10;
                if (out / 10 == exp / 10) eval += 10;
                if (out / 100 == exp / 100) eval += 10;
                if (out / 1000 == exp / 1000) eval += 10;
                if (out >= 0 && exp >= 0 || out < 0 && exp < 0) eval += 10;
                if (out == exp || (out != 0 && exp % out == 0)) eval += 10;
                if (out % 2 == 0 && exp % 2 == 0 || out % 2 != 0 && exp % 2 != 0) eval += 10;
                if (out == exp) eval = 100;
            }

            return eval;
        }

        public void backProp(Map<Integer, Node> nodes) {
            if (isCompute()) {       
                double lowerLimit = this.avgEval-10;
                List<Node> parents  = Arrays.asList(nodes.get(this.ida), nodes.get(this.idb));

                //Give credit to parents -10pts
                for (Node n : parents) if (n.isCompute() && n.avgEval < lowerLimit) {
                    n.avgEval = lowerLimit;
                    n.backProp(nodes);
                }
                //Better parent. remove the child...
                for (Node n : parents) if (n.avgEval > this.avgEval) {
                    this.avgEval = 0.0;
                }
            }
        }
        
        public Node cleanUp(Integer k, Map<Integer, Node> nodes, double min) {
            if (isCompute() && avgEval <= min) {
                return new Node(nodes, k);
            }
            //Reset outs for next simulation
            outs = new ArrayList<>();
            evals = new ArrayList<>();
            avgEval = 0.0;
            return this;
        }

        @Override
        public String toString() {
            return Arrays.asList(ida+" "+idb+" "+op, "ev:"+avgEval).toString();
        }
        public static double calcAverage(List<Integer> numbers) {
            if (numbers.isEmpty()) return 0.0;
            int sum = 0; for (int number : numbers) sum += number; 
            return (double) sum / numbers.size();
        }
        private Value lastOut() {
            if (outs.isEmpty()) return new Value();
            return outs.get(outs.size()-1);
        }
        public boolean isInput() {
            return op == MAX_OP;
        }
        public boolean isCompute() {
            return op != MAX_OP;
        }

    }//End of Node

    private static double getMin(Map<Integer, NpSolver.Node> nodes) {
        double min=100;
        for(Node n : nodes.values()) {
            double cur = n.avgEval;
            if (cur != 0.0 && cur < min) min = cur;
        }
        return min;
    }
    private static double getMax(Map<Integer, NpSolver.Node> nodes) {
        double max=0;
        for(Node n : nodes.values()) {
            double cur = n.avgEval;
            if (cur > max) max = cur;
        }
        return max;
    }

}//End of NpSolver

