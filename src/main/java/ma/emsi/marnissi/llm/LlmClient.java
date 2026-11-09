
package ma.emsi.marnissi.llm;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LlmClient {

    private final GuideTouristique assistant;

    public LlmClient() {
        // La clé Gemini doit être configurée sur le serveur Payara.
        // Elle ne doit jamais être enregistrée dans Git.
        String apiKey = System.getProperty("GEMINI_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "La propriété système GEMINI_KEY est absente.");
        }

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        // Service sans mémoire : les requêtes REST sont indépendantes.
        assistant = AiServices.create(GuideTouristique.class, model);
    }

    /**
     * Génère les informations touristiques pour un lieu.
     *
     * @param villeOuPays nom de la ville ou du pays
     * @param nombre nombre d'endroits touristiques demandés
     * @return document JSON produit par Gemini
     */
    public String endroitsDeVisite(String villeOuPays, int nombre) {

        if (villeOuPays == null || villeOuPays.isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom du lieu est obligatoire.");
        }

        if (nombre < 1 || nombre > 20) {
            throw new IllegalArgumentException(
                    "Le nombre d'endroits doit être compris entre 1 et 20.");
        }

        String demande = """
                Lieu : %s
                Nombre exact d'endroits à visiter : %d
                """.formatted(villeOuPays, nombre);

        return assistant.chat(demande);
    }
}
