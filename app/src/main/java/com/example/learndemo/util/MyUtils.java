package com.example.learndemo.util;

import android.content.Context;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.example.learndemo.MyApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by MuCS on 2016/9/19 0019.
 */
public class MyUtils {
    /**
     * 设置文字中特殊的颜色
     * @param tv
     * @param startL 左边起始位
     * @param startR 右边起始位
     * @param color 指定中间的特殊的颜色
     */
    public static void setTextColor(TextView tv, int startL, int startR, int color) {
        String text = tv.getText().toString();
        if(TextUtils.isEmpty(text))return;
        int len = text.length();
        SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());
        ForegroundColorSpan specialColor = new ForegroundColorSpan(color);
        builder.setSpan(specialColor, startL,len - startR, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
    }

    /**
     * 获取前几天的时期
     * @param index
     * @return
     */
    public static String getDate(int index){
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.DAY_OF_YEAR,-index);
        Date time = ca.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String t=format.format(time);
        return t;
    }

    public static void writeJson2TXT(String json) {
        File file = null;
        FileWriter writer = null;
        try {
            file = FileInOutHelper.createWithOverwriteExistFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/json.txt");
            writer = new FileWriter(file);
            writer.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer!=null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        StringWriter writer = new StringWriter();


    }
    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }

    public static String parseTime(String time){
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        dateFormat.setTimeZone(timeZone);
        try {
            Date date= dateFormat.parse(time);
            Log.i("UDP","date : "+date.getTime());
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmsssss");
            String time1 = dateFormat1.format(date);
            Log.i("UDP","time 1 : "+time1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmsssss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getDataString(String data){
        String timeString = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsssss");
        Calendar ca = Calendar.getInstance();
        String now = format.format(ca.getTime());
        try{
            long time1 = Long.valueOf(data);
            long now1 = Long.valueOf(now);

            if ((now1/1000000000 - time1/1000000000) == 1){
                long timeHour = time1 / 10000000 % 100;
                long timeMin = time1 / 100000 % 100;
                timeString += "昨天";
                if (timeHour < 10){
                    timeString += "0" + timeHour;
                } else {
                    timeString += timeHour;
                }

                if (timeMin < 10){
                    timeString += ":0" + timeMin;
                } else {
                    timeString += ":" + timeMin;
                }
            } else if ((now1/1000000000 - time1/1000000000) == 0){
                long timeHour = time1 / 10000000 % 100;
                long timeMin = time1 / 100000 % 100;
                if (timeHour < 10){
                    timeString += "0" + timeHour;
                } else {
                    timeString += timeHour;
                }

                if (timeMin < 10){
                    timeString += ":0" + timeMin;
                } else {
                    timeString += ":" + timeMin;
                }
            } else if((now1/1000000000 - time1/1000000000) > 1){
                long timeMonth = (time1 / 1000 / 10000 / 100 / 100 ) % 100;
                long timeDay = time1 / 1000 / 10000 / 100 % 100;
                long timeHour = time1 / 10000000 % 100;
                long timeMin = time1 / 100000 % 100;
                timeString += timeMonth +"月";
                timeString += timeDay + "日";
                if (timeHour < 10){
                    timeString += "0" + timeHour;
                } else {
                    timeString += timeHour;
                }

                if (timeMin < 10){
                    timeString += ":0" + timeMin;
                } else {
                    timeString += ":" + timeMin;
                }
            } else if ((now1/1000000000 - time1/1000000000) > 365){
                long timeYear = time1 / 1000 / 10000 / 100 / 100 / 100;
                long timeMonth = (time1 / 1000 / 10000 / 100 / 100 ) % 100;
                long timeDay = time1 / 1000 / 10000 / 100 % 100;
                long timeHour = time1 / 10000000 % 100;
                long timeMin = time1 / 100000 % 100;
                timeString += timeYear + "年";
                timeString += timeMonth + "月";
                timeString += timeDay + "日";
                if (timeHour < 10){
                    timeString += "0" + timeHour;
                } else {
                    timeString += timeHour;
                }

                if (timeMin < 10){
                    timeString += ":0" + timeMin;
                } else {
                    timeString += ":" + timeMin;
                }
            }
        }catch (NumberFormatException e){
            return "";
        }
        return timeString;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String getFileName(String url){
        if (url == null){
            return null;
        }
        if (url.indexOf("/")==-1){
            return null;
        }
        return url.substring(url.lastIndexOf("/")+1,url.length());
    }

    /**
     * 获取存储路径
     */
    public static String getDataPath() {
        String path;
        if (isExistSDcard())
            path = Environment.getExternalStorageDirectory().getPath() + "/albumSelect";
        else
            path = MyApp.getMyApp().getFilesDir().getPath();
        if (!path.endsWith("/"))
            path = path + "/";
        return path;
    }

    /**
     * 检测SDcard是否存在
     *
     * @return
     */
    public static boolean isExistSDcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED))
            return true;
        else {
            return false;
        }
    }

    /**
     * 用于解析群聊中的时间，群聊中发送过来的时间用的不是GTM8的时间，所以要进行转化
     * 这个方法不是很好，需要优化
     * @param time
     * @return
     */
    public static String groupTime(String time){
        String[] times = time.split(":");
        Long hour = Long.valueOf(times[0]) + 8;
        int min = Integer.valueOf(times[1]) + 3;
        int second = Integer.valueOf(times[2]);
        time = hour + "";
        if (min < 10) {
            time += "0" + min;
        } else {
            time += "" + min;
        }

        if (second < 10) {
            time += "0" + second;
        } else {
            time += "" + second;
        }
        time += "000";
        return time;
    }
}
