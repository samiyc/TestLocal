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
        while (max < 99 && count++ < 1000) {

            map.clear();
            for(int j=0; j<1000; j++) map.add(new InOut(NB_INPUT));
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
        List<Integer> in;
        Integer out;
        public InOut(int nbInupt){
            in = new ArrayList<>();
            Random random = new Random();
            for(int i=nbInupt; i>0; i--) in.add(random.nextInt(100)-50);
            
            //InputCode
            int a=in.get(0), b=in.get(1), c=in.get(2);
            if (a > b) out = a;
            else out = b + c;
        }
        @Override
        public String toString() {
            return in + " => " + out;
        }
    }//End of InOut

    // ***************************************************************************************************************************************************
    // ***************************************************************************************************************************************************

    static class Node {
        private static final int MAX_OP = 7;
        int ida, idb, op;
        double avgEval=0.0;
        List<Integer> outs;
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
                int a = nodes.get(ida).lastOut(), b = nodes.get(idb).lastOut();
                if (op == 0) outs.add(a + b);           //A+B
                if (op == 1) outs.add(a > b  ? a : 0);  //Supp & Inf
                if (op == 2) outs.add(a < b  ? a : 0);
                if (op == 3) outs.add(b == 0 ? a : b);  //Replace 0 by
                if (op == 4) outs.add(a == 0 ? b : a);  
                if (op == 5) outs.add(a * b);  
                if (op == 6) outs.add(a - b);
            }
        }
        
        public void evaluate(List<InOut> map) {
            if (isCompute()) {
                int lout=0, lexp=0;
                for (int i=0; i<outs.size(); i++) {
                    int out=outs.get(i);
                    int exp=map.get(i).out;
                    int outDif=out-lout, expDif=exp-lexp;

                    evals.add(baseEval(out, exp, outDif, expDif));

                    lout=out; lexp=exp;
                }
                avgEval = calcAverage(evals);
            }
        }

        public static int baseEval(int out, int exp, int outDif, int expDif) {
            int eval = 0;
            if (outDif==expDif) eval+=10;
            if (outDif>0 && expDif>0 || outDif<0 && expDif<0) eval+=10;
            if (out/10==exp/10) eval+=10;
            if (out/100==exp/100) eval+=10;
            if (out/1000==exp/1000) eval+=10;
            if (out>=0 && exp>=0 || out<0 && exp<0) eval+=10;
            if (out==exp || (out!=0 && exp%out==0)) eval+=10;
            if (out%2==0 && exp%2==0 || out%2!=0 && exp%2!=0) eval+=10;
            if (out==exp) eval=100;
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
        private int lastOut() {
            if (outs.isEmpty()) return 0;
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

