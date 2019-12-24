package com.bigdata2019.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata2019.mysite.repository.GuestbookDao;
import com.bigdata2019.mysite.vo.GuestbookVo;
import com.bigdata2019.mysite.web.util.WebUtil;

public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("a");
		if("deleteform".equals(action)) {
			String no = request.getParameter("no");
			
			request.setAttribute("no", no);
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteform.jsp");
		}else {
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("a");
		//sysout으로 콘솔에서 확인 가능
//		System.out.println(action);
		
		if("insert".equals(action)) {
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String contents = request.getParameter("content");
			GuestbookVo vo = new GuestbookVo(name, password, contents);
			new GuestbookDao().insert(vo);
			
			response.sendRedirect(request.getContextPath()+"/guestbook");
		} else if("delete".equals(action)) {
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			
			new GuestbookDao().delete(Long.parseLong(no), password);
			
			WebUtil.redirect(request, response, request.getContextPath()+"/guestbook");
		}
	}
}