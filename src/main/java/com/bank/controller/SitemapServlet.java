package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sitemap.xml")
public class SitemapServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/xml; charset=UTF-8");

        String baseUrl = "https://skminibank.onrender.com";

        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">"

                        + "<url>"
                        + "<loc>" + baseUrl + "/</loc>"
                        + "<changefreq>daily</changefreq>"
                        + "<priority>1.0</priority>"
                        + "</url>"

                        + "<url>"
                        + "<loc>" + baseUrl + "/splash.jsp</loc>"
                        + "<changefreq>weekly</changefreq>"
                        + "<priority>0.8</priority>"
                        + "</url>"

                        + "<url>"
                        + "<loc>" + baseUrl + "/about.jsp</loc>"
                        + "<changefreq>monthly</changefreq>"
                        + "<priority>0.6</priority>"
                        + "</url>"

                        + "<url>"
                        + "<loc>" + baseUrl + "/contact.jsp</loc>"
                        + "<changefreq>monthly</changefreq>"
                        + "<priority>0.6</priority>"
                        + "</url>"

                        + "<url>"
                        + "<loc>" + baseUrl + "/login.jsp</loc>"
                        + "<changefreq>monthly</changefreq>"
                        + "<priority>0.5</priority>"
                        + "</url>"

                        + "<url>"
                        + "<loc>" + baseUrl + "/register.jsp</loc>"
                        + "<changefreq>monthly</changefreq>"
                        + "<priority>0.8</priority>"
                        + "</url>"

                        + "</urlset>";

        response.getWriter().write(xml);
    }
}