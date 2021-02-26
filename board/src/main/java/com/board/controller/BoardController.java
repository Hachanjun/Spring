package com.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.board.domain.BoardVo;
import com.board.service.BoardService;

// 해당 클래스를 웹 요청을 처리하는 컨트롤러로 사용
@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Inject
	BoardService service;

	// URL을 컨트롤러에 매핑 해주는 역할
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	// model은 컨트롤러와 뷰를 연결해주는 역할
	// 게시물 목록
	public void getList(Model model) throws Exception {
		List<BoardVo> list = null;
		list = service.list();
		
		model.addAttribute("list", list);
	}
	
	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite() throws Exception {
		
	}
	
	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(BoardVo vo) throws Exception {
		service.write(vo);
		
		return "redirect:/board/list";
	}
}
