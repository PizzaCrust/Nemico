package online.pizzacrust.nemico.patch;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;

import net.openhft.compiler.CachedCompiler;
import net.openhft.compiler.CompilerUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import online.pizzacrust.nemico.bytecode.DecompiledClass;

/**
 * Takes in a {@link DecompiledClass} and a diff patch string.
 * Compiles the class and loads it into the current loader. (default process)
 */
public class Patcher {

    private final DecompiledClass input;
    private final String patch;

    private String code = null;

    public Patcher(DecompiledClass input, String patch) {
        this.input = input;
        this.patch = patch;
    }

    @SuppressWarnings("unchecked")
    public byte[] compile() throws Exception {
        JavaCompiler s_compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = s_compiler.getStandardFileManager(null,
                null, null);
        Constructor constructor =
                Class.forName("net.openhft.compiler.MyJavaFileManager").getConstructor(StandardJavaFileManager.class);
        constructor.setAccessible(true);
        Object myJavaFileManager = constructor.newInstance(standardJavaFileManager);
        Method method = CachedCompiler.class.getDeclaredMethod("compileFromJava", String.class,
                String.class,
                Class.forName("net.openhft.compiler.MyJavaFileManager"));
        method.setAccessible(true);
        Map<String, byte[]> map =
                (Map<String, byte[]>) method.invoke(CompilerUtils.CACHED_COMPILER, input.name, code,
                myJavaFileManager);
        return (byte[]) map.values().toArray()[0];
    }

    public void patch() throws Exception {
        List<String> classLines = Arrays.asList(input.content.split(System.lineSeparator()));
        List<String> patchLines = Arrays.asList(patch.split(System.lineSeparator()));
        Patch<String> patch = UnifiedDiffUtils.parseUnifiedDiff(patchLines);
        List<String> patched = DiffUtils.patch(classLines, patch);
        StringBuilder stringBuilder = new StringBuilder();
        patched.forEach((string) -> stringBuilder.append(string).append(System.lineSeparator()));
        this.code = stringBuilder.toString();
    }
}
