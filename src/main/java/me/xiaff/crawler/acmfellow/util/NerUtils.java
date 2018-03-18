package me.xiaff.crawler.acmfellow.util;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 从文本中抽取Ph.D.学校和年份
 * Created by Lu Chenwei on 2017/7/13.
 */
public class NerUtils {
    private static Pattern pattern = Pattern.compile("(ph\\.?d\\.?)|(doctorate)|(Doctor of Philosophy)", Pattern.CASE_INSENSITIVE);
    public static final Logger logger = LoggerFactory.getLogger(NerUtils.class);
    private static final String TYPE_ORGANIZATION = "ORGANIZATION";
    private static final String TYPE_DATE = "DATE";

    public static Pair<String, String> getPhdSchoolAndYear(String text) {
        text = text.replaceAll("[\r|\n]+", ". ");
        Document doc = new Document(text);
        List<Sentence> sentences = doc.sentences();
        for (Sentence sentence : sentences) {
            String sentenceStr = sentence.text();
            Matcher matcher = pattern.matcher(sentenceStr);
            if (matcher.find()) {
                int phdKeywordIndex = 0;
                List<String> words = sentence.words();
                for (; phdKeywordIndex < words.size(); phdKeywordIndex++) {
                    String s = words.get(phdKeywordIndex);
                    Matcher wordMatcher = pattern.matcher(s);
                    if (wordMatcher.find()) {
                        break;
                    }
                }
//                System.out.println("phdKeywordIndex: " + phdKeywordIndex);
                String organization = getOrgFromSentence(sentence, phdKeywordIndex);
                if (StringUtils.isEmpty(organization)) {
                    continue;
                }
                System.out.println("\t[Hit] " + sentence);
                String year = getYearFromSentence(sentence, phdKeywordIndex);
                return new ImmutablePair<>(organization, year);
            }

        }
        return null;
    }

    private static String getOrgFromSentence(Sentence sentence, int phdIndex) {
        List<String> nerTags = sentence.nerTags();
        List<String> words = sentence.words();
        Pair<Integer, Integer> range = getIndexRangeOfOrgWords(nerTags, phdIndex);
        if (range.getLeft() == -1) {
            return null;
        }

        List<String> orgWords = words.subList(range.getLeft(), range.getRight() + 1);
        return StringUtils.join(orgWords, " ");
    }

    private static String getYearFromSentence(Sentence sentence, int phdIndex) {
        String year = null;
        List<String> nerTags = sentence.nerTags();
        int dateIndex = getDateIndexOfOrgWords(nerTags, phdIndex);
        if (dateIndex != -1) {
            year = sentence.words().get(dateIndex);
        }
        return year;
    }

    private static int getDateIndexOfOrgWords(List<String> nerTags, int mid) {
        int maxDistance = Math.max(mid, nerTags.size() - mid);

        for (int i = 0; i < maxDistance; i++) {
            int indexAfter = mid + i;
            int indexBefore = mid - i;
            String typeAfter = "NULL";
            String typeBefore = "NULL";
            if (indexAfter < nerTags.size()) {
                typeAfter = nerTags.get(indexAfter);
            }
            if (indexBefore >= 0 && indexBefore < nerTags.size()) {
                typeBefore = nerTags.get(indexBefore);
            }
            if (TYPE_DATE.equals(typeAfter)) {
                return indexAfter;
            } else if (TYPE_DATE.equals(typeBefore)) {
                return indexBefore;
            }
        }
        return -1;
    }

    private static Pair<Integer, Integer> getIndexRangeOfOrgWords(List<String> nerTags, int mid) {
        logger.debug("NER tags: {}", nerTags);

        int maxDistance = Math.max(mid, nerTags.size() - mid);

        for (int i = 1; i < maxDistance; i++) {
            int indexAfter = mid + i;
            int indexBefore = mid - i;
            String typeAfter = "NULL";
            String typeBefore = "NULL";
            if (indexAfter < nerTags.size()) {
                typeAfter = nerTags.get(indexAfter);
            }
            if (indexBefore >= 0 && indexBefore < nerTags.size()) {
                typeBefore = nerTags.get(indexBefore);
            }
            if (TYPE_ORGANIZATION.equals(typeAfter)) {
                return new ImmutablePair<>(indexAfter, getEndIndexOfType(nerTags, indexAfter, true));
            } else if (TYPE_ORGANIZATION.equals(typeBefore)) {
                return new ImmutablePair<>(getEndIndexOfType(nerTags, indexBefore, false), indexBefore);
            }
        }
        return new ImmutablePair<>(-1, -1);
    }

