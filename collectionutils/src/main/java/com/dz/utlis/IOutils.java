package com.dz.utlis;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Io处理类
 */
public class IOutils {

    public static int bytesToInt(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return 0;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return Integer.parseInt(stringBuilder.toString(), 16);
    }

    /**
     * 整数转成byte数组
     *
     * @param @param  value
     * @param @param  len
     * @param @return
     * @return byte[]
     * @Title: intToBytes
     * @Description: TODO
     */
    public static int[] intToBytes(int value, int len) {
        int[] b = new int[len];
        String sl = Integer.toHexString(value);
        int sur = len * 2 - sl.length();
        for (int i = 0; i < sur; i++) {
            sl = "0" + sl;
        }
        for (int i = 0; i < b.length; i++) {
            b[i] = (Integer.parseInt(sl.substring(i * 2, (i + 1) * 2), 16));
        }
        return b;
    }


    /**
     * bitmap转base64位
     *
     * @param bitmap
     * @return
     */
    public static String imgToBase64(Bitmap bitmap) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static final String FILENAME = "IOutils" + IOutils.class.getName();

    /**
     * desc:保存对象
     *
     * @param context
     * @param key
     * @param obj     要保存的对象，只能保存实现了serializable的对象 modified:
     */
    public static void saveObject(Context context, String key, Object obj) {
        try {
            // 保存对象
            SharedPreferences.Editor sharedata = context.getSharedPreferences(
                    FILENAME, 0).edit();
            // 先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            // 将对象序列化写入byte缓存
            os.writeObject(obj);
            // 将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            // 保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "保存obj失败");
        }
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:获取保存的Object对象
     *
     * @param context
     * @param key
     * @return modified:
     */
    public Object readObject(Context context, String key) {
        try {
            SharedPreferences sharedata = context.getSharedPreferences(
                    FILENAME, 0);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    // 将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(
                            stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    // 返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 所有异常返回null
        return null;

    }

    /**
     * desc:将16进制的数据转为数组
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch; // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); // //两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16; // // 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; // // A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); // /两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); // // 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; // // A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;// 将转化后的数放入Byte里
        }
        return retData;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
    }

    public static void flushQuietly(Flushable flushable) {
        if (flushable != null)
            try {
                flushable.flush();
            } catch (Exception ignored) {
            }
    }

    public static void closeQuietly(HttpURLConnection urlConnection) {
        if (urlConnection != null)
            urlConnection.disconnect();
    }

    public static BufferedInputStream toBufferedInputStream(InputStream inputStream) {
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
    }

    public static BufferedOutputStream toBufferedOutputStream(OutputStream outputStream) {
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static BufferedWriter toBufferedWriter(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    public static InputStream toInputStream(CharSequence input) {
        return new ByteArrayInputStream(input.toString().getBytes());
    }

    public static InputStream toInputStream(CharSequence input, String encoding) throws UnsupportedEncodingException {
        byte[] bytes = input.toString().getBytes(encoding);
        return new ByteArrayInputStream(bytes);
    }

    public static String toString(InputStream input) throws IOException {
        return new String(toByteArray(input));
    }

    public static String toString(InputStream input, String encoding) throws IOException {
        return new String(toByteArray(input), encoding);
    }

    public static String toString(Reader input) throws IOException {
        return new String(toByteArray(input));
    }

    public static String toString(Reader input, String encoding) throws IOException {
        return new String(toByteArray(input), encoding);
    }

    public static String toString(byte[] byteArray) {
        return new String(byteArray);
    }

    public static String toString(byte[] byteArray, String encoding) {
        try {
            return new String(byteArray, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(byteArray);
        }
    }

    public static byte[] toByteArray(CharSequence input) {
        if (input == null)
            return new byte[0];
        return input.toString().getBytes();
    }

    public static byte[] toByteArray(CharSequence input, String encoding) throws UnsupportedEncodingException {
        if (input == null)
            return new byte[0];
        else
            return input.toString().getBytes(encoding);
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(input, output);
        output.close();
        return output.toByteArray();
    }

    public static byte[] toByteArray(InputStream input, int size) throws IOException {
        if (size < 0)
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);

        if (size == 0) return new byte[0];

        byte[] data = new byte[size];
        int offset = 0;
        int byteCount;
        while ((offset < size) && (byteCount = input.read(data, offset, size - offset)) != -1)
            offset += byteCount;

        if (offset != size)
            throw new IOException("Unexpected byte count size. current: " + offset + ", excepted: " + size);
        return data;
    }

    public static byte[] toByteArray(Reader input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(input, output);
        output.close();
        return output.toByteArray();
    }

    public static byte[] toByteArray(Reader input, String encoding) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(input, output, encoding);
        output.close();
        return output.toByteArray();
    }

    public static char[] toCharArray(CharSequence input) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        write(input, output);
        return output.toCharArray();
    }

    public static char[] toCharArray(InputStream input) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        write(input, output);
        return output.toCharArray();
    }

    public static char[] toCharArray(InputStream input, String encoding) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        write(input, output, encoding);
        return output.toCharArray();
    }

    public static char[] toCharArray(Reader input) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        write(input, output);
        return output.toCharArray();
    }

    public static List<String> readLines(InputStream input, String encoding) throws IOException {
        Reader reader = new InputStreamReader(input, encoding);
        return readLines(reader);
    }

    public static List<String> readLines(InputStream input) throws IOException {
        Reader reader = new InputStreamReader(input);
        return readLines(reader);
    }

    public static List<String> readLines(Reader input) throws IOException {
        BufferedReader reader = toBufferedReader(input);
        List<String> list = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }

    public static void write(byte[] data, OutputStream output) throws IOException {
        if (data != null)
            output.write(data);
    }

    public static void write(byte[] data, Writer output) throws IOException {
        if (data != null)
            output.write(new String(data));
    }

    public static void write(byte[] data, Writer output, String encoding) throws IOException {
        if (data != null)
            output.write(new String(data, encoding));
    }

    public static void write(char[] data, Writer output) throws IOException {
        if (data != null)
            output.write(data);
    }

    public static void write(char[] data, OutputStream output) throws IOException {
        if (data != null)
            output.write(new String(data).getBytes());
    }

    public static void write(char[] data, OutputStream output, String encoding) throws IOException {
        if (data != null)
            output.write(new String(data).getBytes(encoding));
    }

    public static void write(CharSequence data, Writer output) throws IOException {
        if (data != null)
            output.write(data.toString());
    }

    public static void write(CharSequence data, OutputStream output) throws IOException {
        if (data != null)
            output.write(data.toString().getBytes());
    }

    public static void write(CharSequence data, OutputStream output, String encoding) throws IOException {
        if (data != null)
            output.write(data.toString().getBytes(encoding));
    }

    public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
        int len;
        byte[] buffer = new byte[4096];
        while ((len = inputStream.read(buffer)) != -1)
            outputStream.write(buffer, 0, len);
    }

    public static void write(Reader input, OutputStream output) throws IOException {
        Writer out = new OutputStreamWriter(output);
        write(input, out);
        out.flush();
    }

    public static void write(InputStream input, Writer output) throws IOException {
        Reader in = new InputStreamReader(input);
        write(in, output);
    }

    public static void write(Reader input, OutputStream output, String encoding) throws IOException {
        Writer out = new OutputStreamWriter(output, encoding);
        write(input, out);
        out.flush();
    }

    public static void write(InputStream input, OutputStream output, String encoding) throws IOException {
        Reader in = new InputStreamReader(input, encoding);
        write(in, output);
    }

    public static void write(InputStream input, Writer output, String encoding) throws IOException {
        Reader in = new InputStreamReader(input, encoding);
        write(in, output);
    }

    public static void write(Reader input, Writer output) throws IOException {
        int len;
        char[] buffer = new char[4096];
        while (-1 != (len = input.read(buffer)))
            output.write(buffer, 0, len);
    }

    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
        input1 = toBufferedInputStream(input1);
        input2 = toBufferedInputStream(input2);

        int ch = input1.read();
        while (-1 != ch) {
            int ch2 = input2.read();
            if (ch != ch2) return false;
            ch = input1.read();
        }

        int ch2 = input2.read();
        return ch2 == -1;
    }

    public static boolean contentEquals(Reader input1, Reader input2) throws IOException {
        input1 = toBufferedReader(input1);
        input2 = toBufferedReader(input2);

        int ch = input1.read();
        while (-1 != ch) {
            int ch2 = input2.read();
            if (ch != ch2) return false;
            ch = input1.read();
        }

        int ch2 = input2.read();
        return ch2 == -1;
    }

    public static boolean contentEqualsIgnoreEOL(Reader input1, Reader input2) throws IOException {
        BufferedReader br1 = toBufferedReader(input1);
        BufferedReader br2 = toBufferedReader(input2);

        String line1 = br1.readLine();
        String line2 = br2.readLine();
        while ((line1 != null) && (line2 != null) && (line1.equals(line2))) {
            line1 = br1.readLine();
            line2 = br2.readLine();
        }
        return line1 != null && (line2 == null || line1.equals(line2));
    }

    /**
     * Access to a directory available size.
     *
     * @param path path.
     * @return space size.
     */
    public static long getDirSize(String path) {
        StatFs stat;
        try {
            stat = new StatFs(path);
        } catch (Exception e) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            return stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        else
            return stat.getBlockSize() * stat.getAvailableBlocks();
    }

    /**
     * If the folder can be written.
     *
     * @param path path.
     * @return True: success, or false: failure.
     */
    public static boolean canWrite(String path) {
        return new File(path).canWrite();
    }

    /**
     * If the folder can be read.
     *
     * @param path path.
     * @return True: success, or false: failure.
     */
    public static boolean canRead(String path) {
        return new File(path).canRead();
    }

    /**
     * Create a folder, If the folder exists is not created.
     *
     * @param folderPath folder path.
     * @return True: success, or false: failure.
     */
    public static boolean createFolder(String folderPath) {
        if (!TextUtils.isEmpty(folderPath)) {
            File folder = new File(folderPath);
            return createFolder(folder);
        }
        return false;
    }

    /**
     * Create a folder, If the folder exists is not created.
     *
     * @param targetFolder folder path.
     * @return True: success, or false: failure.
     */
    public static boolean createFolder(File targetFolder) {
        if (targetFolder.exists()) {
            if (targetFolder.isDirectory())
                return true;
            //noinspection ResultOfMethodCallIgnored
            targetFolder.delete();
        }
        return targetFolder.mkdirs();
    }

    /**
     * Create a folder, If the folder exists is not created.
     *
     * @param folderPath folder path.
     * @return True: success, or false: failure.
     */
    public static boolean createNewFolder(String folderPath) {
        return delFileOrFolder(folderPath) && createFolder(folderPath);
    }

    /**
     * Create a folder, If the folder exists is not created.
     *
     * @param targetFolder folder path.
     * @return True: success, or false: failure.
     */
    public static boolean createNewFolder(File targetFolder) {
        return delFileOrFolder(targetFolder) && createFolder(targetFolder);
    }

    /**
     * Create a file, If the file exists is not created.
     *
     * @param filePath file path.
     * @return True: success, or false: failure.
     */
    public static boolean createFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            return createFile(file);
        }
        return false;
    }

