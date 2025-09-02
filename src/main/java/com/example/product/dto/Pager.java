package com.example.product.dto;
import java.util.stream.IntStream;
import lombok.Data;

// 전체를 보여줄 수 없고 부분을 보여줘야 하는 곳에 사용할 수 있다. 
@Data
public class Pager {
	private int totalRows;		//전체 행수
	private int totalPageNo;	//전체 페이지 수
	private int totalGroupNo;	//전체 그룹 수
	private int startPageNo;	//그룹의 시작 페이스 번호
	private int endPageNo;		//그룹의 끝 페이지 번호
	private int[] pageArray;	//startPageNo ~ endPageNo 배열
	private int pageNo;			//현재 페이지 번호
	private int pagesPerGroup;	//그룹당 페이지 수
	private int groupNo;		//현재 그룹 번호
	private int rowsPerPage;	//페이지당 행 수
	private int startRowNo;		//페이지의 시작 행 번호(1, ..., n)
	private int startRowIndex;	//페이지의 시작 행 인덱스(0, ..., n-1) for mysql
	private int endRowNo;		//페이지의 마지막 행 번호
	private int endRowIndex;	//페이지의 마지막 행 인덱스

	// 검색에 의해서 일부분을 페이징할 수 있다. ex) 실제 테이블에 1000개라고 했을 때 600개를 검색 할 수 있다.
	// totalRows는 데이터를 가져와야 한다면 전체 행 수를 가져와야 한다. -개발자가 지정  /rowsPerPage는  -개발자가 고정으로 지정할 수 있다. / pagePerGroup은 개발자가 고정해서 넣는다 / pageNo사용자가 페이지 번호를 클릭한다.
	public Pager(int rowsPerPage, int pagesPerGroup, int totalRows, int pageNo) {
		this.rowsPerPage = rowsPerPage;
		this.pagesPerGroup = pagesPerGroup;
		this.totalRows = totalRows;
		this.pageNo = pageNo;

		totalPageNo = totalRows / rowsPerPage;
		if(totalRows % rowsPerPage != 0) totalPageNo++;
		
		totalGroupNo = totalPageNo / pagesPerGroup;
		if(totalPageNo % pagesPerGroup != 0) totalGroupNo++;
		
		groupNo = (pageNo - 1) / pagesPerGroup + 1;
		
		startPageNo = (groupNo-1) * pagesPerGroup + 1;
		
		endPageNo = startPageNo + pagesPerGroup - 1;
		if(groupNo == totalGroupNo) endPageNo = totalPageNo;
		
		pageArray = IntStream.rangeClosed(startPageNo, endPageNo).toArray();
		
		startRowNo = (pageNo - 1) * rowsPerPage + 1;
		startRowIndex = startRowNo - 1;
		endRowNo = pageNo * rowsPerPage;
		endRowIndex = endRowNo - 1; 
	}
}








