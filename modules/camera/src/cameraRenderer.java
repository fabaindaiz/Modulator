import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.LanguageLoader;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class cameraRenderer {

    private final Modulator plugin;
    private final IModule module;
    private final LanguageLoader lang;
    private boolean isLoaded;

    private File resourcePackFile;
    private HashMap<Material, BufferedImage> imageHashMap = new HashMap<>();
    private Map<Material, Color> blocksMap = new HashMap<Material, Color>();
    ;

    protected cameraRenderer(Modulator modulator, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLanguageLoader();

        initialize();
    }

    public void render(Player player) {

        boolean transparentWater = false; // settings.camera.transparentWater
        boolean shadows = true; // settings.camera.shadows

        // get pitch and yaw of players head to calculate ray trace directions
        Location eyes = player.getEyeLocation();
        double pitch = -Math.toRadians(player.getEyeLocation().getPitch());
        double yaw = Math.toRadians(player.getEyeLocation().getYaw() + 90);

        BufferedImage image = new BufferedImage(960, 540, 4);

        // loop through every pixel on map
        for (int x = 0; x < 960; x++) {
            for (int y = 0; y < 540; y++) {

                // calculate ray rotations
                double xrotate = ((x) * 1.6 / 960 - .8);
                double yrotate = -((y) * 1.6 / 960 - .45);

                Vector rayTraceVector = new Vector(Math.cos(yaw + xrotate) * Math.cos(pitch + yrotate),
                        Math.sin(pitch + yrotate), Math.sin(yaw + xrotate) * Math.cos(pitch + yrotate));

                RayTraceResult result = player.getWorld().rayTraceBlocks(eyes, rayTraceVector, 256);

                RayTraceResult liquidResult = player.getWorld().rayTraceBlocks(eyes,
                        rayTraceVector, 256, FluidCollisionMode.ALWAYS, false);

                // Color change for liquids
                double[] dye = new double[]{1, 1, 1}; // values color is multiplied by
                if (transparentWater) {
                    if (liquidResult != null) {
                        if (liquidResult.getHitBlock().getType().equals(Material.WATER))
                            dye = new double[]{.5, .5, 1};
                        if (liquidResult.getHitBlock().getType().equals(Material.LAVA))
                            dye = new double[]{1, .3, .3};
                    }
                }

                if (result != null) {
                    byte lightLevel = result.getHitBlock().getRelative(result.getHitBlockFace()).getLightLevel();

                    if (lightLevel > 0 && shadows) {
                        double shadowLevel = 15.0;

                        for (int i = 0; i < dye.length; i++) {
                            dye[i] = dye[i] * (lightLevel / shadowLevel);
                        }
                    }

                    Color color;
                    if (transparentWater) {
                        color = colorFromType(result.getHitBlock(), dye);
                    } else {
                        color = colorFromType(liquidResult.getHitBlock(), dye);
                    }
                    image.setRGB(x, y, color.getRGB());
                } else if (liquidResult != null) {
                    // set map pixel to color of liquid block found
                    Color color = colorFromType(liquidResult.getHitBlock(), new double[]{1, 1, 1});
                    image.setRGB(x, y, color.getRGB());
                } else {
                    // no block was hit, so we will assume we are looking at the sky
                    Color color = new Color(135, 206, 235);
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }
        try {
            File output = new File(plugin.getDataFolder(), "Example.jpg");
            ImageIO.write(image, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Color colorFromType(Block block, double[] dye) {
        if (blocksMap.containsKey(block.getType())) {
            // if blockMap has a color for the material, use that color
            Color color = blocksMap.get(block.getType());
            int redColor = (int) (color.getRed() * dye[0]);
            int greenColor = (int) (color.getGreen() * dye[1]);
            int blueColor = (int) (color.getBlue() * dye[2]);

            if (redColor > 255) redColor = 255;
            if (greenColor > 255) greenColor = 255;
            if (blueColor > 255) blueColor = 255;
            return new Color(redColor, greenColor, blueColor);
        }
        if (imageHashMap.containsKey(block.getType())) {
            // if imageMap has a color for the material, use that color
            BufferedImage image = imageHashMap.get(block.getType());
            if (image == null) {
                Bukkit.getLogger().info("Missing Image For: " + block.getType());
            } else {
                // gets certain pixel in image to use as color TODO: Create a hashmap of colors
                // so we don't need to access the image multiple times.
                Color color = new Color(image.getRGB((int) (image.getWidth() / 1.5), (int) (image.getHeight() / 1.5)));

                int redColor = (int) (color.getRed() * dye[0]);
                int greenColor = (int) (color.getGreen() * dye[1]);
                int blueColor = (int) (color.getBlue() * dye[2]);

                if (redColor > 255) redColor = 255;
                if (greenColor > 255) greenColor = 255;
                if (blueColor > 255) blueColor = 255;
                return new Color(redColor, greenColor, blueColor);
            }
        }
        return Color.gray;
    }

    public void initialize() {
        File dataFolder = plugin.getDataFolder();
        File mapDir = new File(dataFolder, "resource-packs");
        if (!mapDir.exists()) {
            mapDir.mkdir();
        }
        if (mapDir.listFiles().length == 0) {
            Bukkit.getLogger().info("No resource pack found, downloading... (this may take a while)");
            cameraUtil.downloadResourcePack(plugin);
        }
        for (File file : mapDir.listFiles()) {
            if (!file.getName().endsWith(".zip")) {
                this.resourcePackFile = file;
            } else {
                file.delete();
            }
        }
        cameraUtil.loadColors(blocksMap);

        new BukkitRunnable() {
            @Override
            public void run() {
                initializeImageHashmap();
                cancel();
            }
        }.runTaskAsynchronously(plugin);
    }

    private void initializeImageHashmap() {
        for (Material material : Material.values()) {
            File textureFile = getTextureByMaterial(material);
            if (textureFile == null) {
                return;
            }
            try {
                BufferedImage image = ImageIO.read(textureFile);
                imageHashMap.put(material, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.isLoaded = true;
    }

    public File getTextureByMaterial(Material material) {
        String textureName = material.toString().toLowerCase();
        File[] listOfFiles = resourcePackFile.listFiles();
        for (File file : listOfFiles) {
            if (!file.isFile()) {
                return null;
            }
            String fileName = file.getName();
            if (fileName.toLowerCase().contains(textureName))
                return file;
            while (textureName.contains("_")) {
                textureName = textureName.substring(0, textureName.lastIndexOf('_'));
                if (fileName.toLowerCase().contains(textureName))
                    return file;
            }
        }
        return null;
    }

}
