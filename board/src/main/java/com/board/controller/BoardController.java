package com.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardVo;
import com.board.domain.Page;
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
		return "redirect:/board/view?bno=" + vo.getBno();
	}
		
	// 게시물 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("bno") int bno) throws Exception {
		
		service.delete(bno);
		return "redirect:/board/list";
	}
	
	// 게시물 목록 + 페이징
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void getListPage(Model model, @RequestParam("num") int num) throws Exception {
		
		Page page = new Page();
		
		page.setNum(num);
		page.setCount(service.count());
		
		List<BoardVo> list = null;
		list = service.listPage(page.getDisplayPost(), page.getPostNum());
		model.addAttribute("list", list);
		model.addAttribute("pageNum", page.getPageNum());
		
		/*
		// 시작 및 끝 번호
		model.addAttribute("startPageNum", page.getStartPageNum());
		model.addAttribute("endPageNum", page.getEndPageNum());
		
		// 이전 및 다음
		model.addAttribute("prev", page.getPrev());
		model.addAttribute("next", page.getNext());
		*/
		model.addAttribute("page", page);
		// 현재 페이지
		model.addAttribute("select", num);
		/*
		// 게시물 총 갯수
		int count = service.count();
		
		// 한 페이지에 출력할 게시물 갯수
		int postNum = 10;
		
		// 하단 페이징 번호([게시물 총 갯수 / 한 페이지에 출력할 갯수]의 올림
		int pageNum = (int)Math.ceil( (double)count/postNum );
		
		// 출력할 게시물
		int displayPost = (num - 1) * postNum;
		
		// 한 번에 표시할 페이징 번호의 갯수
		int pageNum_cnt = 10;
		
		// 표시되는 페이지 번호 중 마지막 번호
		int endPageNum = (int)(Math.ceil( (double)num / (double)pageNum_cnt ) * pageNum_cnt );
		
		// 표시되는 페이지 번호 중 첫 번째 번호
		int startPageNum = endPageNum - ( pageNum_cnt - 1 );
		
		// 마지막 번호 재계산
		int endPageNum_tmp = (int)(Math.ceil( (double)count / (double)pageNum_cnt ) );
		if ( endPageNum > endPageNum_tmp ) {
			endPageNum = endPageNum_tmp;
		}
		
		boolean prev = startPageNum == 1 ? false : true;
		boolean next = endPageNum * pageNum_cnt >= count ? false : true;
		
		List<BoardVo> list = null;
		list = service.listPage(displayPost, postNum);
		model.addAttribute("list", list);
		model.addAttribute("pageNum", pageNum);
		
		// 시작 및 끝 번호
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		
		// 이전 및 다음
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		
		// 현재 페이지
		model.addAttribute("select", num);
		 */
	}
	// 게시물 목록 + 페이징 + 검색
	@RequestMapping(value = "/listPageSearch", method = RequestMethod.GET)
	// value는 받고자 할 데이터의 키 / required는 해당 데이터의 필수 여부 / defaultValue는 만약 데이터가 들어오지 않았을 경우 대신할 기본 값
	// defaultValue 값이 있으면 URL에 해당 값이 없더라도 기본 값을 지정할 수 있으므로 에러가 발생하지 않을 수 있다.
	public void getListPageSearch(Model model, @RequestParam("num") int num,
			@RequestParam(value = "searchType", required = false, defaultValue = "title") String searchType, 
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword)
			throws Exception {
		
		Page page = new Page();
		
		page.setNum(num);
		//page.setCount(service.count());
		page.setCount(service.searchCount(searchType, keyword));
		
		// 검색 타입과 검색어
		//page.setSearchTypeKeyword(searchType, keyword);
		page.setSearchType(searchType);
		page.setKeyword(keyword);
		
		List<BoardVo> list = null;
		list = service.listPageSearch(page.getDisplayPost(), page.getPostNum(), searchType, keyword);
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("select", num);
		
		//model.addAttribute("searchType", searchType);
		//model.addAttribute("keyword", keyword);
	}
}
