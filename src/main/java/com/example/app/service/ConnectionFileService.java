package com.example.app.service;

import java.util.List;

import com.example.app.domain.ConnectionFile;

public interface ConnectionFileService {
	public List<ConnectionFile>getConnectionFileList() throws Exception;
	public ConnectionFile getConnectionFileById(Integer id) throws Exception;
	public void addConnectionFile(ConnectionFile connectionFile) throws Exception;
	public void deleteConnectionFile(ConnectionFile connectionFile) throws Exception;
	public ConnectionFile getLatestData() throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<ConnectionFile> getListByPage(int page, int numPerPage) throws Exception;
}
