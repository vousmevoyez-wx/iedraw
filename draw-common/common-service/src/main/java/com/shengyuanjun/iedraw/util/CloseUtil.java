package com.shengyuanjun.iedraw.util;

import java.io.Closeable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloseUtil {
	private static Logger logger = LoggerFactory.getLogger(CloseUtil.class);

	public static void close(Closeable... items) {
		if (items != null) {
			for (Closeable item : items) {
				try {
					item.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		}
	}
}
