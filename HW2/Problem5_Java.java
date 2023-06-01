public class Problem5_Java{
	public static void fileExample(){
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
		};
	}
}