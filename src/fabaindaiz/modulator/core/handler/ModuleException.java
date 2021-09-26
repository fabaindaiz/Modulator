package fabaindaiz.modulator.core.handler;

/**
 * Represents a class which has multiple module exceptions
 */
public class ModuleException {

    /**
     * This exception is thrown when a module load fails
     */
    public static class ModuleLoadException extends RuntimeException {
        public ModuleLoadException(String name, Throwable exception) {
            super(name + " module could not be loaded. Change module jar file", exception);
        }
    }

    /**
     * This exception is thrown when the execution of a command of some module fails
     */
    public static class ModuleCommandException extends RuntimeException {
        public ModuleCommandException(String name, Throwable exception) {
            super("Cannot execute the requested command of the module " + name, exception);
        }
    }

}
