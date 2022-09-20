package myjob.common.util;

import java.text.SimpleDateFormat;

public class IDBuilder {
    public static volatile int Guid = 100;

    public static Long getGuid() {

        IDBuilder.Guid+=1;

        long now = System.currentTimeMillis();
        //获取4位年份数字
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");
        //获取时间戳
        String time=dateFormat.format(now);

        String info=now+"";

        //获取三位数
        int ran=0;
        if(IDBuilder.Guid > 999){
            IDBuilder.Guid = 100;
        }
        ran= IDBuilder.Guid;

        return Long.parseLong(time+info.substring(2, info.length())+ran);
    }
}
