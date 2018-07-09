package nz.ac.auckland.SPARER;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class SaltGenerator {
	
    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

    public static final String DIGITS = "0123456789";

    public static final String ALPHANUM = UPPER + LOWER + DIGITS;

    private final Random _random;

    private final char[] _symbols;

    private final char[] _buf;

    public SaltGenerator(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this._random = Objects.requireNonNull(random);
        this._symbols = symbols.toCharArray();
        this._buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public SaltGenerator(int length, Random random) {
        this(length, random, ALPHANUM);
    }

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < _buf.length; ++idx)
            _buf[idx] = _symbols[_random.nextInt(_symbols.length)];
        return new String(_buf);
    }

}
