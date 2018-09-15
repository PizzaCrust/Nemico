package online.pizzacrust.nemico.patch;

import java.util.List;

public class NemicoConfiguration {

    public static class PatchRegistration {

        public String className;

        public String patch;

    }

    public List<NemicoConfiguration> patches;

    public String mainClass;

}
