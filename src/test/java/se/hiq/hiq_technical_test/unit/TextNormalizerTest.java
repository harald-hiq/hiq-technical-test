package se.hiq.hiq_technical_test.unit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import se.hiq.hiq_technical_test.service.TextNormalizer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TextNormalizerTest {
    private TextNormalizer textNormalizer;

    @BeforeAll
    void prepare() {
        textNormalizer = new TextNormalizer();
    }

    @Test
    void testNull() {
        final String result = textNormalizer.normalize(null);
        assertEquals("", result);
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {
            "test",
            "a longer string"
    })
    void testUnchanged(String s) {
        final String result = textNormalizer.normalize(s);
        assertEquals(s, result);
    }

    @Test
    void testSentence() {
        final String original = """
                There once lived a poor tailor, who had a son called Aladdin,
                a careless, idle boy who would do nothing but play all day long in
                the streets with little idle boys like himself.  This so grieved the
                father that he died; yet, in spite of his mother's tears and prayers,
                Aladdin did not mend his ways.
                """;
        final String expected = """
                There once lived a poor tailor who had a son called Aladdin a careless idle boy who would do nothing but play all day long in the streets with little idle boys like himself This so grieved the father that he died yet in spite of his mother s tears and prayers Aladdin did not mend his ways""";
        final String result = textNormalizer.normalize(original);
        assertEquals(expected, result);
    }

    @Test
    void testNonWordCharacters() {
        final String original = "[V * P] - [[(b^2) / (2m)] * D^2(P)] = E * P";
        final String expected = "V P b 2 2m D 2 P E P";
        final String result = textNormalizer.normalize(original);
        assertEquals(expected, result);
    }
}
