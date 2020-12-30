package com.dz.utlis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/30
 * create_time: 14:07
 * describe 字节处理工具
 **/
@SuppressWarnings("all")
public class ByteUtil {

    /**
     * 校验和
     *
     * @param data 字节数组
     * @return true 校验成功；false 校验失败
     */
    public static boolean checkSum(byte[] data) {
        boolean b = false;

        if (data == null || data.length < 12)
            return b;

        int sum = 0;
        for (int i = 10; i < data.length - 1; i++) {
            sum = data[i] + sum;
        }

        BigInteger i = new BigInteger(Integer.valueOf(sum).toString());
        byte[] temp = i.toByteArray();

        return (byte) (256 - (temp[temp.length - 1] & 0xFF)) == data[data.length - 1];
    }

    /**
     * 获取校验和
     *
     * @param b 字节数组
     * @return 校验和字节
     */
    public static byte getCheckSum(byte[] b) {
        if (b == null || b.length < 12)
            return 0;

        int c = 0;
        for (int i = 10; i < b.length; i++) {
            c = b[i] + c;
        }

        BigInteger i = new BigInteger(Integer.valueOf(c).toString());
        byte[] temp = i.toByteArray();

        return (byte) (256 - (temp[temp.length - 1] & 0xFF));
    }

    /**
     * 移除字节数组尾部连续为0x00的所有字节
     *
     * @param b 字节数组
     * @return 字节数组
     */
    public static byte[] trimRightBytes(byte[] b) {
        int c = 0;
        for (int i = b.length - 1; i > 0; i--) {
            if (b[i] != 0x00)
                break;
            c++;
        }

        int l = b.length - c;

        byte[] bytes = new byte[l];

        System.arraycopy(b, 0, bytes, 0, l);

        return bytes;
    }

