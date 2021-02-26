package com.board.service;

import java.util.List;

import com.board.domain.BoardVo;

public interface BoardService {

	// 게시물 목록
	public List<BoardVo> list() throws Exception;
	
	// 게시물 작성
	public void write(BoardVo vo) throws Exception;
}
