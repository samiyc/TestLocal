import java.util.*;

//NpSolver
public class NpSolver {
    private static final int MAX_ID = 30;
    private static final int NB_INPUT = 3;
    public static void main(String W[]) {

        List<InOut> map = new ArrayList<>();
        
        List<Node> nodes = new ArrayList<>();
        for(int i=0; i<NB_INPUT; i++) nodes.add(new Node("INPUT", i));
        for(int i=NB_INPUT; i<MAX_ID; i++) nodes.add(new Node(nodes, i));
        //System.out.println("\n### NODES - nbNodes:" + nodes.size()); System.out.println(nodes);

        //Verify eval system
        int eval, oldI=0;
        for (int i=-100; i<=100; i+=Math.abs(i/10)+1) {
            eval=Node.baseEval(i, i, i-oldI, i-oldI);
            if (eval != 100) throw new RuntimeException("WARNING EVAL KO !!! - i:"+i+" ev:"+eval);
            //System.out.println("i:"+i+" dt:"+(i-oldI)+" ev:"+eval);
            oldI = i;
        }
        
        double min=0, max=0; int count=0;
        while (min < 75 && count++ < 1000) {

            map.clear();
            for(int j=0; j<1000; j++) map.add(new InOut(NB_INPUT));
            //System.out.println("\n### THE MAP - nbTest:" + map.size()); System.out.println(map);
            
            for(InOut io : map) nodes.forEach(n -> n.compute(io));
            //System.out.println("\n### SIM > OUTS"); System.out.println(nodes);
            
            nodes.forEach(n -> n.evaluate(map));
            //System.out.println("\n### EVALUATE > EVALS"); System.out.println(nodes);

            nodes.forEach(n -> n.backProp(nodes));
            System.out.println("\n### AVG EVALS & BACK PROPAGATION"); System.out.println(nodes);

            double min2=getMin(nodes); min = min2; max=getMax(nodes);
            nodes.forEach(n -> n.cleanUp(min2));
            nodes.removeIf(n -> n.isCompute() && (!n.asParent() || n.avgEval==0.0));
            nodes.forEach(n -> n.reset());
            for (int i=0; i<MAX_ID; i++) {
                if (i < nodes.size()) nodes.get(i).id = i;
                else {
                    nodes.add(new Node(nodes, i));
                }
            }
            System.out.println("\n###"+count+" CLEAN UP - max:" + max + " min:" + min); //System.out.println(nodes);
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
            int a=in.get(0).number, b=in.get(1).number, c=in.get(2).number;

            //  /!\Input Code /!\
            //if (a+b > c) out = new Value(a);
            //else out = new Value(b);
            out = new Value(a*a+b*b);
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
            if (bothInt(other))  return new Value(number + other.number);
            if (bothBool(other)) return new Value(bool || other.bool);
            return new Value();
        }
        public Value minus(Value other) {
            if (other.isEmpty()) return this;
            if (bothInt(other))  return new Value(number - other.number);
            if (bothBool(other)) return new Value(bool != other.bool);
            return new Value();
        }
        public Value mult(Value other) {
            if (isEmpty() || other.isEmpty()) return new Value();
            if (bothInt(other))  return new Value(number * other.number);
            if (bothBool(other)) return new Value(bool && other.bool);
            if (isInt() && other.isBool() && other.bool) return new Value(number);
            if (isBool() && other.isInt() && bool) return new Value(other.number);
            return new Value();
        }
        public Value inf(Value other) {
            if (bothInt(other))  return new Value(number < other.number);
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
        private static final int MAX_OP = 8;
        Node nodeA, nodeB; int id, op;
        double avgEval=0.0;
        List<Value> outs;
        List<Integer> evals;
        List<Node> childs;

        //New Input nodes
        public Node(String s, int curId) {
            id = curId;
            op = MAX_OP;
            outs = new ArrayList<>();
            evals = new ArrayList<>();
            childs = new ArrayList<>();
        }

        //New Compute node
        public Node(List<Node> nodes, int curId) {
            id = curId;
            outs = new ArrayList<>();
            evals = new ArrayList<>();
            childs = new ArrayList<>();
            List<Integer> ids = checkIds(nodes);
            nodeA = nodes.stream().filter(p->p.id == ids.getFirst()).findFirst().get();
            nodeB = nodes.stream().filter(p->p.id == ids.getLast()).findFirst().get();
            nodeA.addChild(this);
            nodeB.addChild(this);
        }

        private void addChild(Node node) {
            //FIXME!
        }

        private List<Integer> checkIds(List<Node> nodes) {
            //Randomly choose a unique ida, idb and operator
            Random random = new Random();
            int count=0, ida, idb;
            boolean any;
            do {
                ida = random.nextInt(id-1);
                idb = random.nextInt(id-1);
                if (ida == idb) idb++;
                if (ida > idb) { //ida < idb only. Simplify duplicate checking
                    int tmp= idb; idb = ida; ida =tmp;
                }
                op = random.nextInt(MAX_OP);

                //Duplicate ? retry 10x
                final int fida=ida, fidb=idb;
                any=nodes.stream().anyMatch(p->
                        p.nodeA != null && p.nodeA.id == fida
                     && p.nodeB != null && p.nodeB.id == fidb && p.op==op);
            } while (
                any && ++count < 10
            ); if (count > 9) throw new RuntimeException("WARNING CONFLIC i:"+id+" count:"+count);
            return Arrays.asList(ida, idb);
        }

        public void compute(InOut io) {
            if (op == MAX_OP){
                outs.add(io.in.get(id));
            }
            else {
                Value a = nodeA.lastOut(), b = nodeB.lastOut();
                if (op == 0) outs.add(a.add(b));   //A+B
                if (op == 1) outs.add(a.inf(b));   //Supp & Inf
                if (op == 2) outs.add(b.inf(a));
                if (op == 3) outs.add(b.alternative(a));  //Replace 0 by
                if (op == 4) outs.add(a.alternative(b));
                if (op == 5) outs.add(a.mult(b));
                if (op == 6) outs.add(a.minus(b));
                if (op == 7) outs.add(b.minus(a));
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
                if (outDif == expDif) eval += 5;
                if (outDif > 0 && expDif > 0 || outDif < 0 && expDif < 0) eval += 5;
                if (out / 10 == exp / 10) eval += 5;
                if (out / 100 == exp / 100) eval += 5;
                if (out / 1000 == exp / 1000) eval += 5;
                if (out >= 0 && exp >= 0 || out < 0 && exp < 0) eval += 5;
                if (out == exp || (out != 0 && exp % out == 0)) eval += 5;
                if (out % 2 == 0 && exp % 2 == 0 || out % 2 != 0 && exp % 2 != 0) eval += 5;
                if (out != 0 && exp % out == 0) eval += 5;
                if (out == exp) eval = 100;
            }

            return eval;
        }

        public void backProp(List<Node> nodes) {
            if (isCompute() && asParent()) {
                double lowerLimit = this.avgEval-10;
                List<Node> parents  = Arrays.asList(this.nodeA, this.nodeB);

                //Give credit to parents -10pts
                for (Node n : parents) if (n.isCompute() && n.avgEval < lowerLimit) {
                    n.avgEval = lowerLimit;
                    n.backProp(nodes);
                }
                //Better parent. remove the child...
                for (Node n : parents) if (n.avgEval > this.avgEval) {
                    prepareForDelete();
                }
            }
        }

        public void cleanUp(double min) {
            if (isCompute() && avgEval <= min) {
                prepareForDelete();
            }
        }
        private void prepareForDelete() {
            this.avgEval = 0.0;
            nodeA=null;
            nodeB=null;
            childs.forEach(n->n.prepareForDelete());
        }

        public void reset() {
            //Reset outs for next simulation
            outs = new ArrayList<>();
            evals = new ArrayList<>();
            avgEval = 0.0;
        }

        @Override
        public String toString() {
            int ida = nodeA!=null ? nodeA.id:0;
            int idb = nodeB!=null ? nodeB.id:0;
            return Arrays.asList("id:"+id+" in:"+ida+" "+idb+" "+op+" ev:"+avgEval).toString();
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
        private boolean asParent() {
            return nodeA != null && nodeB != null;
        }
        public boolean isInput() {
            return op == MAX_OP;
        }
        public boolean isCompute() {
            return op != MAX_OP;
        }
    }//End of Node

    private static double getMin(List<Node> nodes) {
        double min=100;
        for(Node n : nodes) {
            double cur = n.avgEval;
            if (cur != 0.0 && cur < min) min = cur;
        }
        return min;
    }
    private static double getMax(List<Node> nodes) {
        double max=0;
        for(Node n : nodes) {
            double cur = n.avgEval;
            if (cur > max) max = cur;
        }
        return max;
    }

}//End of NpSolver

