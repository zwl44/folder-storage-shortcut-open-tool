package zh.dragon.zl.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import zh.dragon.zl.entry.FileInformation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author zwl
 * @date 2024年07月10日 19:47:38
 * @packageName sample.ch.makery.address.util
 * @className JsonUtil
 */
public class JsonUtil {

	public static <T> void saveToJson(String path, List<T> list) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(path), list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <T> List<T> loadFromJson(String path, Class<T> t) {
		List<T> data = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			data = mapper.readValue(new File(path), new TypeReference<List<T>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static List<FileInformation> loadFromJson(String path) {
		List<FileInformation> data = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			data = mapper.readValue(new File(path), new TypeReference<List<FileInformation>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static List<FileInformation> loadFromJson(InputStream path) {
		List<FileInformation> data = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			data = mapper.readValue(path, new TypeReference<List<FileInformation>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}


	public static <T> void saveToJson(OutputStream path, List<T> list) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(path, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
