package com.example.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.dao.CircularBoardDao;
import com.example.app.dao.GenericDao;
import com.example.app.domain.CircularBoard;

@Service
public class CircularBoardServiceImpl extends GenericService<CircularBoard> implements CircularBoardService {
	// 日付を取得
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	String now = df.format(date);
	// 共通画像名
	private String commonImgName = "b-" + now;
	@Autowired
	CircularBoardDao circularBoardDao;

	public CircularBoardServiceImpl(CircularBoardDao circularBoardDao) {
		this.circularBoardDao = circularBoardDao;
	}

	@Override
	protected GenericDao<CircularBoard> getDao() {
		return circularBoardDao;
	}

	@Override
	public List<CircularBoard> getCircularBoardList() throws Exception {
		return circularBoardDao.selectAll();
	}

	@Override
	public CircularBoard getCircularBoardById(Integer id) throws Exception {
		return circularBoardDao.selectById(id);
	}

	@Override
	public CircularBoard getLatestData() throws Exception {
		return circularBoardDao.selectByLatestData();
	}

	@Override
	public void addCircularBoard(CircularBoard circularBoard) throws Exception {
		
		// 最新の回覧板から日付が変わっているか確認
		int count = 0;
		CircularBoard latestBoard = getLatestData();
		if (!(latestBoard == null)) {
			String latestDate = df.format(latestBoard.getCreated());
			String latestCountStr = latestBoard.getName().substring(latestBoard.getName().length() - 2);
			// 最新のアップロード回数を取得
			if (latestCountStr.startsWith("0")) {
				count = Integer.parseInt(latestCountStr.substring(latestCountStr.length() - 1));
			} else {
				count = Integer.parseInt(latestCountStr);
			}
			if (!now.equals(latestDate)) {
				count = 0;
			}
		}
		count++;
		
		String countStr = "";
		if(count < 10) {
			countStr = "0" + count;
		}else {
			countStr = Integer.toString(count);
		}
		String imgName = commonImgName + countStr;
		System.out.println(imgName); // デバッグ
		// 画像名とパスをセット
		circularBoard.setName(imgName);
		circularBoard.setFilePath("uploads/boards/" + imgName);
		// 画像の登録処理
		MultipartFile upfile = circularBoard.getUpfile();
		if (!upfile.isEmpty()) {
			// 画像ファイルの保存
			Path path = Paths.get("uploads/boards/" + imgName);
			upfile.transferTo(path);
		}
		circularBoardDao.insert(circularBoard);
	}

	@Override
	public void deleteCircularBoard(CircularBoard circularBoard) throws Exception {
		circularBoardDao.delete(circularBoard);
	}

}
