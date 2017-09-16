package com.youyou;

import com.youyou.conf.BaseData;
import com.youyou.conf.ExpressBase;
import com.youyou.conf.SymbolEnums;
import com.youyou.utils.StringSplit;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        StringSplit split = new StringSplit();
        List<BaseData> list = split.split("111+3*9-3");
        ExpressBase expressBase = new ExpressBase(list);
        expressBase.startExpress();
    }

}
