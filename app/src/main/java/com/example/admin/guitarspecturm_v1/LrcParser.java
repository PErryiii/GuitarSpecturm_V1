package com.example.admin.guitarspecturm_v1;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LrcParser {
    public List<LrcBean> parseStr2Bean(String lrcString){
        //创建LrcBean数组，用于存放歌词实体
        List<LrcBean> lrcBeanList = new ArrayList<>();
        //还原转义字符
        String lrcText = lrcString.replaceAll("&#58;",":")
                .replaceAll("&#10;","\n")
                .replaceAll("&#46;",".")
                .replaceAll("&#32;"," ")
                .replaceAll("&#45;","-")
                .replaceAll("&#13;","\r")
                .replaceAll("&#39;","'");
        //根据换行符\n将文本截取为每一行歌词
        String[] lrcFragments = lrcText.split("\n");
        //通过截取每一行歌词，将歌词数据提取出来
        for (int i = 0; i < lrcFragments.length; i++){
            String lrc = lrcFragments[i];
            if (lrc.contains(".")){
                String min = lrc.substring(lrc.indexOf("[")+1, lrc.indexOf("[")+3);
                String seconds = lrc.substring(lrc.indexOf(":")+1, lrc.indexOf(":")+3);
                String mills = lrc.substring(lrc.indexOf(".")+1, lrc.indexOf(".")+3);
                long beginDt = Long.valueOf(min)*60*1000 + Long.valueOf(seconds)*1000 + Long.valueOf(mills)*10;
                String text = lrc.substring(lrc.indexOf("]")+1);
                if (text == null || "".equals(text)){
                    text = "NullMusic";
                }
                LrcBean lrcBean = new LrcBean();
                lrcBean.setLrc(text);
                lrcBean.setBeginDt(beginDt);
                lrcBeanList.add(lrcBean);
                if (lrcBeanList.size() > 1){
                    lrcBeanList.get(lrcBeanList.size() - 2).setEndDt(beginDt);
                }
                if (i == lrcFragments.length - 1){
                    lrcBeanList.get(lrcBeanList.size()-1).setEndDt(beginDt+100000);
                }
            }
        }
        return lrcBeanList;
    }
    public List<LrcBean> ParseLrc2Bean(Context context, int fileInRaw){
        //创建LrcBean数组，用于存放歌词实体
        List<LrcBean> lrcBeanList = new ArrayList<>();
        //读取文件流
        try{
            String encoding = "GBK";
            InputStream in = context.getResources().openRawResource(fileInRaw);
            InputStreamReader read = new InputStreamReader(in, encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String regex = "\\[(\\d{1,2}):(\\d{1,2}).(\\d{1,2})\\]"; // 正则表达式
            Pattern pattern = Pattern.compile(regex); // 创建 Pattern 对象
            String lineStr = null; // 每次读取一行字符串
            while ((lineStr = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(lineStr);
                while (matcher.find()) {
                    // 用于存储当前时间和文字信息的容器
                    Map<Long, String> map = new HashMap<Long, String>();
                    // System.out.println(m.group(0)); // 例：[02:34.94]
                    // [02:34.94] ----对应---> [分钟:秒.毫秒]
                    String min = matcher.group(1); // 分钟
                    String sec = matcher.group(2); // 秒
                    String mill = matcher.group(3); // 毫秒，注意这里其实还要乘以10
                    long time = Long.valueOf(min) * 60 * 1000 + Long.valueOf(sec) * 1000 + Long.valueOf(mill) * 10;
                    // 获取当前时间的歌词信息
                    String text = lineStr.substring(matcher.end());

                    LrcBean lrcBean = new LrcBean();
                    lrcBean.setLrc(text);
                    lrcBean.setBeginDt(time);
                    lrcBeanList.add(lrcBean);
                    if (lrcBeanList.size() > 1) {
                        lrcBeanList.get(lrcBeanList.size() - 2).setEndDt(time);
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return lrcBeanList;
    }
}
