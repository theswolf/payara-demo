import core.september.demo.DemoApp;
import core.september.demo.rest.Hello;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class TestRest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(DemoApp.class, Hello.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }




    @Test
    @RunAsClient
    public void testGet(
            @ArquillianResteasyResource final WebTarget webTarget)
    {
        final Response response = webTarget
                .path("/helloCtrl/hello")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals("hello", response.readEntity(String.class));
    }

}
