package utils;

import exceptions.InvalidTimeException;

/**
 * 时间工具类
 */
public class DateUtil {
    private static DateUtil dateUtil;

    public DateUtil(){}

    public static DateUtil getInstance(){
        if(null==dateUtil){
            dateUtil=new DateUtil();
        }
        return dateUtil;
    }

    /**
     * 计算时间差
     * @param startTime 开始时间戳
     * @param endTime 结束时间戳
     * @return 总耗时（单位：秒）
     * @throws Exception 无效时间
     */
    public String getElapsedTime(long startTime,long endTime) throws InvalidTimeException{
        if(startTime<0 || endTime<0){
            throw new InvalidTimeException("时间数值需要不能小于0！");
        }
        if(endTime<startTime){
            throw new InvalidTimeException("结束时间需要大于开始时间！");
        }
        long diff=endTime-startTime;
        return (diff/1000)+"."+(diff%1000)+"秒";
    }
}
