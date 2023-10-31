package Programmers.LV2;
import java.util.*;

class Q159993 {
    static char[][] map;
    static int answer = 0;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public int solution(String[] maps) {
        map = new char[maps.length][maps[0].length()];

        int[] start = new int[2];
        int[] lever = new int[2];
        int[] exit = new int[2];

        for(int i = 0; i < maps.length; i++) {
            for(int j = 0; j < maps[i].length(); j++) {
                map[i][j] = maps[i].charAt(j);
                if(map[i][j] == 'S') start = new int[] {i, j};
                if(map[i][j] == 'L') lever = new int[] {i, j};
                if(map[i][j] == 'E') exit = new int[] {i, j};
            }
        }

        if(!bfs(start, lever)) return -1;
        if(!bfs(lever, exit)) return -1;

        return answer;
    }

    public static boolean bfs(int[] start, int[] end) {
        boolean[][] visited = new boolean[map.length][map[0].length];

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {start[0], start[1], 0});
        visited[start[0]][start[1]] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i = 0; i < 4; i++) {
                int nr = cur[0]+dr[i];
                int nc = cur[1]+dc[i];

                if(nr == end[0] && nc == end[1]) {
                    answer += cur[2]+1;
                    return true;
                }
                if(!check(nr, nc)) continue;
                if(visited[nr][nc]) continue;
                if(map[nr][nc] == 'X') continue;

                q.add(new int[] {nr, nc, cur[2]+1});
                visited[nr][nc] = true;
            }
        }

        return false;
    }

    public static boolean check(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[0].length;
    }
}
