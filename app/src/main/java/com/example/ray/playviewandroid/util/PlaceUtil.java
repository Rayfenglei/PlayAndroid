package com.example.ray.playviewandroid.util;

/**地点相关类
 */
public class PlaceUtil {

	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_DISTRICT = 2;
	public static final int LEVEL_TOWN = 3;
	public static final int LEVEL_ROAD = 4;
	public static final int[] LEVELS = {
		LEVEL_PROVINCE,
		LEVEL_CITY,
		LEVEL_DISTRICT,
		LEVEL_TOWN,
		LEVEL_ROAD,
	};
	
	public static final String NAME_PROVINCE = "省";
	public static final String NAME_CITY = "市";
	public static final String NAME_DISTRICT = "区";
	public static final String NAME_TOWN = "镇";
	public static final String NAME_ROAD = "街";
	public static final String[] LEVEL_NAMES = {
		NAME_PROVINCE,
		NAME_CITY,
		NAME_DISTRICT,
		NAME_TOWN,
		NAME_ROAD,
	};
	
	
	/**
	 * @param level
	 * @return
	 */
	public static boolean isContainLevel(int level) {
		for (int existLevel : LEVELS) {
			if (level == existLevel) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param level
	 * @return
	 */
	public static String getNameByLevel(int level) {
		return isContainLevel(level) ? LEVEL_NAMES[level - LEVEL_PROVINCE] : "";
	}	

}
