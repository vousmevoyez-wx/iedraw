package com.shengyuanjun.iedraw.controller;

import java.io.File;
import java.io.RandomAccessFile;
import javax.servlet.http.HttpServletRequest;
import com.shengyuanjun.iedraw.util.AjaxResp;
import com.shengyuanjun.iedraw.util.CloseUtil;
import com.shengyuanjun.iedraw.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

	public static final String FOLDER = "uploadFiles";
	public static final String SUFFIX = ".jpg";
	private Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResp uploadFile(@RequestParam MultipartFile file, @RequestParam String sid, HttpServletRequest request) throws Exception {

		AjaxResp res = new AjaxResp();
		RandomAccessFile rf = null;
		try {
			String path = getImageFullPath(request);
			String fileName = MD5Util.md5(file.getBytes());
			int i = file.getOriginalFilename().lastIndexOf('.');
			String suffix = "";
			if (i > 0) {
				suffix = file.getOriginalFilename().substring(i);
			}
			path += fileName + suffix;
			log.info(path);
			// If the file is existed, do not save it again.
			File storeFile = new File(path);
			if (!storeFile.exists()) {
				rf = new RandomAccessFile(path, "rw");
				rf.write(file.getBytes());
			} else {
				log.info("The file is existed,No need to resave it again.");
			}

			res.getArgs().put("code", "200");
			res.getArgs().put("path", fileName + suffix);
			res.getArgs().put("size", file.getSize() + "");
			res.getArgs().put("sid", sid);
			res.setCode(AjaxResp.OK);
			log.info("Save Upload File[{}] successfully.", path);
		} catch (Exception e) {
			res.setCode(AjaxResp.ERROR);
			log.error("", e);
		} finally {
		   if(rf!=null) CloseUtil.close(rf);
		}
		return res;
	}

	private String getImageFullPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + FOLDER + File.separator;

		File rootFolder = new File(path);
        if(!rootFolder.exists()) {
        	rootFolder.mkdirs();
        }
		return path;
	}
}
