package online.pizzacrust.nemico.bytecode;

import java.io.InputStream;

public interface Decompiler {

    DecompiledClass decompile(String path, InputStream file);

    default DecompiledClass decompileFromClassLoader(String reflectName,
                                                     ClassLoader classLoader) {
        return decompile(reflectName.replace("\\.", "/"),
                classLoader.getResourceAsStream(reflectName.replace(
                "\\.",
                "/") +
                ".class"));
    }

}
