package com.kedu.common.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.kedu.member.dao.MemberDao;
import com.kedu.member.dto.MemberDto;

public class MemberInsertAction implements Action {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		MemberDto mDto = new MemberDto();
		mDto.setName(name);
		mDto.setEmail(email);
		mDto.setPassword(password);
		
		MemberDao mDao = MemberDao.getInstance();
		int result = mDao.insertMember(mDto);
		System.out.println("Result : " + result);
		
		if( result == 1) {
			mDto = mDao.lastInsert();
		}
		
		System.out.println("last mDto : " + mDto);
		
		JSONObject json = new JSONObject();
		json.put("no", mDto.getNo());
		json.put("name", mDto.getName());
		json.put("email", mDto.getEmail());
		json.put("password", mDto.getPassword());
		
		System.out.println("Json action : " + json);
		
		response.setContentType("charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		
		/*MemberDto mDto = new MemberDto();
		
		mDto.setName(request.getParameter("name"));
		mDto.setEmail(request.getParameter("email"));
		mDto.setPassword(request.getParameter("password"));
		
		MemberDao mDao = MemberDao.getInstance();
		mDao.insertMember(mDto);
		
		new MemberListAction().execute(request, response);*/
		
	}

}
