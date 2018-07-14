package hw3.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Board implements WorldState{
    private int[][] tiles;
    private int[][] goal;
    private int N;

    public Board(int[][] tiles){
        N =  tiles.length;
        this.tiles = new int[N][N];
        goal = new int[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                this.tiles[i][j] = tiles[i][j];
                this.goal[i][j] = i * N + j + 1;
            }
        }
        this.goal[N - 1][N - 1] = 0;
    }

    public int tileAt(int i, int j){
        if(i < 0 || i >= N || j < 0 || j >= N)
            throw new IndexOutOfBoundsException("i or j ");
        return tiles[i][j];
    }

    public int size(){
        return N;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug= -1;
        int zug = -1;
        for(int rug = 0; rug < hug; rug++){
            for(int tug = 0; tug < hug; tug++){
                if(tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }


    public int hamming(){
        int count = 0;
        for(int i = 0; i < N - 1; i++){
            for(int j = 0; j < N - 1; j++){
                if(goal[i][j] != tileAt(i,j))
                    count++;
            }
        }
        return count;
    }

    public int manhattan(){
        int manhattanCount = 0;
        for(int i = 0; i < N ; i++){
            for(int j = 0; j < N ; j++){
                int currentTile = tiles[i][j];
                if(currentTile == 0)
                    continue;
                int real_i = (currentTile -1) / N;
                int real_j = (currentTile - 1) % N;
                manhattanCount = Math.abs(real_i - i) + Math.abs(real_j - j);
            }
        }
        return manhattanCount;
    }

    @Override
    public int estimatedDistanceToGoal(){
        return manhattan();
    }

    public boolean isGoal(){
        return estimatedDistanceToGoal() == 0;
    }

    public boolean equals(Object y){
        if(this == y)
            return true;
        if(y == null || getClass() != y.getClass())
            return false;
        Board y1 = (Board)y;
        if(Arrays.deepEquals(tiles, y1.tiles))
            return true;
        return false;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
