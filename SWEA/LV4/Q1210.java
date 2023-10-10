package SWEA.LV4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Q1210 {
    static int result;
    static boolean isFound;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = {0, 0, -1};
    static int[] dc = {1, -1 , 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for(int k = 0; k < 10; k++) {
            map = new int[100][100];
            visited = new boolean[100][100];
            isFound = false;

            int n = Integer.parseInt(br.readLine());

            int finishR = -1;
            int finishC = -1;

            for (int i = 0; i < 100; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int j = 0; j < 100; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());

                    if(map[i][j] == 2) {
                        finishR = i;
                        finishC = j;
                    }
                }
            }

            dfs(finishR, finishC);
            System.out.println("#" + n + " " + result);
        }
    }

    private static void dfs(int r, int c) {
        visited[r][c] = true;

        if(r == 0) {
            result = c;
            isFound = true;
            return;
        }

        for(int i = 0; i < 3; i++) {
            int nr = r+dr[i];
            int nc = c+dc[i];

            if(isFound) return;
            if(!check(nr, nc)) continue;
            if(visited[nr][nc]) continue;
            if(map[nr][nc] == 0) continue;

            dfs(nr, nc);
        }
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < 100 && c >= 0 && c < 100;
    }
}
