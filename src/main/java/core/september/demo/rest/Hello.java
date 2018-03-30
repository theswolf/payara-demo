package core.september.demo.rest;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.security.Principal;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@ApplicationScoped
@Path("helloCtrl")
public class Hello {

    @Inject
    private Principal principal;

    @GET
    @Path("hello")
    public String hello() {
        return "hello";
    }

    @GET
    @RolesAllowed({"architect"})
    @Path("protected")
    @Produces(TEXT_PLAIN)
    public String protectedResource() {
        return
                "This is a protected resource \n" +
                        "web username: " + principal.getName() + "\n";
    }
}
