package com.syte.ai.android_sdk.data.result.text_search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Tagging{

	@SerializedName("Brand")
	private List<String> brand;

	@SerializedName("Color")
	private List<String> color;

	@SerializedName("Cat")
	private List<String> cat;

	@SerializedName("Neckline")
	private List<String> neckline;

	@SerializedName("Length")
	private List<String> length;

	@SerializedName("Sleeve")
	private List<String> sleeve;

	@SerializedName("Pattern")
	private List<String> pattern;

	@SerializedName("Type")
	private List<String> type;

	@SerializedName("Closure")
	private List<String> closure;

	@SerializedName("Style")
	private List<String> style;

	@SerializedName("Look")
	private List<String> look;

	@SerializedName("Texture")
	private List<String> texture;

	@SerializedName("Detail")
	private List<String> detail;

	@SerializedName("Rise")
	private List<String> rise;

	@SerializedName("Shape")
	private List<String> shape;

	@SerializedName("Height")
	private List<String> height;

	@SerializedName("LapelStyle")
	private List<String> lapelStyle;

	@SerializedName("Trim")
	private List<String> trim;

	public List<String> getBrand(){
		return brand;
	}

	public List<String> getColor(){
		return color;
	}

	public List<String> getCat(){
		return cat;
	}

	public List<String> getNeckline(){
		return neckline;
	}

	public List<String> getLength(){
		return length;
	}

	public List<String> getSleeve(){
		return sleeve;
	}

	public List<String> getPattern(){
		return pattern;
	}

	public List<String> getType(){
		return type;
	}

	public List<String> getClosure(){
		return closure;
	}

	public List<String> getStyle(){
		return style;
	}

	public List<String> getLook(){
		return look;
	}

	public List<String> getTexture(){
		return texture;
	}

	public List<String> getDetail(){
		return detail;
	}

	public List<String> getRise(){
		return rise;
	}

	public List<String> getShape(){
		return shape;
	}

	public List<String> getHeight(){
		return height;
	}

	public List<String> getLapelStyle(){
		return lapelStyle;
	}

	public List<String> getTrim(){
		return trim;
	}
}