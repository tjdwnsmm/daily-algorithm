package Programmers.LV3;

class Q42898 {
    public int solution(int m, int n, int[][] puddles) {
        int answer = 0;

        int[][] road = new int[n+1][m+1];
        road[1][1] = 1;

        for(int i = 0; i < puddles.length; i++) {
            road[puddles[i][1]][puddles[i][0]] = -1;
        }

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= m; j++) {
                if(i == 1 && j == 1) continue;
                if(road[i][j] == -1) {
                    road[i][j] = 0;
                    continue;
                }
                road[i][j] = (road[i-1][j] + road[i][j-1]) % 1000000007;
            }
        }

        return road[n][m];
    }
}