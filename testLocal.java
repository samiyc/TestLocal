import java.util.*;


class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); in.nextLine();
        String shots = in.nextLine();
        System.err.println(Arrays.asList("n",n, "shots",shots));

        int w=0, d=0;
        char prev = 0;
        for(char c : shots.toCharArray()) {
            if (c == 'W') {
                w++; prev=c;
            }
            if (c == 'D') {
                d++; prev=c;
            }
            if (c == '*' && prev == 'W') w += 2;
            if (c == '*' && prev == 'D') d += 2;
        }
        System.out.println((int)Math.min(d, w));
    }

}

