import java.util.Scanner;

/**
 * ASCII ART
 */
class Solution {
    public static void main(String args []) {
        Scanner in= new Scanner(System.in);
        int length = in.nextInt();
        int high = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String input = in.nextLine();
        char[] caInput = input.toUpperCase().toCharArray();

        char[][] map = new char [high][length*27];
        for(int i=0; i < high; i++) {
            map[i] = in.nextLine().toCharArray();
        }

        for(int h=0; h < high; h++) {
            for(int i=0; i < caInput.length; i++) {
                int cint = caInput[i] -65;
                cint = cint < 0 || cint > 26 ? 26 : cint;

                for(int l=cint * length; l < (cint+1) * length; l++) {
                    System.out.print(map[h][l]);
                }
            }
            System.out.println();
        }
    }
}