    /**
     * 移除字节数组头部连续为0x00的所有字节
     *
     * @param b 字节数组
     * @return 字节数组
     */
    public static byte[] trimLeftBytes(byte[] b) {
        int c = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i] != 0x00)
                break;
            c++;
        }

        int l = b.length - c;

        byte[] bytes = new byte[l];

        System.arraycopy(b, c, bytes, 0, l);

        return bytes;
    }

    /**
     * long类型转成byte数组
     *
     * @param l long型数据
     * @return byte数组
     */
    public static byte[] longToByte(long l) {
        long temp = l;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();
            // 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * byte数组转成long
     *
     * @param b byte数组
     * @return long型数据
     */
    public static long byteToLong(byte[] b) {
        long s = 0;
        long s0 = b.length > 0 ? b[0] & 0xff : 0;// 最低位
        long s1 = b.length > 1 ? b[1] & 0xff : 0;
        long s2 = b.length > 2 ? b[2] & 0xff : 0;
        long s3 = b.length > 3 ? b[3] & 0xff : 0;
        long s4 = b.length > 4 ? b[4] & 0xff : 0;// 最低位
        long s5 = b.length > 5 ? b[5] & 0xff : 0;
        long s6 = b.length > 6 ? b[6] & 0xff : 0;
        long s7 = b.length > 7 ? b[7] & 0xff : 0;

        // s0不变
        s1 <<= 8;
        s2 <<= 8 * 2;
        s3 <<= 8 * 3;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    /**
     * int转换成byte数组
     *
     * @param number int型数据
     * @return byte数组
     */
    public static byte[] intToByteArr(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();
            // 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * int转换成byte
     *
     * @param number int型数据
     * @return byte数组
     */
    public static byte intToByte(int number) {
        int temp = number;
        byte b = new Integer(temp & 0xff).byteValue();
        return b;
    }

    /**
     * byte数组转换成int
     *
     * @param b byte数组
     * @return int型数据
     */
    public static int byteToInt(byte[] b) {
        int s = 0;
        int s0 = b.length > 0 ? b[0] & 0xff : 0;// 最低位
        int s1 = b.length > 1 ? b[1] & 0xff : 0;
        int s2 = b.length > 2 ? b[2] & 0xff : 0;
        int s3 = b.length > 3 ? b[3] & 0xff : 0;
        s3 <<= 24;
        s2 <<= 16;
        s1 <<= 8;
        s = s0 | s1 | s2 | s3;
        return s;
    }

    /**
     * short转换成byte数组
     *
     * @param s short型数据
     * @return byte数组
     */
    public static byte[] shortToByte(short s) {
        int temp = s;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();
            // 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * byte数组转换成short
     *
     * @param b byte数组
     * @return short型数据
     */
    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = b.length > 0 ? (short) (b[0] & 0xff) : 0;// 最低位
        short s1 = b.length > 1 ? (short) (b[1] & 0xff) : 0;
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    /**
     * 字符串转字节数组
     *
     * @param str        要转换的字符串
     * @param charEncode 字符编码，如：UTF-8、GB2312，UTF-8编码一个汉字占三个字节，GB2312一个汉字占两个字节
     * @return 字节数组
     */
    public static byte[] stringToByte(String str, String charEncode) {
        byte[] destObj = null;
        try {
            if (null == str || str.trim().equals("")) {
                destObj = new byte[0];
                return destObj;
            } else {
                destObj = str.getBytes(charEncode);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return destObj;
    }

    /**
     * 字节数组转字符串
     *
     * @param b          要转换的字节数组
     * @param charEncode 字符编码，如：UTF-8、GB2312，UTF-8编码一个汉字占三个字节，GB2312一个汉字占两个字节
     * @return 字符串
     */
    public static String byteToString(byte[] b, String charEncode) {
        String destObj = null;
        try {
            destObj = new String(b, charEncode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return destObj.replaceAll("\0", " ");
    }

    /**
     * 分割字节数组
     *
     * @param b     字节数组
     * @param split 分隔字节
     * @return 二维字节数组
     */
    public static byte[][] splitByte(byte[] b, byte split) {
        List<Integer> indexs = new ArrayList<Integer>();
        for (int i = 0; i < b.length; i++) {
            if (b[i] == split) {
                indexs.add(i);
            }
        }
        byte[][] bb = new byte[indexs.size() + 1][];
        for (int i = 0; i < indexs.size() + 1; i++) {
            int start = i == 0 ? 0 : indexs.get(i - 1) + 1;
            int end = i == indexs.size() ? b.length : indexs.get(i);
            bb[i] = new byte[end - start];
            System.arraycopy(b, start, bb[i], 0, bb[i].length);
        }
        return bb;
    }

    /**
     * Object对象转换成字节数组
     *
     * @param obj Object对象
     * @return 字节数组
     */
    public static byte[] objectToByte(Object obj) {
        byte[] bytes = new byte[1024];
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (bytes);
    }

    /**
     * 字节数组转Object对象
     *
     * @param bytes 字节数组
     * @return Object对象
     */
    public static Object byteToObject(byte[] bytes) {
        Object obj = new Object();
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();

            bi.close();
            oi.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 字节数组转换成十六进制字符串
     *
     * @param b     字节数组
     * @param split 分隔字符串，主要用于输出调试
     * @return
     */
    public static String byteArrToHexStr(byte[] b, String split) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
            hs = hs + split;
        }
        return hs.toUpperCase();
    }

    /**
     * 字节转换成十六进制字符串
     *
     * @param b     字节
     * @param split 分隔字符串，主要用于输出调试
     * @return
     */
    public static String byteToHexStr(byte b, String split) {
        String hs = "";
        String stmp = "";
        stmp = (Integer.toHexString(b & 0XFF));
        if (stmp.length() == 1) hs = hs + "0" + stmp;
        else hs = hs + stmp;
        hs = hs + split;
        return hs.toUpperCase();
    }


    /**
     * 字节数组转换成十六进制字符串
     *
     * @param b     字节数组
     * @param split 分隔字符串，主要用于输出调试
     * @return
     */
    public static String byteToHexStr(byte[] b, int len, String split) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < len; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
            hs = hs + split;
        }
        return hs.toUpperCase();
    }

    public static byte[] clearZear(byte[] b, int len) {
        List<Byte> list = new ArrayList<Byte>();
        boolean isStart = false;
        for (int i = 0; i < len; i++) {
            if (b[i] == 0x00) {
                if (!isStart)
                    list.add(b[i]);
                isStart = true;
            } else {
                isStart = false;
                list.add(b[i]);
            }
        }
        byte[] data = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i);
        }
        return data;
    }

    /**
     * 十六进制字符串转换成字节数组
     *
     * @param hexstr 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexStrToByte(String hexstr) {
        int len = (hexstr.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hexstr.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (((byte) "0123456789ABCDEF".indexOf(achar[pos])) << 4 | ((byte) "0123456789ABCDEF".indexOf(achar[pos + 1])));
        }
        return result;
    }

    public static byte[] irConvert(byte[] data, int lead1Units, int lead2Units, int zore1Units, int zore2Units, int one1Units, int one2Units) {
        byte[] result = new byte[data.length * 8 * 2 * 2 + 4];

        result[0] = (byte) 0x80;
        result[1] = ByteUtil.intToByteArr(lead1Units)[0];
        result[2] = (byte) 0x00;
        result[3] = ByteUtil.intToByteArr(lead2Units)[0];

        for (int i = 4; i < result.length; i = i + 2) {
            if (i % 4 == 0) {
                result[i] = (byte) 0x80;
            } else {
                result[i] = (byte) 0x00;
            }
        }

        for (int i = 0; i < data.length; i++) {
            int point = i * 32 + 4;
            byte b = data[i];
            for (int j = 0; j < 8; j++) {
                if (b >> j == 0x00) {
                    result[point + j * 4 + 1] = (byte) zore1Units;
                    result[point + j * 4 + 3] = (byte) zore2Units;
                } else {
                    result[point + j * 4 + 1] = (byte) one1Units;
                    result[point + j * 4 + 3] = (byte) one2Units;
                }
            }
        }

        return result;
    }


    /*
     * 字节数组转16进制字符串
     */
    public static String bytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp);
        }

        return sb.toString();
    }

    /**
     * @param n
     * @Title: intTohex
     * @Description: int型转换成16进制
     * @return: String
     */
    public static String intTohex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        while (n != 0) {
            s = s.append(b[n % 16]);
            n = n / 16;
        }
        a = s.reverse().toString();
        if ("".equals(a)) {
            a = "00";
        }
        if (a.length() == 1) {
            a = "0" + a;
        }
        return a;
    }


    /**
     * 字符串转16进制字符串
     *
     * @param strPart
     * @return
     */
    public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

    /**
     * 十六进制转字节数组
     *
     * @param src
     * @return
     */
    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }


    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }


    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }


    public static String convertStringToHex(String str) {

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }

    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < hex.length() - 1; i += 2) {

            String s = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            sb.append((char) decimal);
            sb2.append(decimal);
        }

        return sb.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        // toUpperCase将字符串中的所有字符转换为大写
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        // toCharArray将此字符串转换为一个新的字符数组。
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    //返回匹配字符
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    //将字节数组转换为short类型，即统计字符串长度
    public static short bytes2Short2(byte[] b) {
        short i = (short) (((b[1] & 0xff) << 8) | b[0] & 0xff);
        return i;
    }

    //将字节数组转换为16进制字符串
    public static String BinaryToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + " ";
        }
        return result;
    }


    private static String hexStr = "0123456789ABCDEF";
    private static String[] binaryArray =
            {"0000", "0001", "0010", "0011",
                    "0100", "0101", "0110", "0111",
                    "1000", "1001", "1010", "1011",
                    "1100", "1101", "1110", "1111"};


    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     *
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }


    /**
     * 字符串转换成为16进制(无需Unicode编码)
     *
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 16进制转换成为string类型字符串
     *
     * @param 51 54 4A
     * @return QTJ
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {

            s = new String(baKeyword, "GBK");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * @return 转换为二进制字符串
     */
    public static String bytes2BinaryStr(byte[] bArray) {

        String outStr = "";
        int pos = 0;
        for (byte b : bArray) {
            //高四位
            pos = (b & 0xF0) >> 4;
            outStr += binaryArray[pos];
            //低四位
            pos = b & 0x0F;
            outStr += binaryArray[pos];
        }
        return outStr;

    }


    /**
     * @param hexString
     * @return 将十六进制转换为字节数组
     */
    public static byte[] HexStringToBinary(String hexString) {
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位

        for (int i = 0; i < len; i++) {
            //右移四位得到高位
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            bytes[i] = (byte) (high | low);//高地位做或运算
        }
        return bytes;
    }







    /**
     * int转换为小端byte[]（高位放在高地址中）
     * @param iValue
     * @return
     */
    public static byte[] Int2Bytes_LE(int iValue){
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[0] = (byte)(iValue & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte)((iValue & 0xFF00) >> 8 );
        // int 倒数第三个字节
        rst[2] = (byte)((iValue & 0xFF0000) >> 16 );
        // int 第一个字节
        rst[3] = (byte)((iValue & 0xFF000000) >> 24 );
        return rst;
    }



    /**
     * 转换byte数组为int（小端）
     * @return
     * @note 数组长度至少为4，按小端方式转换,即传入的bytes是小端的，按这个规律组织成int
     */
    public static int Bytes2Int_LE(byte[] bytes){
        if(bytes.length < 4)
            return -1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;
        iRst |= (bytes[2] & 0xFF) << 16;
        iRst |= (bytes[3] & 0xFF)<< 24;

        return iRst;
    }



    /**
     * 转换byte数组为int（大端）
     * @return
     * @note 数组长度至少为4，按小端方式转换，即传入的bytes是大端的，按这个规律组织成int
     */
    public static int Bytes2Int_BE(byte[] bytes){
        if(bytes.length < 4)
            return -1;
        int iRst = (bytes[0] << 24) & 0xFF;
        iRst |= (bytes[1] << 16) & 0xFF;
        iRst |= (bytes[2] << 8) & 0xFF;
        iRst |= bytes[3] & 0xFF;

        return iRst;
    }



    /**
     * 转换byte数组为Char（小端）
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public synchronized static char Bytes2Char_LE(byte[] bytes){
        if(bytes.length < 2)
            return (char)-1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;

        return (char)iRst;
    }




    /**
     * 转换byte数组为char（大端）
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public static char Bytes2Char_BE(byte[] bytes){
        if(bytes.length < 2)
            return (char)-1;
        int iRst = (bytes[0] << 8) & 0xFF;
        iRst |= bytes[1] & 0xFF;

        return (char)iRst;
    }





    /**
     * 10进制字符串转 16进制字符串
     *
     * @param tenString 20 52 87 127 126 83 52 15 42
     * @param partten   分割符
     * @param litter    是否启用小段模式
     * @return
     */

    public static synchronized String tenToHexString(String tenString, String partten, boolean litter) {

        String[] tenStrings = tenString.split(partten);
        StringBuffer buffer = new StringBuffer();
        try {

            if (null != tenStrings) {
                for (int i = 0; i < tenStrings.length; i++) {
                    String hex = Integer.toHexString(Integer.parseInt(tenStrings[i]));
                    if (hex.length() == 1 || hex.length() == 3) {
                        hex = "0" + hex;
                    }
                    if (hex.length() == 4) {
                        if (litter) {
                            buffer.append(hex.substring(2, 4))
                                    .append(partten)
                                    .append(hex.substring(0, 2))
                                    .append(partten);
                        } else {
                            buffer.append(hex.substring(0, 2))
                                    .append(partten)
                                    .append(hex.substring(2, 4))
                                    .append(partten);
                        }

                    } else {
                        buffer.append(hex).append(partten);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("解析出现异常!!!");
        }

        return buffer.toString().toUpperCase();
    }


    /**
     * 和上面一个方法位移的区别是 自动补齐成两个字节
     * 10进制字符串转 16进制字符串
     *
     * @param tenString 20 52 87 127 126 83 52 15 42
     * @param partten   分割符
     * @param litter    是否启用小段模式
     * @return
     */

    public static String tenToTwoByteHexString(String tenString, String partten, boolean litter) {

        String[] tenStrings = tenString.split(partten);
        StringBuffer buffer = new StringBuffer();
        try {

            if (null != tenStrings) {
                for (int i = 0; i < tenStrings.length; i++) {
                    String hex = Integer.toHexString(Integer.parseInt(tenStrings[i]));
                    if (hex.length() == 1 || hex.length() == 3) {
                        hex = "0" + hex;
                    }
                    if (hex.length() == 4) {
                        if (litter) {
                            buffer.append(hex.substring(2, 4))
                                    .append(partten)
                                    .append(hex.substring(0, 2))
                                    .append(partten);
                        } else {
                            buffer.append(hex.substring(0, 2))
                                    .append(partten)
                                    .append(hex.substring(2, 4))
                                    .append(partten);
                        }

                    } else {
                        buffer.append(hex).append(partten).append("00").append(partten);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("解析出现异常!!!");
        }

        return buffer.toString().toUpperCase();
    }


//
//    public static void main(String[] args) {
//
//        try {
//            String str = "二进制与十六进制互转测试";
//            System.out.println("源字符串：\n" + str);
//
//            String hexString = str2HexStr(str);
//            String hexString1 = BinaryToHexString(str.getBytes());
//            System.out.println("转换为十六进制：\n" + hexString);
//            System.out.println("转换为十六进制1：\n" + hexString1);
//
//
//            if (hexString.equals(hexString1.replace(" ", ""))) {
//                System.out.println("--------相等-----------");
//            }
//            System.out.println("转换为二进制：\n" + bytes2BinaryStr(str.getBytes()));
//
//            byte[] bArray = HexStringToBinary(hexString);
//
//            System.out.println("将str的十六进制文件转换为二进制再转为String：" + hexStr2Str(hexString1.replace(" ", "")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        byte[] b = {0x11, 0x12, 0x13};
//        // 字节转换为16进制字符串
//        String hexStr = bytesToHexString(b);
//        System.out.println("hexStr:" + hexStr);
//        // int 转 16进制字符串
//        String hexStr1 = Integer.toHexString(20);
//        System.out.println("hexStr1:" + hexStr1);
//        // 16进制转字节数组
//        byte[] c = hexString2Bytes(Integer.toHexString(2345));
//        System.out.println("十六进制字节数组：" + c);
//        // int 转16进制字符串
//        System.out.println(intTohex(2345));
//        // int转16进制 后转16进制字节数组
//        System.out.println("十六进制字节数组：" + hexToByteArray(Integer.toHexString(2345)));
//
//
//        System.out.println("======ASCII码转换为16进制======");
//        String str = "*00007VERSION\\n1$";
//        System.out.println("字符串: " + str);
//        String hex = convertStringToHex(str);
//        System.out.println("====转换为16进制=====" + hex);
//
//        System.out.println("======16进制转换为ASCII======");
//        System.out.println("Hex : " + hex);
//        System.out.println("ASCII : " + convertHexToString(hex));
//
//        byte[] bytes = hexStringToBytes(hex);
//
//        System.out.println(BinaryToHexString(bytes));
//    }


}