package com.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.dao.BoardDAO;
import com.board.domain.BoardVo;

// Repository를 통해 데이터베이스에서 데이터를 가져온 후 컨트롤러에게 전달해 주는 클래스임을 명시
@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;
	
	// 게시물 목록
	@Override
	public List<BoardVo> list() throws Exception {
		
		return dao.list();
	}

	// 게시물 작성
	@Override
	public void write(BoardVo vo) throws Exception {
		
		dao.write(vo);
	}

	// 게시물 조회
	@Override
	public BoardVo view(int bno) throws Exception {
		
		return dao.view(bno);
	}

}
