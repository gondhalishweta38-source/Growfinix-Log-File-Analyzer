import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class LogAnalyzer {
    public static void main(String[] args) {
        try {
            List<String> logs = Files.readAllLines(Paths.get("logs.txt"));

            Pattern pattern = Pattern.compile("ERROR\\s(\\d+)");

            Map<String, Long> errorCounts = logs.stream()
                    .map(line -> {
                        Matcher matcher = pattern.matcher(line);
                        return matcher.find() ? matcher.group(1) : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(
                            code -> code,
                            Collectors.counting()
                    ));

            System.out.println("Error Code Count:");
            errorCounts.forEach((code, count) ->
                    System.out.println(code + " -> " + count));
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}