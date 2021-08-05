package xyz.b1zzu.reportportalfilter;

import com.epam.ta.reportportal.ws.model.TestItemResource;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/v1")
@RegisterRestClient
@RegisterClientHeaders(ReportPortalHeadersFactory.class)
public interface ReportPortalService {

    @GET
    @Path("/{projectName}/item")
    Page<TestItemResource> getTestItem(@PathParam String projectName,
                                       @QueryParam("filterId") String filterId,
                                       @QueryParam("launchesLimit") int launchesLimit,
                                       @QueryParam("filter.has.ticketId") String hasTicketId);
}

