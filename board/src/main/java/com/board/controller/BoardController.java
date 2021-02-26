package com.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	// 게시물 조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	//@RequestParam을 이용하면 주소에 있는 특정한 값을 가져올 수 있다. ex) 주소에서 bno값을 찾아 그 값을 int bno에 넣어준다.
	public void getView(@RequestParam("bno") int bno, Model model) throws Exception {
		
		// vo를 이용하여 서비스에서 데이터를 받고, 모델을 이용하여 뷰에 데이터를 넘겨준다.
		BoardVo vo = service.view(bno);
		model.addAttribute("view", vo);
	}
	
	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(@RequestParam("bno") int bno, Model model) throws Exception {
		BoardVo vo = service.view(bno);
		model.addAttribute("view", vo);
	}
	
	// 게시물 수정
		@RequestMapping(value = "/modify", method = RequestMethod.POST)
		public String postModify(BoardVo vo) throws Exception {
			
			// 뷰에서 컨트롤러로 넘어온 데이터(BoardVo)를 이용해 수정을 끝내고 
			service.modify(vo);
			
			// 현재 bno에 해당되는 조회 페이지로 이동
			return "redirect:/board/view?bno="+vo.getBno();
		}
}
