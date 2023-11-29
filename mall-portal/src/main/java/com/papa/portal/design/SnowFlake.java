package com.papa.portal.design;

import io.swagger.annotations.ApiModelProperty;
import org.apiguardian.api.API;

public class SnowFlake {
    @ApiModelProperty("起始的时间戳")
    private final static long START_STAMP = 1638288000000L;


    private final static long SEQUENCE_BIT = 12;

    private final static long MACHINE_BIT = 10;


    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);


    private final static long MACHINE_LEFT = SEQUENCE_BIT;

    private final static long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT;


    private long machineId;

    private long sequenceId = 0L;

    private long lastStamp = -1L;


    public SnowFlake(long machineId){
        if(machineId > MAX_MACHINE_NUM || machineId < 0){
            throw new RuntimeException("机器超过最大数量");
        }
        this.machineId = machineId;
    }

    public synchronized String  nextId(){
        long currStamp = getNewStamp();
        if(currStamp < lastStamp) throw new RuntimeException("时钟后移,拒绝生成");
        if(currStamp == lastStamp){
            sequenceId = (sequenceId+1) & MAX_SEQUENCE;
            if(sequenceId == 0L){
                //同一毫秒下的序列号满了
                currStamp = getNewStamp();
            }
        }else{
            sequenceId = 0L;
        }

        lastStamp = currStamp;

        long res = (currStamp-START_STAMP)<<TIMESTAMP_LEFT
                |machineId<<MACHINE_LEFT
                |sequenceId;
        return Long.toString(res);
    }


    private long getNewStamp(){
        return System.currentTimeMillis();
    }
    private long getNextMill(){
        long mill;
        do {
            mill = getNewStamp();
        }while(mill <= lastStamp);
        return mill;
    }
}
