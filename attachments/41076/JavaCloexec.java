// based on code from https://bugs.openjdk.java.net/browse/JDK-8068370
// run: javac JavaCloexec.java && rm -rf /tmp/subdir* && java JavaCloexec
// pass an arg to try workarounds

import java.nio.*;
import java.util.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class JavaCloexec {
    public static void main(String[] args) throws Throwable {
        for (int j = 0; j != 3; ++j) {
            Path subdir = Files.createDirectory(Paths.get("/tmp/subdir" + j));
            new Thread(() -> {
                for (int i = 0; i != 100000; ++i) {
                    try {
                        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwx------");
                        FileAttribute fileAttribute = PosixFilePermissions.asFileAttribute(perms);
                        Path sh = Files.createTempFile(subdir, "script", ".sh", fileAttribute);
                        //Path sh2 = Files.createTempFile(subdir, "script", ".sh", fileAttribute);
                        //  1 in 100000 for a "text file busy" when the copy name is generated in Java
                        Path sh2 = subdir.resolve(Paths.get("copy" + i));
                        Files.write(sh, "#!/bin/bash\n".getBytes());
                        Files.setPosixFilePermissions(sh, perms);
                        if (args.length > 0) {
                            if (args[0].equals("cp")) {
                                new ProcessBuilder("cp", sh.toString(), sh2.toString()).start().waitFor();
                            } else if (args[0].equals("Files.copy")) {
                                Files.copy(sh, sh2);
                            } else {
                                throw new RuntimeException("Invalid arg");
                            }
                            Files.setPosixFilePermissions(sh2, perms);
                            sh = sh2;  // use the copy in the subprocess below
                        }
                        new ProcessBuilder(sh.toString()).start().waitFor();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }} ).start();
        }
    }
}
