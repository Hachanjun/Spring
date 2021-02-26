package com.board.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.board.domain.BoardVo;

// 외부 I/O 처리(DB, 파일)
@Repository
public class BoardDAOImpl implements BoardDAO {

	// 의존 관계를 자동으로 연결, 자바 기존의 어노테이션, 타입에 맞춰서 연결
	@Inject
	private SqlSession sql;
	
	// boardMapper.xml 에서 설정한 namespace와 동일해야함
	private static String namespace = "com.board.mappers.board";
	
	// 메서드 선언부에서만 사용할 수 있고, 부모 자료형에 선언된 메서드를 재정의 할 때 사용
	@Override
	// 게시물 목록
	public List<BoardVo> list() throws Exception {
		
		return sql.selectList(namespace + ".list");
	}

	// 게시물 작성
	@Override
	public void write(BoardVo vo) throws Exception {
		
		sql.insert(namespace + ".write", vo);
	}

	// 게시물 조회
	@Override
	public BoardVo view(int bno) throws Exception {
		
		return sql.selectOne(namespace + ".view", bno);
	}

	// 게시물 수정
	@Override
	public void modify(BoardVo vo) throws Exception {
		
		sql.update(namespace + ".modify", vo);
	}

}
