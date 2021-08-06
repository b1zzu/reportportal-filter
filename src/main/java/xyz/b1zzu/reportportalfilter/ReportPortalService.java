package xyz.b1zzu.reportportalfilter;

import com.epam.ta.reportportal.ws.model.TestItemResource;
import com.epam.ta.reportportal.ws.model.filter.UserFilterResource;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/v1")
@RegisterRestClient
@RegisterClientHeaders(ReportPortalHeadersFactory.class)
public interface ReportPortalService {

    @GET
    @Path("/{projectName}/item")
    Page<TestItemResource> getTestItem(@PathParam String projectName,
                                       @QueryParam("filterId") String filterId,
                                       @QueryParam("launchesLimit") int launchesLimit,
                                       @QueryParam("filter.has.ticketId") String hasTicketId,
                                       @QueryParam("page.page") int page,
                                       @QueryParam("page.size") int pageSize,
                                       @QueryParam("page.sort") List<String> pageSort);

    @GET
    @Path("/{projectName}/filter")
    Page<UserFilterResource> getFilterByName(@PathParam String projectName,
                                             @QueryParam("filter.eq.name") String filterName,
                                             @QueryParam("page.page") int page,
                                             @QueryParam("page.size") int pageSize,
                                             @QueryParam("page.sort") List<String> pageSort);

    @GET
    @Path("/{projectName}/filter/{filterId}")
    UserFilterResource getFilter(@PathParam String projectName,
                                 @PathParam String filterId);
}

