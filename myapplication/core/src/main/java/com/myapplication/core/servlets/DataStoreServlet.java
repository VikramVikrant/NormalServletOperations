package com.myapplication.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import com.myapplication.core.models.UserModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/submitdata2" })
public class DataStoreServlet extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException
    {
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource resource = resourceResolver
                .getResource("/content/myapplication/us/en/collectdata/jcr:content/root/container/container/datastore");
        UserModel model = resource.adaptTo(UserModel.class);
        response.getWriter()
                .print("Data stored in the resource is:\nFirst Name: " + model.getFirstName() + "\nLast Name: "
                        + model.getLastName() + "\nGender: " + model.getGender() + "\nCountry: "
                        + model.getCountry());
        resourceResolver.close();
    }
}
