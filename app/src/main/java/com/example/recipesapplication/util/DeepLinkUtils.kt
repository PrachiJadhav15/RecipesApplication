package com.example.recipesapplication.util

import android.net.Uri
import java.net.URLDecoder

object DeepLinkUtils {

    private val IGNORED_WORDS = setOf(
        "recipe", "recipes", "open", "details", "view", "index", "home", "http", "https", "com", "www"
    )

    /**
     * Extracts a recipe ID from a given Uri.
     * Supports extraction from:
     * - Query parameters (`id`, `recipeId`, `i`)
     * - Path segments (e.g., `/recipe/52893` or `/52893`)
     * - Host segment for custom schemes (e.g., `recipe://52893`)
     */
    fun extractRecipeId(data: Uri?): String? {
        if (data == null) return null
        return try {
            extractFromUri(data) ?: extractFromString(data.toString())
        } catch (_: Throwable) {
            extractFromString(data.toString())
        }
    }

    /**
     * Helper overload to extract recipe ID from a URI string representation.
     */
    fun extractRecipeId(uriString: String?): String? {
        if (uriString.isNullOrBlank()) return null
        return extractFromString(uriString)
    }

    private fun extractFromUri(data: Uri): String? {
        // 1. Query parameters: id, recipeId, i
        val idFromQuery = data.getQueryParameter("id")?.trim()?.takeIf { it.isNotBlank() }
            ?: data.getQueryParameter("recipeId")?.trim()?.takeIf { it.isNotBlank() }
            ?: data.getQueryParameter("i")?.trim()?.takeIf { it.isNotBlank() }
        if (idFromQuery != null) {
            return decodeIfNeeded(idFromQuery)
        }

        // 2. Path segments
        val segments = data.pathSegments
        if (!segments.isNullOrEmpty()) {
            val recipeIndex = segments.indexOfFirst {
                it.equals("recipe", ignoreCase = true) || it.equals("recipes", ignoreCase = true)
            }
            if (recipeIndex != -1 && recipeIndex + 1 < segments.size) {
                val nextSegment = segments[recipeIndex + 1].trim()
                if (nextSegment.isNotBlank() && nextSegment.lowercase() !in IGNORED_WORDS) {
                    return decodeIfNeeded(nextSegment)
                }
            }

            val lastSegment = segments.lastOrNull()?.trim()
            if (!lastSegment.isNullOrBlank() && lastSegment.lowercase() !in IGNORED_WORDS) {
                return decodeIfNeeded(lastSegment)
            }
        }

        // 3. Host segment for custom schemes (e.g. recipe://52893)
        val host = data.host?.trim()
        if (!host.isNullOrBlank() && !host.contains(".") && host.lowercase() !in IGNORED_WORDS) {
            return decodeIfNeeded(host)
        }

        return null
    }

    private fun extractFromString(uriString: String?): String? {
        if (uriString.isNullOrBlank()) return null

        val trimmed = uriString.trim()

        // 1. Query parameters: id, recipeId, i
        val queryStartIndex = trimmed.indexOf('?')
        if (queryStartIndex != -1 && queryStartIndex < trimmed.length - 1) {
            val queryString = trimmed.substring(queryStartIndex + 1)
            val queryParams = parseQueryParams(queryString)
            val idVal = queryParams["id"]?.trim()?.takeIf { it.isNotBlank() }
                ?: queryParams["recipeid"]?.trim()?.takeIf { it.isNotBlank() }
                ?: queryParams["i"]?.trim()?.takeIf { it.isNotBlank() }
            if (idVal != null) {
                return decodeIfNeeded(idVal)
            }
        }

        // Separate path/host part from query string and fragment
        val mainPart = if (queryStartIndex != -1) {
            trimmed.substring(0, queryStartIndex)
        } else {
            val fragmentIndex = trimmed.indexOf('#')
            if (fragmentIndex != -1) trimmed.substring(0, fragmentIndex) else trimmed
        }

        // Check for scheme
        val schemeEnd = mainPart.indexOf("://")
        val scheme = if (schemeEnd != -1) mainPart.substring(0, schemeEnd).lowercase() else ""
        val rest = if (schemeEnd != -1) mainPart.substring(schemeEnd + 3) else mainPart

        // Path segments
        val segments = rest.split("/").filter { it.isNotBlank() }

        if (segments.isNotEmpty()) {
            val hasDomainHost = segments.first().contains(".") || scheme == "http" || scheme == "https"

            val recipeIndex = segments.indexOfFirst {
                it.equals("recipe", ignoreCase = true) || it.equals("recipes", ignoreCase = true)
            }
            if (recipeIndex != -1 && recipeIndex + 1 < segments.size) {
                val nextSegment = segments[recipeIndex + 1].trim()
                if (nextSegment.isNotBlank() && nextSegment.lowercase() !in IGNORED_WORDS) {
                    return decodeIfNeeded(nextSegment)
                }
            }

            val pathSegments = if (hasDomainHost && segments.size > 1) {
                segments.subList(1, segments.size)
            } else if (!hasDomainHost) {
                segments
            } else {
                emptyList()
            }

            val lastSegment = pathSegments.lastOrNull()?.trim()
            if (!lastSegment.isNullOrBlank() && lastSegment.lowercase() !in IGNORED_WORDS) {
                return decodeIfNeeded(lastSegment)
            }

            if (!hasDomainHost && scheme.isNotBlank() && segments.size == 1) {
                val hostOrFirst = segments.first().trim()
                if (hostOrFirst.isNotBlank() && hostOrFirst.lowercase() !in IGNORED_WORDS) {
                    return decodeIfNeeded(hostOrFirst)
                }
            }
        }

        return null
    }

    private fun parseQueryParams(queryString: String): Map<String, String> {
        val map = mutableMapOf<String, String>()
        val pairs = queryString.split("&")
        for (pair in pairs) {
            val parts = pair.split("=", limit = 2)
            if (parts.isNotEmpty()) {
                val key = parts[0].lowercase().trim()
                val value = if (parts.size > 1) parts[1].trim() else ""
                map[key] = value
            }
        }
        return map
    }

    private fun decodeIfNeeded(value: String): String {
        return if (value.contains("%")) {
            try {
                URLDecoder.decode(value, "UTF-8")
            } catch (_: Exception) {
                value
            }
        } else {
            value
        }
    }
}
