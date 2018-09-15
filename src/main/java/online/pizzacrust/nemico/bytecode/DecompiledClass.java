package online.pizzacrust.nemico.bytecode;

public class DecompiledClass {

    public final String name;
    public final String content;

    public DecompiledClass(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public String toString() {
        return "DecompiledClass{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
