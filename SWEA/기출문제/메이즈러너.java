package SWEA.기출문제;

import java.util.*;
import java.io.*;

public class 메이즈러너 {
    static int[][] map;
    static int[][] playerMap;
    static int N, M, K, exitR, exitC, result, playerCount, exitCount;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        playerMap = new int[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;

            playerMap[r][c]++;
            playerCount++;
        }

        st = new StringTokenizer(br.readLine());

        exitR = Integer.parseInt(st.nextToken())-1;
        exitC = Integer.parseInt(st.nextToken())-1;

        map[exitR][exitC] = -1;
        ArrayList<int[]> players = new ArrayList<>();

        for(int i = 0; i < K; i++) {
            if(playerCount == exitCount) break;
            players.clear();
            for(int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    if (playerMap[j][k] > 0) {
                        players.add(new int[] {j, k});
                    }
                }
            }

            int[][] playerMovedMap = new int[N][N];

            for(int[] player : players) {
                int j = player[0];
                int k = player[1];

                ArrayList<Integer> possibleDirections = movePlayer(j, k);
                int count = 0;

                for(int p = 0; p < possibleDirections.size(); p++) {
                    int direction = possibleDirections.get(p);
                    if(map[j+dr[direction]][k+dc[direction]] > 0) continue;
                    count++;
                }

                if(count > 1) {
                    for(int p = 0; p < possibleDirections.size(); p++) {
                        int direction = possibleDirections.get(p);

                        if(direction % 2 != 0) continue;

                        playerMovedMap[j+dr[direction]][k+dc[direction]] += playerMap[j][k];
                        result += playerMap[j][k];
                    }
                }

                if(count == 1) {
                    for(int p = 0; p < possibleDirections.size(); p++) {
                        int direction = possibleDirections.get(p);
                        if(map[j+dr[direction]][k+dc[direction]] > 0) continue;

                        playerMovedMap[j+dr[direction]][k+dc[direction]] += playerMap[j][k];
                        result += playerMap[j][k];
                    }
                }

                if(count == 0) playerMovedMap[j][k] = playerMap[j][k]+playerMovedMap[j][k];
            }

            for(int j = 0; j < N; j++) {
                for(int k = 0; k < N; k++) {
                    if(map[j][k] == -1) {
                        exitCount += playerMovedMap[j][k];
                        playerMap[j][k] = 0;
                        continue;
                    }
                    playerMap[j][k] = playerMovedMap[j][k];
                }
            }

            int[] nearestPlayer = getNearestPlayer(exitR,  exitC);
            int dist = Math.max(Math.abs(exitR - nearestPlayer[0]), Math.abs(exitC - nearestPlayer[1]))+1;

            int[] smallestSection;

            for(int l = dist; l < N; l++) {
                smallestSection = getSmallestSection(dist);
                if (smallestSection[0] > -1) {
                    turn(smallestSection[0], smallestSection[1], l);
                    break;
                }
            }
        }

        System.out.println(result);
        System.out.println((exitR+1) + " " + (exitC+1));
    }

    private static int[] getSmallestSection(int n) {
        for (int i = 0; i < N-n+1; i++) {
            for(int j = 0; j < N-n+1; j++) {
                boolean hasExit = false;
                boolean hasPlayer = false;

                for(int k = 0; k < n; k++) {
                    for(int l = 0; l < n; l++) {
                        if(playerMap[i+k][j+l] > 0) hasPlayer = true;
                        if(map[i+k][j+l] == -1) hasExit = true;
                    }
                }

                if(hasPlayer && hasExit) return new int[] {i, j};
            }
        }

        return new int[] {-1, -1};
    }

    private static ArrayList<Integer> movePlayer(int r, int c) {
        int minDistance = Math.abs(exitR-r) + Math.abs(exitC-c);
        ArrayList<Integer> possibleDirections = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
            int nr = r+dr[i];
            int nc = c+dc[i];

            if(!check(nr, nc)) continue;

            int exitDistance = Math.abs(exitR-nr) + Math.abs(exitC-nc);

            if(minDistance < exitDistance) continue;
            if(minDistance > exitDistance) {
                possibleDirections.clear();
                possibleDirections.add(i);
                minDistance = exitDistance;
                continue;
            }
            if(minDistance == exitDistance) possibleDirections.add(i);
        }

        return possibleDirections;
    }

    private static void turn(int r, int c, int n) {
        for(int i = 0; i < n/2; i++) {
            for(int j = 0; j < n; j++) {
                int tmp = map[r+i][c+j];
                map[r+i][c+j] = map[r+(n-1-i)][c+j];
                map[r+(n-1-i)][c+j] = tmp;

                tmp = playerMap[r+i][c+j];
                playerMap[r+i][c+j] = playerMap[r+(n-1-i)][c+j];
                playerMap[r+(n-1-i)][c+j] = tmp;
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = i; j < n; j++) {
                int tmp = map[r+i][c+j];
                map[r+i][c+j] = map[r+j][c+i];
                map[r+j][c+i] = tmp;

                tmp = playerMap[r+i][c+j];
                playerMap[r+i][c+j] = playerMap[r+j][c+i];
                playerMap[r+j][c+i] = tmp;

                if(map[r+i][c+j] == -1) {
                    exitR = r+i;
                    exitC = c+j;
                }

                if(map[r+j][c+i] == -1) {
                    exitR = r+j;
                    exitC = c+i;
                }
            }
        }

        for(int i = r; i < r+n; i++) {
            for(int j = c; j < c+n; j++) {
                if(map[i][j] > 0) map[i][j]--;
            }
        }
    }

    private static int[] getNearestPlayer(int r, int c) {
        boolean[][] visited = new boolean[N][N];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));

        pq.add(new int[] {r, c, Math.max(Math.abs(exitR-r), Math.abs(exitC-c))});
        visited[r][c] = true;

        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            if(playerMap[cur[0]][cur[1]] > 0) return new int[] {cur[0], cur[1]};

            for(int i = 0; i < 4; i++) {
                int nr = cur[0]+dr[i];
                int nc = cur[1]+dc[i];

                if(!check(nr, nc)) continue;
                if(visited[nr][nc]) continue;

                pq.add(new int[] {nr, nc, Math.max(Math.abs(exitR-nr), Math.abs(exitC-nc))});
                visited[nr][nc] = true;
            }
        }

        return new int[] {-1, -1};
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}