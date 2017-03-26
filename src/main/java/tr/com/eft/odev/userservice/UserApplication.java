package tr.com.eft.odev.userservice;

import org.flywaydb.core.Flyway;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import tr.com.eft.odev.userservice.api.AuthUser;
import tr.com.eft.odev.userservice.api.User;
import tr.com.eft.odev.userservice.auth.UserAuthenticator;
import tr.com.eft.odev.userservice.auth.UserAuthorizer;
import tr.com.eft.odev.userservice.config.UserConfiguration;
import tr.com.eft.odev.userservice.filter.LogFilter;
import tr.com.eft.odev.userservice.health.UserHealthCheck;
import tr.com.eft.odev.userservice.repository.UserDAO;
import tr.com.eft.odev.userservice.resources.UserResource;

public class UserApplication extends Application<UserConfiguration> {

	public static void main(String[] args) throws Exception {
		new UserApplication().run(args);
	}

	private final HibernateBundle<UserConfiguration> hibernateBundle = new HibernateBundle<UserConfiguration>(
			User.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(UserConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	private final MigrationsBundle<UserConfiguration> migrationBundle = new MigrationsBundle<UserConfiguration>() {
		@Override
		public DataSourceFactory getDataSourceFactory(UserConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public String getName() {
		return "userservice";
	}

	@Override
	public void initialize(Bootstrap<UserConfiguration> bootstrap) {
		bootstrap.addBundle(hibernateBundle);
		bootstrap.addBundle(migrationBundle);
	}

	@Override
	public void run(UserConfiguration conf, Environment env) throws Exception {
		initDbShema(conf);
		final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
		env.healthChecks().register("user", new UserHealthCheck());
		env.jersey().register(new UserResource(userDAO));

		env.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<AuthUser>()
				.setAuthenticator(new UserAuthenticator()).setAuthorizer(new UserAuthorizer()).buildAuthFilter()));
		env.jersey().register(new AuthValueFactoryProvider.Binder<>(AuthUser.class));
		env.jersey().register(RolesAllowedDynamicFeature.class);

		env.jersey().register(new LogFilter());
	}

	private void initDbShema(UserConfiguration conf) {
		DataSourceFactory dataSourceFactory = conf.getDataSourceFactory();
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSourceFactory.getUrl(), dataSourceFactory.getUser(), dataSourceFactory.getPassword());
		flyway.migrate();
	}

}