    /**
     * Create a file, If the file exists is not created.
     *
     * @param targetFile file.
     * @return True: success, or false: failure.
     */
    public static boolean createFile(File targetFile) {
        if (targetFile.exists()) {
            if (targetFile.isFile())
                return true;
            delFileOrFolder(targetFile);
        }
        try {
            return targetFile.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Create a new file, if the file exists, delete and create again.
     *
     * @param filePath file path.
     * @return True: success, or false: failure.
     */
    public static boolean createNewFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            return createNewFile(file);
        }
        return false;
    }

    /**
     * Create a new file, if the file exists, delete and create again.
     *
     * @param targetFile file.
     * @return True: success, or false: failure.
     */
    public static boolean createNewFile(File targetFile) {
        if (targetFile.exists())
            delFileOrFolder(targetFile);
        try {
            return targetFile.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Delete file or folder.
     *
     * @param path path.
     * @return is succeed.
     * @see #delFileOrFolder(File)
     */
    public static boolean delFileOrFolder(String path) {
        return delFileOrFolder(new File(path));
    }

    /**
     * Delete file or folder.
     *
     * @param file file.
     * @return is succeed.
     * @see #delFileOrFolder(String)
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean delFileOrFolder(File file) {
        if (file == null || !file.exists()) {
            // do nothing
        } else if (file.isFile())
            file.delete();
        else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File sonFile : files)
                    delFileOrFolder(sonFile);
            file.delete();
        }
        return true;
    }


}
