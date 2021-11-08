import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;


/**
 * LeetCode 567. Permutation in String
 * https://leetcode.com/problems/permutation-in-string/
 */
public class PermutationInString {


    /**
     * Given two strings s1 and s2, 
     * return true if s2 contains a permutation of s1, 
     * or false otherwise.
     * 
     * Using a HashMap window.
     * 
     * 106 / 106 test cases passed.
     * Status: Accepted
     * Runtime: 14 ms
     * Memory Usage: 40.1 MB
     */
    public static boolean checkInclusion0(String s1, String s2) {
        
        // **** sanity check(s) ****
        if (s1.length() > s2.length()) return false;

        // **** initialization ****
        HashMap<Character, Integer> freq    = new HashMap<>(26);
        HashMap<Character, Integer> win     = new HashMap<>(26);
        int len1                            = s1.length();
        int len2                            = s2.length();

        // **** populate with lowercase characters and counts of zero ****
        for (int i = 0; i < 26; i++) {
            freq.putIfAbsent(Character.valueOf((char)('a' + i)), 0);
            win.putIfAbsent(Character.valueOf((char)('a' + i)), 0);
        }

        // **** populate freq ****
        for (char ch : s1.toCharArray()) {
            Integer val = freq.get(ch);
            freq.put(ch, ++val);
        }

        // ???? ????
        System.out.println("<<< freq: " + freq.toString());
    
        // **** initialize win ****
        for (int i = 0; i < len1; i++) {
            Integer val = win.get(s2.charAt(i));
            win.put(s2.charAt(i), ++val);
        }

        // ???? ????
        System.out.println("<<<  win: " + win.toString());

        // **** move window right comparing hashmap contents ****
        for (int i = 0; i <= len2 - len1; i++) {

            // **** permutation found ****
            if (freq.equals(win)) return true;

            // **** update window (as needed) ****
            if (i < len2 - len1) {

                // **** remove character from win ****
                Integer val = win.get(s2.charAt(i));
                win.put(s2.charAt(i), --val);

                // **** add character to win ****
                val = win.get(s2.charAt(i + len1));
                win.put(s2.charAt(i + len1), ++val);
    

                // ???? ????
                System.out.println("<<<  win: " + win.toString());
            }
        }

        // **** permutation NOT found ****
        return false;
    }


    /**
     * Given two strings s1 and s2, 
     * return true if s2 contains a permutation of s1, 
     * or false otherwise.
     * 
     * Using an int[] window.
     * 
     * 106 / 106 test cases passed.
     * Status: Accepted
     * Runtime: 3 ms
     * Memory Usage: 39.2 MB
     * 
     * Runtime: 3 ms, faster than 98.79% of Java online submissions.
     * Memory Usage: 39.2 MB, less than 63.11% of Java online submissions.
     */
    public static boolean checkInclusion1(String s1, String s2) {

        // **** initialization ****
        int len1    = s1.length();
        int len2    = s2.length();
        int len     = len2 - len1;
        int[] freq  = new int[26];
        int[] win   = new int[26];

        // **** sanity check(s) ****
        if (len1 > len2) return false;

        // **** populate freq int[] ****
        for (char ch : s1.toCharArray())
            freq[ch - 'a']++;

        // ???? ????
        System.out.println("<<< freq: " + Arrays.toString(freq));

        // **** initialize window int[] - O(m) ****
        for (int i = 0; i < len1; i++)
            win[s2.charAt(i) - 'a']++;

        // ????? ????
        System.out.println("<<<  win: " + Arrays.toString(win));

        // **** check window contents against freq arrays - O(n) ****
        for (int i = 0; i <= len; i++) {

            // ***** compare win and freq arrays - O(m) ****
            int diff = Arrays.compare(win, freq);

            // **** permutation found ****
            if (diff == 0) return true;    

            // **** move window right (if needed) ****
            if (i < len) {
                win[s2.charAt(i) - 'a']--;
                win[s2.charAt(i + len1) - 'a']++;
            
                // ????? ????
                System.out.println("<<<  win: " + Arrays.toString(win));
            }
        }

        // **** permutation NOT found ****
        return false;
    }


    /**
     * Given two strings s1 and s2, 
     * return true if s2 contains a permutation of s1, 
     * or false otherwise.
     * 
     * Using an int[] window plus auxiliary function.
     * 
     * 106 / 106 test cases passed.
     * Status: Accepted
     * Runtime: 3 ms
     * Memory Usage: 39.1 MB
     * 
     * Runtime: 3 ms, faster than 98.79% of Java online submissions.
     * Memory Usage: 39.1 MB, less than 77.36% of Java online submissions.
     */
    public static boolean checkInclusion(String s1, String s2) {

        // **** initialization ****
        int len1    = s1.length();
        int len2    = s2.length();
        int len     = len2 - len1;
        int[] freq  = new int[26];
        int[] win   = new int[26];

        // **** sanity check(s) ****
        if (len1 > len2) return false;

        // **** populate freq int[] ****
        for (char ch : s1.toCharArray())
            freq[ch - 'a']++;

        // ???? ????
        System.out.println("<<< freq: " + Arrays.toString(freq));

        // **** initialize window int[] - O(m) ****
        for (int i = 0; i < len1; i++)
            win[s2.charAt(i) - 'a']++;

        // ????? ????
        System.out.println("<<<  win: " + Arrays.toString(win));

        // **** check window contents against freq arrays - O(n) ****
        for (int i = 0; i <= len; i++) {

            // **** check if arrays are equal ****
            if (areEqual(win, freq)) return true;

            // **** move window right (if needed) ****
            if (i < len) {
                win[s2.charAt(i) - 'a']--;
                win[s2.charAt(i + len1) - 'a']++;
            
                // ????? ????
                System.out.println("<<<  win: " + Arrays.toString(win));
            }
        }

        // **** permutation NOT found ****
        return false;
    }


    /**
     * Auxiliary function.
     */
    static boolean areEqual(int[] arr1, int[] arr2) {

        // // **** sanity check(s) ****
        // int len1 = arr1.length;
        // int len2 = arr2.length;
        // if (len1 != len2) return false;

        // **** compare array contents ****
        for (int i = 0; i < arr1.length; i++)
            if (arr1[i] != arr2[i]) return false;

        // **** contents of arrays are equal ****
        return true;
    }


    /**
     * Test scaffold.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read `s1` ****
        String s1 = br.readLine().trim();

        // **** read `s2` ****
        String s2 = br.readLine().trim();

        // **** close buffered reader ****
        br.close();

        // ???? ????
        System.out.println("main <<< s1 ==>" + s1 + "<==");
        System.out.println("main <<< s2 ==>" + s2 + "<==");

        // **** call function of interest and display output ****
        System.out.println("main <<< output: " + checkInclusion(s1, s2));
    }

}