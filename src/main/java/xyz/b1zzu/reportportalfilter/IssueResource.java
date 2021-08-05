package xyz.b1zzu.reportportalfilter;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.vertx.core.json.Json;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.client.exception.ResteasyWebApplicationException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.JobAttributes;

@Path("/issue")
public class IssueResource {

    @Inject
    Logger log;

    @Inject
    @RestClient
    ReportPortalService reportPortalService;

    @Inject
    Template issue;


    @ConfigProperty(name = "reportportal.url")
    String url;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{projectName}/{filterId}/{ticketId}")
    public TemplateInstance issue(@PathParam String projectName,
                                  @PathParam String filterId,
                                  @PathParam String ticketId) {

        var data = reportPortalService.getTestItem(projectName, filterId, 168, ticketId);
//        data.content.get(0).getEndTime()
//        projectName.replace()
        return issue
            .data("issue", ticketId)
            .data("filter", filterId)
            .data("project", projectName)
            .data("rpurl", url)
            .data("data", data);
    }
}