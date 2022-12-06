package com.myapplication.core.servlets;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.myapplication.core.models.ResolverUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

@Component(
        service = { Servlet.class },
        property = {
                SLING_SERVLET_RESOURCE_TYPES + "=/apps/my/type",
                SLING_SERVLET_METHODS + "=GET",
                SLING_SERVLET_EXTENSIONS + "=html",
                SLING_SERVLET_SELECTORS + "=hello",
        }
)
public class NormalServlet extends SlingAllMethodsServlet {
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            String title = resourceResolver.getResource("/content/RollNumberNodes/1705758").getValueMap()
                    .get("Fullname", String.class);
            response.setContentType("text/plain");
            response.getWriter().write(title);
        }
        catch (LoginException e) {
            throw new RuntimeException(e);
        } catch (org.apache.sling.api.resource.LoginException e) {
            throw new RuntimeException(e);
        }

//        response.getWriter().write("<h1>this is my servlet</h1>");
//        response.setContentType("text/plain");
//        Resource resource = request.getResource();
//        String title = resource.getValueMap().get("jcr:title", String.class);
//        title += " appended from servlet";
//        response.getWriter().write(title);
//        response.setContentType("text/plain");
    }

}
