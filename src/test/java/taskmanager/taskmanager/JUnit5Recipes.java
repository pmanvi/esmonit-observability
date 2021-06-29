package taskmanager.taskmanager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class JUnit5Recipes {

    @Test
    @DisplayName("Welcome JUnit-5! 😎")
    void helloEmojis() {
        BiFunction<Integer, Integer, Integer> adder = (number, number2) -> number + number2;
        assertEquals(Integer.valueOf(2), adder.apply(1, 1), "1 + 1 should equal 2");
    }

    @BeforeAll
    static void setup() {
        System.setProperty("test", "test");
        System.setProperty("test2", "test2");
    }

    @Test
    @DisplayName("Multiple assertions in a single test")
    void groupedAssertions() {
        // failures will be reported together.
        assertAll("EnvTest",
                () -> assertEquals("test", System.getProperty("test")),
                () -> assertEquals("test2", System.getProperty("test2"))
        );
    }

    @Test
    void testGroupAssertions() {

        Person p = new Person("Praveen", "Manvi");

        assertAll("person",
                () -> assertEquals("Manvi", p.lastName),
                () -> assertEquals("Praveen", p.firstName),
                () -> assertEquals("Manvi1", p.lastName)
        );
    }


    @Test
    void failingTest() {
        fail("fail");
    }

    @Test
    @Disabled("skipping")
    void skippedTest() {
        // not executed
    }

    static class Person {
        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
        String firstName;
        String lastName;
    }
}
