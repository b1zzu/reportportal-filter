package xyz.b1zzu.reportportalfilter;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HomeResource {

    @Inject
    Logger log;

    @Inject
    @RestClient
    ReportPortalService reportPortalService;

    @Inject
    Template home;


    @ConfigProperty(name = "reportportal.url")
    String url;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/")
    public TemplateInstance home() {
        return home.instance();
    }
}