package com.moneyManager.moneylens.Ai_Handling

import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiService @Inject constructor() {

    private val model: GenerativeModel by lazy {
        Firebase.ai(
            backend = GenerativeBackend.googleAI()
        ).generativeModel(
            modelName = "gemini-3.5-flash-lite"        )
    }

    suspend fun generateResponse(prompt: String): String {
        return runCatching {
            val response = model.generateContent(prompt)
            response.text ?: throw IllegalStateException("Empty Gemini response")
        }.getOrElse { e ->
            throw IllegalStateException(
                e.localizedMessage ?: "Failed to generate Gemini response"
            )
        }
    }
}