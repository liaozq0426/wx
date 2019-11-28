package com.gavin.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gavin.cfg.WxCfgEnum;
import com.gavin.pojo.AccessToken;
import com.gavin.pojo.Button;
import com.gavin.pojo.ClickButton;
import com.gavin.pojo.ComplexButton;
import com.gavin.pojo.Menu;
import com.gavin.pojo.ViewButton;
import com.gavin.pojo.WxApiCfg;
import com.gavin.pojo.WxCfg;
import com.gavin.service.WxCfgService;
import com.gavin.service.WxMenuService;
import com.gavin.service.WxTokenService;
import com.gavin.util.EncryptionUtil;
import com.gavin.util.WxUtil;
import com.google.gson.Gson;

@Service
public class WxMenuServiceImpl implements WxMenuService{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private WxTokenService wxTokenService;
	
	@Autowired
	private WxCfgService wxCfgService;

	/**
	 * @title 根据平台创建微信公众号菜单
	 * @author gavin
	 * @date 2019年5月23日
	 * @param platform
	 * @return
	 * @throws Exception
	 */
	@Override
	public Menu makeMenu(String platform) throws Exception {
		// 查询一级菜单
		System.out.println(platform);
		WxCfg parentParam = new WxCfg();
		parentParam.setType(WxCfgEnum.MENU.getType());
		parentParam.setEnabled(true);
		parentParam.setPlatform(platform);
		parentParam.setOrderBy("sort asc");
		List<WxCfg> cfgList = this.wxCfgService.select(parentParam);
		if (cfgList.size() > 0) {
			Menu menu = new Menu();

			List<Button> buttons = new ArrayList<Button>();
			for (WxCfg cfg : cfgList) {
				WxCfg subParam = new WxCfg();
				subParam.setType(WxCfgEnum.MENU.getType());
				subParam.setParentId(cfg.getId());
				subParam.setEnabled(true);
				subParam.setPlatform(platform);
				// 查询二级菜单
				List<WxCfg> subCfgList = this.wxCfgService.select(subParam);
				if (subCfgList.size() > 0) {
					// 构建多级按钮
					List<Button> subButtons = new ArrayList<Button>();
					for (WxCfg subCfg : subCfgList) {
						Button sub = createButton(subCfg);
						subButtons.add(sub);
					}
					ComplexButton complexButton = new ComplexButton();
					complexButton.setName(cfg.getDesc());
					complexButton.setSub_button(subButtons.toArray(new Button[subButtons.size()]));
					buttons.add(complexButton);
				} else {
					Button button = createButton(cfg);
					buttons.add(button);
				}
			}
			// 获取accessToken
			AccessToken token = this.wxTokenService.readAccessToken(AccessToken.TYPE_ACCESS_TOKEN, platform);
			menu.setButton(buttons.toArray(new Button[buttons.size()]));
			Gson gson = new Gson();
			String menuJson = gson.toJson(menu);
			System.out.println(menuJson);
			Map<String , Object> message = WxUtil.createMenu(menu, token.getAccess_token());
			logger.info(message.toString());
			if (message.get("errcode") != null 
					&& (Integer)message.get("errcode") == 0
					&& "ok".equalsIgnoreCase((String)message.get("errmsg"))) {
				logger.info("微信菜单创建成功！");
				return menu;
			}else {				
				logger.error("微信菜单创建失败");
			}
		}
		return null;
	}
	
	/**
	 * @title 根据配置信息创建button，这里只实现了最常用的click和view两种类型的按钮
	 * @author gavin
	 * @date 2019年5月23日
	 * @param cfg
	 * @return
	 * @throws Exception
	 */
	private Button createButton(WxCfg cfg) throws Exception {
		String btnType = cfg.getName();
		if (btnType.startsWith(Button.TYPE_CLICK)) {
			// 普通点击按钮
			ClickButton click = new ClickButton();
			click.setName(cfg.getDesc());
			click.setType(Button.TYPE_CLICK);
			
			// key可以根据自己的需要设置,这里我设置为[name_desc]
			click.setKey(cfg.getName() + "_" + cfg.getDesc());
			return click;
		} else if (btnType.startsWith(Button.TYPE_VIEW)) {
			// view按钮
			ViewButton view = new ViewButton();
			view.setName(cfg.getDesc());
			view.setType(Button.TYPE_VIEW);
			// 动态生成url
			WxCfg cfgParam = new WxCfg();
			cfgParam.setPlatform(cfg.getPlatform());
			cfgParam.setType(WxCfgEnum.WX_KEY_APPID.getType());
			cfgParam.setName(WxCfgEnum.WX_KEY_APPID.getName());
			WxCfg queryCfg = this.wxCfgService.selectOne(cfgParam);
			if(queryCfg != null) {
				String appId = EncryptionUtil.base64Decode(queryCfg.getValue());
				String wxType = cfg.getWxType();
				String url = null;
				if(WxCfgEnum.WX_TYPE_SERVICE.getType().equals(wxType)) {
					// 如果是服务号，生成网页授权的格式url
					String redirectUrl = cfg.getValue();
					if(!StringUtils.isBlank(redirectUrl)) {
						redirectUrl = redirectUrl + "?state=" + cfg.getName() + "&platform=" + cfg.getPlatform();
					}
					url = WxUtil.makeViewUrl(appId, WxApiCfg.SCOPE_BASE, redirectUrl,
							cfg.getName());
				}else if(WxCfgEnum.WX_TYPE_SUBSCRIBE.getType().equals(wxType)) {
					// 如果是订阅号，生成普通url格式
					url = cfg.getValue();
					if(!StringUtils.isBlank(url))
						url = url + "?state=" + cfg.getName() + "&platform=" + cfg.getPlatform();
				}
				logger.info(url);
				view.setUrl(url);
				return view;				
			}
		}
		return null;
	}
	
	
	
	
	
}
