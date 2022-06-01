package acme.components.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

import acme.framework.configuration.TilesConfiguration;

@Configuration
@Primary
public class CustomTilesConfiguration extends TilesConfiguration{

	@Override
	@Bean
	@Primary
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer result;
		String[] definitions;

		definitions = new String[] { //			
			"classpath*:/WEB-INF/views/**/tiles.xml" //
		};

		result = new TilesConfigurer();
		result.setCheckRefresh(true);
		result.setDefinitions(definitions);

		return result;
	}
}
