package sorazodia.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

	public static byte[] createChecksum(String filename, String hash) throws IOException, NoSuchAlgorithmException {
		
		MessageDigest message = MessageDigest.getInstance(hash);
		DigestInputStream steam = new DigestInputStream(new FileInputStream(filename), message);
		byte buffer[] = new byte[1024];
		
		while (steam.read(buffer) != -1);

		steam.close();
		return message.digest();
	}

	public static String getChecksum(String filename, String hash) throws NoSuchAlgorithmException, IOException {
		byte[] b = createChecksum(filename, hash);
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < b.length; i++) {
			str.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
		}
		return str.toString();
	}

	public static String getHash(String path, String hash) {
		String checksum = "file error";
		try {
			checksum = getChecksum(path, hash);
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return checksum;
	}

}
