package com.gavin.test;

import com.gavin.pojo.Button;
import com.gavin.pojo.ClickButton;
import com.gavin.pojo.ComplexButton;
import com.gavin.pojo.Menu;
import com.gavin.pojo.ViewButton;
import com.google.gson.Gson;

public class MenuTest {
	
	public static void main(String[] args) {
		
		// 复合按钮
		ComplexButton view1 = new ComplexButton();	
		view1.setName("学习资料");
		
		ViewButton view11 = new ViewButton();
		view11.setName("JAVA");
		view11.setType(Button.TYPE_VIEW);
		view11.setUrl("http://www.gavin.com/java.html");
		
		ViewButton view12 = new ViewButton();
		view12.setName("MySQL");
		view12.setType(Button.TYPE_VIEW);
		view12.setUrl("http://www.gavin.com/mysql.html");
		
		view1.setSub_button(new Button[] {view11 , view12}); // 设置子按钮数组
		
		// view按钮
		ViewButton view2 = new ViewButton();
		view2.setName("在线题库");
		view2.setType(Button.TYPE_VIEW);
		view2.setUrl("http://www.gavin.com/information.html");
		
		// click按钮
		ClickButton click = new ClickButton();
		click.setName("关于");
		click.setType(Button.TYPE_CLICK);
		click.setKey("click_关于");
		
		// menu
		Menu menu = new Menu();
		menu.setButton(new Button[] {view1 , view2 , click});
		Gson gson = new Gson();
		String menuJsonStr = gson.toJson(menu);
		System.out.println(menuJsonStr);
	}
}
