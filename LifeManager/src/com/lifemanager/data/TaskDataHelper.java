
package com.lifemanager.data;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDataHelper implements TaskPriority, TaskTiming {

    /**
     * <pre>
     * String[] formats = new String[] {
     *         &quot;yyyy-MM-dd&quot;,
     *         &quot;yyyy-MM-dd HH:mm&quot;,
     *         &quot;yyyy-MM-dd HH:mmZ&quot;,
     *         &quot;yyyy-MM-dd HH:mm:ss.SSSZ&quot;,
     *         &quot;yyyy-MM-dd'T'HH:mm:ss.SSSZ&quot;,
     * };
     * for (String format : formats) {
     *     SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
     *     System.out.format(&quot;%30s %s\n&quot;, format, sdf.format(new Date(0)));
     *     sdf.setTimeZone(TimeZone.getTimeZone(&quot;UTC&quot;));
     *     System.out.format(&quot;%30s %s\n&quot;, format, sdf.format(new Date(0)));
     * }
     * </pre>
     * <p>
     * Which produces this output when run in the America/Los_Angeles time zone:
     * 
     * <pre>
     *                     yyyy-MM-dd 1969-12-31
     *                     yyyy-MM-dd 1970-01-01
     *               yyyy-MM-dd HH:mm 1969-12-31 16:00
     *               yyyy-MM-dd HH:mm 1970-01-01 00:00
     *              yyyy-MM-dd HH:mmZ 1969-12-31 16:00-0800
     *              yyyy-MM-dd HH:mmZ 1970-01-01 00:00+0000
     *       yyyy-MM-dd HH:mm:ss.SSSZ 1969-12-31 16:00:00.000-0800
     *       yyyy-MM-dd HH:mm:ss.SSSZ 1970-01-01 00:00:00.000+0000
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ 1969-12-31T16:00:00.000-0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ 1970-01-01T00:00:00.000+0000
     * </pre>
     * <p>
     */
    @SuppressLint("SimpleDateFormat")
    public final static SimpleDateFormat DF_MDHM = new SimpleDateFormat("MM-dd HH:mm");
    @SuppressLint("SimpleDateFormat")
    public final static SimpleDateFormat DF_YMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * @param strings {String priority , String Date}
     * @param strings {String priority , String Date , String title}
     * @see TaskPriority
     * @see SimpleDateFormat
     * @return
     */
    public static Task trasferTaskFromStringArray(String... strings) {

        if (strings[0] != null && strings[1] != null && strings[2] != null) {
            try {
                return new LiMaTask(Integer.parseInt(strings[0]), DF_MDHM.parse(strings[1]), strings[2]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (strings[0] != null && strings[1] != null) {
            try {
                return new LiMaTask(Integer.parseInt(strings[0]), DF_MDHM.parse(strings[1]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static Task[] getTestTaskArray() {
        Task[] taskArray = new Task[12];

        String[][] baseDate = new String[][] {
                new String[] {
                        "3", "12-21 6:30", "起床啦！"
                },
                new String[] {
                        "3", "12-21 9:00", "去银行办理信用卡"
                },
                new String[] {
                        "1", "12-21 8:15", "出发出发 ... "
                },
                new String[] {
                        "1", "12-21 9:30", "来杯咖啡，振作精神"
                },
                new String[] {
                        "1", "12-21 11:45", "逻辑思维"
                },
                new String[] {
                        "1", "12-21 12:00", "中午藍色港灣吃火鍋"
                },
                new String[] {
                        "2", "12-21 13:15", "Pre-Plan\n 1.zhouxiaobing\n 2.ShiChen \n 3.guoli"
                },
                new String[] {
                        "2", "12-21 14:00", "？"
                },
                new String[] {
                        "3", "12-21 14:00", "讨论下ListView的自定义"
                },
                new String[] {
                        "1", "12-21 14:30", "抽干烟吧"
                },
                new String[] {
                        "1", "12-21 17:00", "吃点东西，补充能量，晚上去跑步"
                },
                new String[] {
                        "2", "12-21 20:00", "足协杯 恒大VS北京"
                }
        };
        for (int i = 0; i < baseDate.length; i++) {
            taskArray[i] = trasferTaskFromStringArray(baseDate[i]);
            //System.out.println(taskArray[i]);
        }
        return taskArray;
    }

    public static String getTimeString(Date date, String format) {
        SimpleDateFormat fomatter = new SimpleDateFormat(format);
        String time = fomatter.format(date);
        return time;
    }

    public static void main(String args[]) {
        getTestTaskArray();
    }

}
