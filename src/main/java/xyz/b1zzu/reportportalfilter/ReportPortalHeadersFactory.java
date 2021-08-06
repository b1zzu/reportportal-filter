package xyz.b1zzu.reportportalfilter;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

public class ReportPortalHeadersFactory implements ClientHeadersFactory {

    @ConfigProperty(name = "reportportal.token")
    String token;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> clientOutgoingHeaders) {

        MultivaluedMap<String, String> result = new MultivaluedMapImpl<>();
        result.add(HttpHeaders.AUTHORIZATION, "bearer " + token);
        return result;
    }
}
