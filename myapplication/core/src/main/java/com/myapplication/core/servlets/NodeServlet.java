package com.myapplication.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.servlet.Servlet;

import java.io.IOException;


@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths=" + "/bin/submitdata" })
public class NodeServlet extends SlingAllMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(NodeServlet.class);

    @Override
    protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) {
        try {
            ResourceResolver resourceResolver = req.getResourceResolver();
            Resource resource = resourceResolver.getResource("/content/RollNumberNodes");
            log.info("Resource is at path {}", resource.getPath());
            Node node = resource.adaptTo(Node.class);
            Node newNode = node.addNode(getNodeName(req, "NodeModel"), "nt:unstructured");
            newNode.setProperty("Fullname", getRequestParameter(req, "Fullname"));

            newNode.setProperty("age", getRequestParameter(req, "age"));
            newNode.setProperty("rollNo", getRequestParameter(req, "rollNo"));

//            resp.getWriter().write("Form submitted");
            resp.sendRedirect("/content/myapplication/us/en/form.html");
            resourceResolver.commit();

        } catch (LockException e) {
            throw new RuntimeException(e);
        } catch (ItemExistsException e) {
            throw new RuntimeException(e);
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException(e);
        } catch (ValueFormatException e) {
            throw new RuntimeException(e);
        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRequestParameter(SlingHttpServletRequest request, String s) {
        String var = request.getParameter(s);
        return var;
    }

    public static String getNodeName(SlingHttpServletRequest request, String s) {

        String rollNo = request.getParameter("rollNo");

        String UserNodeName = rollNo;
        return UserNodeName;
    }
}
