package Programmers.LV2;

class Q70129 {
    static int zeroCount;

    public int[] solution(String s) {
        int[] answer = {};
        int conversionCount = 0;

        while(true) {
            s = binaryConversion(s);
            conversionCount++;
            if(s.equals("1")) break;
        }

        answer = new int[] {conversionCount, zeroCount};

        return answer;
    }

    public String binaryConversion(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '0') zeroCount++;
        }

        s = s.replace("0", "");

        return Integer.toString(s.length(), 2);
    }
}