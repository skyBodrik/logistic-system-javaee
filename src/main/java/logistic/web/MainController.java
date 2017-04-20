package logistic.web;

import org.jtwig.web.servlet.JtwigRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bodrik on 20.04.17.
 */
public class MainController extends HttpServlet implements iController {
    protected final JtwigRenderer pageTemplate = JtwigRenderer.defaultRenderer();

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected Map<String, Object> dataTemplate = new HashMap<>();

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.request = req;
        this.response = resp;
        this.run();
    }

    public void run() throws ServletException, IOException {
        // Nothing todo
    }

    protected void renderTemplate(String path) throws ServletException, IOException {
        pageTemplate.dispatcherFor(path)
            .with(dataTemplate)
            .render(this.request, this.response);
    }
}
