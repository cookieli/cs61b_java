
public class OffByN implements CharacterComparator {
    private int step;
    public OffByN(int N) {
        this.step = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        if(Math.abs(x - y) == step) return true;
        return false;
    }

    public static void main(String[] args) {
        OffByN offby5 = new OffByN(5);
        System.out.print(offby5.equalChars('a','f'));
    }
}
