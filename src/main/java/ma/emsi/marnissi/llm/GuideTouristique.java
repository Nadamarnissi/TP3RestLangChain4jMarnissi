
package ma.emsi.marnissi.llm;

import dev.langchain4j.service.SystemMessage;

public interface GuideTouristique {

    @SystemMessage("""
            Tu es un guide touristique professionnel.
            Pour le lieu demandé, indique exactement les deux
            principaux endroits à visiter et le prix moyen
            d'un repas dans la devise du pays.

            Réponds uniquement avec un document JSON valide,
            sans Markdown, respectant exactement cette structure :
            {
              "ville_ou_pays": "nom de la ville ou du pays",
              "endroits_a_visiter": ["endroit 1", "endroit 2"],
              "prix_moyen_repas": "<prix> <devise du pays>"
            }
            """)
    String chat(String lieu);
}
