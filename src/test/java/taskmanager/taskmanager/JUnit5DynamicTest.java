package taskmanager.taskmanager;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class JUnit5DynamicTest {

    @TestFactory
    public Stream<DynamicTest> testFactory() {
        InputStream inputFS = getClass().getClassLoader().getResourceAsStream("taskData.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

        return br.lines().skip(1).map(mapCsvLineToDynamicTest());
    }

    private Function<String, DynamicTest> mapCsvLineToDynamicTest() {
        return s -> {
            final String[] values = s.split(",");
            return dynamicTest(values[0], () -> assertAddition(values));
        };
    }

    private void assertAddition(String[] values) {
        assertThat(Integer.parseInt(values[1]) + Integer.parseInt(values[2]))
                .isEqualTo(Integer.parseInt(values[3]));
    }
}