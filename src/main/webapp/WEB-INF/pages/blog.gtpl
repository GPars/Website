<% include '/WEB-INF/includes/header.gtpl' %>

    <%
        log.info "outputing the blog attribute"
    %>
<%= request.getAttribute('blog') %>    
<% include '/WEB-INF/includes/footer.gtpl' %>

