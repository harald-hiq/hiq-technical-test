package se.hiq.hiq_technical_test.unit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import se.hiq.hiq_technical_test.service.TextTransformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TextTransformerTest {
    private TextTransformer textTransformer;

    @BeforeAll
    void prepare() {
        textTransformer = new TextTransformer();
    }

    @Test
    void testNull() {
        assertThrows(IllegalArgumentException.class, () -> textTransformer.process(null, null));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {
            "test",
            "a longer string",
            """
                    
                    
                    ====== Top 10 Reasons ======
                    Why Beer Is Better Than Jesus
                    
                    10. No one will kill you for not drinking Beer."""
    })
    void testUnchanged(String s) {
        final String result = textTransformer.process(s, "nonExistingString");
        assertEquals(s, result);
    }

    @Test
    void testSimple() {
        String s = "this is a test";
        final String result = textTransformer.process(s, "is");
        assertEquals("this fooisbar a test", result);
    }

    @Test
    void testParagraph() {
        String s = """
               It has been proven succesfully that chickens have a definite  \s
               wave-like nature. In reproducing Thomas Young's famous double-slit\s
               experiment of 1801, Sir Kenneth Harbour-Thomas showed that chickens
               not only diffract, but produce interference patterns as well. (This\s
               experiment is fully documented in Sir Kenneth's famous treatise\s
               "Tossing Chickens Through Various Apertures in Modern Architecture",\s
               1897)""";
        String expected = """
               It has been proven succesfully that foochickensbar have a definite  \s
               wave-like nature. In reproducing Thomas Young's famous double-slit\s
               experiment of 1801, Sir Kenneth Harbour-Thomas showed that foochickensbar
               not only diffract, but produce interference patterns as well. (This\s
               experiment is fully documented in Sir Kenneth's famous treatise\s
               "Tossing Chickens Through Various Apertures in Modern Architecture",\s
               1897)""";
        final String result = textTransformer.process(s, "chickens");
        assertEquals(expected, result);
    }
}
