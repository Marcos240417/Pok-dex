package com.example.newpokedex.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newpokedex.core.dao.PokemonDao
import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.core.data.local.entity.PokemonEvolutionEntity
import com.example.newpokedex.core.data.local.entity.PokemonMoveEntity
import com.example.newpokedex.core.data.local.entity.PokemonStatEntity

/**
 * @Database: Esta anotação define as configurações do banco.
 * entities: Lista todas as tabelas que o banco deve criar.
 * version: O número da versão. Se você mudar a estrutura das tabelas no futuro,
 * deverá aumentar este número para o Room saber que houve uma mudança.
 * exportSchema: Quando true, gera um arquivo JSON com a estrutura do banco (útil para histórico).
 */
@Database(
    entities = [
        PokemonEntity::class,
        PokemonStatEntity::class,
        PokemonMoveEntity::class,
        PokemonEvolutionEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    /**
     * Aqui declaramos os DAOs. O Room vai implementar essas funções para nós.
     * É através deste método que o Repository terá acesso às funções de busca e inserção.
     */
    abstract fun pokemonDao(): PokemonDao

    companion object {
        /**
         * @Volatile: Garante que o valor da variável 'INSTANCE' esteja sempre atualizado
         * para todas as threads do processador, evitando erros de leitura.
         */
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        /**
         * getDatabase: Função que cria ou retorna o banco de dados.
         * synchronized(this): Garante que duas partes do app não tentem criar o banco
         * ao mesmo tempo em threads diferentes.
         */
        fun getDatabase(context: Context): PokemonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemon_database" // O nome do arquivo físico no celular
                )
                    /**
                     * fallbackToDestructiveMigration: Se você mudar a versão do banco sem
                     * criar um plano de migração, o Room apagará os dados antigos e criará
                     * novos. Útil durante o desenvolvimento.
                     */
                    .fallbackToDestructiveMigration(false)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}