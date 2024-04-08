import java.util.*;

//NpSolver
public class NpSolver {
    private static final int MAX_ID = 30;
    private static final int NB_INPUT = 3;
    public static void main(String[] args) {

        List<InOut> map = new ArrayList<>();
        
        Map<Integer, Node> nodes = new HashMap<>();
        for(int i=0; i<NB_INPUT; i++) nodes.put(i, new Node("IN", i));
        for(int i=NB_INPUT; i<MAX_ID; i++) nodes.put(i, new Node(nodes, i));
        //System.out.println("\n### NODES - nbNodes:" + nodes.size()); System.out.println(nodes);

        

        //Checking eval sys
        int eval, oldI=0;
        for (int i=-100; i<=100; i+=Math.abs(i/10)+1) {
            eval=Node.baseEval(i, i, i-oldI, i-oldI);
            if (eval != 100) System.out.println("WARNING EVAL KO !!! - i:"+i+" ev:"+eval);
            oldI = i;
        }
        
        double min, max=0; int count=0;
        while (max < 99 && count++ < 1000) {

            map.clear();
            for(int j=0; j<10000; j++) map.add(new InOut(NB_INPUT));
            //System.out.println("\n### THE MAP - nbTest:" + map.size()); System.out.println(map);
            
            for(InOut io : map) nodes.forEach((k, v) -> v.compute(k, nodes, io));
            //System.out.println("\n### SIM > OUTS"); System.out.println(nodes);
            
            nodes.forEach((k, v) -> v.evaluate(k, nodes, map));
            //System.out.println("\n### EVALUATE > EVALS"); System.out.println(nodes);

            nodes.forEach((k, v) -> v.backProp(nodes));
            System.out.println("\n### AVG EVALS & BACK PROPAGATION"); System.out.println(nodes);

            double min2=getMin(nodes); min = min2; max=getMax(nodes);
            nodes.forEach((k, v) -> nodes.put(k, v.cleanUp(nodes, min2)));
            System.out.println("\n### CLEAN UP - max:" + max + " min:" + min);// System.out.println(nodes);
        }
        System.out.println();
    }

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
            if (in.get(0) > in.get(1)) out = in.get(0);
            else out = in.get(1) + in.get(2);
            //out = in.get(0) * in.get(1) * in.get(2);
        }
        @Override
        public String toString() {
            return in + " => " + out;
        }
    }//End of InOut

    // ***************************************************************************************************************************************************
    // ***************************************************************************************************************************************************

    static class Node {
        private static final int MAX_OP = 6;
        int id=-1, ida=-1, idb=-1, op, min, max, med;
        double avgEval=0.0;
        List<Integer> outs;
        List<Integer> evals;

        public Node(String s, int curId) {
            id = curId;
            op = MAX_OP;
            min=Integer.MAX_VALUE;
            max=Integer.MIN_VALUE;
            outs = new ArrayList<>();
            evals = new ArrayList<>();
        }

        public Node(Map<Integer, Node> nodes, int curId) {
            id = curId;
            Random random = new Random();

            int count=0;
            do {
                int maxId = curId < 20 ? curId : 20;
                int minId = curId > 20 ? curId - 20 : 0;
                ida = random.nextInt(maxId) + minId;
                idb = random.nextInt(maxId) + minId;
                if (ida==idb) idb++;
                if (ida>idb) {
                    int tmp = idb;
                    idb=ida;
                    ida=tmp;
                }
                op = random.nextInt(MAX_OP);
            } while (
                nodes.values().stream().filter(p->p.ida==ida && p.idb==idb && p.op==op)
                .findFirst().isPresent() && count++ < 10
            );
            if (count > 8) System.out.println("WARNING CONFLIC i:"+curId+" count:"+count);

            min=Integer.MAX_VALUE;
            max=Integer.MIN_VALUE;
            outs = new ArrayList<>();
            evals = new ArrayList<>();
        }

        public void compute(int key, Map<Integer, Node> nodes, InOut io) {
            if (op == MAX_OP){
                addToOuts(io.in.get(key));
            }
            else {
                int a = nodes.get(ida).last(), b = nodes.get(idb).last();
                if (op == 0) addToOuts(a + b);           //A+B
                if (op == 1) addToOuts(a > b  ? a : 0);  //A > B ? A : 0
                if (op == 2) addToOuts(a < b  ? a : 0);
                if (op == 3) addToOuts(b == 0 ? a : b);  //Else
                if (op == 4) addToOuts(a == 0 ? b : a);  //replace output
                if (op == 5) addToOuts(a * b);  //replace output
            }
        }
        public void evaluate(int key, Map<Integer, Node> nodes, List<InOut> map) {
            if (op != MAX_OP) {
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
            if (op != MAX_OP) {       
                double LowerLimit = this.avgEval-10;
                Node a=nodes.get(ida), b=nodes.get(idb);

                //give credit to parents /2
                if (!a.isInput() && a.avgEval < LowerLimit) {
                    a.avgEval = LowerLimit;
                    a.backProp(nodes);
                }
                if (!b.isInput() && b.avgEval < LowerLimit) {
                    b.avgEval = LowerLimit;
                    b.backProp(nodes);
                }

                //Better parent. remove the child...
                if (a.avgEval > avgEval || b.avgEval > avgEval) {
                    avgEval = 0.0;
                }
            }
        }
        
        public Node cleanUp(Map<Integer, Node> nodes, double min) {
            if (avgEval <= min && op != MAX_OP) {
                return new Node(nodes, id);
            }
            outs=new ArrayList<>();
            evals = new ArrayList<>();
            avgEval=0.0;
            return this;
        }

        private void addToOuts(int out) {
            if (out < min) min=out;
            if (out > max) max=out;
            outs.add(out);
        }

        private int last() {
            if (outs.size() == 0) return 0;
            return outs.get(outs.size()-1);
        }

        @Override
        public String toString() {
            return Arrays.asList(ida, idb, op, "ev:"+avgEval).toString(); //avgEval<1?"_":
        }

        public static double calcAverage(List<Integer> numbers) {
            // Check if list is empty to avoid division by zero
            if (numbers.isEmpty()) return 0.0;
            
            int sum = 0; // Calculate the sum
            for (int number : numbers) sum += number; 

            // Calculate the average
            return (double) sum / numbers.size();
        }

        public static double calcMedian(List<Integer> numbers) {
            if (numbers != null && !numbers.isEmpty()) {
                Collections.sort(numbers);
        
                int size = numbers.size();
                if (size % 2 == 0) {
                    // If the size is even, return the average of the two middle elements
                    int middleIndex1 = size / 2 - 1;
                    int middleIndex2 = size / 2;
                    return (numbers.get(middleIndex1) + numbers.get(middleIndex2)) / 2.0;
                } else {
                    // If the size is odd, return the middle element
                    int middleIndex = size / 2;
                    return numbers.get(middleIndex);
                }
            }
            return 0.0;
        }

        public double midValue() {
            return (double) (max + min) / 2;
        }

        private boolean isInput() {
            return op == MAX_OP;
        }

    }//End of Node

}//End of NpSolver

