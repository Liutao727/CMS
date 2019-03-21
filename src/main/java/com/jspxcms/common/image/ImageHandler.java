package com.jspxcms.common.image;

public interface ImageHandler {
	boolean crop(String src, String dest, int x, int y, int width, int height);

	boolean resize(String src, String dest, Integer width, Integer height, boolean exact);

	boolean resize(String src, String dest, ScaleParam scaleParam);

	boolean composite(String overlay, String src, String dest, Integer x, Integer y, Integer dissolve);

	boolean composite(String overlay, String src, String dest, WatermarkParam watermarkParam);
}
