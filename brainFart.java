import java.util.Stack;

public class brainFart {
    final String LOWER_AZ = "abcdefghijklmnopqrstuvwxyz ";
    final String UPPER_AZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    final String NUMBER   = "0123456789";

    public static void main(String W[]) {
        brainFart bf = new brainFart();

        //ABCDEFGHIJKLMNOPQRSTUVWXYZ

        String memory = "                              ";  
        String code   = "+++[>>+>+++>++>++++>---->--->-->-[<]<-]>+";

        //double loop issue

        /*
            " FLOU UIF!SJOH    GSPEPO!TBDRVFU!"
                3w[v]>^>;v;[v]  //  <[<]>[-.+>]>+.->>.>-.+>.>[-.+>] >> "FRODON SACQUET got THE RING"
                zl3w[v]>^>;v;[v]  >>  frodon sacquet got the ring
            "FRODON SACQUET GOT THE RING"
                27^ // z^ >> "GSPEPO!TBDRVFU!HPU!UIF!SJOH"
            "frodon sacquet got the ring"
                zuz; >> "FRODON SACQUET GOT THE RING"
            "    " // Empty memory
                -------.>++++++++.<--.>---..>.<<.>++++.<----.>--.<+++++.<.>>-.<----.+++.<.>++.>++.---.>.<.+++++++.<++.<+++++.>>++.>.<---.--.+++++.<<++.>---.
                7-;8+,--;3-.;,<;4+,4-;--,5+,;>-,4-.3+,;++;++.3-;,.7+,++,5+;>++;,3-.--.5+,<++;3-.
                +++[>>+>+++>++>++++>---->--->-->-[<]<-]>+>>>>>>>-.<<<<<-.>>>>.<<<-..<<<<.>>>>>>>.<<<<+.>>>-.<<++.>>>>-.>>.<<<<<<-.>>+.>.>>>.<<+.<<<<<-.>-.<<<<.>>>>.>.>>>>--.<<<<<.>>-.>>>>.<<<<<-.<<+.>>>.<<++.>>>>-.
                >>> THREE RINGS FOR THE ELVEN KINGS
        */

        String out = bf.play(memory, code);
        System.out.println("out:'"+out+"'");
    }

    String play(String mem, String code) {
        Data data = new Data(); 
        data.memory = mem.toCharArray();
        data.code = code.toCharArray();
        String out = "";
        int lastId = 0;

        for (int i = 0; i < data.code.length; i++) {
            final char c = data.code[i];
            
            if (!data.skipLoop) {
                System.out.printf("p:%-2d  b:%s ", data.pos, data.curent());
                String nc = "";
                
                if (c >= '0' && c <= '9') data.updateVal(c);
                if (c == 'z') data.setValToMax();
                if (c == 'i') data.moveToIndexOrRoot();
                if (c == 'w') data.moveToTheStartOfTheNextWord();
                if (c == '<') data.movePos(-1);
                if (c == '>') data.movePos(1);
                if (c == '+') data.changeValue(1);
                if (c == '-') data.changeValue(-1);
                if (c == 'b') data.flipBound();
                if (c == 'u') data.changeCase(true);
                if (c == 'l') data.changeCase(false);
                if (c == '[') i = data.loop(true, i, lastId);
                if (c == '.') nc += data.write();
                if (c == ',') nc += data.writeAndMove(0, -1);
                if (c == ';') nc += data.writeAndMove(0, 1);
                if (c == 'v') nc += data.writeAndMove(-1, 1);
                if (c == '^') nc += data.writeAndMove(1, 1);
                if (!nc.isEmpty()) out += nc;

                System.out.printf(" p:%-2d  v:%-2d c:%s  nc:'%s'  mem:%s \n\r", data.pos, data.val, c, nc, new String(data.memory));
            } else {

            }
            if (c == ']') {
                if (data.skipLoop) {
                    data.skipLoop = false;
                } else {
                    lastId = i;
                    i = data.loop(false, i, 0) - 1;
                }
            }
        }
        System.out.println();
        return out;
    }

    //----------------------------------------------------------------------

    class Data {
        char[] code;
        char[] memory;
        int pos = 0;
        int val = 0;
        String valChar = "";
        Stack<Integer> lastLoopId = new Stack<>();
        boolean skipLoop = false;
        boolean bound = true;
        boolean peek = true;
        boolean defUpper = true;

        char curent() {
            return this.memory[this.pos];
        }

        public void setValToMax() {
            this.val = this.memory.length;
        }
        public void moveToIndexOrRoot() {
            int v = getVal();
            this.pos = v;
        }

        public void moveToTheStartOfTheNextWord() {
            int v = getVal();
            if (v == 0) v = 1;
            for (int i = v; i > 0; i--) {
                while(curent() == ' ') movePos(1);
                if (i > 1) while(curent() != ' ') movePos(1);
            }
        }

        int getVal() {
            int r = this.val;
            this.val = 0;
            this.valChar = "";
            return r;
        }

        void updateVal(char c) {
            this.valChar += c;
            this.val = Integer.valueOf(this.valChar);
        }

        void movePos(int offset) {
            int val = this.getVal();
            if (val == 0) val=1;

            int nPos = this.pos + offset * val;
    
            if (nPos >= this.memory.length)
                nPos %= this.memory.length;
            while (nPos < 0)
                nPos += this.memory.length;
            this.pos = nPos;
        }
    
        void changeValue(int offset) {
            int val = this.getVal();
            if (val == 0) val=1;
            offset *= val;

            if (this.bound) {
                char c = curent();
                char nc = (char)(c + offset);
                if      ((c >= 65 && c <= 90)  || (c == 32 && defUpper))   nc = checkBound(UPPER_AZ, c, offset);
                else if ((c >= 97 && c <= 122) || (c == 32 && !defUpper))  nc = checkBound(LOWER_AZ, c, offset);
                else if (c >= 48 && c <= 57)                               nc = checkBound(NUMBER, c, offset);
                this.memory[this.pos] = nc;    
            } else {
                this.memory[this.pos] += offset;
            }
        }
        char checkBound(String str, char c, int offset) {
            int ic = str.indexOf(c);
            ic += offset;
            if (ic >= str.length()) ic %= str.length();
            while (ic < 0) ic += str.length();
            return str.toCharArray()[ic];
        }
        
        void flipBound() {
            this.bound = !this.bound;
        }

        void changeCase(boolean up) {
            int val = this.getVal();
            if (val == 0) val=1;

            for (int i = 0; i < val; i++) {
                char c = curent();
                
                if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                    changeValue(up ? -32 : 32);
                }
                movePos(1);
            }
        }

        int loop(boolean open, int index, int lastId) {
            if (open) {
                this.skipLoop = false;
                if (this.memory[this.pos] == ' ') {
                    this.skipLoop = true;
                    return lastId;
                }
                this.lastLoopId.push(index);
            } else {
                return this.lastLoopId.pop();
            }
            return index;
        }

        String write() {
            String out = "";
            int val = this.getVal();
            if (val == 0) val=1;
            System.out.printf(">");

            for (int i = 0; i < val; i++) {
                out += this.memory[this.pos];
            }
            return out;
        }

        String writeAndMove(int valOffset, int posOffset) {
            String out = "";
            int val = this.getVal();
            if (val == 0) val=1;
            System.out.printf(">");

            for (int i = 0; i < val; i++) {
                char c = (char)(this.memory[this.pos] + valOffset);
                if (!this.peek) this.memory[this.pos] = c;
                out += c;
                movePos(posOffset);
            }
            return out;
        }
    }
}
