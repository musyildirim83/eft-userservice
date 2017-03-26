package tr.com.eft.odev.userservice.filter;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);
	private static final String TID_KEY = "tid";

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		clearMDC();
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String tid = UUID.randomUUID().toString().replace("-", "");
		addToMDC(TID_KEY, tid);

		String path = requestContext.getUriInfo().getAbsolutePath().getPath();
		String method = requestContext.getMethod();
		LOGGER.info("Request path: {} method: {}", path, method);

	}

	private void addToMDC(String key, String val) {
		MDC.remove(key);
		MDC.put(key, val);
	}

	private void clearMDC() {
		MDC.clear();
	}

}