    private static int getEndIndexOfType(List<String> nerTags, int begin, boolean ascend) {
        int end = begin;
        if (ascend) {
            for (int j = begin; j < nerTags.size(); j++) {
                if (TYPE_ORGANIZATION.equals(nerTags.get(j))) {
                    end = j;
                } else {
                    return end;
                }
            }
        } else {
            for (int j = begin; j >= 0; j--) {
                if (TYPE_ORGANIZATION.equals(nerTags.get(j))) {
                    end = j;
                } else {
                    return end;
                }
            }
        }
        return end;
    }

    public static void main(String[] args) {
        String text0 = "He earned a Bachelor of Arts in Mathematics from Wilkes University in Pennsylvania and a Master of Arts and Doctorate in Mathematics from the University at Albany.";
        System.out.println(getPhdSchoolAndYear(text0));

        String text1 = "Prof. Kuo received his B.S.E. degree in Computer Science and Information Engineering from National Taiwan University in Taipei, Taiwan, in 1986. He received his M.S. and Ph.D. degrees in Computer Sciences from the University of Texas at Austin in 1990 and 1994, respectively. He is a distinguished research fellow and the director of the Research Center for Information Technology Innovation, Academia Sinica, and a distinguished professor at the Department of Computer Science and Information Engineering, National Taiwan University, Taipei, Taiwan, ROC, since August 2009, where he was promoted to a full professor in August 2001. He was the chairman of his department between August 2005 and July 2008 and a deputy dean of the College of the Electronic Engineering and Computer Science between Feb 2006 and July 2008, National Taiwan University, Taipei, Taiwan, ROC.  He served as the vice chairman of his department between August 2002 and July 2005. He was an Associate Professor at the Department of Computer Science and Information Engineering, National Taiwan University, between August 2000 and July 2001, and the Department of Computer Science and Information Engineering of the National Chung Cheng University in Chiayi, Taiwan, ROC between August 1994 and July 2000. The research interests of Prof. Kuo include real-time systems, embedded systems, non-volatile memory storage/memory software designs (such as those of flash memory and PCM), and real-time database systems. \n";
        System.out.println(getPhdSchoolAndYear(text1));

        String text2 = "Michael George Luby is a mathematician and computer scientist, VP Technology at Qualcomm and former co-founder and Chief Technology Officer of Digital Fountain. In coding theory he is known for leading the invention of the Tornado codes and the LT codes. In cryptography he is known for his contributions showing that any one-way function can be used as the basis for private cryptography, and for his analysis, in collaboration with Charles Rackoff, of the Feistel cipher construction. His distributed algorithm to find a maximal independent set in a computer network has also been very influential. He has also contributed to average-case complexity.[1]\n" +
                "\n" +
                "Luby received his B.Sc. in mathematics from MIT in 1975. In 1983 he was awarded a Ph.D. in computer science from UC Berkeley.[2] In 1996-1997, while at the International Computer Science Institute (ICSI), he led the team that invented Tornado codes. These were the first LDPC codes based on an irregular degree design that has proved crucial to all later good LDPC code designs, which provably achieve channel capacity for the erasure channel, and which have linear time encoding and decoding algorithms. In 1998 Luby left ICSI to found the Digital Fountain company, and shortly thereafter in 1998 he invented the LT codes, the first practical fountain codes. Qualcomm acquired Digital Fountain in 2009.[3]";
        System.out.println(getPhdSchoolAndYear(text2));

        String text3 = "Ueli Maurer (born 26 May 1960 in Leimbach, Switzerland) is a professor for cryptography at the Swiss Federal Institute of Technology Zurich (ETH Zurich).\n" +
                "\n" +
                "Maurer studied electrical engineering at ETH Zurich and obtained his PhD in 1990, advised by James Massey. Afterwards, he joined Princeton university as a postdoc. In 1992, he became part of the computer science faculty of ETH Zurich.\n" +
                "\n" +
                "In a seminal work, he showed that the Diffie-Hellman problem is (under certain conditions) equivalent to solving the discrete log problem.[1]\n" +
                "\n" +
                "From 2002 until 2008, Maurer also served on the board of Tamedia AG.[2]\n" +
                "\n" +
                "Maurer is editor of the Journal of Cryptology and used to be editor-in-chief.\n" +
                "\n" +
                "In 2015, he was named a Fellow of the Association for Computing Machinery \"for contributions to cryptography and information security.\"[3]\n" +
                "\n";
        System.out.println(getPhdSchoolAndYear(text3));

        WebDriver webDriver = new ChromeDriver();

        webDriver.get("https://cs.brown.edu/people/faculty/rt.html");
        String html = webDriver.getPageSource();
        String text4 = HtmlUtils.getText(html);
        logger.debug("raw: [{}]", text4);
        System.out.println(getPhdSchoolAndYear(text4));

        webDriver.get("https://en.wikipedia.org/wiki/L_Peter_Deutsch");
        html = webDriver.getPageSource();
        String text5 = HtmlUtils.getText(html);
        logger.debug("raw: [{}]", text5);
        System.out.println(getPhdSchoolAndYear(text5));

    }
}
