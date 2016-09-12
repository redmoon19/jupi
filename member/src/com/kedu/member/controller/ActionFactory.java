package com.kedu.member.controller;

import com.kedu.common.action.Action;
import com.kedu.common.action.MemberDeleteAction;
import com.kedu.common.action.MemberListAction;
import com.kedu.common.action.MemberInsertAction;

public class ActionFactory {

	private static ActionFactory instance = new ActionFactory();
	
	private ActionFactory() {
	}
	
	public static ActionFactory getInstance() {
		return instance;
	}
	
	public Action getAction(String command) {
		Action action = null;
		
		if(command.equals("member_list")){
			action = new MemberListAction();
			
		} else if (command.equals("member_write")){
			action = new MemberInsertAction();
		} else if (command.equals("member_delete")){
			action = new MemberDeleteAction();
		}
		return action;
	}
}
