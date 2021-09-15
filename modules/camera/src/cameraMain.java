import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

public class cameraMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;
    private cameraRenderer renderer;

    public cameraMain(Modulator modulator) {
        setName("camera");
        setDescription("\u00A7e/camera \u00A7fCaptura im\u00e1genes desde el servidor");
        setPermission("modulator.adm");

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        renderer = new cameraRenderer(plugin, this);
        setExecutor(new cameraCommand(plugin, renderer, this));
        setTabCompleter(new cameraTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}