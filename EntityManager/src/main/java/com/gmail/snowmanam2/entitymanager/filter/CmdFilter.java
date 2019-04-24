package com.gmail.snowmanam2.entitymanager.filter;

public class CmdFilter extends CmdBase {
	public CmdFilter() {
		super();
		addChild("mode", new CmdMode());
		addAlias("mode", "m");
		addChild("clear", new CmdClear());
		addAlias("clear", "c");
		addChild("list", new CmdList());
		addAlias("list", "l");
		addChild("add", new CmdAdd());
		addAlias("add", "a");
		addChild("remove", new CmdRemove());
		addAlias("remove", "r");
	}
}
