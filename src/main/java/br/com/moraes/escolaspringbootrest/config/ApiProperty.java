package br.com.moraes.escolaspringbootrest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("api")
public class ApiProperty {

	private final Swagger swagger = new Swagger();
	
	public static class Swagger {
		private boolean show = Boolean.TRUE;

		public boolean isShow() {
			return show;
		}
		public void setShow(boolean show) {
			this.show = show;
		}
	}

	public Swagger getSwagger() {
		return swagger;
	}
}
