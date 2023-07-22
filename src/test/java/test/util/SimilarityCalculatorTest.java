package test.util;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 * <pre>
 * Created on 2023/7/9 下午7:39
 * by @author WeiBoWen
 * </pre>
 */
public class SimilarityCalculatorTest {

    public static void main(String[] args) {
        String text = "这是一段用于计算相似度的示例文本。";
        String[] keywords = {"计算", "相似度", "示例"};

        double similarity = calculateSimilarity(text, keywords);
        System.out.println("相似度值：" + similarity);
    }

    private static double calculateSimilarity(String text, String[] keywords) {
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        String[] words = text.split("\\s+");
        Set<String> wordSet = new HashSet<>(Arrays.asList(words));

        int intersectionCount = 0;
        for (String word : wordSet) {
            if (keywordSet.contains(word)) {
                intersectionCount++;
            }
        }

        double keywordCount = keywordSet.size();
        double wordCount = wordSet.size();

        // 计算相似度值
        double similarity = intersectionCount / Math.sqrt(keywordCount * wordCount);
        return similarity;
    }

}
