package fabaindaiz.modulator.module;

//Interface represent a loadable module
public interface IModule {

    // Enable module
    void onEnable();

    // Disable module
    void onDisable();
}
