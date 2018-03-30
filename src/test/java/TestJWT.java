import core.september.demo.DemoApp;
import core.september.demo.rest.Hello;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.NotAuthorizedException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static junit.framework.TestCase.assertTrue;

@RunWith(Arquillian.class)
public class TestJWT {

    @ArquillianResource
    private URL base;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addClasses(DemoApp.class, Hello.class)
                .addAsResource("payara-mp-jwt.properties")
                .addAsResource("publicKey.pem");
        //.addAsWebInfResource(INSTANCE, "beans.xml");
    }

    @Test
    @RunAsClient
    public void testProtectedResourceLoggedin() throws Exception {

        URI target = URI.create(new URL(base, "rest/helloCtrl/protected").toExternalForm());
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Target: " + target);
        System.out.println("-------------------------------------------------------------------------");


        String response =
                newClient()
                        .target(target)
                        .request(TEXT_PLAIN)
                        .header(AUTHORIZATION, "Bearer " + JWTTestUtils.generateJWTString("jwt-token.json"))
                        .get(String.class);

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Response: " + response);
        System.out.println("-------------------------------------------------------------------------");

        // Now has to be logged-in so page is accessible
        assertTrue(
                "Should have been authenticated, but could not access protected resource",
                response.contains("This is a protected resource")
        );

    }

    @Test
    @RunAsClient
    public void testProtectedResourceNotLoggedin() {

        URI target = null;
        try {
            target = URI.create(new URL(base, "rest/helloCtrl/protected").toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Target: " + target);
        System.out.println("-------------------------------------------------------------------------");


        try {
            String response =
                    newClient()
                            .target(target)
                            .request(TEXT_PLAIN)
                            //.header(AUTHORIZATION, "Bearer " + JWTTestUtils.generateJWTString("jwt-token.json"))
                            .get(String.class);
        } catch (NotAuthorizedException nae) {
            assertTrue(
                    "Should return a 401",
                    nae.getMessage().contains("HTTP 401")
            );
        }


        /*System.out.println("-------------------------------------------------------------------------");
        System.out.println("Response: " + response);
        System.out.println("-------------------------------------------------------------------------");

        // Now has to be logged-in so page is accessible
        assertFalse(
                "Should have been authenticated, but could not access protected resource",
                response.contains("This is a protected resource")
        );*/

    }
}
