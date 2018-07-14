package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] opening;
    private int size;
    private int opensites;

    public Percolation(int N){
        if(N < 0)
            throw new IllegalArgumentException("N < 0");
        uf = new WeightedQuickUnionUF(N*N);
        size = N;
        opening = new boolean[N * N ];
        for(int i = 0; i < N*N - 1; i++){
                opening[i] = false;
        }
        opensites = 0;
    }
    private int transform(int row, int column){
        if(row < 0 || column < 0)
            throw new IllegalArgumentException("row or column number is little than zero");
        return row*size + column;
    }

    private List<Integer> around(int row, int col){
        List<Integer> nums = new ArrayList<>();
        if(row - 1 >= 0 )
            nums.add(transform(row -1, col));
        if(row + 1 < size)
            nums.add(transform(row+1, col));
        if(col + 1 < size)
            nums.add(transform(row, col + 1));
        if(col - 1 >= 0)
            nums.add(transform(row, col -1));
        return nums;
    }

    public void open(int row, int col){
        if(row < 0 || col < 0)
            throw new IllegalArgumentException("row or column number is little than zero");
        opening[transform(row,col)] = true;
        for(Integer num: around(row, col)){
            if(opening[num])
                uf.union(num, transform(row,col));
        }
        opensites++;
    }
    public boolean isOpen(int row, int col){
        if(row < 0 || col < 0)
            throw new IllegalArgumentException("row or column number is little than zero");
        return opening[transform(row,col)];
    }
    public boolean isFull(int row, int col){
        if(transform(row, col) < size && transform(row,col) >= 0)
            return isOpen(row, col);
        for(int i = 0 ; i < size; i++){
            if(uf.connected(i, transform(row, col)))
                return true;
        }
        return false;
    }

    public int numberOfOpenSites(){
        return opensites;
    }
    public boolean percolates(){
        for(int i = 0; i < size ;i++){
            if(isFull(size - 1, i))
                return  true;
        }
        return false;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        System.out.println(p.isOpen(0,0));
        p.open(0,0);
        System.out.println(p.isOpen(0,0));
        System.out.println(p.isFull(0,0));
        p.open(1,0);
        System.out.println(p.isFull(1,0));
    }
}                       
