package org.localmc.tools.hardcodepatcher.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import net.minecraft.client.resources.language.I18n;
import org.localmc.tools.hardcodepatcher.HardcodePatcher;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class HardcodePatcherPatch {
    private static final Gson GSON = new Gson();
    private static boolean isSemimatch = false;
    private final Path patchFile;

    private Map<String, List<TranslationInfo>> map = new HashMap<>();

    private PatchInfo info = new PatchInfo();

    public HardcodePatcherPatch(String patchFile) {
        HardcodePatcher.LOGGER.info("Load Module " + patchFile);
        Path p = HardcodePatcher.CONFIGPATH.resolve(patchFile);
        try {
            Files.createDirectories(p.getParent());
        } catch (IOException e) {
            HardcodePatcher.LOGGER.error("Failed to create {}", p.getParent(), e);
            throw new RuntimeException(e);
        }
        this.patchFile = p;
    }

    private static <K, T> void addEntry(Map<K, List<T>> p, K key, T val) {
        p.computeIfAbsent(key, k -> new ArrayList<>()).add(val);
    }

    public void readConfig(JsonReader reader) throws IOException {
        reader.beginArray();

        PatchInfo patchInfo = new PatchInfo();
        patchInfo.readJson(reader);
        info = patchInfo;

        Map<String, List<TranslationInfo>> m = new HashMap<>();
        while (reader.peek() != JsonToken.END_ARRAY) {
            TranslationInfo translationInfo = new TranslationInfo();
            translationInfo.readJson(reader);
            addEntry(m, translationInfo.getKey(), translationInfo);
        }

        reader.endArray();
        map = m;
    }

    public void readConfig() throws IOException {
        if (Files.notExists(patchFile)) {
            Files.createFile(patchFile);
        }
        try (JsonReader jsonReader = GSON.newJsonReader(new InputStreamReader(Files.newInputStream(patchFile)))) {
            readConfig(jsonReader);
        }
    }

    private List<TranslationInfo> getList(String str) {
        Set<String> set = map.keySet();
        for (String s : set) {
            if (str.contains(s)) {
                return map.get(s);
            }
        }
        return null;
    }

    public String patch(String text, StackTraceElement[] stackTrace) {
        List<TranslationInfo> list;
        if ((list = getList(text)) == null) return null;

        for (TranslationInfo info : list) {
            isSemimatch = info.getValue().startsWith("@");
            if (!isSemimatch && !text.equals(info.getKey())) continue;
            if (info.getValue() == null || info.getKey() == null || info.getKey().isEmpty() || info.getValue().isEmpty())
                continue;
            final TargetClassInfo targetClassInfo = info.getTargetClassInfo();
            if (targetClassInfo.getName().isEmpty() || targetClassInfo.getStackDepth() <= 0 || matchStack(targetClassInfo.getName(), stackTrace)) {
                return patchText(info.getValue(), info.getKey(), text);
            }
            int index = targetClassInfo.getStackDepth();
            if (index >= stackTrace.length) continue;
            if (stackTrace[index].getClassName().contains(targetClassInfo.getName())) {
                return patchText(info.getValue(), info.getKey(), text);
            }
        }

        return null;
    }

    private boolean matchStack(String str, StackTraceElement[] stack) {
        String s = str.toLowerCase();
        stack = Arrays.copyOfRange(stack, 7, 13);
        for (StackTraceElement ste : stack) {
            if (s.startsWith("#")) {
                return ste.getClassName().endsWith(s);
            } else if (s.startsWith("@")) {
                return ste.getClassName().startsWith(s);
            } else return s.equals(ste.getClassName());
        }
        return false;
    }

    private String patchText(String value, String key, String text) {
        if (isSemimatch && !value.startsWith("@@")) {
            value = value.replace("@@", "@").substring(1);
            return text.replace(key, I18n.get(value));
        } else return I18n.get(value);
    }


    @Override
    public String toString() {
        return "HardcodePatcherPatch{" +
                "patchFile=" + patchFile +
                ", map=" + map +
                ", info=" + info +
                '}';
    }
}
