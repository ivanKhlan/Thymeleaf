import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(value = "/time/*")
public class ThymeleafTestController extends HttpServlet {

    private TemplateEngine engine;

    @Override
    public void init() {

        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:\\Users\\Laptop\\IdeaProjects\\Thymeleaf\\templates\\");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        String time = TimezoneFormatter.getTime(req, resp);

        Context simpleContext = new Context(req.getLocale(),
                Map.of("name", time)
        );

        engine.process("test", simpleContext, resp.getWriter());
        resp.getWriter().close();

    }
}
