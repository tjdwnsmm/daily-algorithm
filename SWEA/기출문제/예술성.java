package SWEA.기출문제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class 예술성 {
    static int N, idx, result;
    static int[][] map, numberingMap, borderLine;
    static boolean[][] visited, selected;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static Map<Integer, Integer> sectionValue = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        for(int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int p = 0; p < 4; p++) {
            numberingMap = new int[N][N];
            visited = new boolean[N][N];
            idx = 1;

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(visited[i][j]) continue;
                    countSection(i, j);
                    idx++;
                }
            }

            selected = new boolean[sectionValue.size()+1][sectionValue.size()+1];
            borderLine = new int[sectionValue.size()+1][sectionValue.size()+1];
            visited = new boolean[N][N];

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(visited[i][j]) continue;
                    countBorderLine(i, j);
                }
            }

            visited = new boolean[N][N];

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(visited[i][j]) continue;
                    checkAdjacent(i,j);
                }
            }

            turnCross();
            turnSquare(0, 0, N/2);
            turnSquare(N/2+1, 0, N/2);
            turnSquare(0, N/2+1, N/2);
            turnSquare(N/2+1, N/2+1, N/2);
        }

        System.out.println(result);
    }

    private static void countBorderLine(int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {r, c});
        visited[r][c] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i = 0; i < 4; i++) {
                int nr = cur[0]+dr[i];
                int nc = cur[1]+dc[i];

                if(!check(nr, nc)) continue;
                if(visited[nr][nc]) continue;
                if(numberingMap[nr][nc] != numberingMap[cur[0]][cur[1]]) {
                    borderLine[numberingMap[nr][nc]][numberingMap[cur[0]][cur[1]]]++;
                    borderLine[numberingMap[cur[0]][cur[1]]][numberingMap[nr][nc]]++;
                    continue;
                }

                q.add(new int[] {nr, nc});
                visited[nr][nc] = true;
            }
        }
    }

    private static void checkAdjacent(int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {r, c});
        visited[r][c] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i = 0; i < 4; i++) {
                int nr = cur[0]+dr[i];
                int nc = cur[1]+dc[i];

                if(!check(nr, nc)) continue;
                if(visited[nr][nc]) continue;
                if(selected[numberingMap[nr][nc]][numberingMap[cur[0]][cur[1]]]) continue;
                if(numberingMap[nr][nc] != numberingMap[cur[0]][cur[1]]) {
                    int tmp = (sectionValue.get(numberingMap[nr][nc]) + sectionValue.get(numberingMap[cur[0]][cur[1]]))
                            * map[nr][nc] * map[cur[0]][cur[1]] * borderLine[numberingMap[nr][nc]][numberingMap[cur[0]][cur[1]]];

                    result += tmp;

                    selected[numberingMap[nr][nc]][numberingMap[cur[0]][cur[1]]] = true;
                    selected[numberingMap[cur[0]][cur[1]]][numberingMap[nr][nc]] = true;
                    continue;
                }

                q.add(new int[] {nr, nc});
                visited[nr][nc] = true;
            }
        }
    }

    private static void countSection(int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {r, c});
        visited[r][c] = true;
        int count = 0;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            numberingMap[cur[0]][cur[1]] = idx;
            count++;

            for(int i = 0; i < 4; i++) {
                int nr = cur[0]+dr[i];
                int nc = cur[1]+dc[i];

                if(!check(nr, nc)) continue;
                if(visited[nr][nc]) continue;
                if(map[nr][nc] != map[cur[0]][cur[1]]) continue;

                q.add(new int[] {nr, nc});
                visited[nr][nc] = true;
            }
        }

        sectionValue.put(idx, count);
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    private static void turnSquare(int r, int c, int n) {
        for(int i = 0; i < n/2; i++) {
            for(int j = 0; j < n; j++) {
                int tmp = map[r+i][c+j];
                map[r+i][c+j] = map[r+(n-1-i)][c+j];
                map[r+(n-1-i)][c+j] = tmp;
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = i+1; j < n; j++) {
                int tmp = map[r+i][c+j];
                map[r+i][c+j] = map[r+j][c+i];
                map[r+j][c+i] = tmp;
            }
        }
    }

    private static void turnCross() {
        for(int j = 1; j <= N/2; j++) {
            int tmp = map[N/2][N/2-j];
            map[N/2][N/2-j] = map[N/2-j][N/2];

            int tmp2 = map[N/2+j][N/2];
            map[N/2+j][N/2] = tmp;

            tmp = map[N/2][N/2+j];
            map[N/2][N/2+j] = tmp2;

            map[N/2-j][N/2] = tmp;
        }
    }
}
