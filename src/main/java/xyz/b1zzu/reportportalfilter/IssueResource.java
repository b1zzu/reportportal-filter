package xyz.b1zzu.reportportalfilter;

import com.epam.ta.reportportal.ws.model.TestItemResource;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

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
    @Path("/{projectName}/{filterId}/{ticketId}")
    public Response issueRedirect(@PathParam String projectName,
                                  @PathParam String filterId,
                                  @PathParam String ticketId) {

        var filter = reportPortalService.getFilter(projectName, filterId);

        var redirect = UriBuilder.fromPath("/issue")
            .queryParam("project", projectName)
            .queryParam("filter", filter.getName())
            .queryParam("issue", ticketId)
            .build();

        log.infof("URL: %s", redirect.toString());
        return Response.temporaryRedirect(redirect).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/")
    public TemplateInstance issue(@QueryParam("project") String projectName,
                                  @QueryParam("filter") String filterId,
                                  @QueryParam("issue") String ticketId) {

        Page<TestItemResource> data;

        String filterID = "";

        if (isBlank(projectName)
            || isBlank(filterId)
            || isBlank(ticketId)) {

            data = new Page<>();
        } else {
            var filter = reportPortalService.getFilterByName(
                    projectName,
                    filterId,
                    0, 1,
                    List.of())
                .content.get(0);

            filterID = filter.getFilterId().toString();

            data = reportPortalService.getTestItem(
                projectName,
                filter.getFilterId().toString(),
                168,
                ticketId,
                0, 100,
                List.of("startTime,desc"));
        }

        return issue
            .data("issue", ticketId)
            .data("filter", filterId)
            .data("filterid", filterID)
            .data("project", projectName)
            .data("rpurl", url)
            .data("data", data);
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}