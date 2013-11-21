package jp.dmtc.ing.promoa.data;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class MData {

	public final LinkedHashMap<String, String> image_urls = new LinkedHashMap<String, String>();
	public final LinkedHashSet<String> select_images_id = new LinkedHashSet<String>();

	public String src_iamge_name;
	public String complete_iamge_name;

}
