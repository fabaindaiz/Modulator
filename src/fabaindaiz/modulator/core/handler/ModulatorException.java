package fabaindaiz.modulator.core.handler;

/**
 * Represents a class which has multiple modulator exceptions
 */
public class ModulatorException {

    /**
     * This exception is thrown when modulator load fails
     */
    public static class ModulatorLoadException extends RuntimeException {
        public ModulatorLoadException(Throwable exception) {
            super("Modulator could not be loaded. Try deleting the modulator folder", exception);
        }
    }

}
