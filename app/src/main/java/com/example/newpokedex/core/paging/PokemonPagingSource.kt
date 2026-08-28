package com.example.newpokedex.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newpokedex.core.domain.model.Pokemon
import com.example.newpokedex.pokemon_list_feature.domain.source.PokemonRemoteDataSource

class PokemonPagingSource(
    private val remoteDataSource: PokemonRemoteDataSource
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(PAGE_SIZE) ?: page?.nextKey?.minus(PAGE_SIZE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val offset = params.key ?: 0
        return try {
            val pokemons = remoteDataSource.getPokemons(limit = params.loadSize, offset = offset)
            LoadResult.Page(
                data = pokemons,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (pokemons.isEmpty()) null else offset + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}