/*
 * 
 */

package dyq.dsearch.nlp.stem;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LancasterStemmer{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LancasterStemmer.class);

    private final ArrayList<String> rules = new ArrayList<>();
    private final int[] index = new int[26];

    private final boolean stripPrefix;

    public LancasterStemmer() {
        this(false);
    }

    public LancasterStemmer(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
        try {
            readRules();
        } catch (IOException ex) {
            logger.error("Failed to read Lancaster_rules.txt", ex);
        }
    }

    private static int charCode(char ch) {
        return ((int) ch) - 97;
    }

    private void readRules() throws IOException {

        Files
            .readAllLines(ResourceUtils.getFile("classpath:cfg/Lancaster_rules.txt").toPath(), Charset.forName("utf-8"))
            .stream()
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .forEach(rule -> {
                int j = rule.indexOf(' ');
                if (j != -1) {
                    rule = rule.substring(0, j);
                }
                rules.add(rule);
            });

        char ch = 'a';
        for (int j = 0; j < rules.size(); j++) {
            while (rules.get(j).charAt(0) != ch) {
                ch++;
                index[charCode(ch)] = j;
            }
        }
    }

    private int firstVowel(String word, int last) {
        int i = 0;
        if ((i < last) && (!(vowel(word.charAt(i), 'a')))) {
            i++;
        }

        if (i != 0) {
            while ((i < last) && (!(vowel(word.charAt(i), word.charAt(i - 1))))) {
                i++;
            }
        }

        return Math.min(i, last);
    }

    private String stripSuffixes(String word) {
        // 1 is positive, 0 undecided, -1 negative equivalent of pun vars positive undecided negative
        int ruleok;
        int Continue = 0;

        int pll = 0; // position of last letter
        int xl; // counter for nuber of chars to be replaced and length of stemmed word if rule was aplied
        int pfv; // poition of first vowel
        int prt; // pointer into rule table
        int ir; // index of rule
        int iw; // index of word

        // char variables

        char ll; // last letter

        String rule; // variable holding the current rule
        String stem; // string holding the word as it is being stemmed this is returned as a stemmed word.

        boolean intact = true; // intact if the word has not yet been stemmed to determin a requirement of some stemming
                               // rules

        // set stemming = to word
        stem = cleanup(word.toLowerCase());

        // move through the word to find the position of the last letter before a non letter char
        while ((pll + 1 < stem.length()) && ((stem.charAt(pll + 1) >= 'a') && (stem.charAt(pll + 1) <= 'z'))) {
            pll++;
        }
        if (pll < 1) {
            Continue = -1;
        }
        // find the position of the first vowel
        pfv = firstVowel(stem, pll);
        iw = stem.length() - 1;

        // repeat until continue == negative ie. -1
        while (Continue != -1) {
            Continue = 0;

            // SEEK RULE FOR A NEW FINAL LETTER
            ll = stem.charAt(pll);

            // last letter
            // Check to see if there are any possible rules for stemming
            if ((ll >= 'a') && (ll <= 'z')) {
                prt = index[charCode(ll)];
                // pointer into rule-table
            } else {
                prt = -1;
                // 0 is a vaild rule
            }

            if (prt == -1) {
                Continue = -1;
                // no rule available
            }

            if (Continue == 0) {
                // THERE IS A POSSIBLE RULE (OR RULES) : SEE IF ONE WORKS
                rule = rules.get(prt);
                // Take first rule
                while (Continue == 0) {
                    ruleok = 0;
                    if (rule.charAt(0) != ll) {
                        // rule-letter changes
                        Continue = -1;
                        ruleok = -1;
                    }
                    ir = 1;
                    // index of rule: 2nd character
                    iw = pll - 1;
                    // index of word: next-last letter
                    // repeat untill the rule is not undecided find a rule that is acceptable
                    while (ruleok == 0) {
                        if ((rule.charAt(ir) >= '0') && (rule.charAt(ir) <= '9')) // rule fully matched
                        {
                            ruleok = 1;
                        } else if (rule.charAt(ir) == '*') {
                            // match only if word intact
                            if (intact) {
                                ir = ir + 1;
                                // move forwards along rule
                                ruleok = 1;
                            } else {
                                ruleok = -1;
                            }
                        } else if (rule.charAt(ir) != stem.charAt(iw)) {
                            // mismatch of letters
                            ruleok = -1;
                        } else if (iw <= pfv) {
                            // insufficient stemming remains
                            ruleok = -1;
                        } else {
                            // move on to compare next pair of letters
                            ir = ir + 1;
                            // move forwards along rule
                            iw = iw - 1;
                            // move backwards along word
                        }
                    }

                    // if the rule that has just been checked is valid
                    if (ruleok == 1) {
                        // CHECK ACCEPTABILITY CONDITION FOR PROPOSED RULE
                        xl = 0;
                        // count any replacement letters
                        while (!((rule.charAt(ir + xl + 1) >= '.') && (rule.charAt(ir + xl + 1) <= '>'))) {
                            xl++;
                        }
                        xl = pll + xl + 48 - ((int) (rule.charAt(ir)));
                        // position of last letter if rule used
                        if (pfv == 0) {
                            // if word starts with vowel...
                            if (xl < 1) {
                                // ...minimal stemming is 2 letters
                                ruleok = -1;
                            } else {
                                // ruleok=1; as ruleok must already be positive to reach this stage
                            }
                        } // if word start swith consonant...
                        else if ((xl < 2) | (xl < pfv)) {
                            ruleok = -1;
                            // ...minimal stemming is 3 letters...
                            // ...including one or more vowel
                        } else {
                            // ruleok=1; as ruleok must already be positive to reach this stage
                        }
                    }
                    // if using the rule passes the assertion tests
                    if (ruleok == 1) {
                        // APPLY THE MATCHING RULE
                        intact = false;
                        // move end of word marker to position...
                        // ... given by the numeral.
                        pll = pll + 48 - ((int) (rule.charAt(ir)));
                        ir++;
                        StringBuilder sb = new StringBuilder(stem.substring(0, (pll + 1)));
                        // append any letters following numeral to the word
                        while ((ir < rule.length()) && (('a' <= rule.charAt(ir)) && (rule.charAt(ir) <= 'z'))) {
                            sb.append(rule.charAt(ir));
                            ir++;
                            pll++;
                        }
                        stem = sb.toString();
                        // if rule ends with '.' then terminate
                        if ((rule.charAt(ir)) == '.') {
                            Continue = -1;
                        } else {
                            // if rule ends with '>' then Continue
                            Continue = 1;
                        }
                    } else {
                        // if rule did not match then look for another
                        prt = prt + 1;
                        // move to next rule in RULETABLE
                        if (prt >= rules.size()) {
                            Continue = -1;
                        } else {
                            rule = rules.get(prt);
                            if (rule.charAt(0) != ll) {
                                // rule-letter changes
                                Continue = -1;
                            }
                        }
                    }
                }
            }
        }

        return stem;
    }

    private boolean vowel(char ch, char prev) {
        switch (ch) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            case 'y': {
                switch (prev) {
                    case 'a':
                    case 'e':
                    case 'i':
                    case 'o':
                    case 'u':
                        return false;
                    default:
                        return true;
                }
            }
            default:
                return false;
        }
    }

    private String stripPrefixes(String word) {
        String[] prefixes = {"kilo", "micro", "milli", "intra", "ultra", "mega", "nano", "pico", "pseudo"};

        for (String prefix : prefixes) {
            if ((word.startsWith(prefix)) && (word.length() > prefix.length())) {
                word = word.substring(prefix.length());
                return word;
            }
        }

        return word;
    }

    private String cleanup(String word) {
        int last = word.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < last; i++) {
            if ((word.charAt(i) >= 'a') & (word.charAt(i) <= 'z')) {
                sb.append(word.charAt(i));
            }
        }
        return sb.toString();
    }

    public List<String> stemming(List<String> words) {
        return words.stream().map(word -> {

            word = cleanup(word.toLowerCase());
            if ((word.length() > 3) && (stripPrefix)) {
                word = stripPrefixes(word);
            }
            if (word.length() > 3) {
                word = stripSuffixes(word);
            }
            return word;
        }).collect(Collectors.toList());
    }
}
