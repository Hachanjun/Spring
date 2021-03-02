package com.board.dao;

import java.util.HashMap;
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

	// 게시물 삭제
	@Override
	public void delete(int bno) throws Exception {
		
		sql.delete(namespace + ".delete", bno);
	}

	// 게시물 총 갯수
	@Override
	public int count() throws Exception {
		
		return sql.selectOne(namespace + ".count");
	}

	// 게시물 목록 + 페이징
	@Override
	public List<BoardVo> listPage(int displayPost, int postNum) throws Exception {
		
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		
		data.put("displayPost", displayPost);
		data.put("postNum", postNum);
		
		return sql.selectList(namespace + ".listPage", data);
	}

	// 게시물 목록 + 페이징 + 검색
	@Override
	public List<BoardVo> listPageSearch(int displayPost, int postNum, String searchType, String keyword)
			throws Exception {
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("displayPost", displayPost);
		data.put("postNum", postNum);
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		
		return sql.selectList(namespace + ".listPageSearch", data);
	}

	// 게시물 총 갯수 + 검색 적용
	@Override
	public int searchCount(String searchType, String keyword) throws Exception {
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		
		return sql.selectOne(namespace + ".searchCount", data);
	}

}
