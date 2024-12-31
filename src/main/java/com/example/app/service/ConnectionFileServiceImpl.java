package com.example.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.dao.ConnectionFileDao;
import com.example.app.dao.GenericDao;
import com.example.app.domain.ConnectionFile;

@Service
public class ConnectionFileServiceImpl extends GenericService<ConnectionFile> implements ConnectionFileService {
	// 日付を取得
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	String now = df.format(date);
	// 共通画像名
	private String commonImgName = "f-" + now;
	@Autowired
	ConnectionFileDao connectionFileDao;

	public ConnectionFileServiceImpl(ConnectionFileDao connectionFileDao) {
		this.connectionFileDao = connectionFileDao;
	}

	@Override
	protected GenericDao<ConnectionFile> getDao() {
		// TODO 自動生成されたメソッド・スタブ
		return connectionFileDao;
	}

	@Override
	public List<ConnectionFile> getConnectionFileList() throws Exception {
		return connectionFileDao.selectAll();
	}

	@Override
	public ConnectionFile getConnectionFileById(Integer id) throws Exception {
		return connectionFileDao.selectById(id);
	}

	@Override
	public ConnectionFile getLatestData() throws Exception {
		return connectionFileDao.selectByLatestData();
	}

	@Override
	public void addConnectionFile(ConnectionFile connectionFile) throws Exception {
		// 最新の資料から日付が変わっているか確認
		int count = 0;
		ConnectionFile latestFile = getLatestData();
		if (!(latestFile == null)) {
			String latestDate = df.format(latestFile.getCreated());
			String latestCountStr = latestFile.getName().substring(10, 12);
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
		String imgName = commonImgName + countStr + ".pdf";
		System.out.println(imgName); // デバッグ
		// 画像名とパスをセット
		connectionFile.setName(imgName);
		connectionFile.setFilePath("uploads/files/" + imgName);
		// 画像の登録処理
		MultipartFile upfile = connectionFile.getUpfile();
		if (!upfile.isEmpty()) {
			// 画像ファイルの保存
			Path path = Paths.get("uploads/files/" + imgName);
			upfile.transferTo(path);
		}
		connectionFileDao.insert(connectionFile);
	}

	@Override
	public void deleteConnectionFile(ConnectionFile connectionFile) throws Exception {
		connectionFileDao.delete(connectionFile);
	}

}
