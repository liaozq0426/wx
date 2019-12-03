package com.gavin.test;

public class TestEmoji {

	public static void main(String[] args) {
		String emoji1 = String.valueOf(Character.toChars(0x1F389));	// 庆祝
		String emoji2 = String.valueOf(Character.toChars(0x1F602));	// 笑哭
		String emoji3 = String.valueOf(Character.toChars(0x1F448));	// 向左
		System.out.println(("欢迎！" + emoji1+emoji2+emoji3));	
	}
}
