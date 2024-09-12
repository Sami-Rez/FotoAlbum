package at.spengergasse.FotoAlbum.presentation.www;

public interface RedirectForwardSupport {

    default String redirect(String route) {
        // checks on the route
        return "redirect:%s".formatted(route);
    }

    default String forward(String route) {
        // checks on the route
        return "forward:%s".formatted(route);
    }
}
