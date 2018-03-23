/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("/home/lzx/cs61b/cs61b_java/proj1b/words.txt");
        CharacterComparator cc = new offByOne();
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && Palindrome.isPalindrome(word,cc)) {
                System.out.println(word);
            }
        }
    }
} 
