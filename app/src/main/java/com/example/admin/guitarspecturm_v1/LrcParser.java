package com.example.admin.guitarspecturm_v1;

import java.util.ArrayList;
import java.util.List;

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
}
