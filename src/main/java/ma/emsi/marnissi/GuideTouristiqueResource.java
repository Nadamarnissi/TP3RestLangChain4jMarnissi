
package ma.emsi.marnissi.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ma.emsi.marnissi.llm.LlmClient;

@Path("/guide")
public class GuideTouristiqueResource {

    @Inject
    private LlmClient llmClient;

    /**
     * Génère un guide touristique JSON pour une ville ou un pays.
     *
     * @param villeOuPays nom de la ville ou du pays
     * @return informations touristiques générées par Gemini
     */
    @GET
    @Path("lieu/{ville_ou_pays}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endroitsDeVisite(
            @PathParam("ville_ou_pays") String villeOuPays) {

        if (villeOuPays == null || villeOuPays.isBlank()) {
            throw new WebApplicationException(
                    "Le lieu est obligatoire.",
                    Response.Status.BAD_REQUEST
            );
        }

        try {
            // Le modèle retourne un document JSON déjà généré.
            String json = llmClient.endroitsDeVisite(villeOuPays);

            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (RuntimeException e) {
            // Ne pas exposer les détails internes de Gemini au client REST.
            throw new WebApplicationException(
                    "Impossible de générer le guide touristique.",
                    Response.Status.BAD_GATEWAY
            );
        }
    }
}
