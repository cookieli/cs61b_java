public class Palindrome<Item> {
    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new ArrayDequeSolution<>();
        for(int i = 0; i < word.length(); i++) {
            d.addFirst(word.charAt(i));
        }
        return d;
    }
    public static boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        int size = d.size();
        for(int i = 0, j = size -1; i < size; i++, j--){
            if(d.get(i) != d.get(j))  return false;
            if(i > j) break;
        }
        return true;
    }
    public static boolean isPalindrome(String word, CharacterComparator cc) {
        if(word.length() <= 1) return true;
        if(!cc.equalChars(word.charAt(0),word.charAt(word.length() - 1))) return false;
        return isPalindrome(word.substring(1, word.length() - 1), cc);
    }
    public static void main(String[] args) {
        Deque<Character> d = Palindrome.wordToDeque("noon");
        d.printDeque();
        CharacterComparator cc = new offByOne();
        System.out.println(Palindrome.isPalindrome("nonm",cc));
    }
}