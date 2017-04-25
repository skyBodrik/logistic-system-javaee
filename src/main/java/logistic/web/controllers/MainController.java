package logistic.web.controllers;

import logistic.web.facade.iFacade;
import logistic.web.iStorage;
import logistic.web.models.User;
import org.jtwig.web.servlet.JtwigRenderer;

import javax.enterprise.context.RequestScoped;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Optional;

import javax.ejb.*;
import javax.servlet.http.HttpSession;

/**
 * Created by bodrik on 20.04.17.
 */
@Stateful
public class MainController extends HttpServlet implements iController {
    @EJB
    protected iFacade userFacade;

    protected HttpSession session;

    protected final JtwigRenderer pageTemplate = JtwigRenderer.defaultRenderer(); //new JtwigRenderer(new FixedEnvironmentConfiguration());

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    @EJB
    protected iStorage dataTemplate;

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.request = req;
        this.response = resp;
        this.session = this.request.getSession(true);
        this.request.setCharacterEncoding("UTF-8");
        this.response.setContentType("text/html;charset=utf-8");
        String methodName = "indexPage";
        if (request.getServletPath() != null) {
            String[] chunks = request.getServletPath().split("/");
            if (chunks.length > 2) {
                methodName = chunks[chunks.length - 1] + "Page";
            }
        }

        // Достаём объекты из EJB контейнера
        try {
            InitialContext context = new InitialContext();
            this.dataTemplate = (iStorage) context.lookup("java:module/RequestStorage");

            if (this.session.isNew()) {
                this.userFacade = (iFacade) context.lookup("java:module/UserFacade");
                context.bind("facade" + this.session.getId(), this.userFacade);
            } else {
                this.userFacade = (iFacade) context.lookup("facade" + this.session.getId());
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

        /* Теперь можно пользоваться EJB объектами! */

        this.dataTemplate.getStorage().put("currentUser", this.userFacade.getLoggedUser());

        this.dataTemplate.getStorage().put("sid", this.session.getId());

        this.dataTemplate.getStorage().put("documentFolderPath", Optional.of(request.getContextPath()).orElse(""));

        //if (checkAuthorization()) {
            //this.dataTemplate.getStorage().put("authorizationStatus", true);
        if (checkAuthorization() && this.getClass().getSimpleName().equals("SignInController")) {
            String servletName = "error";
            switch (this.userFacade.getLoggedUser().getType()) {
                case User.TYPE_CLIENT:
                    servletName = "client";
                    break;
                case User.TYPE_OPERATOR:
                    servletName = "operator";
                    break;
                case User.TYPE_CARRIER:
                    servletName = "carrier";
                    break;
            }
            response.sendRedirect((String)this.dataTemplate.getStorage().get("documentFolderPath") + "/" + servletName);
        }
        // Запуск контроллера
        if (!this.checkAndInvoke(methodName)) {
            renderTemplate("error.html");
        };
    }

    /**
     * Вызываем метод класса
     * @return результат вызова, или false в случае ошибки
     */
    protected boolean checkAndInvoke(String methodName) {
        try {
            return (Boolean)this.getClass().getMethod(methodName).invoke(this);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean indexPage() throws ServletException, IOException {
        // Nothing todo
        return false;
    }

    protected void renderTemplate(String path) throws ServletException, IOException {
        pageTemplate.dispatcherFor("/views/" + path)
            .with(this.dataTemplate.getStorage())
                .render(this.request, this.response);
    }

    protected boolean checkAuthorization() throws ServletException, IOException {
        if (this.userFacade.getLoggedUser() != null) {
            return true;
        }
        String email = Optional.ofNullable(request.getParameter("email")).orElse(null);
        String password = Optional.ofNullable(request.getParameter("password")).orElse(null);
        if (email != null && password != null) {
            User user = this.userFacade.login(email, password);
            if (user != null) {
                return true;
            } else {
                this.dataTemplate.getStorage().put("statusMessage", "Имя пользователя или пароль неверны!");
            }
        }
        return false;
    }
}
