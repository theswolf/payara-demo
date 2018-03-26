import core.september.demo.DemoApp;
import core.september.demo.rest.Hello;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class TestIntegration {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(DemoApp.class, Hello.class);
                //.addAsWebInfResource(INSTANCE, "beans.xml");
    }


    @EJB
    private Hello hello;


    @Test
    public void testEjb()
    {
       assertNotNull("Hello should be instanced",hello);
    }

}
