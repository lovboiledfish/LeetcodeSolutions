package solutions;

import java.util.*;

/**
 * Created by PPlovboiledfish on 1/30/16.
 */
public class RemoveInvalidParentheses {
    public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();
        removeLeft(s, ans, new char[s.length()], 0, 0, 0);
        return ans;
    }

    private static void removeLeft(String s, List<String> ans, char[] buf, int pos, int start, int last) {
        int p = start, b = 0;
        for (int i = start; i < s.length(); ++i) {
            if (s.charAt(i) == '(') {
                ++b;
            } else if (s.charAt(i) == ')') {
                --b;
            }
            if (b <= 0) {
                p = i + 1;
            }
            if (b < 0) {
                for (int j = last; j <= i; ++j) {
                    if (s.charAt(j) == ')' && (j == last || s.charAt(j - 1) != ')')) {
                        s.getChars(last, j, buf, pos);
                        removeLeft(s, ans, buf, pos + j - last, i + 1, j + 1);
                    }
                }
                return;
            }
        }
        s.getChars(last, p, buf, pos);
        int rem = b + (last - pos); // total number of parentheses to remove, including already removed
        removeRight(s, ans, buf, buf.length - rem, buf.length - rem, s.length() - 1, s.length() - 1, p);
    }

    private static void removeRight(String s, List<String> ans, char[] buf, int pos, int len,
                                    int start, int last, int p) {
        int b = 0;
        for (int i = start; i >= p; --i) {
            if (s.charAt(i) == ')') {
                ++b;
            } else if (s.charAt(i) == '(') {
                --b;
            }
            if (b < 0) {
                for (int j = last; j >= i; --j) {
                    if (s.charAt(j) == '(' && (j == last || s.charAt(j + 1) != '(')) {
                        s.getChars(j + 1, last + 1, buf, pos - (last - j));
                        removeRight(s, ans, buf, pos - (last - j), len, i - 1, j - 1, p);
                    }
                }
                return;
            }
        }
        s.getChars(p, last + 1, buf, pos - (last + 1 - p));
        ans.add(new String(buf, 0, len));
    }

    static public class Test {
        static private RemoveInvalidParentheses _solution = new RemoveInvalidParentheses();

        static public void test(String s) {
            _solution.removeInvalidParentheses(s).forEach(System.out::println);
        }

        static public void randomTest() {
            test("v)z())(((");
        }
    }
}
