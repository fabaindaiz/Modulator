package fabaindaiz.modulator.core.dependencies;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.AModule;
import fabaindaiz.modulator.core.util.jarUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class libHandler extends AModule {

    private final Modulator plugin;

    public libHandler(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    public boolean loadLib(String name) {
        try {
            File lib = new File(plugin.getDataFolder() + "/lib", name);
            if (!lib.exists()) {
                jarUtils.extractFromJar(lib.getName(), lib.getAbsolutePath());
            }
            if (!lib.exists()) {
                return false;
            }
            //addClassPath(lib.toURI().toURL());

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void addClassPath(URL url) throws IOException {
        try {
            final Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(ClassLoader.getSystemClassLoader(), new Object[] { url });
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void addChildClassPath(URL url, String name) throws IOException {
        try {
            URLClassLoader child = new URLClassLoader(new URL[] { url }, this.getClass().getClassLoader());
            Class classToLoad = Class.forName("lib." + name, true, child);
            Method method = classToLoad.getDeclaredMethod("myMethod");
            Object instance = classToLoad.newInstance();
            Object result = method.invoke(instance);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }

}
