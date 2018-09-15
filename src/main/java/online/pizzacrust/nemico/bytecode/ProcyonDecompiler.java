package online.pizzacrust.nemico.bytecode;

import com.strobel.decompiler.PlainTextOutput;

import java.io.InputStream;
import java.io.StringWriter;

public class ProcyonDecompiler implements Decompiler {
    @Override
    public DecompiledClass decompile(String name, InputStream file) {
        StringWriter stringWriter = new StringWriter();
        com.strobel.decompiler.Decompiler.decompile(name, new PlainTextOutput(stringWriter));
        return new DecompiledClass(name, stringWriter.toString());
    }

    public static void main(String... args) {
        System.out.println(new ProcyonDecompiler().decompileFromClassLoader("online.pizzacrust" +
                ".nemico.ProcyonDecompiler", ProcyonDecompiler.class.getClassLoader()));
    }
}
