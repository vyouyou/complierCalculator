package com.youyou.utils;

import com.youyou.conf.BaseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vyouyou on 17-9-3.
 */
public class StringSplit {
    Pattern numberPattern, calcPattern, braketPattern;

    public StringSplit() {
        numberPattern = Pattern.compile("^[0-9]+");
        calcPattern = Pattern.compile("^[\\+\\-\\*/]+");
        braketPattern = Pattern.compile("^[\\(\\)]+");
    }


    public List<BaseData> split(String str) {
        List<BaseData> list = new ArrayList<>();
        Matcher numberMatcher;
        Matcher calcMatcher;
        Matcher braketMatcher;
        while (!str.equals("")) {
            numberMatcher = numberPattern.matcher(str);
            if (numberMatcher.find()) {
                String tmpStr = numberMatcher.group(0);
                list.add(new BaseData("number",tmpStr));
                str = str.replaceFirst(tmpStr, "");
                continue;
            }
            calcMatcher = calcPattern.matcher(str);
            if (calcMatcher.find()) {
                String tmpStr = calcMatcher.group(0);
                list.add(new BaseData(tmpStr,tmpStr));
                str = str.substring(1);
                continue;
            }
            braketMatcher = braketPattern.matcher(str);
            if (calcMatcher.find()) {
                String tmpStr = braketMatcher.group(0);
                list.add(new BaseData(tmpStr,tmpStr));
                str = str.substring(1);
                continue;
            }
            break;
        }
        list.add(new BaseData("$",null));

        return list;
    }

}
