package com.crowdstore.test.mockcontext;

/**
 * @author fcamblor
 */
public class WebContextLoader extends GenericWebContextLoader {

	public WebContextLoader() {
		super("src/test/resources/META-INF/web-resources", false);
	}

}
