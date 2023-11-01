package Programmers.LV2;

import java.util.*;

class Q169199 {
    static char[][] map;
    static int answer = -1;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public int solution(String[] board) {
        map = new char[board.length][board[0].length()];

        int[] start = new int[2];

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length(); j++) {
                map[i][j] = board[i].charAt(j);
                if(map[i][j] == 'R') start = new int[] {i, j};
            }
        }

        bfs(start);

        return answer;
    }

    public static void bfs(int[] start) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {start[0], start[1], 0});
        boolean[][] visited = new boolean[map.length][map[0].length];
        visited[start[0]][start[1]] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i = 0; i < 4; i++) {
                int[] nextCoord = move(cur[0], cur[1], i);
                int nr = nextCoord[0];
                int nc = nextCoord[1];

                if(visited[nr][nc]) continue;
                if(map[nr][nc] == 'G') {
                    answer = cur[2]+1;
                    return;
                }

                q.add(new int[] {nr, nc, cur[2]+1});
                visited[nr][nc] = true;
            }
        }
    }

    public static int[] move(int r, int c, int d) {
        while(true) {
            r += dr[d];
            c += dc[d];

            if(!check(r, c) || map[r][c] == 'D') {
                return new int[] {r-dr[d], c-dc[d]};
            }
        }
    }

    public static boolean check(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[0].length;
    }
}