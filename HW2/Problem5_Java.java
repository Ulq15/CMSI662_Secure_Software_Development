import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.nio.channels.SeekableByteChannel;
import java.util.*;

class Problem5_Java{
  public static void main(String[] args){
    Path file = new File("somefile.txt").toPath();
    // Throw exception rather than overwrite existing file
    Set<OpenOption> options = new HashSet<OpenOption>();
    options.add(StandardOpenOption.CREATE_NEW);
    options.add(StandardOpenOption.APPEND);
    // File permissions should be such that only user may read/write file
    Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
    FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
    try (SeekableByteChannel sbc = Files.newByteChannel(file, options, attr)) {
      sbc.write(ByteBuffer.wrap("some data\n".getBytes()));
      System.out.println("Success!");
    } catch(IOException e){
      System.out.println("Exception caught: "+e.toString());
    };
  }
}