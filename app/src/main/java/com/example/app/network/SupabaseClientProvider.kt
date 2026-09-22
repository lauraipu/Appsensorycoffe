package com.example.app.network

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

object SupabaseClientProvider {

    private const val SUPABASE_URL = "https://pjcvoqnsrifdvvuxaweb.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_eBj5zx2gt1HKpr9sQAw_VQ_NO7c6GTR" // Tu clave pública completa

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(Realtime)
        install(Auth)
    }
}