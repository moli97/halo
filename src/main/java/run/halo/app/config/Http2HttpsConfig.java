package run.halo.app.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "config", name = "http2https", havingValue = "true")
public class Http2HttpsConfig {

	@Value("${http.port}")
	private Integer httpPort;
	@Value("${server.port}")
	private Integer serverPort;

	@Bean
	public ServletWebServerFactory servletContainer() {

		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {//1

			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(httpConnector());
		tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
		return tomcat;
	}

	@Bean
	public Connector httpConnector() {//2
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(httpPort);
		connector.setSecure(false);
		connector.setRedirectPort(serverPort);
		return connector;
	}
}
