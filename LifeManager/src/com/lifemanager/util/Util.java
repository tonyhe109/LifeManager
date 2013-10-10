package com.lifemanager.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.MailTo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lifemanager.logging.Logger;


public class Util {
    private static final Logger LOG = Logger.getLogger(Util.class);

    private static Toast sToast = null;
//    public final static String PLAYER_SET_FLAG = "/xlPlayerSetFlag";
//    public final static String MSGPUSH_SET_FLAG = "/xlMsgPushFlag";
    private static String mPeerId = null;
    private static String mOSVersion = null;
    private static String mSelfAppVersion = null;
    private static String mIMEI = null;
    private static final String MILLAGE_STRING = "[*:/?<>\"\\：]";
    private static final String REPLACE_STRING = "_";

    public static boolean ensureDir(String path) {
        if (null == path) {
            return false;
        }

        boolean ret = false;

        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            ret = file.mkdirs();
        }

        return ret;
    }

    public static boolean isFileExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public static boolean ensureFile(String path) {
        if (null == path) {
            return false;
        }

        boolean ret = false;

        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            try {
                file.createNewFile();
                ret = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static boolean deleteFile(String path) {
        if (path == null)
            return false;
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        return file.delete();

    }

    public static String getSDCardDir() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static boolean isSDCardExist() {
        return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState());
    }

    // 获取sdcard剩余空间大小单位是MB
    public static long getSdCardAvailaleSize() {
        long ret = 0;
        String strSDCardPath = getSDCardDir();
        if (null != strSDCardPath) {
            StatFs stat = new StatFs(strSDCardPath);
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            ret = (availableBlocks * blockSize) / 1024 / 1024;
        }
        return ret;
    }

    public static long getAvailableExternalMemorySize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static long getTotalExternalMemorySize() {
        File path = Environment.getExternalStorageDirectory();
        Environment.getExternalStorageState();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    public static Bitmap creatBitmap(String path) {
        if (null == path) {
            return null;
        }

        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        return BitmapFactory.decodeFile(path);
    }

    public static void saveBitmap(Bitmap bitmap, String path, String name) {
        if (!path.endsWith("/")) {
            path += "/";
        }
        File file = new File(path + name);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();

            FileOutputStream out = null;
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            bitmap = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(new Date());
    }

    public static String getMillTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(new Date());
    }

    public static String getDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static String getShortDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(new Date());
    }

    public static String mToStr(int m) {
        m = m / 1000;
        int hour = m / 3600;
        int minute = (m - 3600 * hour) / 60;
        int second = m % 60;
        return String.format(Locale.US, "%02d:%02d:%02d", hour, minute, second);

    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            LOG.warn(e);
        }
        return null;
    }

    public static void showToast(Context context, String showMsg, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(context, showMsg, duration);
        }
        sToast.setText(showMsg);
        sToast.show();
    }

    public static String xunleiBase64Decoder(String s) {
        int start = s.indexOf("thunder://");
        String code = s.substring(start + "thunder://".length());
        return String.valueOf(Base64.decode(code, Base64.DEFAULT));
    }


    public static String getUrlPF(Activity ctx) {
        String ret = "pf=540";
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels);
        if (width < 480) {
            ret = "pf=320";
        } else if (width >= 480 && width < 540) {
            ret = "pf=480";
        } else if (width >= 540) {
            ret = "pf=540";
        } else {
            ret = "pf=540";
        }
        ret += "&w=" + dm.widthPixels + "&h=" + dm.heightPixels;
        return ret;
    }

    public static String getFormatTime(int mCurSec) {
        String formattedTime = "00:00:00";
        if (mCurSec == 0) {
            return formattedTime;

        }
        int hour = mCurSec / 60 / 60;
        int leftSec = mCurSec % (60 * 60);
        int min = leftSec / 60;
        int sec = leftSec % 60;
        String strHour = String.format(Locale.US, "%d", hour);
        String strMin = String.format(Locale.US, "%d", min);
        String strSec = String.format(Locale.US, "%d", sec);

        if (hour < 10) {
            strHour = "0" + hour;
        }
        if (min < 10) {
            strMin = "0" + min;
        }
        if (sec < 10) {
            strSec = "0" + sec;
        }
        formattedTime = strHour + ":" + strMin + ":" + strSec;
        return formattedTime;
    }

    public static int parseInt(String str) {
        int result = 0;

        if (str != null && str.trim().length() > 0) {
            result = Integer.parseInt(str);
        }

        return result;
    }

    private static final int BASE_B = 1; // 转换为字节基数
    private static final int BASE_KB = 1024; // 转换为KB
    private static final int BASE_MB = 1024 * 1024; // 转换为M的基数
    private static final int BASE_GB = 1024 * 1024 * 1024;

    private static final String UNIT_BIT = "KB";
    private static final String UNIT_KB = "KB";
    private static final String UNIT_MB = "M";
    private static final String UNIT_GB = "G";

    public static String convertFileSize(long file_size, int precision) {
        long int_part = 0;
        double fileSize = file_size;
        double floatSize = 0L;
        long temp = file_size;
        int i = 0;
        int base = 1;
        String baseUnit = "M";
        String fileSizeStr = null;
        int indexMid = 0;

        while (temp / 1024 > 0) {
            int_part = temp / 1024;
            temp = int_part;
            i++;
        }
        switch (i) {
        case 0:
            // B
            base = BASE_B;
            baseUnit = UNIT_BIT;
            break;

        case 1:
            // KB
            base = BASE_KB;
            baseUnit = UNIT_KB;
            break;

        case 2:
            // MB
            base = BASE_MB;
            baseUnit = UNIT_MB;
            break;

        case 3:
            // GB
            base = BASE_GB;
            baseUnit = UNIT_GB;
            break;

        case 4:
            // TB
            break;
        default:
            break;
        }
        floatSize = fileSize / base;
        fileSizeStr = Double.toString(floatSize);
        if (precision == 0) {
            indexMid = fileSizeStr.indexOf('.');
            if (-1 == indexMid) {
                // 字符串中没有这样的字符
                return fileSizeStr + baseUnit;
            }
            return fileSizeStr.substring(0, indexMid) + baseUnit;
        }
        indexMid = fileSizeStr.indexOf('.');
        if (-1 == indexMid) {
            // 字符串中没有这样的字符
            return fileSizeStr + baseUnit;
        }

        if (fileSizeStr.length() <= indexMid + precision + 1) {
            return fileSizeStr + baseUnit;
        }
        if (indexMid < 3) {
            indexMid += 1;
        }
        if (indexMid + precision < 6) {
            indexMid = indexMid + precision;
        }
        return fileSizeStr.substring(0, indexMid) + baseUnit;
    }

    // 根据url生成图片对象，网络加载图片
    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // 得到进程ID
    static List<RunningAppProcessInfo> apps = null;
    static int mgprocessId = 0;

    public static int getProcessId(Context con) {
        if (mgprocessId == 0) {
            ActivityManager am = (ActivityManager) con.getSystemService(Context.ACTIVITY_SERVICE);
            apps = am.getRunningAppProcesses();// 返回进程列表信息
            for (RunningAppProcessInfo p : apps) {
                if (p.processName.equals("com.xunlei.kankan")) {
                    mgprocessId = p.pid;
                    break;
                }
            }
        }
        return mgprocessId;
    }

    public static void showMailDialg(Context context, String url) {
        MailTo mt = MailTo.parse(url);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL, new String[] { mt.getTo() });
        i.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
        i.putExtra(Intent.EXTRA_CC, mt.getCc());
        i.putExtra(Intent.EXTRA_TEXT, mt.getBody());
        context.startActivity(i);
    }

    public static final int MSG_LOADED_PROGRESS = 123;

    public static boolean isWifiNet(final Context context) {
        boolean bRet = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo && wifiInfo.isConnectedOrConnecting()) {
            bRet = true;
        }
        return bRet;
    }

    static private AlertDialog mSetNetDialog = null;

    public interface IWifiTipCallback {
        public void onMakeChoice();
    }

    public static void showNotWifiNotic(final Context context, final IWifiTipCallback callback) {
        if (null == mSetNetDialog) {
            mSetNetDialog = new AlertDialog.Builder(context).setTitle("提示").setMessage("您正在使用非WIFI网络，建议在WIFI下使用")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                dialog.dismiss();
                                context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                mSetNetDialog = null;
                                if (null != callback) {
                                    callback.onMakeChoice();
                                }
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                mSetNetDialog = null;
                                if (null != callback) {
                                    callback.onMakeChoice();
                                }
                            }
                        }
                    }).create();
            mSetNetDialog.show();
        }
    }

    static private String mCpuInfo = null;
    private static int sFirstLaunch = -1;
    private static Set<String> sFirstCalls = new HashSet<String>();

    private static String shellExce(String[] args) {
        String result = null;
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            process = processBuilder.start();
            baos = new ByteArrayOutputStream();
            is = process.getInputStream();
            int read = -1;
            while (-1 != (read = is.read())) {
                baos.write(read);
            }
            result = new String(baos.toByteArray());
        } catch (Exception e) {
            result = null;
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // hex to string
    public static String hex_2_string(final String hexString) {
        String ret = "";

        if (null != hexString) {
            byte[] bytes = new byte[hexString.length() / 2];
            for (int i = 0; i < bytes.length; ++i) {
                bytes[i] = (byte) (Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16));
            }
            ret = new String(bytes);
        }

        return ret;
    }

    // string to hex
    public static String string_2_hex(final String str) {
        String hexString = "";
        for (int i = 0; i < str.length(); ++i) {
            byte[] b = str.substring(i, i + 1).getBytes();
            int j = 0;
            while (j < b.length) {
                hexString += Integer.toHexString(b[j] & 0xFF);
                ++j;
            }
        }
        return hexString.toUpperCase();
    }

    public static byte[] getByteArr(int length) {
        int byteNum = length * Integer.SIZE / Byte.SIZE;
        byte[] byteArr = new byte[byteNum];
        return byteArr;
    }

    /**
     * 整型数组的二进制流化操作
     * 
     * @param intArr
     * @return
     */
    public static byte[] convertIntArr2ByteArr(int[] intArr) {
        byte[] byteArr = getByteArr(intArr.length);
        int curInt = 0;
        for (int j = 0, k = 0; j < intArr.length; j++, k += 4) {
            curInt = intArr[j];
            byteArr[k + 3] = (byte) ((curInt >>> 24) & 0xFF);
            byteArr[k + 2] = (byte) ((curInt >>> 16) & 0xFF);
            byteArr[k + 1] = (byte) ((curInt >>> 8) & 0xFF);
            byteArr[k] = (byte) ((curInt >>> 0) & 0xFF);
        }
        return byteArr;
    }

    public static byte[] convertInt2ByteArr(int i) {
        int[] intArr = new int[1];
        intArr[0] = i;
        return convertIntArr2ByteArr(intArr);
    }

    /**
     * 把src整个拷贝到dst,从dst索引（第一个元素索引为0）值为from开始追加
     * 
     * @param dst
     *            目标拷贝对象
     * @param src
     *            源拷贝对象
     * @param from
     */
    public static void copyByte(byte[] dst, final byte[] src, int from) {
        int size = src.length;
        if (size + from > dst.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < size && i + from < dst.length; ++i) {
            dst[i + from] = src[i];
        }
    }

    /**
     * 判断会员是否过期
     * 
     * @param date
     *            会员的截至日期
     * @return 如果过期返回true,否则返回false
     */
    public static boolean isVipExpired(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        String today2Str = format.format(today);
        int expriation = today2Str.compareTo(date);
        if (expriation > 0) {
            return true;
        }
        return false;
    }

    public static String readFileByLines(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(read);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                builder.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return builder.toString();
    }

    // 获取peerid
    public static String getPeerid(final Context context) {
        if (null == mPeerId && null != context) {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (null != wm && null != wm.getConnectionInfo()) {
                String mac = wm.getConnectionInfo().getMacAddress();
                mac += "004V";
                mPeerId = mac.replaceAll(":", "");
                mPeerId = mPeerId.replaceAll(",", "");
                mPeerId = mPeerId.replaceAll("[.]", "");
                mPeerId = mPeerId.toUpperCase();
            }
        }
        return mPeerId;
    }

    // 获取系统版本信息
    public static String getOSVersion() {
        if (null == mOSVersion) {
            mOSVersion = "SDKV = " + android.os.Build.VERSION.RELEASE;
            mOSVersion += "_MANUFACTURER = " + android.os.Build.MANUFACTURER;
            mOSVersion += "_MODEL = " + android.os.Build.MODEL;
            mOSVersion += "_PRODUCT = " + android.os.Build.PRODUCT;
            mOSVersion += "_FINGERPRINT = " + android.os.Build.FINGERPRINT;
            mOSVersion += "_CPU_ABI = " + android.os.Build.CPU_ABI;
            mOSVersion += "_ID = " + android.os.Build.ID;
        }
        return mOSVersion;
    }

    // 获取自己的版本号
    public static String getSelfAppVersion(final Context context) {
        if (null == mSelfAppVersion && null != context) {
            try {
                mSelfAppVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return mSelfAppVersion;
    }

    // 获取IMEI值
    public static String getIMEI(final Context context) {
        if (null == mIMEI && null != context) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != tm) {
                mIMEI = tm.getDeviceId();
            }
            if (null == mIMEI) {
                mIMEI = "000000000000000";
            }
        }
        return mIMEI;
    }

    /**
     * 设置软键盘状态 隐藏或者显示
     * 
     * @param binder
     * @param isHide
     *            若为真 则隐藏软键盘 假则显示软件盘
     */
    public static void setSoftInputWindow(View view, boolean isHide, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isHide) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            imm.showSoftInput(view, 0);
        }

    }

    static public String inputStream2String(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 去除字符串中所有空格 以及首位的换行符 ，返回结果不影响原始字符串的内容
     * 
     * @param src
     * @return
     */
    static public String filterSpace(String src) {
        if (StringEx.isNullOrEmpty(src)) {
            return null;
        }
        StringBuilder dst = new StringBuilder();
        src = src.trim();
        for (int i = 0; i < src.length(); ++i) {
            char ch = src.charAt(i);
            if (!Character.isSpace(ch)) {
                dst.append(ch);
            }
        }
        return dst.toString();
    }

    public static boolean isQQNum(String s) {
        if (StringEx.isNullOrEmpty(s)) {
            return false;
        }
        return s.matches("([1-9][0-9]{4})|([0-9]{6,10})");
    }

    public static boolean isEmail(String s) {
        if (StringEx.isNullOrEmpty(s)) {
            return false;
        }
        return s.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    }

    /**
     * 格式化url 在url后边追加参数 return String 格式化后的url
     */
    public static String formatUrl(final String xmlUrl, final Context context) {
        String ret = xmlUrl;
        if (null != xmlUrl && !"".equals(xmlUrl)) {
            if (-1 != xmlUrl.indexOf("?")) {
                ret = xmlUrl.trim() + "&";
            } else {
                ret = xmlUrl.trim() + "?";
            }
            ret += "procduct_id=10&clinet_version=" + Util.getSelfAppVersion(context) + "&peer_id="
                    + Util.getPeerid(context)
                    + "&os=" + Uri.encode(Util.getOSVersion()) + "&imei=" + Util.getIMEI(context);
            ret = ret.trim();
        }
        return ret;
    }

    // 文件名过滤
    public static String formatFileName(final String fileNameString) {
        String retString = fileNameString;
        if (null != fileNameString) {
            retString = retString.replaceAll(MILLAGE_STRING, REPLACE_STRING);
        }
        return retString;
    }

    public static boolean isExistApp(final String appPackage) {
        boolean ret = false;
        if (new File("/data/data/" + appPackage).exists()) {
            ret = true;
        }
        return ret;
    }

    public static boolean copyOneFile(final String src, final String dest) {
        boolean ret = false;
        File file = new File(dest);
        if (file.exists()) {
            file.delete();
        }
        ensureFile(dest);
        try {
            InputStream is = new FileInputStream(new File(src));
            BufferedInputStream inBuff = new BufferedInputStream(is);

            byte[] readBuf = new byte[1024 * 8];
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream outBuff = new BufferedOutputStream(fos);
            while (inBuff.read(readBuf) != -1) {
                outBuff.write(readBuf);
            }
            outBuff.flush();
            inBuff.close();
            outBuff.close();
            fos.close();

            is.close();
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean isFirstCall(String context) {
        boolean result = !(sFirstCalls.contains(context));
        if (result) {
            sFirstCalls.add(context);
        }

        return result;
    }

    public static boolean isFirstLaunch(Context context) {
        if (sFirstLaunch == -1) {
            sFirstLaunch = 0;

            SharedPreferences prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
            String version = prefs.getString("version", "");
            String appVersion = getVerName(context);
            if (!(version.equals(appVersion))) {
                sFirstLaunch = 1;
                Editor edit = prefs.edit();
                edit.putString("version", appVersion);
                edit.commit();
            }
        }

        return sFirstLaunch != 0;
    }
    
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            LOG.warn(e);
        }
        return verName;
    }

    /**
     * 如果date1比date2早，返回-1 如果date1比date2晚，返回1 如果date1与date2相同，返回0 参数格式不对，返回-2
     */
    public static int compareDate(String date1, String date2) {
        return compareDate(date1, date2, "yyyy-MM-dd HH:mm:ss");
    }

    public static int compareDate(String date1, String date2, String format) {
        int returnCode = -2;

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);

            returnCode = d1.compareTo(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnCode;
    }

    public static Date convertDate(String convertDate, String format) {
        Date date = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            date = dateFormat.parse(convertDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 时间格式：yyyy-MM-dd
     */
    public static Date convertDate(String convertDate) {
        return convertDate(convertDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatTime(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(c.getTime());
    }


    public static void copy(InputStream is, OutputStream os) {
        try {
            byte[] bytes = new byte[1024 * 8];
            int len = -1;
            while ((len = is.read(bytes, 0, bytes.length)) != -1) {
                os.write(bytes, 0, len);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
