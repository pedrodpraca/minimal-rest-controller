package br.uva.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author csiqueira
 */
@Component
public class AllowCrossDomainAjax extends OncePerRequestFilter {

	private static final String BASE_PATH = "/";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = request.getServletPath();
        if (path.matches("^" + BASE_PATH + "/.*$")) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        }
        chain.doFilter(request, response);
    }
}
