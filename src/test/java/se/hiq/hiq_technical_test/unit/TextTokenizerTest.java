package se.hiq.hiq_technical_test.unit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import se.hiq.hiq_technical_test.entity.Text;
import se.hiq.hiq_technical_test.service.TextTokenizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TextTokenizerTest {
    private TextTokenizer textTokenizer;

    @BeforeAll
    void prepare() {
        textTokenizer = new TextTokenizer();
    }

    @Test
    void testSingleWord() {
        String s = "test";
        Text text = textTokenizer.tokenize(s);
        assertEquals(s, text.getOriginal());
        assertEquals(1, text.getWordCounts().size());
        assertEquals(1, text.getWordCounts().get(s));
    }

    @Test
    void testEmpty() {
        String s = "";
        Text text = textTokenizer.tokenize(s);
        assertEquals(s, text.getOriginal());
        assertEquals(1, text.getWordCounts().size());
        assertEquals(1, text.getWordCounts().get(s));
    }

    @Test
    void testNull() {
        assertThrows(IllegalArgumentException.class, () -> textTokenizer.tokenize(null));
    }

    @Test
    void testSimple() {
        String s = "a b b c d";
        Text text = textTokenizer.tokenize(s);
        assertEquals(s, text.getOriginal());
        assertEquals(4, text.getWordCounts().size());
        assertEquals(1, text.getWordCounts().get("a"));
        assertEquals(2, text.getWordCounts().get("b"));
        assertEquals(1, text.getWordCounts().get("c"));
        assertEquals(1, text.getWordCounts().get("d"));
    }

    @Test
    public void testParagraph() {
        String s = """
                To Sherlock Holmes she is always the woman I have seldom heard him mention her under any other name In \
                his eyes she eclipses and predominates the whole of her sex It was not that he felt any emotion akin \
                to love for Irene Adler All emotions and that one particularly were abhorrent to his cold precise \
                but admirably balanced mind He was I take it the most perfect reasoning and observing machine that the \
                world has seen but as a lover he would have placed himself in a false position He never spoke of the \
                softer passions save with a gibe and a sneer They were admirable things for the observer excellent for \
                drawing the veil from men s motives and actions But for the trained reasoner to admit such intrusions \
                into his own delicate and finely adjusted temperament was to introduce a distracting factor which might \
                throw a doubt upon all his mental results Grit in a sensitive instrument or a crack in one of his own \
                high power lenses would not be more disturbing than a strong emotion in a nature such as his And yet \
                there was but one woman to him and that woman was the late Irene Adler of dubious and questionable \
                memory\
                """;
        Text text = textTokenizer.tokenize(s);
        assertEquals(s, text.getOriginal());
        assertEquals(137, text.getWordCounts().size());
        assertEquals(10, text.getWordCounts().get("a"));
        assertEquals(9, text.getWordCounts().get("the"));
        assertEquals(2, text.getWordCounts().get("Irene"));
    }
}
