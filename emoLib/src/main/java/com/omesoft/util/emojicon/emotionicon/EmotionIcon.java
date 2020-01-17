package com.omesoft.util.emojicon.emotionicon;

import java.io.Serializable;

public class EmotionIcon implements Serializable {

    private static final long serialVersionUID = 6703672015108581251L;
    private String name;
    private String resource;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getResource() {
	return resource;
    }

    public void setResource(String resource) {
	this.resource = resource;
    }

    @Override
    public String toString() {
	return "EmotionIcon [name=" + name + ", resource=" + resource + "]";
    }

}
