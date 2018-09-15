package online.pizzacrust.nemico.remap;

import net.techcable.srglib.FieldData;
import net.techcable.srglib.JavaType;
import net.techcable.srglib.MethodData;
import net.techcable.srglib.MethodSignature;
import net.techcable.srglib.mappings.Mappings;

import org.objectweb.asm.commons.Remapper;

public class NemicoRemapper extends Remapper {

    private final Mappings mappings;

    public NemicoRemapper(Mappings mappings) {
        this.mappings = mappings;
    }

    @Override
    public String mapMethodName(String owner, String name, String desc) {
        MethodData methodData = MethodData.create(JavaType.fromInternalName(owner), name,
                MethodSignature.fromDescriptor(desc));
        if (mappings.contains(methodData)) {
            return mappings.getNewMethod(methodData).getName();
        }
        return name;
    }

    @Override
    public String mapType(String type) {
        if (mappings.contains(JavaType.fromInternalName(type))) {
            return mappings.getNewType(JavaType.fromInternalName(type)).getInternalName();
        }
        return type;
    }

    @Override
    public String mapFieldName(String owner, String name, String desc) {
        FieldData fieldData = FieldData.create(JavaType.fromInternalName(owner), name);
        if (mappings.contains(fieldData)) {
            return mappings.getNewField(fieldData).getName();
        }
        return name;
    }
}
