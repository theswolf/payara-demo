package core.september.demo;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({"architect", "bar", "kaz"})
@ApplicationPath("rest")
public class DemoApp extends Application {
}
