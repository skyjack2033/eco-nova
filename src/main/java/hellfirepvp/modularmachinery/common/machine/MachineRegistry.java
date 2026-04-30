package hellfirepvp.modularmachinery.common.machine;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.mmce.common.util.StructureDefinition;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

public class MachineRegistry {

    private static final MachineRegistry INSTANCE = new MachineRegistry();

    private final Map<String, DynamicMachine> loadedMachines = new HashMap<>();

    private MachineRegistry() {
    }

    public static MachineRegistry getInstance() {
        return INSTANCE;
    }

    public static MachineRegistry getRegistry() {
        return INSTANCE;
    }

    public DynamicMachine getMachine(ResourceLocation name) {
        return loadedMachines.get(name == null ? null : name.getResourcePath());
    }

    public DynamicMachine getMachine(String name) {
        return loadedMachines.get(name);
    }

    public Collection<DynamicMachine> getLoadedMachines() {
        return Collections.unmodifiableCollection(loadedMachines.values());
    }

    public void registerMachine(DynamicMachine machine) {
        if (machine != null && machine.getMachineName() != null) {
            loadedMachines.put(machine.getMachineName(), machine);
        }
    }

    /** Clear all loaded machine definitions. */
    public void clearMachinery() {
        loadedMachines.clear();
    }

    /**
     * Load all structure definitions from the default_machinery resource directory.
     * Called during preInit before any tile entities are created.
     */
    public void loadMachineryDefinitions() {
        String basePath = "assets/" + ECOAEExtension.MOD_ID + "/default_machinery/";
        try {
            // Scan classpath for JSON files in default_machinery
            URL url = getClass().getClassLoader().getResource(basePath);
            if (url == null) {
                ECOAEExtension.log.warn("No default_machinery directory found at " + basePath);
                return;
            }
            List<String> files = listResourceFiles(basePath);
            for (String file : files) {
                if (file.endsWith(".json")) {
                    loadDefinition(basePath + file);
                }
            }
            ECOAEExtension.log.info("Loaded {} machine definitions", loadedMachines.size());
        } catch (Exception e) {
            ECOAEExtension.log.error("Failed to load machinery definitions", e);
        }
    }

    private void loadDefinition(String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) return;
            String content = IOUtils.toString(is, "UTF-8");
            StructureDefinition def = StructureDefinition.fromJsonString(content);
            if (def != null) {
                DynamicMachine machine = new DynamicMachine(def.registryName);
                machine.setStructureDef(def);
                registerMachine(machine);
            }
        } catch (Exception e) {
            ECOAEExtension.log.warn("Failed to load machine definition: " + path, e);
        }
    }

    private List<String> listResourceFiles(String path) throws IOException, URISyntaxException {
        List<String> result = new ArrayList<>();
        URL url = getClass().getClassLoader().getResource(path);
        if (url == null) return result;

        URI uri = url.toURI();
        if ("jar".equals(uri.getScheme())) {
            // Running from JAR
            try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                Path dir = fs.getPath(path);
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                    for (Path p : stream) {
                        result.add(p.getFileName().toString());
                    }
                }
            }
        } else {
            // Running from filesystem (IDE/dev)
            File dir = new File(uri);
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    result.add(f.getName());
                }
            }
        }
        return result;
    }
}
