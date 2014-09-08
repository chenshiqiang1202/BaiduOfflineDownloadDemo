package com.csq.baiduoffline.utils.pinyin;

import java.util.ArrayList;

public class PinyinUtil
{
	// 汉字返回拼音，字母原样返回，都转换为小写
	public static String getPinYin(String input)
	{
		ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0)
		{
			for (HanziToPinyin.Token token : tokens)
			{
				if (HanziToPinyin.Token.PINYIN == token.type)
				{
					sb.append(token.target);
				} else
				{
					sb.append(token.source);
				}
			}
		}
        String pinyin = sb.toString().toLowerCase();
		return pinyin;
	}

    /*public static void main(String[] args) {
        System.out.println(getPinYin("csq"));
        System.out.println(getPinYin("陈士强"));
    }*/
}
