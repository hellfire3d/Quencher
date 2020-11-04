package zone.arctic.quencher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;

public class MiscUtils {
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteArrayToHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
    
    public static long byteArrayToLong(byte[] guidBytes) {
        return ((guidBytes[0] & 0xFFL) << 24) | ((guidBytes[1] & 0xFFL) << 16) | ((guidBytes[2] & 0xFFL) <<  8) | ((guidBytes[3] & 0xFFL));
    }
    
    
    public static byte[] longToByteArray(long value)
    {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putLong(value);

        return Arrays.copyOfRange(bytes, 4, 8);
    }

    
    public static void copyStream(InputStream input, OutputStream output, long start, long end) throws IOException
    {
        for(int i = 0; i<start;i++) input.read();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1 && bytesRead<=end)
        {
            output.write(buffer, 0, bytesRead);
        }
    }
    
    public static String SHAsum(byte[] convertme) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-1"); 
        return byteArray2Hex(md.digest(convertme));
    }

    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
    
    public static byte[] sha1FromFile(File file) throws Exception  {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        InputStream fis = new FileInputStream(file);
        int n = 0;
        byte[] buffer = new byte[8192];
        while (n != -1) {
            n = fis.read(buffer);
            if (n > 0) {
                digest.update(buffer, 0, n);
            }
        }
        return digest.digest();
    }
}
