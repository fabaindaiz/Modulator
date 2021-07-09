import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class cameraMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;
    private cameraRenderer renderer;

    public cameraMain(Modulator modulator) {
        setName("camera");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, getName(), jarName);

        renderer = new cameraRenderer(plugin, this);
        setExecutor(new cameraCommand(plugin, renderer, this));
        setTabCompleter(new cameraTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}