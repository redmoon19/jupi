package com.kedu.common.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.kedu.member.dao.MemberDao;

public class MemberDeleteAction implements Action {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		
		MemberDao mDao = MemberDao.getInstance();
		int result = mDao.deleteMember(Integer.parseInt(no));
		JSONObject json = new JSONObject();
		if(result == 1) {
			json.put("msg", "success");
		} else {
			json.put("msg", "fail");
		}
		
		new MemberListAction().execute(request, response);
	}

}
