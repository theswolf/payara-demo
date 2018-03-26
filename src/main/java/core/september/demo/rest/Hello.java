package core.september.demo.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Stateless
@Path("helloCtrl")
public class Hello {


    @GET
    @Path("hello")
    public String hello() {
        return "hello";
    }
}
