import java.util.*;

/**
 * NpSolver
 */
public class NpSolver {
    private static final int MAX_ID = 100;
    public static void main(String[] args) {
        int nbInput = 6;

        List<InOut> map = new ArrayList<>();
        for(int i=0; i<100; i++) map.add(new InOut(nbInput));
        //System.out.println(map);

        Map<Integer, Node> nodes = new HashMap<>();
        for(int i=0; i<nbInput; i++) nodes.put(i, new Node("IN", i));
        for(int i=nbInput; i<MAX_ID; i++) nodes.put(i, new Node(i));
        System.out.println("### NODES");
        //System.out.println(nodes);
        
        for(InOut io : map) nodes.forEach((k, v) -> v.compute(k, nodes, io));
        System.out.println("\n### SIM");
        //System.out.println(nodes);

        nodes.forEach((k, v) -> v.evaluate(k, nodes, map));
        System.out.println("\n### EVALUATE");
        System.out.println(nodes);

        nodes.forEach((k, v) -> v.backProp(nodes, map));
        System.out.println("\n### BACK PROPAGATION");
        System.out.println(nodes);
    }
   
    static class InOut {
        List<Integer> in;
        Integer out;
        public InOut(int nbInupt){
            in = new ArrayList<>();
            Random random = new Random();
            for(int i=nbInupt; i>0; i--) in.add(random.nextInt(100)-50);
            //InputCode
            if (in.get(0) > in.get(1) && in.get(2) % 5 == 0) out = 100;
            else out = in.get(4) * in.get(5);
        }
        @Override
        public String toString() {
            return in + " => " + out;
        }
    }//End of InOut

    // *************************************************************************************************
    static class Node {
        private static final int MAX_OP = 5;
        int id=-1, ida=0, idb=0, op, ticks=0, min, max, med;
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

        public Node(int curId) {
            id = curId;
            Random random = new Random();
            ida = random.nextInt(curId);
            idb = random.nextInt(curId);
            op = random.nextInt(MAX_OP);
            min=Integer.MAX_VALUE;
            max=Integer.MIN_VALUE;
            outs = new ArrayList<>();
            evals = new ArrayList<>();
        }

        public void compute(int key, Map<Integer, Node> nodes, InOut io) {
            ticks++;
            if (op == MAX_OP){
                addToOuts(io.in.get(key));
            }
            else {
                int a = nodes.get(ida).last(), b = nodes.get(idb).last();
                if (op == 0) addToOuts(a+b);
                if (op == 1) addToOuts(a-b);
                if (op == 2) addToOuts(a+b);
                if (op == 3) addToOuts(b!=0?a/b:0);
                if (op == 4) addToOuts(a>b?1:0);
            }
        }
        public void evaluate(int key, Map<Integer, Node> nodes, List<InOut> map) {
            int lout=0, lexp=0;
            for (int i=0; i<outs.size(); i++) {
                int out=outs.get(i);
                int exp=map.get(i).out;
                int outDif=out-lout, expDif=exp-lexp;

                int eval = 0;
                if (out==exp) eval=20;
                if (outDif==expDif) eval+=10;
                if (outDif>0 && expDif>0 || outDif<0 && expDif<0) eval+=10;
                if (out/10==exp/10) eval+=10;
                if (out/100==exp/100) eval+=10;
                if (out/1000==exp/1000) eval+=10;
                if (out>0 && exp>0 || out<0 && exp<0) eval+=10;
                if (out!=0 && exp%out==0) eval+=10;
                if (out%2==0 && exp%2==0 || out%2==1 && exp%2==1) eval+=10;
                evals.add(eval);

                lout=out; lexp=exp;
            }
        }
        public void backProp(Map<Integer, Node> nodes, List<InOut> map) {
            if (avgEval==0.0) avgEval=calcAverage(evals);
            Node a=nodes.get(ida), b=nodes.get(idb);
            if (a.avgEval < avgEval) {
                a.avgEval = avgEval;
                a.backProp(nodes, map);
            }
            if (b.avgEval < avgEval) {
                b.avgEval = avgEval;
                b.backProp(nodes, map);
            }
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
            return avgEval<0?"_":Arrays.asList(ida, idb, "ev:"+avgEval).toString();
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
    }//End of Node
}


