package zh.dragon.zl.entry;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zwl
 * @date 2024年09月05日 17:00:03
 * @packageName zh.dragon.zl.entry
 * @className FileInformation
 */
@Data
@NoArgsConstructor
public class FileInformation {


	/**
	 * 目录名称
	 */
	private Integer id;
	/**
	 * 目录名称
	 */
	private String name;

	/**
	 * 目录路径
	 */
	private String path;
}
