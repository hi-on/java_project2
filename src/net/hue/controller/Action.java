package net.hue.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

	public abstract ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception; //추상메서드 정의
}
