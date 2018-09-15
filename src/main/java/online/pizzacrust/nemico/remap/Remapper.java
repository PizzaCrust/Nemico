package online.pizzacrust.nemico.remap;

import net.techcable.srglib.mappings.Mappings;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.tree.ClassNode;

import online.pizzacrust.nemico.patch.Patcher;

public class Remapper {

    private final Mappings mappings;
    private final Patcher patcher;

    public Remapper(Mappings mappings, Patcher patcher) {
        this.patcher = patcher;
        this.mappings = mappings;
    }

    public byte[] remapAndLoad(ClassLoader classLoader) throws Exception {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(patcher.compile());
        ClassRemapper classRemapper = new ClassRemapper(classNode, new NemicoRemapper(mappings));
        classReader.accept(classRemapper, 0);
        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

}
