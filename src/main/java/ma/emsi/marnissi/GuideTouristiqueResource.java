package ma.emsi.marnissi.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/guide")
public class GuideTouristiqueResource {

    /**
     * Retourne un tableau JSON contenant le nom de la ville
     * ou du pays fourni dans l'URL.
     *
     * @param villeOuPays nom de la ville ou du pays
     * @return tableau contenant le nom du lieu
     */
    @GET
    @Path("lieu/{ville_ou_pays}")
    @Produces(MediaType.APPLICATION_JSON)
    public String[] endroitsDeVisite(
            @PathParam("ville_ou_pays") String villeOuPays) {

        return new String[]{villeOuPays};
    }
}
