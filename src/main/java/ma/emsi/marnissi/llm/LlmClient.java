
package ma.emsi.marnissi.llm;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LlmClient {

    private final GuideTouristique assistant;

    public LlmClient() {
        // La clé est fournie au serveur, jamais enregistrée dans Git.
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

        // Chaque requête REST est indépendante :
        // aucune mémoire conversationnelle partagée entre utilisateurs.
        assistant = AiServices.create(GuideTouristique.class, model);
    }

    public String endroitsDeVisite(String villeOuPays) {
        if (villeOuPays == null || villeOuPays.isBlank()) {
            throw new IllegalArgumentException("Le lieu est obligatoire.");
        }

        return assistant.chat(villeOuPays);
    }
}
