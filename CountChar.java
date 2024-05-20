import java.security.Provider.Service;
import java.text.Format;
import java.util.*;

public class CountChar {

    public static void main(String[] args) {

        String input = """
            n = int(input())
            shots = input()
            birds = ''
            for i in shots:
                if i == '*':birds+=birds[-1]*2
                elif i == 'W' or i == 'D':birds+=i
            w = birds.count('W')
            d = len(birds) - w
            print(min(d,w))
            class Solution {
                public static void main(String args[]) {
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
            List<Integer> in = Arrays.asList(1,2,3,1,3,4,4);
            List<Integer> out = new ArrayList<>();
            System.err.println(in);
            int w=0;
            for (Integer val : in) {
                w ^= val;
                if (out.contains(val)) out.remove(val);
                else out.add(val);
            }
            System.err.println(out);
            System.err.println("Solution xor:"+w+" std:"+out.get(0));
            Scanner in = new Scanner(System.in);
            Map<Integer,Integer> ia = new HashMap<>();
            for (int i = 0; i < N; i++) {
                if (ia.containsKey(val)) ia.put(val, 2);
                else ia.put(val, 1);
            }
            int out = ia.entrySet().stream().filter(e->e.getValue()==1).findFirst().get().getKey();
            int sum = str.chars().filter(c->Character.isUpperCase(c)).map(Integer::valueOf).sum();
            String maj = str.chars().filter(c -> Character.isUpperCase(c)).collect(
                StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append
            ).toString();
            System.out.println(sum+" "+maj);
            int n = 5; String s = "Hello_world_!";
            System.err.println(Arrays.asList(n, s));
            List<String> ls = new ArrayList<>(Collections.nCopies(n, ""));
            int maxLen = (int) Math.ceil((double) s.length() / n);
            int idx = 0;
            for (char c : s.toCharArray()) {
                ls.set(idx, ls.get(idx) + c);
                idx = (idx + 1) % n;
            }
            for (int i = 0; i < n; i++) {
                String ns = ls.get(i);
                while (ns.length() < maxLen) ns += "x";
                ls.set(i, ns);
            }
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }
            LinkedList<Integer> lli = new LinkedList<>(Arrays.asList(4,0,1,7,3,5,6,2,8));
            char[][] ca = new char[3][3];
            int N = 6;
            for (int i=0; i<N; i++) {
                int v = lli.pollFirst();
                ca[v / 3][v % 3] = i%2==0 ? 'o' : 'x';
            }
            for (char[] row : ca) {
                StringJoiner sj = new StringJoiner("|");
                for (char ch : row) sj.add(ch!=0 ? ""+ch : " ");
            }
            for (i = 1; i < Integer.MAX_VALUE; i++) {
                boolean flag = true;
                for (int j=a; j<=b; j++) if (i%j!=0) flag=false;
                if (flag) break;
            }
            System.out.println(new Scanner(System.in).next().chars().filter(ch->ch=='R').count()*4);
            n = [int(e) for e in input()]
            print(math.prod(n)-sum(n))
            for (char c : n.toCharArray()) {
                int digit = Character.getNumericValue(c); //or s.charAt(i)-'0';
                mult *= digit;
                sum += digit;
            }
            int n = in.nextInt();
            String s="";
            for(;n>0;n--)s+="1";
            int out =Integer.parseInt(s, 2);
            
            a = input()
            n = a
            while len(n)>1:
                n = str(sum(int(e)**2 for e in n))
            print(f"{a} IS {'UN'*(n!='1')}HAPPY")
            
            int src = in.nextInt();
            int n = src;
            while (n != 1 && n != 4) { //n != 4 to avoid infinite loop with unhappy numbers
                int sum = 0;
                while (n > 0) {
                    int digit = n % 10;
                    sum += digit * digit;
                    n /= 10;
                }
                n = sum;
            }
            String result = (n == 1) ? " IS HAPPY" : " IS UNHAPPY";
            
            int out=0, value = in.nextInt();
            System.err.println(type+">"+value);
    
            for (int i = 0; i <= value; i++) {
                if (i%2==0 && type.equals("even")) out+=i;
                if (i%2!=0 && type.equals("odd")) out+=i;
            }
            
            String ticketChain = in.nextLine();
            List<Integer> li = new ArrayList<>();
            String[] sa = ticketChain.split("-");
            for(String s : sa) li.add(Integer.valueOf(s));
            int mid=0;
            for(int i=0; i<li.size(); i++) mid += li.get(i);
            mid /= 2;
            int i=0, a=0;
            for(i=0; i<li.size(); i++) {
                if (a + li.get(i) > mid) break;\s
                a += li.get(i);
            }
            if (a != mid) println("IMPOSSIBLE");
            else println(a+" "+i);
            
            String name1 = "jennifer";
            String name2 = "dan";
            System.err.println("n1:"+name1+" n2:"+name2);
            String voy = "AEIOUaeiou";
            char[] ca1 = name1.toCharArray();
            char[] ca2 = name2.toCharArray();
            String out = ""+ca1[0];
            for(int i=1; i< name1.length(); i++){
                out+=ca1[i];
                if(voy.indexOf(ca1[i]) != -1) break;
            }
            boolean flag = false;
            for(int i=1; i< name2.length(); i++){
                if (flag) out+=ca2[i];
                if(voy.indexOf(ca2[i]) != -1) flag=true;
            }
            String out2 = ""+ca2[0];
            for(int i=1; i< name2.length(); i++){
                out2+=ca2[i];
                if(voy.indexOf(ca2[i]) != -1) break;
            }
            flag = false;
            for(int i=1; i< name1.length(); i++){
                if (flag) out2+=ca1[i];
                if(voy.indexOf(ca1[i]) != -1) flag=true;
            }
            //Tri alphabethique
            out = (out.compareTo(out2) <= 0) ? out + " or " + out2 : out2 + " or " + out;
            
            List<Integer> li = Arrays.asList(0,255,12,1024,60,13);
            TreeMap<Integer, String> map = new TreeMap<>();
            for (Integer item : li) map.put(Integer.bitCount(item), ""+item);
            System.out.println( String.join(" ", map.values()) );
            
            int nbitems = in.nextInt(), max=0;
            Map<Integer,Integer> map = new HashMap<>();
            for (int i = 0; i < nbitems; i++) {
                int nb1 = Integer.toBinaryString(item).replace("0", "").length();
                map.put(nb1, item);
                if (nb1>max) max=nb1;
            }
            StringJoiner j = new StringJoiner(" ");
            for (int i = 0; i <= max; i++) {
                Integer out = map.get(i);
                if (out != null) j.add(""+out);
            }
            
            System.err.println(Arrays.asList(m,k));
            char[] ma = m.toCharArray();
            char[] ka = k.toCharArray();
            for(int idx=0; idx<m.length(); idx++) {
                int idxk = idx % k.length();
                boolean a = ma[idx]=='1';
                boolean b = ka[idxk]=='1';
                System.err.println(Arrays.asList(">", idx, idxk, ma[idx], ka[idxk], a, b));
                String out = ((a && !b) || (!a && b)) ? "1" : "0";
                System.out.print(out);
            }
            
            int t = 1;
            String[] sa = new Scanner(System.in).nextLine().split(" ");
            for(String s:sa) {
                int m=0;
                for(char c:s.toCharArray()) m += Integer.valueOf(""+c);
                t*=m;
            }
            
            double size = (feet * 12 + inches);
            double bmi = 703 * (weight / (size*size));
            bmi = roundToNearestTenth(bmi);
            String out="--";
            if (bmi < 18.5) out="Underweight";
            if (bmi >= 18.5 && bmi < 24.9) out="Normal";
            if (bmi >= 25.0 && bmi < 29.9) out="Overweight";
            if (bmi >= 30.0) out="Obese";
            
            String[] sa = input.split(" ");
            Stack<String> stack = new Stack<>();
            StringJoiner j = new StringJoiner(" ");
            for (String s : sa) stack.add(s);
            while (!stack.isEmpty()) j.add(stack.pop());
            
            int total=0;
            for(int i=3; i>0; i--) {
                int n = in.nextInt();
                if (n%2==0) total += n;
                else  total -= n;
            }
            
            i = gets.chomp
            l = i.tr('^a-zA-Z','').length
            n = i.tr('^0-9','').length
            puts [l,n]*' '
            for (long i=1; i<=n; i++){
                if (i%2 == 0) e+=i;
                else o+=i;
            }
            n = [float(i) for i in input().split(',')]
            print('*'*int(sum(n)//len(n)))
            
            String[] sa = s.split(",");
            for(String r : sa) sum += Float.valueOf(r);
            int nbs = (int)Math.floor(sum / sa.length);
            for(; nbs>0; nbs--) System.out.print("*");
            
            String[]a=new Scanner(System.in).nextLine().split(" ");
            for(String w:a){
                int n=0;
                for(char c:w.toCharArray())if("AEIOUaeiou".indexOf(c)!=-1)n++;
                System.out.print(n);
                if(w!=a[a.length-1])System.out.print(" ");
            }
            StringJoiner j = new StringJoiner(" ");
            for(String w:a){
                int n=0;
                for(char c:w.toCharArray())if("AEIOUaeiou".indexOf(c)!=-1)n++;
                j.add(""+n);
            }
            System.out.print(j);
            
            public static boolean isPrime(int n) {
                if (n < 2) return false;
                for (int i=2; i*i <= n; i++)
                    if (n % i == 0) return false;
                return true;
            }
            for(int i=0; i<s.length(); i++) {
                int idx = s.length() -s.length()%2 -1 -i*2;
                if (idx < 0) idx = i*2 -s.length() +s.length()%2;
                System.out.print(s.charAt(idx));
            }
            
            for (int i = 0; i < 16; i++) {
                String line = in.nextLine();
                for (char c : line.toCharArray()) {
                    c = c=='0'? ' ':'#';
                    System.out.print(c);
                }
            }
            Integer.toBinaryString(new Scanner(System.in).nextInt());
            for(char c : s.toCharArray()) if (c=='1') z++;
            for(int i=0; i<ca.length; i++)
                if (i%2 != 0 && voy.indexOf(ca[i]) != -1) out ++;
                
            Matcher m = Pattern.compile("-?\\\\d+").matcher(in);
            while(m.find()) out += Integer.valueOf(m.group());
            
            for (int i = 0; i < count; i++) {
                int n = in.nextInt();
                String chk = (n%2 != 0) ? "x":" ";
                System.out.println("["+chk+"] "+n);
            }
            for(int i=3;i<=N;i++)S+=(i%3==0||i%5==0)?i:0;
            
            n = int(input())
            for i in range(n):
                url = urlparse(input())
                print(url.netloc.split('.')[-2])
            
            for (int i = 0; i < urls.size(); i++) {
                String url = urls.get(i).url;
                URL aURL = new URL(url);
                String auth = aURL.getAuthority();
                String domain = urls.get(i).domain;
                String[] s = auth.split("\\\\.", 0);
                String out = s[s.length-2];
                boolean match = domain.equals(out);
                System.err.printf("%-15s %b \\n",out,match);
            }
            
            //Loading bridge
            char[][] bridge = new char[4][0];
            for (int i=0; i<4; i++) bridge[i] = in.next().toCharArray();
            for (char[] ca : bridge) System.err.println(String.valueOf(ca));
            // Game loop
            while (true) {
                int motorBikeSpeed = in.nextInt(); // the motorbikes' speed
                List<MotorBike> mbList = new ArrayList<>();
                //Loading bike infos
                for (int i = 0; i < nbMotorBike; i++) {
                    int x = in.nextInt(); // x coordinate of the motorbike
                    int y = in.nextInt(); // y coordinate of the motorbike
                    int Activated = in.nextInt(); // indicates whether the motorbike is activated "1" or detroyed "0"
                    if (Activated == 1) mbList.add(new MotorBike(x,y));
                }
                System.err.printf("Speed:%d MotoBike:", motorBikeSpeed);
                System.err.println(mbList.toString());
                String out = "SPEED";
                for(MotorBike mb : mbList) {
                    if (mb.x + motorBikeSpeed < bridge[0].length-1) {
                        if (motorBikeSpeed >= 2 && bridge[mb.y][mb.x + 1] == '0') {
                            out = "JUMP";
                            break;
                        }
                        if (bridge[mb.y][mb.x + motorBikeSpeed] == '0') {
                            if (mb.y > 0 && bridge[mb.y - 1][mb.x + motorBikeSpeed] == '.') {
                                out = "UP";
                                break;
                            }
                            if (mb.y < 3 && bridge[mb.y + 1][mb.x + motorBikeSpeed] == '.') {
                                out = "DOWN";
                                break;
                            }
                        }
                        if (bridge[mb.y][mb.x + motorBikeSpeed + 1] == '0') {
                            if (motorBikeSpeed > 1 && bridge[mb.y][mb.x + motorBikeSpeed - 1] == '.') {
                                out = "WAIT";
                                break;
                            }
                        }
                    }
                }
                // A single line containing one of 6 keywords: SPEED, SLOW, JUMP, WAIT, UP, DOWN.
                System.out.println(out);
            }
            
            List<Integer> queue = new ArrayList<>();
            for (int i = 0; i < nbGroup; i++) {
                int groupSize = in.nextInt();
                queue.add(groupSize);
                if (i < 10) System.err.println("gs:" + groupSize);
            }
            System.err.println();
            int first=0, testlimit=0, index=0;\s
            long out=0;
            while (maxUse > 0) {
                testlimit = limit;
                for(int i=0; i < nbGroup; i++) {
                    first = queue.get(index);
                    if (first > testlimit) {
                        break;
                    }
                    testlimit -= first;
                    index++;
                    if (index >= nbGroup) index=0;
                }
                System.err.printf("index:%3d testlimit:%d \\n", index, testlimit);
                out += limit-testlimit;
                maxUse --;
            }
            
            static double calcDist(Stop a, Stop b) {
                Double x = (b.longitude - a.longitude) * Math.cos((a.latitude + b.latitude) / 2);
                Double y = (b.latitude - a.latitude);
                return Math.sqrt(x*x + y*y) * 6371;
            }
            while (!oneWay.isEmpty()) System.out.println(stopMap.get(oneWay.pop().start).lngName);
            Stack<Route> oneWay = new Stack<>();
            Stop cur = endStop;
            while (cur != startstop) {
                Stop curr = cur;
                Route rte = routesMap.get(curr.dad.name)
                        .stream().filter(p -> p.end.equals(curr.name)).findFirst().get();
                oneWay.push(rte);
                cur = cur.dad;
            }
            //One way to output test
            Double oneWayDist = 0.0;
            for(Route r : oneWay) oneWayDist += r.dist;
            System.err.printf("dist:%.2f \\n", oneWayDist);
            
            //Reading input
            for (int i = 0; i < nbInputLine; i++) {
                int cur = in.nextInt(), l = in.nextInt(), r = in.nextInt();
                nodeList.add(new Node(cur, l, r));
                System.err.printf("P:%d L:%d R:%d \\n",cur,l,r);
            }
            
            int a,b,i,h,l,c;
            a=Z.nextInt();b=Z.nextInt();Z.nextLine();char[]I=Z.nextLine().toUpperCase().toCharArray();
            for (h=0;h<b;h++){
                char[]M=Z.nextLine().toCharArray();
                for(i=0;i<I.length;i++) {
                    c=I[i]-65;
                    c=c<0||c>26?26:c;
                    for(l=c*a;l<(c+1)*a;l++)System.out.print(M[l]);
                }
                System.out.println();
            }}}
            if (a<0) {
                a++;
            } else if (a==b) {
                a--;
            }
            
            const [fact, dim] = [parseInt(readline()), parseInt(readline())];
            for (let i = 0; i < dim; i++) readline().split(' ').forEach(p => Array(fact).fill().forEach(() => console.log(Array(fact).fill(p).join(' '))));
            
            for (let i = 0; i < dim; i++) {
                const row = readline().split(' ').map(Number);
                const expandedRow = row.flatMap(value => Array(fact).fill(value));
                Array(fact).fill().forEach(() => console.log(expandedRow.join(' ')));
            }
            
            for (let i = 0; i < dim; i++) {
                var inputs = readline().split(' ');
                liste = []
                for (let j = 0; j < dim; j++) {
                    const p = parseInt(inputs[j]);
                    for (let k = 0; k < fact; k++) {  // liste.push(...Array(fact).fill(p));
                        liste.push(p);
                    }
                }
                for (let k = 0; k < fact; k++) {
                    console.log(liste.join(' '));
                }
            }
            r=readline;r();print(r().split(" ").sort((a,b)=>Math.abs(a)-Math.abs(b)||b-a)[0]||0);
            
            d = gets.to_i
            puts d.odd? ? ((d - 32) * 5 / 9.0).round : ((9 / 5.0 * d) + 32).round
            d=gets.to_i;puts d.odd??"#{((d-32)*5/9.0).round}":"#{(9/5.0*d+32).round}"
            d=gets.to_i;puts d.odd?? ((d-32)*5/9.0).round: (9/5.0*d+32).round
            
            String dice = "4d6";
            int[] ia = Arrays.stream(dice.split("d", 0)).mapToInt(Integer::valueOf).toArray();
            int average = (ia[0] + ia[0]*ia[1])/2;
            System.out.println("Range "+ia[0]+"-"+ia[0]*ia[1]+",average "+average); //Range 4-24,average 14
            
            // ASCII ART COMPACT
            import java.util.*;class Solution{public static void main(String args[]){Scanner Z=new Scanner(System.in);
            int a,b,i,h,l,c;a=Z.nextInt();b=Z.nextInt();Z.nextLine();char[]I=Z.nextLine().toUpperCase().toCharArray();
            for (h=0;h<b;h++){
                char[]M=Z.nextLine().toCharArray();
                for(i=0;i<I.length;i++) {
                    c=I[i]-65;c=c<0||c>26?26:c;for(l=c*a;l<(c+1)*a;l++)System.out.print(M[l]);
                }
                System.out.println();
            }}}
            
            String input = in.nextLine().toLowerCase();
            char[] ca = input.toCharArray();
            String out ="";
            System.err.println("input:"+input);
            for(int i=0; i<ca.length; i++) {
                int val = ((int)ca[i] - (i+1));
                val = val < 97 ? val + 26 : val;
                char c = (char) val;
                System.err.println("c:"+ca[i]+" c:"+c+" i+1:"+i+1+" val:"+val);
                out += c;
            }
            
            char[] ca = s.toCharArray();
            char[] ca2 = new char[2];
            ca2[0] = ca[0];
            for(int i=0; i<ca.length; i++) {
                if (ca[i] != ca2[0]) ca2[1] = ca[i];
            }
            String out ="";
            for(int i=0; i<ca.length; i++) {
                char c = ca[i] == ca2[0] ? ca2[1] : ca2[0];
                out += c;
            }
            
            String[] sa = input.split(" ");
            //System.err.println(Arrays.asList(sa));
            Map<Character, Integer> firstMap = new HashMap<>();
            Map<Character, Integer> allMap = new HashMap<>();
            Map<String, Integer> twoMap = new HashMap<>();
            Map<String, Integer> thirdMap = new HashMap<>();
            Map<String, Integer> fourMap = new HashMap<>();
            int wordCount=0;
            for(String s : sa) {
                s = s.replaceAll("\\\\s", "").toLowerCase();
                if (!s.isEmpty()) {
                    wordCount++;
                    //System.err.println(s+", ");
                    for (int i=0; i<s.length(); i++) {
                        char car = s.charAt(i);
                        if (car == '\\n' || car == '\\n') car = '§';
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
            System.out.println();
            System.out.print("## first letter: ");
            System.out.println(firstEnt.toString().substring(1));
            System.out.print("##  all letters: ");
            System.out.println(allEnt.toString().substring(1));
            System.out.print("## 2 letter seq: ");
            int count = 0; for (Map.Entry<String, Integer> entry : twoEnt) {
                System.out.print(entry+" ");
                if (entry.getValue() < 20) break;
            }
            System.out.println();
            System.out.print("## 3 letter seq: ");
            count = 0; for (Map.Entry<String, Integer> entry : thirdEnt) {
                System.out.print(entry+" ");
                if (entry.getValue() < 15) break;
            }
            System.out.println();
            System.out.print("## 4 letter seq: ");
            count = 0; for (Map.Entry<String, Integer> entry : fourEnt) {
                System.out.print(entry+" ");
                if (entry.getValue() < 13) break;
            }
            System.out.println();
            System.out.println("## wordCount: " + wordCount);
            System.out.println();
        """;
        String[] sa = input.split(" ");
        //System.err.println(Arrays.asList(sa));

        Map<Character, Integer> firstMap = new HashMap<>();
        Map<Character, Integer> allMap = new HashMap<>();
        Map<String, Integer> twoMap = new HashMap<>();
        Map<String, Integer> thirdMap = new HashMap<>();
        Map<String, Integer> fourMap = new HashMap<>();
        int wordCount=0;

        for(String s : sa) {
            s = s.replaceAll("\\s", "").toLowerCase();
            if (!s.isEmpty()) {
                wordCount++;
                //System.err.println(s+", ");
                for (int i=0; i<s.length(); i++) {
                    char car = s.charAt(i);
                    if (car == '\n' || car == '\n') car = '§';

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
        
        System.out.println();
        System.out.print("## first letter: ");
        System.out.println(firstEnt.toString().substring(1));
        System.out.print("##  all letters: ");
        System.out.println(allEnt.toString().substring(1));
        System.out.print("## 2 letter seq: ");
        int count = 0; for (Map.Entry<String, Integer> entry : twoEnt) {
            System.out.print(entry+" ");
            if (entry.getValue() < 20) break;
        }
        System.out.println();
        System.out.print("## 3 letter seq: ");
        count = 0; for (Map.Entry<String, Integer> entry : thirdEnt) {
            System.out.print(entry+" ");
            if (entry.getValue() < 15) break;
        }
        System.out.println();
        System.out.print("## 4 letter seq: ");
        count = 0; for (Map.Entry<String, Integer> entry : fourEnt) {
            System.out.print(entry+" ");
            if (entry.getValue() < 13) break;
        }
        System.out.println();
        System.out.println("## wordCount: " + wordCount);
        System.out.println();
    }  
}
